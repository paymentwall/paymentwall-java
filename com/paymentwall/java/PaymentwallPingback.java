package com.paymentwall.java;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class PaymentwallPingback extends PaymentwallBase {
    /**
     * Pingback types
     */
    public final int PINGBACK_TYPE_REGULAR = 0;
    public final int PINGBACK_TYPE_GOODWILL = 1;
    public final int PINGBACK_TYPE_NEGATIVE = 2;

    public final int PINGBACK_TYPE_RISK_UNDER_REVIEW = 200;
    public final int PINGBACK_TYPE_RISK_REVIEWED_ACCEPTED = 201;
    public final int PINGBACK_TYPE_RISK_REVIEWED_DECLINED = 202;

    public final int PINGBACK_TYPE_SUBSCRIPTION_CANCELLATION = 12;
    public final int PINGBACK_TYPE_SUBSCRIPTION_EXPIRED = 13;
    public final int PINGBACK_TYPE_SUBSCRIPTION_PAYMENT_FAILED = 14;

    /**
     * Pingback parameters, usually received from parsing _GET
     */

    protected LinkedHashMap<String,ArrayList<String>> parameters = new LinkedHashMap<String, ArrayList<String>>();

    /**
     * IP address, usually _SERVER["REMOTE_ADDR"]
     */
    protected String ipAddress;

    /**
     * @param map parameters array of parameters received by pingback processing script, e.g. _GET
     * @param string ipAddress IP address from where the pingback request originates, e.g. "127.0.0.1"
     */
    public PaymentwallPingback(LinkedHashMap<String,ArrayList<String>> parameters_, String ipAddress_) {
        parameters = parameters_;
        ipAddress = ipAddress_;
    }

    /**
     * Check whether pingback is valid
     *
     * @param bool skipIpWhitelistCheck if IP whitelist check should be skipped, e.g. if you have a load-balancer changing the IP
     * @return bool
     */

    public boolean validate() {
        return validate(false);
    }

    public boolean validate(boolean skipIpWhitelistCheck) {
        boolean validated = false;

        if (isParametersValid())
            if (isIpAddressValid() || skipIpWhitelistCheck)
                if (isSignatureValid())
                    validated = true;
                else appendToErrors("Wrong signature");
            else appendToErrors("IP address is not whitelisted");
        else appendToErrors("Missing parameters");
        return validated;
    }

    /**
     * @return bool
     */


    public boolean isSignatureValid() {
        LinkedHashMap<String, ArrayList<String>> signatureParamsToSign = new LinkedHashMap<String, ArrayList<String>>();
        ArrayList<String> signatureParams;
        if (getApiType() == API_VC) {
            signatureParams = new ArrayList<String>(){{
                add("uid");
                add("currency");
                add("type");
                add("ref");
            }};
        } else if (getApiType() == API_GOODS) {
            signatureParams = new ArrayList<String>(){{
                add("uid");
                add("goodsid");
                add("slength");
                add("speriod");
                add("type");
                add("ref");
            }};
        } else { // API_CART
            signatureParams = new ArrayList<String>(){{
                add("uid");
                add("goodsid");
                add("type");
                add("ref");
            }};
            parameters.put("sign_version",new ArrayList<String>(){{add(Integer.toString(SIGNATURE_VERSION_2));}});
    }

        if (!(parameters.containsKey("sign_version")))
            parameters.put("sign_version",new ArrayList<String>(){{add(Integer.toString(SIGNATURE_VERSION_1));}});

        if (parameters.get("sign_version").isEmpty()||parameters.get("sign_version").get(0).equals(""))
            parameters.put("sign_version",new ArrayList<String>(){{add(Integer.toString(SIGNATURE_VERSION_1));}});

        if (parameters.get("sign_version").get(0).equals(Integer.toString(SIGNATURE_VERSION_1)))
            for(String field : signatureParams)
                signatureParamsToSign.put(field, parameters.containsKey(field) ? parameters.get(field) : new ArrayList<String>());
        else signatureParamsToSign.putAll(parameters);

        String signatureCalculated = calculateSignature(signatureParamsToSign, secretKey, Integer.parseInt(parameters.get("sign_version").get(0)));
        String signature = parameters.containsKey("sig") ? parameters.get("sig").get(0) : "";
        return signature.equals(signatureCalculated);
    }

    /**
     * @return bool
     */
    public boolean isIpAddressValid() {
        ArrayList<String> ipsWhitelist = new ArrayList<String>();
        {
            ipsWhitelist.add("174.36.92.186");
            ipsWhitelist.add("174.36.96.66");
            ipsWhitelist.add("174.36.92.187");
            ipsWhitelist.add("174.36.92.192");
            ipsWhitelist.add("174.37.14.28");
        }
        return ipsWhitelist.contains(ipAddress);
    }

    /**
     * @return bool
     */
    public boolean isParametersValid()
    {
        ArrayList<String> requiredParams = new ArrayList<String>();
        {
            requiredParams.add("uid");
            requiredParams.add("type");
            requiredParams.add("ref");
            requiredParams.add("sig");
        }
        int errorsNumber = 0;

        if (getApiType()==API_VC) {
            requiredParams.add("currency");
        } else {
            if (getApiType() == API_GOODS) {
                requiredParams.add("goodsid");
            }
            else requiredParams.add("goodsid");
        }

        for (String field : requiredParams) {
            if (!parameters.containsKey(field) || parameters.get(field).isEmpty()) {
                appendToErrors("Parameter " + field + " is missing");
                errorsNumber++;
            }
        }

        return errorsNumber == 0;
    }

    /**
     * Get pingback parameter
     *
     * @param key
     * @return string
     */
    public ArrayList<String> getParameter(String key) {
        if (parameters.containsKey(key)) {
            return parameters.get(key);
        }
        appendToErrors("Parameter " + key + " is missing");
        return new ArrayList<String>();
    }

    /**
     * Get pingback parameter "type"
     *
     * @return int
     */
    public String getType() {
        if (parameters.containsKey("type")) {
            return parameters.get("type").get(0);
        }
        appendToErrors("Parameter " + "type" + " is missing");
        return "";
    }

    /**
     * Get verbal explanation of the informational pingback
     *
     * @return string
     */
    public String getTypeVerbal() {
        LinkedHashMap<Integer,String> pingbackTypes = new LinkedHashMap<Integer, String>();
        {
            pingbackTypes.put(PINGBACK_TYPE_SUBSCRIPTION_CANCELLATION,"user_subscription_cancellation");
            pingbackTypes.put(PINGBACK_TYPE_SUBSCRIPTION_EXPIRED,"user_subscription_expired");
            pingbackTypes.put(PINGBACK_TYPE_SUBSCRIPTION_PAYMENT_FAILED,"user_subscription_payment_failed");
        }

        if (!parameters.get("type").isEmpty())
            if (pingbackTypes.containsKey(Integer.parseInt(parameters.get("type").get(0))))
                return pingbackTypes.get(Integer.parseInt(parameters.get("type").get(0)));

        appendToErrors("Parameter " + "type" + " is missing");
        return "";
    }

    /**
     * Get pingback parameter "uid"
     *
     * @return string
     */
    public String getUserId() {
        return getParameter("uid").get(0);
    }

    /**
     * Get pingback parameter "currency"
     *
     * @return string
     */
    public double getVirtualCurrencyAmount() {
        return Double.parseDouble(getParameter("currency").get(0));
    }

    /**
     * Get product id
     *
     * @return string
     */
    public String getProductId() {
        return getParameter("goodsid").get(0);
    }

    /**
     * @return int
     */
    public int getProductPeriodLength() {
        return Integer.parseInt(getParameter("slength").get(0));
    }

    /**
     * @return string
     */
    public String getProductPeriodType()  {
        return getParameter("speriod").get(0);
    }

    /**
     * @return Paymentwall_Product
     */
    public PaymentwallProduct getProduct()  {
        PaymentwallProductBuilder a = new PaymentwallProductBuilder(getProductId());
        {
            a.setPeriodType(getProductPeriodType());
            a.setPeriodLength(getProductPeriodLength());
            a.setProductType(getProductPeriodLength() > 0 ? PaymentwallProduct.TYPE_SUBSCRIPTION : PaymentwallProduct.TYPE_FIXED);
        }
        return a.buildPaymentwallProduct();
    }

    /**
     * @return array Paymentwall_Product
     */
    public ArrayList<PaymentwallProduct> getProducts() { //
        ArrayList<PaymentwallProduct> result = new ArrayList<PaymentwallProduct>();
        ArrayList<String> productIds = new ArrayList<String>();

        if (!getParameter("goodsid").isEmpty())
            productIds.addAll(getParameter("goodsid"));

        if (!productIds.isEmpty()) {
            for(String Id : productIds) {
                result.add(new PaymentwallProductBuilder(Id).buildPaymentwallProduct());
            }
        }

        return result;
    }

    /**
     * Get pingback parameter "ref"
     *
     * @return string
     */
    public String getReferenceId() {
        return getParameter("ref").get(0);
    }

    /**
     * Returns unique identifier of the pingback that can be used for checking
     * if the same pingback was already processed by your servers.
     * Two pingbacks with the same unique ID should not be processed more than once
     *
     * @return string
     */
    public String getPingbackUniqueId() {
        return getReferenceId() + "_" + getType();
    }

    /**
     * Check whether product is deliverable
     *
     * @return bool
     */
    public boolean isDeliverable() {
        switch (Integer.parseInt(getType())) {
            case PINGBACK_TYPE_GOODWILL : return true;
            case PINGBACK_TYPE_REGULAR  : return true;
            case PINGBACK_TYPE_RISK_REVIEWED_ACCEPTED : return true;
            default: return false;
        }
    }

    /**
     * Check whether product is cancelable
     *
     * @return bool
     */
    public boolean isCancelable() {
        switch (Integer.parseInt(getType())) {
            case PINGBACK_TYPE_NEGATIVE : return true;
            case PINGBACK_TYPE_RISK_REVIEWED_DECLINED  : return true;
            default: return false;
        }
    }

    /**
     * Check whether product is under review
     *
     * @return bool
     */
    public boolean isUnderReview() {
        return Integer.parseInt(getType()) == PINGBACK_TYPE_RISK_UNDER_REVIEW;
    }

    /**
     * Build signature for the pingback received
     *
     * @param array params
     * @param string secret Paymentwall Secret Key
     * @param int version Paymentwall Signature Version
     * @return string
     */
    protected String calculateSignature(LinkedHashMap<String,ArrayList<String>> params, String secret, int version) {
        String baseString = "";

        if (!params.containsKey("uid")) appendToErrors("No uid is present!");

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
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA-256");
                md.update(baseString.getBytes("UTF-8"));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (version == SIGNATURE_VERSION_3) return String.format("%032X", new BigInteger(1, md.digest())).toLowerCase();
            return DigestUtils.md5Hex(baseString);
        }

        return "wrong sign_version";
    }
}