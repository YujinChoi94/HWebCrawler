package com.hyundai.crawler.service;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.hyundai.crawler.config.properties.TargetProperties;
import com.hyundai.crawler.exception.CrawlingFailException;
import com.hyundai.crawler.util.CrawlingSortHelper;
import org.jsoup.Jsoup;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CrawlingServiceImpl implements CrawlingService {
    private final TargetProperties targetProperties;
    private AsyncCache<String, String> asyncCache;

    public CrawlingServiceImpl(final TargetProperties targetProperties,
                               AsyncCache<String, String> asyncCache) {
        this.targetProperties = targetProperties;
        this.asyncCache = asyncCache;
    }

    private Mono<String> asyncCrawl(final String url) {
        return Mono.fromFuture(asyncCache.get(url, this::crawlCore))
            .timeout(Duration.ofSeconds(targetProperties.getTimeout()));
    }

    private String crawlCore(final String url) {
        try {
            return Jsoup.connect(url).execute().body();
        } catch (IOException e) {
            throw new CrawlingFailException(url);
        }
    }

    @Override
    public Mono<String> crawl() {
        return Flux.fromIterable(targetProperties.getTargets())
            .flatMap(this::asyncCrawl)
            .collectList()
            .map(this::merge)
            .map(CrawlingSortHelper::sort);
    }

    private String merge(final List<String> results) {
        StringBuilder sb = new StringBuilder();
        for (String s : results) {
            sb.append(s);
        }
        return sb.toString();
    }
}
