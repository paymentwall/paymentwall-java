package com.paymentwall.java;

public class Subscription extends ApiObject implements ApiObjectInterface {
    public Subscription(String id) {
        super(id);
    }

    public Subscription() {}

    public String getId() {
        return this.id;
    }

    public boolean isTrial() {
        return getAsBoolean(PROP_ISTRIAL);
    }

    public boolean isActive() {
        return getAsBoolean(PROP_ISACTIVE);
    }

    public boolean isSuccessful() {
        return get(PROP_OBJECT_CHARGE).equals(API_OBJECT_SUBSCRIPTION);
    }

    public boolean isExpired() {
        return getAsBoolean(PROP_ISEXPIRED);
    }

    public String getEndpointName() {
        return API_OBJECT_SUBSCRIPTION;
    }

    public Card getCard() {
        return new Card(getAsJSON(PROP_CARD_NO));
    }

    public ApiObject get() throws Exception {
        return this.doApiAction("", httpMethod.GET);
    }

    public ApiObject cancel() throws Exception {
        return this.doApiAction("cancel", httpMethod.POST);
    }
}
