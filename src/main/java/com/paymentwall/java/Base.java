package com.paymentwall.java;

/**
 * Base class for backward compatibility
 * Please use @{@link Config} instead
 */
@Deprecated @SuppressWarnings("deprecated")
public class Base extends Config {
    @Deprecated
    /**
     * Use @{@link Config#setLocalApiType(int)}
     */
    public static void setApiType(int apiType) { getInstance().setLocalApiType(apiType); }
    @Deprecated
    /**
     * Use @{@link Config#setPublicKey(String)}
     */
    public static void setAppKey(String appKey) { getInstance().setPublicKey(appKey); }
    @Deprecated
    /**
     * Use @{@link Config#setPrivateKey(String)}
     */
    public static void setSecretKey(String secretKey) { getInstance().setPrivateKey(secretKey); }
}
