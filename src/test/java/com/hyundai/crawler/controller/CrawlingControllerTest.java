package com.hyundai.crawler.controller;

import static org.mockito.Mockito.when;

import com.hyundai.crawler.controller.response.CrawlResponse;
import com.hyundai.crawler.service.CrawlingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(CrawlingController.class)
public class CrawlingControllerTest {
    @MockBean
    private CrawlingService crawlerService;

    @Autowired
    private WebTestClient webTestClient;

    @DisplayName("CrawlController 테스트 : GET 요청에 성공한다.")
    @Test
    void should_return_ok() {
        var result = "thisisresultstring";

        when(crawlerService.crawl()).thenReturn(Mono.just(result));

        webTestClient.get()
            .uri("/v1/crawl")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(CrawlResponse.class)
            .value(response -> {
                Assertions.assertTrue(response.ok());
                Assertions.assertEquals(result, response.result());
            });
    }
}
