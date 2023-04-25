package com.hyundai.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class HWebCrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HWebCrawlerApplication.class, args);
    }

}
