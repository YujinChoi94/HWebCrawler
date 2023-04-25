package com.hyundai.crawler.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cache.duration")
public class CacheProperties {
    private final int expiry;

    public CacheProperties(int expiry) {
        this.expiry = expiry;
    }

    public int getExpiry() {
        return expiry;
    }
}
