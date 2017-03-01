package com.paymentwall.java;

import com.paymentwall.java.response.Factory;
import com.paymentwall.java.response.Interface;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;

public abstract class ApiObject extends Instance {
    protected JSONObject properties = new JSONObject();
    protected String id;

    abstract String getEndpointName();

    protected ApiObject(String id) {
        this.id = id;
    }

    protected ApiObject() {}

    /**
     * @param params prepared parameters for API call to Paymentwall server
     * @return ApiObject with API response stored as json
     * @throws Exception in case of IOError or empty response from server
     */
    public ApiObject create(final HashMap<String, String> params) throws Exception {
        final HttpActionBuilder httpActionBuilder = new HttpActionBuilder(this) {{
            setParams(params);
            setHeaders(sList(getApiBaseHeader()));
        }};
        HttpAction httpAction = httpActionBuilder.build();

        setPropertiesFromResponse(
                httpAction.run()
        );
        return this;
    }

    public String get(String property) {
        return properties.containsKey(property)? properties.get(property).toString() : "";
    }

    public JSONObject getAsJSON(String property) {
        return (JSONObject)(properties.containsKey(property) ? (properties.get(property) != null ? properties.get(property) : null) : null);
    }

    public boolean getAsBoolean(String property) {
        return get(property).equals("true");
    }

    public int getAsInt(String property) {
        return Integer.parseInt(get(property));
    }

    public String getApiUrl() {
        if ((getEndpointName().equals(API_OBJECT_ONE_TIME_TOKEN)) && !getConfig().isTest()) {
            return OneTimeToken.GATEWAY_TOKENIZATION_URL;
        } else {
            return getApiBaseUrl() + API_BRICK_URL + getEndpointName();
        }
    }

    public JSONObject getPublicData() {
        Interface responseModel = Factory.get(getPropertiesFromResponse());
        return responseModel.process();
    }

    protected void setPropertiesFromResponse(String response) throws Exception {
        if (!response.isEmpty()) {
            properties = preparePropertiesFromResponse(response);
        } else {
            throw new Exception(EXCEPTION_EMPTY_RESPONSE);
        }
    }

    protected JSONObject getPropertiesFromResponse() { return properties; }

    protected JSONObject preparePropertiesFromResponse(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject)parser.parse(response);
    }

    protected String getApiBaseHeader() { return "X-ApiKey: " + getPrivateKey(); }

    protected ApiObject doApiAction(String action, httpMethod method) throws Exception {
        String actionUrl = getApiUrl() + "/" + id + "/" + action;

        HttpActionBuilder httpActionBuilder = new HttpActionBuilder(this);
        httpActionBuilder.setParams(new HashMap<String, String>(){{ put("id",id); }});
        httpActionBuilder.setHeaders(sList(getApiBaseHeader()));

        HttpAction httpAction = httpActionBuilder.build();

        setPropertiesFromResponse( method.equals(httpMethod.GET) ? httpAction.get(actionUrl) : httpAction.post(actionUrl) );
        return this;
    }
}