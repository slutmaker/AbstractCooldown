package ru.abstractcoder.abstractcooldown.util;

import java.util.StringJoiner;

public class StringUtils {

    private StringUtils() {
    }

    public static String combine(String[] strings, String delimiter, int startIndex) {
        StringJoiner stringJoiner = new StringJoiner(delimiter);
        for (; startIndex < strings.length; startIndex++) {
            stringJoiner.add(strings[startIndex]);
        }
        return stringJoiner.toString();
    }

    public static String combine(String[] strings, String delimiter) {
        return combine(strings, delimiter, 0);
    }

    public static String combine(String[] strings) {
        return combine(strings, " ");
    }

}