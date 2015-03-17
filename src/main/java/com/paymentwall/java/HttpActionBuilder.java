package com.paymentwall.java;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Pattern Builder for HttpAction class
 */
public class HttpActionBuilder {
    private ApiObject object;
    private HashMap<String, String> params = new HashMap<String, String>(){{
        put("public_key", Config.getInstance().getPublicKey());
    }};
    private ArrayList<String> headers = new ArrayList<String>();

    public HttpActionBuilder(ApiObject object) {
        this.object = object;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public void setHeaders(ArrayList<String> headers) {
        this.headers = headers;
    }

    public HttpAction build() {
        return new HttpAction(object,params,headers);
    }
}
