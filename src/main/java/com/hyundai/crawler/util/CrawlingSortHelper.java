package com.hyundai.crawler.util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CrawlingSortHelper {
    private static final int NUMBER_LENGTH = 10;
    private static final int ALPHABET_LENGTH = 27;

    private CrawlingSortHelper() {
    }

    public static String sort(final String body) {
        // body에 등장한 숫자, 알파벳 대문자, 소문자를 넣을 배열을 생성한다.
        Character[] numbers = new Character[NUMBER_LENGTH];
        Character[] lowerCases = new Character[ALPHABET_LENGTH];
        Character[] upperCases = new Character[ALPHABET_LENGTH];

        fillArrayWithOccurrences(body, numbers, lowerCases, upperCases);
        final var numberList = Arrays.stream(numbers)
            .filter(Objects::nonNull).toList();
        return createStringFromArrays(lowerCases, upperCases, numberList);
    }

    private static String createStringFromArrays(Character[] lowerCases, Character[] upperCases, List<Character> numberList) {
        StringBuilder sb = new StringBuilder();

        int j = 0;
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            if (upperCases[i] != null) {
                sb.append(upperCases[i]);
            }

            if (lowerCases[i] != null) {
                sb.append(lowerCases[i]);
            }

            if (j < numberList.size() && numberList.get(j) != null) {
                sb.append(numberList.get(j++));
            }
        }

        return sb.toString();
    }

    private static void fillArrayWithOccurrences(final String body,
                                                 Character[] numbers,
                                                 Character[] lowerCases,
                                                 Character[] upperCases) {

        // 배열이 모두 찬 경우, 더 이상 배열에 값을 추가할 필요가 없다.
        // 따라서 이 상황을 구분하기 위해 각 배열이 꽉 찼는지를 나타내는 변수를 사용한다.
        var numberArraySize = 0;
        var upperCaseArraySize = 0;
        var lowerCaseArraySize = 0;

        for (char c : body.toCharArray()) {
            // 또한, 해당 문자열이 이미 배열에 존재할 경우 배열에 값을 추가할 필요가 없다.
            // 이를 위해 null 체크를 한다.
            if (isDigitAscii(c) && numberArraySize <= NUMBER_LENGTH && numbers[c - '0'] == null) {
                numberArraySize++;
                // 아스키 코드 값을 통해 인덱스를 계산하여 각 배열을 채운다.
                numbers[c - '0'] = c;
            } else if (isUpperCaseAscii(c) && upperCaseArraySize <= ALPHABET_LENGTH && upperCases[c - 'A'] == null) {
                upperCaseArraySize++;
                upperCases[c - 'A'] = c;
            } else if (isLowerCaseAscii(c) && lowerCaseArraySize <= ALPHABET_LENGTH && lowerCases[c - 'a'] == null) {
                lowerCaseArraySize++;
                lowerCases[c - 'a'] = c;
            }
        }
    }

    private static boolean isLowerCaseAscii(char c) {
        return c >= 'a' && c <= 'z';
    }

    private static boolean isUpperCaseAscii(char c) {
        return c >= 'A' && c <= 'Z';
    }

    private static boolean isDigitAscii(char c) {
        return c >= '0' && c <= '9';
    }
}
