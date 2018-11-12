package com.paymentwall.java;

import com.paymentwall.java.signature.Abstract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.net.util.SubnetUtils;

public class Pingback extends Instance {
        /**
         * Pingback types
         */
        public final int PINGBACK_TYPE_REGULAR = 0;
        public final int PINGBACK_TYPE_GOODWILL = 1;
        public final int PINGBACK_TYPE_NEGATIVE = 2;

        public final int PINGBACK_TYPE_RISK_UNDER_REVIEW = 200;
        public final int PINGBACK_TYPE_RISK_REVIEWED_ACCEPTED = 201;
        public final int PINGBACK_TYPE_RISK_REVIEWED_DECLINED = 202;

        public final int PINGBACK_TYPE_RISK_AUTHORIZATION_VOIDED = 203;

        public final int PINGBACK_TYPE_SUBSCRIPTION_CANCELLATION = 12;
        public final int PINGBACK_TYPE_SUBSCRIPTION_EXPIRED = 13;
        public final int PINGBACK_TYPE_SUBSCRIPTION_PAYMENT_FAILED = 14;

        /**
         * Pingback parameters, usually received from parsing _GET
         */

        protected HashMap<String,ArrayList<String>> parameters = new HashMap<String, ArrayList<String>>();

        /**
         * IP address, usually _SERVER["REMOTE_ADDR"]
         */
        protected String ipAddress;

        /**
         * @param parameters array of parameters received by pingback processing script, e.g. _GET
         * @param ipAddress IP address from where the pingback request originates, e.g. "127.0.0.1"
         */
        @SuppressWarnings("deprecated")
        public Pingback(Map<String, String[]> parameters, String ipAddress) {
            this.parameters = Base.parseQuery(parameters);
            this.ipAddress = ipAddress;
        }

        /**
         * Check whether pingback is valid
         *
         * @param skipIpWhitelistCheck if IP whitelist check should be skipped, e.g. if you have a load-balancer changing the IP
         * @return bool
         */

        public boolean validate(boolean skipIpWhitelistCheck) {
            boolean validated = false;
            if (isParametersValid())
                if (isIpAddressValid() || skipIpWhitelistCheck)
                    if (isSignatureValid())
                        validated = true;
                    else appendToErrors(EXCEPTION_WRONG_SIGN);
                else appendToErrors(EXCEPTION_IP_NOT_WHITELISTED);
            else appendToErrors(EXCEPTION_MISSING_PARAMETERS);
            return validated;
        }

        public boolean validate() {
            return validate(false);
        }

        /**
         * @return bool
         *
         */

