package com.paymentwall.java;

/**
 * Base class for backward compatibility
 */
@Deprecated @SuppressWarnings("deprecated")
public class Base extends Config {
    public static void setApiType(int apiType) { getInstance().setLocalApiType(apiType); }
    public static void setAppKey(String appKey) { getInstance().setPublicKey(appKey); }
    public static void setSecretKey(String secretKey) { getInstance().setPrivateKey(secretKey); }
}
