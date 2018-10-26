package com.paymentwall.java;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class Config extends Messages{
    final static String VERSION = "2.1.0";

    final static String API_BASE_URL = "https://api.paymentwall.com/api";

    final public static int API_VC = 1;
    final public static int API_GOODS = 2;
    final public static int API_CART = 3;

    protected int apiType;
    protected String publicKey;
    protected String privateKey;
    protected String apiBaseUrl = API_BASE_URL;

    private static final Config instance = new Config();

    protected Config() {} //no default constructor

    public String getApiBaseUrl() { return apiBaseUrl; }

    public void setApiBaseUrl(String url) { apiBaseUrl = url; }

    public int getLocalApiType() { return apiType; }

    public void setLocalApiType(int apiType) {
        this.apiType = apiType;
    }

    public String getPublicKey() { return publicKey; }

    public void setPublicKey(String key) { publicKey = key; }

    public String getPrivateKey() { return privateKey; }

    public void setPrivateKey(String key) { privateKey = key; }

    public String getVersion() { return VERSION; }

    public boolean isTest() { return getPublicKey().startsWith("t_")&&getPrivateKey().startsWith("t_"); }

    public void set(HashMap<String,String> config) {
        if (config.containsKey("api_base_url")) setApiBaseUrl(config.get("api_base_url"));
        if (config.containsKey("api_type"))     setLocalApiType(Integer.parseInt(config.get("api_type")));
        if (config.containsKey("public_key"))   setPublicKey(config.get("public_key"));
        if (config.containsKey("private_key"))  setPrivateKey(config.get("private_key"));
    }

    public static Config getInstance() { return instance; }

    public static double round(double value, int places) {
        double factor = Math.pow(10, places);
        return Math.round(value * factor) / factor;
    }

    /**
     * @param _GET Map of parameters received
     * @return set of parameters converted to HashMap
     */
    public static HashMap<String,ArrayList<String>> parseQuery(Map<String,String[]> _GET) {
        final HashMap<String, ArrayList<String>> parameters = new HashMap<String, ArrayList<String>>();
        if (!_GET.isEmpty()) {
            for (final Map.Entry<String,String[]> entry : _GET.entrySet()) {
                String fixedKey = entry.getKey();
                if (entry.getKey().contains("[") && entry.getKey().contains("]"))
                    fixedKey = entry.getKey().replaceFirst("\\[\\d*\\]","");
                final String finalFixedKey = fixedKey;
                parameters.put(fixedKey, new ArrayList<String>(){{
                    for (String value : entry.getValue()) {
                        if (parameters.containsKey(finalFixedKey)) {
                            for (int i=0; i<parameters.get(finalFixedKey).size(); i++)
                                add(this.size(), parameters.get(finalFixedKey).get(i));
                        }
                        try {
                            add(this.size(), URLDecoder.decode(value, "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }});
            }
        }
        return parameters;
    }
}