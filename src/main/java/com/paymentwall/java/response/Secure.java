package com.paymentwall.java.response;

import org.json.simple.JSONObject;

/**
 * Created by hoahieu on 12/01/17.
 */
public class Secure extends Abstract implements Interface {
    public Secure(JSONObject response) {
        super(response);
    }

    public JSONObject process() {
        if(response.isEmpty() || !response.containsKey(PROP_SECURE)) return wrapInternalError();
        response.put(PROP_SUCCESS, 0);

        return response;
    }
}
