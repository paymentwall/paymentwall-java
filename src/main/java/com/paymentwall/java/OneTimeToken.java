package com.paymentwall.java;

public class OneTimeToken extends ApiObject {

    public OneTimeToken() {}

    public OneTimeToken(String id) {
        super(id);
    }

    public String getToken() { return get(PROP_TOKEN); }

    public boolean isTest() { return getAsBoolean(PROP_ISTEST); }

    public boolean isActive() { return getAsBoolean(PROP_ISACTIVE); }

    public int getExpirationTime() { return getAsInt(PROP_EXPIRATION); }

    public String getEndpointName() { return API_OBJECT_ONE_TIME_TOKEN; }
}