package com.paymentwall.java;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class HttpAction allows to make API call to Paymentwall server
 */
public class HttpAction extends Instance {
    protected ApiObject apiObject;
    protected HashMap<String, String> apiParams;
    protected ArrayList<String> apiHeaders;
    private static final int httpTimeoutMs = 60000;

    HttpAction(ApiObject object, HashMap<String, String> params, ArrayList<String> headers) {
        setApiObject(object);
        setApiParams(params);
        setApiHeaders(headers);
    }

    public ApiObject getApiObject() {
        return apiObject;
    }

    public void setApiObject(ApiObject apiObject) {
        this.apiObject = apiObject;
    }

    public HashMap<String, String> getApiParams() {
        return apiParams;
    }

    public void setApiParams(HashMap<String, String> params) {
        apiParams = params;
    }

    public ArrayList<String> getApiHeaders() {
        return apiHeaders;
    }

    public void setApiHeaders(ArrayList<String> headers) {
        apiHeaders = headers;
    }

    /**
     * Makes request to Brick API
     * @return answer from a server
     * @throws IOException
     */
    public String run() throws Exception {
        String result = "";
        if (getApiObject() != null) {
            result = apiObjectPostRequest(getApiObject());
        }
        return result;
    }

    private String apiObjectPostRequest(ApiObject object) throws Exception {
        return request(httpMethod.POST, object.getApiUrl(), getApiParams(), getApiHeaders());
    }

    /**
     * GET/POST requests
     * @param url server address
     * @return answer from a server
     * @throws IOException
     */
    public String post(String url) throws Exception {
        return request(httpMethod.POST, url, getApiParams(), getApiHeaders());
    }

    public String get(String url) throws Exception {
        return request(httpMethod.GET, url, getApiParams(), getApiHeaders());
    }

    /**
     * @param method http method of request, possible values: GET, POST
     * @param url api url
     * @param params prepared GET or POST request parameters
     * @param customHeaders api headers to include
     * @return response
     * @throws Exception in case of IOError in communication between server and client or wrong Header format provided
     */
    protected String request(httpMethod method, String url, HashMap<String, String> params, ArrayList<String> customHeaders) throws Exception {
        ArrayList<String> headers = sList(getLibraryDefaultRequestHeader());
        String encodedParams;
        URL obj;
        {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet())
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            encodedParams = URLEncodedUtils.format(list, "UTF-8");
        }
        obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        if (!customHeaders.isEmpty()) headers.addAll(customHeaders);
        for (String header : headers) {
            String[] pair = header.split(":", 2);
            if (pair.length == 2) {
                String key = pair[0];
                String value = pair[1];
                con.setRequestProperty(key, value);
            } else {
                throw new Exception(EXCEPTION_WRONG_HEADER);
            }
        }

        con.setRequestMethod(method.name());
        con.setConnectTimeout(httpTimeoutMs);
        con.setDoOutput(true);

        if (method.equals(httpMethod.POST)) {
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(encodedParams);
            wr.flush();
            wr.close();
        }

        final int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader((responseCode>=200 && responseCode<=299) ? con.getInputStream() : con.getErrorStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return prepareResponse(response.toString());
    }

    protected String getLibraryDefaultRequestHeader() {
        return DEFAULT_REQUEST_HEADER + this.getConfig().getVersion();
    }

    protected String prepareResponse(String string) {
        return string.replaceAll("\\uFEFF", "");
    }
}