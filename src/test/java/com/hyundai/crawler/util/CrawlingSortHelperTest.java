package com.hyundai.crawler.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrawlingSortHelperTest {
    @DisplayName("CustomSortHelper 테스트 : 일반적인 케이스 1")
    @Test
    void test1() {
        Assertions.assertEquals("Aa1B2c3D", CrawlingSortHelper.sort("ABDac123"));
    }

    @DisplayName("CustomSortHelper 테스트 : 일반적인 케이스 2")
    @Test
    void test2() {
        Assertions.assertEquals("Aa1B2C4DdefghIilmtv", CrawlingSortHelper.sort("html124divABCDefgtaBleImg1"));
    }

    @DisplayName("CustomSortHelper 테스트 : 실제 crawl 데이터")
    @Test
    void test3() throws IOException {
        String path = "src/test/resources/com/hyundai/crawler/util/test.txt";
        final var s = Files.readString(Path.of(path));
        Assertions.assertEquals("Aa0Bb1Cc2Dd3Ee4Ff5Gg6Hh7Ii8Jj9KkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz", CrawlingSortHelper.sort(s));
    }

    @DisplayName("CustomSortHelper 테스트 : 숫자로만 이루어진 문자열")
    @Test
    void test4() {
        Assertions.assertEquals("123456", CrawlingSortHelper.sort("61423345"));
    }
}
