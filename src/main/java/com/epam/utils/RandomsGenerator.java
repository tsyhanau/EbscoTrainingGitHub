package com.epam.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

public class RandomsGenerator {

    private static final int FIVE = 5;
    private static final String DOT = ".";

    public static String getFileNameWithRandomNameAndDefinedType(String fileType) {
        return getRandomName() + DOT + fileType;
    }

    public static String getRandomName() {
        return getRandomString() + new Date().getTime();
    }

    public static String getRandomString() {
        return RandomStringUtils.randomAlphabetic(FIVE);
    }
}
