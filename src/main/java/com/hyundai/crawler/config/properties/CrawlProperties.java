package com.hyundai.crawler.config.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "crawl")
public class CrawlProperties {
    private RetryProperties retry;
    private int timeout;
    private List<String> targets;

    public CrawlProperties(RetryProperties retry, int timeout, List<String> targets) {
        this.retry = retry;
        this.timeout = timeout;
        this.targets = targets;
    }

    public RetryProperties getRetry() {
        return retry;
    }

    public int getTimeout() {
        return timeout;
    }

    public List<String> getTargets() {
        return targets;
    }

    public static class RetryProperties {
        private int max;
        private int backoff;

        public RetryProperties(int max, int backoff) {
            this.max = max;
            this.backoff = backoff;
        }

        public int getMax() {
            return max;
        }

        public int getBackoff() {
            return backoff;
        }
    }
}