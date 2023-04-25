package com.hyundai.crawler.service;

import reactor.core.publisher.Mono;

public interface CrawlingService {
    Mono<String> crawl();
}
