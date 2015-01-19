package com.paymentwall.java;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Widget extends Base
{
    /**
     * Widget call URL
     */
    public final String BASE_URL = "https://api.paymentwall.com/api";

    /**
     * param userId identifier of the end-user who is viewing the widget
     * param widgetCode e.g. p1 or p1_1, can be found inside of your Paymentwall Merchant account in the Widgets section
     * param ArrayList products array that consists of Product entities; for Flexible Widget Call use array of 1 product
     * param ArrayList extraParams associative array of additional params that will be included into the widget URL,
     * e.g. "sign_version" or "email". Full list of parameters for each API is available at http://paymentwall.com/documentation
     */

    protected String userId;
    protected String widgetCode;
    protected ArrayList<Product> products;
    protected LinkedHashMap<String,String> extraParams;

    public Widget(String userId_, String widgetCode_, ArrayList<Product> products_, LinkedHashMap<String, String> extraParams_) {
        userId = userId_;
        widgetCode = widgetCode_;
        products = products_;
        extraParams = extraParams_;
    }
    /**
     * Get default signature version for this API type
     *
     * @return int
     */
    public int getDefaultSignatureVersion() {
        return getApiType() != API_CART ? DEFAULT_SIGNATURE_VERSION : SIGNATURE_VERSION_2;
    }
    /**
     * Return URL for the widget
     *
     * @return string
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
                    Product postTrialProduct = null;
                    Product product = products.get(0);
                    if (product.getTrialProduct() != null) {
                        postTrialProduct = product;
                        product = postTrialProduct.getTrialProduct();
                    }
                    final Product finalproduct = product;
                    final Product finaltrialproduct = postTrialProduct;
                    params.put("amount",new ArrayList<String>(){{add(Double.toString(finalproduct.getAmount()));}});
                    params.put("currencyCode",new ArrayList<String>(){{add(finalproduct.getCurrencyCode());}});
                    params.put("ag_name", new ArrayList<String>(){{add(finalproduct.getName());}});
                    params.put("ag_external_id", new ArrayList<String>(){{add(finalproduct.getId());}});
                    params.put("ag_type", new ArrayList<String>(){{add(finalproduct.getType());}});
                    if (product.getType().equals(Product.TYPE_SUBSCRIPTION)) {
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
            for (Product product : products) {
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

            params.put("sign_version",(new ArrayList<String>(){{add(extraParams.get("sign_version"));}})) ;
            signatureVersion = extraParams.get("sign_version");
        } else {
            signatureVersion = Integer.toString(getDefaultSignatureVersion());
            params.put("sign_version", new ArrayList<String>(){{ add(Integer.toString(getDefaultSignatureVersion()));}});
        }

        TreeMap<String,ArrayList<String>> sortedParams = new TreeMap<String, ArrayList<String>>();
        {
            for (final Map.Entry<String,String> each : extraParams.entrySet()) {
                params.put(each.getKey(),new ArrayList<String>(){{ add(each.getValue()); }});
            }
            sortedParams.putAll(params);
            try {
                ArrayList<String> signature = new ArrayList<String>();
                signature.add(calculateSignature(params, getSecretKey(), Integer.parseInt(signatureVersion)));
                sortedParams.put("sign", signature);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String baseString;

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for(Map.Entry<String,ArrayList<String>> entry : sortedParams.entrySet()) {

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
     * @param attributes associative array of additional HTML attributes, e.g. Pairs ("width","100%")
     * @return String
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
        return getHtmlCode(new LinkedHashMap<String, String>());
    }
    /**
     * Build controller URL depending on API type
     *
     * @param widget code of the widget
     * @param flexibleCall boolean is the call flexible?
     * @return String
     */
    protected String buildController(String widget, boolean flexibleCall) {
        switch (getApiType()) {
            case API_VC : if (!widget.matches("^(w|s|mw).*")) return CONTROLLER_PAYMENT_VIRTUAL_CURRENCY;
            case API_GOODS : if (!flexibleCall) {if (!widget.matches("^(w|s|mw).*")) return CONTROLLER_PAYMENT_DIGITAL_GOODS; else return ""; } else return  CONTROLLER_PAYMENT_DIGITAL_GOODS;
            default: return CONTROLLER_PAYMENT_CART;
        }
    }

    protected String buildController(String widget) {

        return buildController(widget, false);
    }
    /**
     * Build signature for the widget specified
     *
     * @param params parameters used for signature calculation
     * @param secret Paymentwall Secret Key
     * @param version Paymentwall Signature Version
     * @return String
     */
    public static String calculateSignature(LinkedHashMap<String, ArrayList<String>> params, String secret, int version) throws NoSuchElementException {
        String baseString = "";

        if (version == SIGNATURE_VERSION_1) {
            if (!params.containsKey("uid")) throw new NoSuchElementException("No uid is present!");

            baseString += !params.get("uid").isEmpty() ? params.get("uid").get(0) : "";
            baseString += secret;

            return DigestUtils.md5Hex(baseString);
        } else {
            TreeMap<String,ArrayList<String>> sortedParams = new TreeMap<String, ArrayList<String>>();
            sortedParams.putAll(params);
            for(Map.Entry<String,ArrayList<String>> pair : sortedParams.entrySet()) {
                if (pair.getValue().size()==1)
                    baseString += pair.getKey() + "=" + pair.getValue().get(0);
                else for (int i=0; i<pair.getValue().size(); i++) baseString+= pair.getKey()+"["+i+"]" + "=" + pair.getValue().get(i);
            }
            baseString += secret;
            MessageDigest sha;
            MessageDigest md;
            try {
                sha = MessageDigest.getInstance("SHA-256");
                sha.update(baseString.getBytes("UTF-8"));
                md = MessageDigest.getInstance("MD5");
                md.update(baseString.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return "";
            }
            if (version == SIGNATURE_VERSION_3) return String.format("%032X", new BigInteger(1, sha.digest())).toLowerCase();

            return String.format("%032X", new BigInteger(1, md.digest())).toLowerCase();
        }
    }
}