package com.hyundai.crawler.config.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("crawl")
public class TargetProperties {

    private final List<String> targets;
    private final int timeout;

    public TargetProperties(List<String> targets, int timeout) {
        this.targets = targets;
        this.timeout = timeout;
    }

    public List<String> getTargets() {
        return targets;
    }

    public int getTimeout() {
        return timeout;
    }
}
