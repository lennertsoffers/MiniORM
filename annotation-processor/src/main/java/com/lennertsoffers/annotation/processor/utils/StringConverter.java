package com.lennertsoffers.annotation.processor.utils;

public class StringConverter {
    public static String toSnakeCase(String s) {
        if (!s.isEmpty()) {
            StringBuilder sb = new StringBuilder(String.valueOf(Character.toLowerCase(s.charAt(0))));

            for (int i = 1; i < s.length(); i++) {
                char c = s.charAt(i);

                if (Character.isUpperCase(c)) {
                    sb.append('_');
                }

                sb.append(Character.toLowerCase(c));
            }

            return sb.toString();
        }

        return "";
    }
}
