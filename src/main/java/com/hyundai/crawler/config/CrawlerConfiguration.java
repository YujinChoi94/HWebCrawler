package com.hyundai.crawler.config;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.hyundai.crawler.config.properties.CacheProperties;
import com.hyundai.crawler.config.properties.CrawlProperties;
import com.hyundai.crawler.service.CrawlingServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CrawlerConfiguration {

    @Bean
    public CrawlingServiceImpl crawlerService(CrawlProperties crawlProperties,
                                              AsyncCache<String, String> asyncCache) {
        return new CrawlingServiceImpl(crawlProperties, asyncCache);
    }

    @Bean
    public AsyncCache<String, String> asyncLoadingCache(CacheProperties cacheProperties) {
        return Caffeine.newBuilder()
            .expireAfterWrite(cacheProperties.getExpiry(), TimeUnit.MILLISECONDS)
            .buildAsync();
    }
}
