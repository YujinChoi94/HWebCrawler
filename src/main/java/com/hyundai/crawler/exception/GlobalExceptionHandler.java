package com.hyundai.crawler.exception;

import java.util.concurrent.TimeoutException;

import com.hyundai.crawler.controller.response.CrawlResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CrawlingFailException.class, TimeoutException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public CrawlResponse handleException(Exception e) {
        return CrawlResponse.builder().result(e.getMessage())
            .ok(false)
            .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CrawlResponse handleGeneralException(Exception e) {
        return CrawlResponse.builder()
            .ok(false)
            .result(e.getMessage())
            .build();
    }
}
