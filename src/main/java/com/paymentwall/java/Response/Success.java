package com.paymentwall.java.Response;

import org.json.simple.JSONObject;

public class Success extends Abstract implements Interface {
    public Success(JSONObject response) { super(response); }

    public JSONObject process() {
        if (response.isEmpty())
            return wrapInternalError();
        response = new JSONObject();
        response.put(PROP_SUCCESS, 1);
        return response;
    }
}
