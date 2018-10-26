package com.paymentwall.java.response;

import com.paymentwall.java.Messages;
import org.json.simple.JSONObject;

public abstract class Factory extends Messages {

    public static Interface get(JSONObject response) {
        return getObject(getClassName(response),response);
    }

    public static Interface getObject(String name, JSONObject response) {
        if (name.equals(RESPONSE_SUCCESS))
            return new Success(response);
        else if (name.equals(RESPONSE_SECURE))
            return new Secure(response);
        else
            return new Error(response);
    }

    public static String getClassName(JSONObject response) {
        if (response.isEmpty() || (response.containsKey(PROP_TYPE)
                && response.get(PROP_TYPE).toString().equals(VALUE_ERROR))) {
            return RESPONSE_ERROR;
        } else if (response.containsKey(PROP_SECURE)) {
            return RESPONSE_SECURE;
        } else return RESPONSE_SUCCESS;
    }
}
