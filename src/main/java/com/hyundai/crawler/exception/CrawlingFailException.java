package com.hyundai.crawler.exception;

public class CrawlingFailException extends RuntimeException {
    public CrawlingFailException(String url) {
        super("Failed to crawl a website(\"" + url + "\")");
    }
}
