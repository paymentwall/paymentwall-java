package com.paymentwall.java.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hoahieu on 04/01/17.
 */
public abstract class Hasher {
    public abstract byte[] md5(byte[] input);
    public abstract byte[] sha1(byte[] input);
    public abstract byte[] sha256(byte[] input);
    public abstract byte[] md5(String input);
    public abstract byte[] sha1(String input);
    public abstract byte[] sha256(String input);
    public abstract String md5Hex(byte[] input);
    public abstract String sha1Hex(byte[] input);
    public abstract String sha256Hex(byte[] input);
    public abstract String md5Hex(String input);
    public abstract String sha1Hex(String input);
    public abstract String sha256Hex(String input);

    private static Hasher DEFAULT_HASHER = new Hasher() {
        private final String MD5 = "MD5";
        private final String SHA1 = "SHA-1";
        private final String SHA256 = "SHA-256";
        private final String UTF8 = "UTF-8";

        public byte[] md5(byte[] input) {
            return hash(MD5, input);
        }

        public byte[] sha1(byte[] input) {
            return hash(SHA1, input);
        }

        public byte[] sha256(byte[] input) {
            return hash(SHA256, input);
        }

        public byte[] md5(String input) {
            return hash(MD5, input.getBytes(Charset.forName(UTF8)));
        }

        public byte[] sha1(String input) {
            return hash(SHA1, input.getBytes(Charset.forName(UTF8)));
        }

        public byte[] sha256(String input) {
            return hash(SHA256, input.getBytes(Charset.forName(UTF8)));
        }

        public String md5Hex(byte[] input) {
            return bytesToString(md5(input));
        }

        public String sha1Hex(byte[] input) {
            return bytesToString(sha1(input));
        }

        public String sha256Hex(byte[] input) {
            return bytesToString(sha256(input));
        }

        public String md5Hex(String input) {
            return bytesToString(md5(input));
        }

        public String sha1Hex(String input) {
            return bytesToString(sha1(input));
        }

        public String sha256Hex(String input) {
            return bytesToString(sha256(input));
        }

        private byte[] hash(String messageDigestMethod, byte[] input) {
            try {
                MessageDigest md = MessageDigest.getInstance(messageDigestMethod);
                return md.digest(input);
            } catch (NoSuchAlgorithmException e) {
                return null;
            }
        }

        final char[] hexArray = "0123456789abcdef".toCharArray();

        public String bytesToString(byte[] bytes) {
            char[] hexChars = new char[bytes.length * 2];
            for ( int j = 0; j < bytes.length; j++ ) {
                int v = bytes[j] & 0xFF;
                hexChars[j * 2] = hexArray[v >>> 4];
                hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            }
            return new String(hexChars);
        }
    };

    public static Hasher getDefault() {
        return DEFAULT_HASHER;
    }
}
