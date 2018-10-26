package com.paymentwall.java.response;

import com.paymentwall.java.Messages;
import org.json.simple.JSONObject;

public class Abstract extends Messages {
    protected JSONObject response = new JSONObject();
    
    public Abstract(JSONObject response) {
        this.response = response;
    }

    @SuppressWarnings("unchecked")
    protected JSONObject wrapInternalError() {
        JSONObject object = new JSONObject();
        {
            JSONObject subObject = new JSONObject();
            subObject.put(PROP_MESSAGE, VALUE_INTERNAL_ERROR);
            object.put(PROP_SUCCESS, 0);
            object.put(PROP_ERROR, subObject);
        }
        return object;
    }
}