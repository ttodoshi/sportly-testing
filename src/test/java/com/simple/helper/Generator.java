package com.simple.helper;

import java.util.Random;

public class Generator {
    public static String generateRandomString(int length) {
        Random random = new Random();
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = (char) (random.nextInt(26) + 97);
        }
        return new String(chars);
    }
}
