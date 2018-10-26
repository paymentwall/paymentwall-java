package com.paymentwall.java;

import org.json.simple.JSONObject;


public class Card extends Messages {
    protected JSONObject fields;

    public Card(JSONObject details) { fields = details; }

    public String get(String property) { return fields.get(property) != null ? fields.get(property).toString() : null; }

    public String getToken() { return get(PROP_TOKEN); }

    public String getType() { return get(PROP_TYPE); }

    public String getAlias() { return get(PROP_LAST4); }

    public String getMonthExpirationDate() { return get(PROP_EXPIRATION_MONTH); }

    public String getYearExpirationDate() { return get(PROP_EXPIRATION_YEAR); }
}
