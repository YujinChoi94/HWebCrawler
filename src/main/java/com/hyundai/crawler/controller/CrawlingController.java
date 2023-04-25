package com.hyundai.crawler.controller;

import com.hyundai.crawler.controller.response.CrawlResponse;
import com.hyundai.crawler.service.CrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/crawl")
public class CrawlingController {
    private final CrawlingService crawlerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<CrawlResponse> crawl() {
        return crawlerService.crawl()
            .map(merge -> CrawlResponse.builder()
                .ok(true)
                .result(merge)
                .build());
    }
}
