package com.ericho.coupleShare.util;

public class StringUtil {
    public static boolean equal(String a, String b) {
        return (a == null) ? (b == null) : (a.equals(b));
    }
}
