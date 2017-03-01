package com.paymentwall.java;

import java.util.HashMap;

public class Charge extends ApiObject implements ApiObjectInterface {
    private UserProfile userProfile;

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public ApiObject create(HashMap<String, String> params) throws Exception {
        if(userProfile != null && params != null) params.putAll(userProfile.toParameters());
        return super.create(params);
    }

    public Charge(String id) {
        super(id);
    }

    public Charge() {}

    public String getId() {
        return id;
    }

    public boolean isTest() {
        return getAsBoolean(PROP_ISTEST);
    }

    public boolean isSuccessful() {
        return get(PROP_OBJECT_CHARGE).equals(API_OBJECT_CHARGE);
    }

    public boolean isCaptured() {
        return getAsBoolean(PROP_ISCAPTURED);
    }

    public boolean isUnderReview() {
        return get(PROP_RISK_STATUS).equals(API_RISK_PENDING);
    }

    public boolean isRefunded() {
        return getAsBoolean(PROP_ISREFUNDED);
    }

    public String getEndpointName() {
        return API_OBJECT_CHARGE;
    }

    public Card getCard() {
        return new Card(getAsJSON(PROP_CARD_NO));
    }

    public ApiObject get() throws Exception {
        return doApiAction("", httpMethod.GET);
    }

    public ApiObject refund() throws Exception {
        return doApiAction(API_ACTION_REFUND, httpMethod.POST);
    }

    public ApiObject capture() throws Exception {
        return doApiAction(API_ACTION_CAPTURE, httpMethod.POST);
    }

    public ApiObject void_() throws Exception {
        return doApiAction(API_ACTION_VOID, httpMethod.POST);
    }
}