        public boolean isSignatureValid(){
            LinkedHashMap<String,ArrayList<String>> signatureParamsToSign = new LinkedHashMap<String, ArrayList<String>>();
            ArrayList<String> signatureParams;
            if (getApiType() == Config.API_VC) {
                signatureParams = new ArrayList<String>(){{
                    add(PARAM_UID);
                    add(PARAM_CURRENCY);
                    add(PARAM_TYPE);
                    add(PARAM_REF);
                }};
            } else if (getApiType() == Config.API_GOODS) {
                signatureParams = new ArrayList<String>(){{
                    add(PARAM_UID);
                    add(PARAM_GOODSID);
                    add(PARAM_SLENGTH);
                    add(PARAM_SPERIOD);
                    add(PARAM_TYPE);
                    add(PARAM_REF);
                }};
            } else { // API_CART
                signatureParams = new ArrayList<String>(){{
                    add(PARAM_UID);
                    add(PARAM_GOODSID);
                    add(PARAM_TYPE);
                    add(PARAM_REF);
                }};
                parameters.put(PARAM_SIGN_VERSION, sList(Integer.toString(Abstract.VERSION_TWO)));
            }

            if (!(parameters.containsKey(PARAM_SIGN_VERSION)))
                parameters.put(PARAM_SIGN_VERSION, sList(Integer.toString(Abstract.VERSION_ONE)));

            if (parameters.get(PARAM_SIGN_VERSION).isEmpty() || parameters.get(PARAM_SIGN_VERSION).get(0).equals(""))
                parameters.put(PARAM_SIGN_VERSION, sList(Integer.toString(Abstract.VERSION_ONE)));

            if (parameters.get(PARAM_SIGN_VERSION).get(0).equals(Integer.toString(Abstract.VERSION_ONE)))
                for(String field : signatureParams)
                    signatureParamsToSign.put(field, parameters.containsKey(field) ? parameters.get(field) : sList(""));
            else signatureParamsToSign.putAll(parameters);

            com.paymentwall.java.signature.Pingback pingbackSignatureModel = new com.paymentwall.java.signature.Pingback();
            String signatureCalculated = pingbackSignatureModel.calculate(
                    signatureParamsToSign,
                    Integer.parseInt(parameters.get(PARAM_SIGN_VERSION).get(0))
            );
            String signature = parameters.containsKey(PARAM_SIG) ? parameters.get(PARAM_SIG).get(0) : "";

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
            
            SubnetUtils utils = new SubnetUtils("216.127.71.0/24");
            String[] allIps = utils.getInfo().getAllAddresses();
            
            for (int i = 0; i < allIps.length; i++) 
            {
             ipsWhitelist.add(allIps[i]);
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
                requiredParams.add(PARAM_UID);
                requiredParams.add(PARAM_TYPE);
                requiredParams.add(PARAM_REF);
                requiredParams.add(PARAM_SIG);
            }
            int errorsNumber = 0;

            if (getApiType()==Config.API_VC) {
                requiredParams.add(PARAM_CURRENCY);
            } else {
                if (getApiType() == Config.API_GOODS) {
                    requiredParams.add(PARAM_GOODSID);
                }
                else requiredParams.add(PARAM_GOODSID);
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
         * @param key links to element in params hashmap by key
         * @return string
         */
        public ArrayList<String> getParameter(String key) {
            if (parameters.containsKey(key)) {
                return parameters.get(key);
            }
            appendToErrors("Parameter " + key + " is missing");
            return sList("");
        }

        /**
         * Get pingback parameter "type"
         *
         * @return int
         */
        public int getType() {
            if (parameters.containsKey(PARAM_TYPE)) {
                return Integer.parseInt(parameters.get(PARAM_TYPE).get(0));
            }
            appendToErrors("Parameter " + PARAM_TYPE + " is missing");
            return 0;
        }

        /**
         * Get verbal explanation of the informational pingback
         *
         * @return string
         */
        public String getTypeVerbal() {
            HashMap<Integer,String> pingbackTypes = new HashMap<Integer, String>();
            {
                pingbackTypes.put(PINGBACK_TYPE_SUBSCRIPTION_CANCELLATION, PINGBACK_VERBAL_SUBSCRIPTION_CANCELLATION);
                pingbackTypes.put(PINGBACK_TYPE_SUBSCRIPTION_EXPIRED, PINGBACK_VERBAL_SUBSCRIPTION_EXPIRED);
                pingbackTypes.put(PINGBACK_TYPE_SUBSCRIPTION_PAYMENT_FAILED, PINGBACK_VERBAL_SUBSCRIPTION_PAYMENT_FAILED);
            }

            if (!parameters.get(PARAM_TYPE).isEmpty())
                if (pingbackTypes.containsKey(Integer.parseInt(parameters.get(PARAM_TYPE).get(0))))
                    return pingbackTypes.get(Integer.parseInt(parameters.get(PARAM_TYPE).get(0)));

            appendToErrors("Parameter " + PARAM_TYPE + " is missing");
            return "";
        }

        /**
         * Get pingback parameter "uid"
         *
         * @return string
         */
        public String getUserId() {
            return getParameter(PARAM_UID).get(0);
        }

        /**
         * Get pingback parameter "currency"
         *
         * @return string
         */
        public Integer getVirtualCurrencyAmount() {
            return Integer.parseInt(getParameter(PARAM_CURRENCY).get(0));
        }

        /**
         * Get product id
         *
         * @return string
         */
        public String getProductId() {
            return getParameter(PARAM_GOODSID).get(0);
        }

        /**
         * @return int
         */
        public int getProductPeriodLength() {
            try {
                return Integer.parseInt(getParameter(PARAM_SLENGTH).get(0));
            }
            catch (NumberFormatException e) {
                return 0; // not a number or empty value
            }
        }

        /**
         * @return string month, day, etc
         */
        public String getProductPeriodType()  {
            return getParameter(PARAM_SPERIOD).get(0);
        }

        /**
         * @return Paymentwall_Product
         */
        public Product getProduct() {
            ProductBuilder a = new ProductBuilder(getProductId());
            {
                a.setPeriodType(getProductPeriodType());
                a.setPeriodLength(getProductPeriodLength());
                a.setProductType(getProductPeriodLength() > 0 ? Product.TYPE_SUBSCRIPTION : Product.TYPE_FIXED);
            }
            return a.build();
        }

        /**
         * @return array Paymentwall_Product
         */
        public ArrayList<Product> getProducts() { //
            ArrayList<Product> result = new ArrayList<Product>();
            ArrayList<String> productIds = new ArrayList<String>();

            if (!getParameter(PARAM_GOODSID).isEmpty())
                productIds.addAll(getParameter(PARAM_GOODSID));

            if (!productIds.isEmpty()) {
                for(String Id : productIds) {
                    result.add(new ProductBuilder(Id).build());
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
            return getParameter(PARAM_REF).get(0);
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
            switch (getType()) {
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
            switch (getType()) {
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
            return getType() == PINGBACK_TYPE_RISK_UNDER_REVIEW;
        }

}
