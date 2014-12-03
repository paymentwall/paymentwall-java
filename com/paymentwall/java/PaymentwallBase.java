package com.paymentwall.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

abstract public class PaymentwallBase {
    /**
     * Paymentwall library version
     */
    static final String VERSION = "1.1.1";

    /**
     * URLs for Paymentwall Pro
     */
    static public final String CHARGE_URL = "https://api.paymentwall.com/api/pro/v1/charge";
    static public final String SUBS_URL = "https://api.paymentwall.com/api/pro/v1/subscription";

    /**
     * API types
     */
    static public final int API_VC = 1;
    static public final int API_GOODS = 2;
    static public final int API_CART = 3;

    /**
     * Controllers for APIs
     */
    static public final String CONTROLLER_PAYMENT_VIRTUAL_CURRENCY = "ps";
    static public final String CONTROLLER_PAYMENT_DIGITAL_GOODS = "subscription";
    static public final String CONTROLLER_PAYMENT_CART = "cart";

    /**
     * Signature versions
     */
    static public final int DEFAULT_SIGNATURE_VERSION = 2;
    static public final int SIGNATURE_VERSION_1 = 1;
    static public final int SIGNATURE_VERSION_2 = 2;
    static public final int SIGNATURE_VERSION_3 = 3;

    protected ArrayList<String> errors = new ArrayList<String>() {{ add(""); }};

    /**
     * Paymentwall API type
     * @param int apiType
     */
    static int apiType;

    /**
     * Paymentwall application key - can be found in your merchant area
     * @param string appKey
     */
    static String appKey;

    /**
     * Paymentwall secret key - can be found in your merchant area
     * @param string secretKey
     */
    static String secretKey;

    /**
     * Paymentwall Pro API Key
     * @param string proApiKey
     */
    static String proApiKey;

    /**
     * @param int apiType API type, Paymentwall_Base::API_VC for Virtual Currency, Paymentwall_Base::API_GOODS for Digital Goods
     * Paymentwall_Base::API_CART for Cart, more details at http://paymentwall.com/documentation
     */
    public static void setApiType(int apiType_) {
        apiType = apiType_;
    }

    public static int getApiType() {
        return apiType;
    }

    /**
     * @param string appKey application key of your application, can be found inside of your Paymentwall Merchant Account
     */
    public static void setAppKey(String appKey_) {
        appKey = appKey_;
    }

    public static String getAppKey() {
        return appKey;
    }

    /**
     * @param string secretKey secret key of your application, can be found inside of your Paymentwall Merchant Account
     */
    public static void setSecretKey(String secretKey_) {
        secretKey = secretKey_;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    /**
     * @param proApiKey_ API key used for Pro authentication
     */
    public static void setProApiKey(String proApiKey_) {
        proApiKey = proApiKey_;
    }

    public static String getProApiKey() {
        return proApiKey;
    }

    /**
     * Fill the array with the errors found at execution
     *
     * @param err
     * @return int List size
     */
    protected int appendToErrors(String err) {
        errors.add(err);
        return errors.size();
    }

    /**
     * Return errors
     *
     * @return List<String>
     */
    public ArrayList<String> getErrors() {
        return errors;
    }

    /**
     * Return error summary
     *
     * @return string
     */
    public String getErrorSummary() {
        String listString = "";
        for (String s : errors)
            listString += s + "\n";
        return listString;
    }

    public static double round(double value, int places) {
        if (places >= 0) {
            double factor = Math.pow(10, places);
            return Math.round(value * factor) / factor;
        }
        throw new IllegalArgumentException("places should be more than or equals 0");
    }

    public static LinkedHashMap parseQuery(Map<String,String[]> _GET) {
        LinkedHashMap<String, ArrayList<String>> parameters = new LinkedHashMap<String, ArrayList<String>>();

        if (!_GET.isEmpty())
            for (final Map.Entry<String,String[]> entry : _GET.entrySet())
                if (entry.getKey().contains("[")&&entry.getKey().contains("]")) {
                    String key = Arrays.asList(entry.getKey().split("\\[\\d*\\]")).get(0);

                    //int index = Integer.parseInt(entry.getKey().replace(key, "").substring(0, entry.getKey().replace(key,"").length()-1));

                    ArrayList<String> this_parameter_keys = new ArrayList<String>();
                    if (parameters.containsKey(key))
                        this_parameter_keys.addAll(parameters.get(key));
                    this_parameter_keys.addAll(Arrays.asList(entry.getValue()));

                    parameters.put(key, this_parameter_keys);
                } else parameters.put(entry.getKey(), new ArrayList<String>(Arrays.asList(entry.getValue())));
        return parameters;
    }
}
