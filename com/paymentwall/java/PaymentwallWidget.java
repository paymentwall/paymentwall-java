package com.paymentwall.java;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;

public class PaymentwallWidget extends PaymentwallBase
{
    /**
     * Widget call URL
     */
    public final String BASE_URL = "https://api.paymentwall.com/api";
    protected String userId;
    protected String widgetCode;
    protected ArrayList<PaymentwallProduct> products;
    protected LinkedHashMap<String,ArrayList<String>> extraParams;
    /**
     * param String userId identifier of the end-user who is viewing the widget
     * param String widgetCode e.g. p1 or p1_1, can be found inside of your Paymentwall Merchant account in the Widgets section
     * param ArrayList products array that consists of Paymentwall_Product entities; for Flexible Widget Call use array of 1 product
     * param ArrayList extraParams associative array of additional params that will be included into the widget URL,
     * e.g. "sign_version" or "email". Full list of parameters for each API is available at http://paymentwall.com/documentation
     */
    public PaymentwallWidget(String userId_, String widgetCode_, ArrayList<PaymentwallProduct> products_, LinkedHashMap<String,ArrayList<String>> extraParams_) {
        userId = userId_;
        widgetCode = widgetCode_;
        products = products_;
        extraParams = extraParams_;
    }
    /**
     * Get default signature version for this API type
     *
     * return int
     */
    public int getDefaultSignatureVersion() {
        return getApiType() != API_CART ? DEFAULT_SIGNATURE_VERSION : SIGNATURE_VERSION_2;
    }
    /**
     * Return URL for the widget
     *
     * return string
     */
    public String getUrl() {
        LinkedHashMap<String,ArrayList<String>> params = new LinkedHashMap<String, ArrayList<String>>();
        {
            params.put("key",new ArrayList<String>(){{add(getAppKey());}});
            params.put("uid",new ArrayList<String>(){{add(userId);}});
            params.put("widget",new ArrayList<String>(){{add(widgetCode);}});
        }
        if (getApiType() == API_GOODS) {
            if (!products.isEmpty()) {
                if (products.size() == 1) {
                    PaymentwallProduct postTrialProduct = null;
                    PaymentwallProduct product = products.get(0);
                    if (product.getTrialProduct() != null) {
                        postTrialProduct = product;
                        product = product.getTrialProduct();
                    }
                    final PaymentwallProduct finalproduct = product;
                    final PaymentwallProduct finaltrialproduct = postTrialProduct;
                    params.put("amount",new ArrayList<String>(){{add(Double.toString(finalproduct.getAmount()));}});
                    params.put("currencyCode",new ArrayList<String>(){{add(finalproduct.getCurrencyCode());}});
                    params.put("ag_name", new ArrayList<String>(){{add(finalproduct.getName());}});
                    params.put("ag_external_id", new ArrayList<String>(){{add(finalproduct.getId());}});
                    params.put("ag_type", new ArrayList<String>(){{add(finalproduct.getType());}});
                    if (product.getType().equals(PaymentwallProduct.TYPE_SUBSCRIPTION)) {
                        params.put("ag_period_length", new ArrayList<String>(){{add(Integer.toString(finalproduct.getPeriodLength()));}});
                        params.put("ag_period_type", new ArrayList<String>(){{add(finalproduct.getPeriodType());}});
                        if (product.isRecurring()) {
                            params.put("ag_recurring", new ArrayList<String>(){{add("1");}});
                            if (finaltrialproduct != null) {
                                params.put("ag_trial",new ArrayList<String>(){{add("1");}});
                                params.put("ag_post_trial_external_id", new ArrayList<String>(){{add(finaltrialproduct.getId());}});
                                params.put("ag_post_trial_period_length", new ArrayList<String>(){{add(Integer.toString(finaltrialproduct.getPeriodLength()));}});
                                params.put("ag_post_trial_period_type", new ArrayList<String>(){{add(finaltrialproduct.getPeriodType());}});
                                params.put("ag_post_trial_name", new ArrayList<String>(){{add(finaltrialproduct.getName());}});
                                params.put("post_trial_amount",new ArrayList<String>(){{add(Double.toString(finaltrialproduct.getAmount()));}});
                                params.put("post_trial_currencyCode", new ArrayList<String>(){{add(finaltrialproduct.getCurrencyCode());}});
                            }
                        }
                    }
                } else {
                    appendToErrors("Only 1 product is allowed in flexible widget call");
                }
            }
        } else if (getApiType() == API_CART) {
            ArrayList<String> external_ids = new ArrayList<String>();
            ArrayList<String> prices = new ArrayList<String>();
            ArrayList<String> currencies = new ArrayList<String>();
            for (PaymentwallProduct product : products) {
                external_ids.add(product.getId()); //external_ids
                if (product.getAmount()>0) {
                    prices.add(Double.toString(product.getAmount())); //prices
                }
                if (!product.getCurrencyCode().equals("")) {
                    currencies.add(product.getCurrencyCode()); //currencies
                }
            }
            params.put("external_ids",external_ids);
            params.put("prices",prices);
            params.put("currencies",currencies);
        }
        String signatureVersion;
        if (extraParams.containsKey("sign_version") && !extraParams.get("sign_version").isEmpty()) {
            params.put("sign_version",extraParams.get("sign_version"));
            signatureVersion = extraParams.get("sign_version").get(0);
        } else {
            signatureVersion = Integer.toString(getDefaultSignatureVersion());
            params.put("sign_version", new ArrayList<String>(){{ add(Integer.toString(getDefaultSignatureVersion()));}});
        }

        TreeMap<String,ArrayList<String>> finalparams = new TreeMap<String, ArrayList<String>>();
        {
            params.putAll(extraParams);
            finalparams.putAll(params);
            try {
                ArrayList<String> signature = new ArrayList<String>();
                signature.add(calculateSignature(params, getSecretKey(), Integer.parseInt(signatureVersion)));
                finalparams.put("sign",signature);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String baseString;

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for(Map.Entry<String,ArrayList<String>> entry : finalparams.entrySet()) {

            if (entry.getValue().size()==1)
                list.add(new BasicNameValuePair(entry.getKey(),entry.getValue().get(0)));
            else {
                for (int i = 0; i<entry.getValue().size(); i++) {
                    list.add(new BasicNameValuePair(entry.getKey()+"["+Integer.toString(i)+"]",entry.getValue().get(i)));
                }
            }
        }
        baseString = URLEncodedUtils.format(list, "UTF-8");
        return BASE_URL + "/" + buildController(widgetCode) + "?" + baseString;
    }
    /**
     * Return HTML code for the widget
     *
     * param array attributes associative array of additional HTML attributes, e.g. array("width" => "100%")
     * return string
     */
    public String getHtmlCode(LinkedHashMap<String,String> attributes) {
        LinkedHashMap<String,String> defaultAttributes = new LinkedHashMap<String,String>();
        {
            defaultAttributes.put("frameborder","0");
            defaultAttributes.put("width","750");
            defaultAttributes.put("height","800");
        }
        defaultAttributes.putAll(attributes);
        String attributesQuery = "";
        for (Map.Entry<String,String> entry : defaultAttributes.entrySet()) {
            attributesQuery += " " + entry.getKey() + "=\"" + entry.getValue() + "\"";
        }
        return "<iframe src=\"" + getUrl() + "\" " + attributesQuery + "></iframe>";
    }

    public String getHtmlCode() {
        LinkedHashMap<String,String> defaultAttributes = new LinkedHashMap<String,String>();
        {
            defaultAttributes.put("frameborder","0");
            defaultAttributes.put("width","750");
            defaultAttributes.put("height","800");
        }
        String attributesQuery = "";
        for (Map.Entry<String,String> entry : defaultAttributes.entrySet()) {
            attributesQuery += " " + entry.getKey() + "=\"" + entry.getValue() + "\"";
        }
        return "<iframe src=\"" + getUrl() + "\" " + attributesQuery + "></iframe>";
    }
    /**
     * Build controller URL depending on API type
     *
     * param string widget code of the widget
     * param bool flexibleCall
     * return string
     */
    protected String buildController(String widget) {
        switch (getApiType()) {
            case API_VC : if (!widget.matches("/^w|s|mw/")) return CONTROLLER_PAYMENT_VIRTUAL_CURRENCY;
            case API_GOODS : if (!widget.matches("/^w|s|mw/")) return CONTROLLER_PAYMENT_DIGITAL_GOODS;
            default: return CONTROLLER_PAYMENT_CART;
        }
    }
    /**
     * Build signature for the widget specified
     *
     * param array params
     * param string secret Paymentwall Secret Key
     * param int version Paymentwall Signature Version
     * return string
     */
    public static String calculateSignature(LinkedHashMap<String, ArrayList<String>> params, String secret, int version) throws Exception {
        String baseString = "";

        if (!params.containsKey("uid")) throw new Exception("No uid is present!");

        if (version == SIGNATURE_VERSION_1) {
            baseString += params.containsKey("uid") ? params.get("uid") : "";

            baseString += secret;
            return DigestUtils.md5Hex(baseString);
        }

        params.remove("sign");
        params.remove("sig");

        TreeMap<String,ArrayList<String>> sortedParams = new TreeMap<String, ArrayList<String>>();
        if ((version == SIGNATURE_VERSION_2 || version == SIGNATURE_VERSION_3) && params.size()>1) {
            sortedParams.putAll(params);
            for(Map.Entry<String,ArrayList<String>> pair : sortedParams.entrySet()) {
                if (pair.getValue().size()==1)
                    baseString += pair.getKey() + "=" + pair.getValue().get(0);
                else for (int i=0; i<pair.getValue().size(); i++) baseString+= pair.getKey()+"["+i+"]" + "=" + pair.getValue().get(i);
            }
            baseString += secret;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(baseString.getBytes("UTF-8"));
            if (version == SIGNATURE_VERSION_3) return String.format("%032X", new BigInteger(1, md.digest())).toLowerCase();
            return DigestUtils.md5Hex(baseString);
        }

        return "wrong sign_version";
    }
}