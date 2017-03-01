package com.paymentwall.java.utils;

/**
 * Created by hoahieu on 03/01/17.
 */
public class HashUtils {
    public static String sha256String(String input) {
        return Hasher.getDefault().sha256Hex(input);
    }

    public static String sha1String(String input) {
        return Hasher.getDefault().sha1Hex(input);
    }

    public static String md5String(String input) {
        return Hasher.getDefault().md5Hex(input);
    }
}
