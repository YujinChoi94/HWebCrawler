package com.hyundai.crawler.controller.response;

import lombok.Builder;

public record CrawlResponse (boolean ok,
                             String result) {
    @Builder
    public CrawlResponse {
    }
}
