package com.side.daangn.util;

import java.security.SecureRandom;

public class CodeUtil {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate() {
        StringBuilder result = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }
        return result.toString();
    }
}
