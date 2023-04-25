package com.hyundai.crawler.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.hyundai.crawler.config.properties.CrawlProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CrawlingServiceImplTest {
    @Mock
    CrawlProperties crawlProperties;
    @Mock
    AsyncCache asyncCache;

    @InjectMocks
    CrawlingServiceImpl crawlerService;

    @DisplayName("CrawlerServiceImp 테스트 : GET 요청에 성공한다.")
    @Test
    void should_crawl_and_sort() {
        // given
        var targets = List.of("url1", "url2");
        var body = "ThisIsBody1234";

        // when
        when(crawlProperties.getTargets()).thenReturn(targets);
        when(asyncCache.get(anyString(), any(Function.class)))
            .thenReturn(CompletableFuture.completedFuture(body))
            .thenReturn(CompletableFuture.completedFuture(body));

        StepVerifier.create(crawlerService.crawl())
        .expectNext("1B23d4hIiosTy")
        .verifyComplete();
    }
}
