package com.hyundai.crawler.service;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.hyundai.crawler.config.properties.CrawlProperties;
import com.hyundai.crawler.exception.CrawlingFailException;
import com.hyundai.crawler.util.CrawlingSortHelper;
import org.jsoup.Jsoup;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

public class CrawlingServiceImpl implements CrawlingService {

    private final CrawlProperties crawlProperties;
    private AsyncCache<String, String> asyncCache;

    public CrawlingServiceImpl(final CrawlProperties crawlProperties,
                               AsyncCache<String, String> asyncCache) {
        this.crawlProperties = crawlProperties;
        this.asyncCache = asyncCache;
    }

    private Mono<String> asyncCrawl(final String url) {
        return Mono.fromFuture(asyncCache.get(url, this::crawlCore))
            .retryWhen(getRetrySpec());
    }

    private RetryBackoffSpec getRetrySpec() {
        return Retry.backoff(crawlProperties.getRetry().getMax(),
            Duration.ofMillis(crawlProperties.getRetry().getBackoff()))
            .filter(throwable -> throwable instanceof CrawlingFailException);
    }

    private String crawlCore(final String url) {
        try {
            return Jsoup.connect(url).timeout(crawlProperties.getTimeout()).execute().body();
        } catch (IOException e) {
            throw new CrawlingFailException(url, e.getMessage());
        }
    }

    @Override
    public Mono<String> crawl() {
        return Flux.fromIterable(crawlProperties.getTargets())
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
