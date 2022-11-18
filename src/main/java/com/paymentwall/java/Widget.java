package com.paymentwall.java;

import com.paymentwall.java.signature.Abstract;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.*;

public class Widget extends Instance {
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
    protected HashMap<String, String> extraParams;

    public Widget(String userId, String widgetCode, ArrayList<Product> products, HashMap<String, String> extraParams) {
        this.userId = userId;
        this.widgetCode = widgetCode;
        this.products = products;
        this.extraParams = extraParams;
    }

    /**
     * Set extra parameters
     *
     * @param extraParams
     */
    public void setExtraParams(HashMap<String, String> extraParams) {
        this.extraParams = extraParams;
    }


    /**
     * Get default signature version for this API type
     *
     * @return int
     */
    public int getDefaultSignatureVersion() {
        return getApiType() == Config.API_CART ? Abstract.VERSION_TWO : Abstract.DEFAULT_VERSION;
    }

    /**
     * Return URL for the widget
     *
     * @return string
     */
    public String getUrl() {
        final HashMap<String, String> p = new HashMap<String, String>();
        {
            p.put("key", getPublicKey());
            p.put("uid", userId);
            p.put("widget", widgetCode);
        }
        if (getApiType() == Config.API_GOODS) {
            if (!products.isEmpty()) {
                if (products.size() == 1) {
                    Product postTrialProduct = null;
                    Product product = products.get(0);
                    if (product.getTrialProduct() != null) {
                        postTrialProduct = product;
                        product = postTrialProduct.getTrialProduct();
                    }
                    final Product finalProduct = product;
                    final Product finalTrialProduct = postTrialProduct;
                    p.put(PARAM_AMOUNT, Double.toString(finalProduct.getAmount()));
                    p.put(PARAM_CURRENCY_CODE, finalProduct.getCurrencyCode());
                    p.put(PARAM_AG_NAME, finalProduct.getName());
                    p.put(PARAM_AG_EXTERNAL_ID, finalProduct.getId());
                    p.put(PARAM_AG_TYPE, finalProduct.getType());
                    if (product.getType().equals(Product.TYPE_SUBSCRIPTION)) {
                        p.put(PARAM_AG_PERIOD_L, Integer.toString(finalProduct.getPeriodLength()));
                        p.put(PARAM_AG_PERIOD_TYPE, finalProduct.getPeriodType());
                        if (product.isRecurring()) {
                            p.put(PARAM_AG_RECURRING, "1");
                            if (finalTrialProduct != null) {
                                p.put(PARAM_AG_TRIAL, "1");
                                p.put(PARAM_AG_P_TRIAL_EXTERNAL_ID, finalTrialProduct.getId());
                                p.put(PARAM_AG_P_TRIAL_PERIOD_L, Integer.toString(finalTrialProduct.getPeriodLength()));
                                p.put(PARAM_AG_P_TRIAL_PERIOD_TYPE, finalTrialProduct.getPeriodType());
                                p.put(PARAM_AG_P_TRIAL_NAME, finalTrialProduct.getName());
                                p.put(PARAM_AG_P_TRIAL_AMOUNT, Double.toString(finalTrialProduct.getAmount()));
                                p.put(PARAM_AG_P_TRIAL_CURRENCY_CODE, finalTrialProduct.getCurrencyCode());
                            }
                        }
                    }
                } else {
                    appendToErrors(EXCEPTION_ONLY_1_PRODUCT);
                }
            }
        } else if (getApiType() == Config.API_CART) {
            int i = 0;
            for (final Product product : products) {
                p.put(PARAM_EXTERNAL_IDS + "[" + i + "]", product.getId());
                if (product.getAmount() > 0.0) {
                    p.put(PARAM_PRICES + "[" + i + "]", String.valueOf(product.getAmount()));
                }
                if (!product.getCurrencyCode().equals("")) {
                    p.put(PARAM_CURRENCIES + "[" + i + "]", product.getCurrencyCode());
                }
                if (!product.getName().equals("")) {
                    p.put(PARAM_NAMES + "[" + i + "]", product.getName());
                }
                i++;
            }
        }
        final int signatureVersion;
        if (extraParams.containsKey(PARAM_SIGN_VERSION) && !extraParams.get(PARAM_SIGN_VERSION).isEmpty()) {
            p.put(PARAM_SIGN_VERSION, extraParams.get(PARAM_SIGN_VERSION));
            signatureVersion = Integer.parseInt(extraParams.get(PARAM_SIGN_VERSION));
        } else {
            signatureVersion = getDefaultSignatureVersion();
            p.put(PARAM_SIGN_VERSION, Integer.toString(signatureVersion));
        }
        final com.paymentwall.java.signature.Widget widgetSignatureModel = new com.paymentwall.java.signature.Widget();
        p.putAll(extraParams);
        p.put(PARAM_SIGN, widgetSignatureModel.calculate(new LinkedHashMap<String, ArrayList<String>>() {{
            for (final Map.Entry<String, String> entry : p.entrySet())
                put(entry.getKey(), sList(entry.getValue()));
        }}, signatureVersion));
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String, String> entry : p.entrySet())
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        return BASE_URL + "/" + buildController(widgetCode) + "?" + URLEncodedUtils.format(list, "UTF-8");
    }

    /**
     * Return HTML code for the widget
     *
     * @param attributes associative array of additional HTML attributes, e.g. Pairs ("width","100%")
     * @return String
     */
    public String getHtmlCode(HashMap<String, String> attributes) {
        HashMap<String, String> defaultAttributes = new HashMap<String, String>();
        {
            defaultAttributes.put("frameborder", "0");
            defaultAttributes.put("width", "750");
            defaultAttributes.put("height", "800");
            defaultAttributes.putAll(attributes);
        }
        String attributesQuery = "";
        for (Map.Entry<String, String> entry : defaultAttributes.entrySet()) {
            attributesQuery += " " + entry.getKey() + "=\"" + entry.getValue() + "\"";
        }
        return "<iframe src=\"" + getUrl() + "\" " + attributesQuery + "></iframe>";
    }

    public String getHtmlCode() {
        return getHtmlCode(new HashMap<String, String>());
    }

    /**
     * Build controller URL depending on API type
     *
     * @param widget code of the widget
     * @return String
     */
    protected String buildController(String widget) {
        String controller = "";
        boolean isPaymentWidget = !widget.matches("^(w|s|mw).*");
        if (getApiType() == Config.API_VC) {
            if (isPaymentWidget) {
                controller = CONTROLLER_PAYMENT_VIRTUAL_CURRENCY;
            }
        } else if (getApiType() == Config.API_GOODS) {
            /**
             * @todo cover case with offer widget for digital goods for non-flexible widget call
             */
            controller = CONTROLLER_PAYMENT_DIGITAL_GOODS;
        } else {
            controller = CONTROLLER_PAYMENT_CART;
        }
        return controller;
    }
}