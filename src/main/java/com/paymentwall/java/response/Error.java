package com.paymentwall.java.response;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Error extends Abstract implements Interface {
    final private static int GENERAL_INTERNAL = 1000;
    final private static int APPLICATION_NOT_LOADED = 1001;
    final private static int CHARGE_NOT_FOUND = 3000;
    final private static int CHARGE_PERMISSION_DENIED = 3001;
    final private static int CHARGE_WRONG_AMOUNT = 3002;
    final private static int CHARGE_WRONG_CARD_NUMBER = 3003;
    final private static int CHARGE_WRONG_EXP_MONTH  = 3004;
    final private static int CHARGE_WRONG_EXP_YEAR  = 3005;
    final private static int CHARGE_WRONG_EXP_DATE = 3006;
    final private static int CHARGE_WRONG_CURRENCY = 3007;
    final private static int CHARGE_EMPTY_FIELDS = 3008;
    final private static int CHARGE_WRONG_TOKEN = 3111;
    final private static int CHARGE_WRONG_ONE_TIME_TOKEN = 3112;
    final private static int CHARGE_WRONG_TEST_CREDENTIALS = 3113;
    final private static int CHARGE_TOKEN_DELETED = 3114;
    final private static int CHARGE_WRONG_MIN_AMOUNT = 3115;
    final private static int CHARGE_WRONG_MAX_AMOUNT = 3116;
    final private static int CHARGE_CARD_NUMBER_ERROR = 3101;
    final private static int CHARGE_CARD_NUMBER_EXPIRED = 3102;
    final private static int CHARGE_UNSUPPORTED_CARD = 3103;
    final private static int CHARGE_UNSUPPORTED_COUNTRY = 3104;
    final private static int CHARGE_BILLING_ADDRESS_ERROR = 3009;
    final private static int CHARGE_BANK_DECLINE = 3010;
    final private static int CHARGE_INSUFFICIENT_FUNDS = 3011;
    final private static int CHARGE_GATEWAY_DECLINE = 3012;
    final private static int CHARGE_CVV_ERROR = 3014;
    final private static int CHARGE_FAILED = 3200;
    final private static int CHARGE_ALREADY_REFUNDED = 3201;
    final private static int CHARGE_CANCEL_FAILED = 3202;
    final private static int CHARGE_ALREADY_CAPTURED = 3203;
    final private static int CHARGE_REFUND_FAILED  = 3204;
    final private static int CHARGE_DUPLICATE = 3205;
    final private static int CHARGE_AUTH_EXPIRED = 3206;
    final private static int FIELD_FIRSTNAME = 3301;
    final private static int FIELD_LASTNAME = 3302;
    final private static int FIELD_ADDRESS = 3303;
    final private static int FIELD_CITY = 3304;
    final private static int FIELD_STATE = 3305;
    final private static int FIELD_ZIP = 3306;
    final private static int SUBSCRIPTION_WRONG_PERIOD = 3401;
    final private static int SUBSCRIPTION_NOT_FOUND = 3402;
    final private static int SUBSCRIPTION_WRONG_PERIOD_DURATION = 3403;
    final private static int SUBSCRIPTION_MISSING_TRIAL_PARAMS = 3404;
    final private static int SUBSCRIPTION_WRONG_TRIAL_PERIOD = 3405;
    final private static int SUBSCRIPTION_WRONG_TRIAL_PERIOD_DURATION = 3406;
    final private static int SUBSCRIPTION_WRONG_TRIAL_AMOUNT = 3407;
    final private static int SUBSCRIPTION_WRONG_PAYMENTS_LIMIT = 3408;
    final private static int API_UNDEFINED_METHOD = 4004;
    final private static int API_EMPTY_REQUEST = 4005;
    final private static int API_KEY_MISSED = 4006;
    final private static int API_KEY_INVALID = 4007;
    final private static int API_DECRYPTION_FAILED = 4008;
    final private static int USER_BANNED = 5000;

    final static ArrayList<Integer> errorCodes = new ArrayList<Integer>() {{
        add(GENERAL_INTERNAL);
        add(APPLICATION_NOT_LOADED);
        add(CHARGE_NOT_FOUND);
        add(CHARGE_PERMISSION_DENIED);
        add(CHARGE_WRONG_AMOUNT);
        add(CHARGE_WRONG_CARD_NUMBER);
        add(CHARGE_WRONG_EXP_MONTH);
        add(CHARGE_WRONG_EXP_YEAR);
        add(CHARGE_WRONG_EXP_DATE);
        add(CHARGE_WRONG_CURRENCY);
        add(CHARGE_EMPTY_FIELDS);
        add(CHARGE_WRONG_TOKEN);
        add(CHARGE_WRONG_ONE_TIME_TOKEN);
        add(CHARGE_WRONG_TEST_CREDENTIALS);
        add(CHARGE_TOKEN_DELETED);
        add(CHARGE_WRONG_MIN_AMOUNT);
        add(CHARGE_WRONG_MAX_AMOUNT);
        add(CHARGE_CARD_NUMBER_ERROR);
        add(CHARGE_CARD_NUMBER_EXPIRED);
        add(CHARGE_UNSUPPORTED_CARD);
        add(CHARGE_UNSUPPORTED_COUNTRY);
        add(CHARGE_CVV_ERROR);
        add(CHARGE_BILLING_ADDRESS_ERROR);
        add(CHARGE_BANK_DECLINE);
        add(CHARGE_INSUFFICIENT_FUNDS);
        add(CHARGE_GATEWAY_DECLINE);
        add(CHARGE_FAILED);
        add(CHARGE_ALREADY_REFUNDED);
        add(CHARGE_CANCEL_FAILED);
        add(CHARGE_ALREADY_CAPTURED);
        add(CHARGE_REFUND_FAILED);
        add(CHARGE_DUPLICATE);
        add(CHARGE_AUTH_EXPIRED);
        add(FIELD_FIRSTNAME);
        add(FIELD_LASTNAME);
        add(FIELD_ADDRESS);
        add(FIELD_CITY);
        add(FIELD_STATE);
        add(FIELD_ZIP);
        add(SUBSCRIPTION_WRONG_PERIOD);
        add(SUBSCRIPTION_NOT_FOUND);
        add(SUBSCRIPTION_WRONG_PERIOD_DURATION);
        add(SUBSCRIPTION_MISSING_TRIAL_PARAMS);
        add(SUBSCRIPTION_WRONG_TRIAL_PERIOD);
        add(SUBSCRIPTION_WRONG_TRIAL_PERIOD_DURATION);
        add(SUBSCRIPTION_WRONG_TRIAL_AMOUNT);
        add(SUBSCRIPTION_WRONG_PAYMENTS_LIMIT);
        add(API_UNDEFINED_METHOD);
        add(API_EMPTY_REQUEST);
        add(API_KEY_MISSED);
        add(API_KEY_INVALID);
        add(API_DECRYPTION_FAILED);
        add(USER_BANNED);
    }};

    public Error(JSONObject response) { super(response); }

    @SuppressWarnings("unchecked")
    public JSONObject process() {
        if (response.isEmpty())
            return wrapInternalError();

        JSONObject object = new JSONObject();
        {
            object.put(PROP_SUCCESS, 0);
            JSONObject subObject = new JSONObject();
            subObject.put(PROP_ERROR, getErrorMessageAndCode(response));
            object.put(PROP_ERROR, subObject);
        }
        return object;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getErrorMessageAndCode(JSONObject response) {
        JSONObject result = new JSONObject();
        if (errorCodes.contains(Integer.parseInt(response.get(PROP_CODE).toString()))) {
            result.put(PROP_MESSAGE, response.get(PROP_ERROR));
            result.put(PROP_CODE, response.get(PROP_CODE));
        }
        return result;
    }
}
