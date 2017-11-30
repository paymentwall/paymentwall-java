package com.paymentwall.java;

public class OneTimeToken extends ApiObject {

    public OneTimeToken() {}

    public OneTimeToken(String id) {
        this(Config.getInstance(), id);
    }

    public OneTimeToken(Config config, String id) {
        super(config, id);
    }

    public OneTimeToken(Config config) {
        super(config);
    }

    public String getToken() { return get(PROP_TOKEN); }

    public boolean isTest() { return getAsBoolean(PROP_ISTEST); }

    public boolean isActive() { return getAsBoolean(PROP_ISACTIVE); }

    public int getExpirationTime() { return getAsInt(PROP_EXPIRATION); }

    public String getEndpointName() { return API_OBJECT_ONE_TIME_TOKEN; }
}