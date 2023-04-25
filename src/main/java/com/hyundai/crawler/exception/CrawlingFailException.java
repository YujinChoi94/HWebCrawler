package com.hyundai.crawler.exception;

public class CrawlingFailException extends RuntimeException {
    public CrawlingFailException(String url, String message) {
        super("Failed to crawl a website(" + url + "). Cause: " + message);
    }
}
