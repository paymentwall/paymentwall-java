package com.paymentwall.java;

public class PaymentwallProduct {
    /**
     * Product types
     */
    public final static String TYPE_SUBSCRIPTION = "subscription";
    public final static String TYPE_FIXED = "fixed";
    /**
     * Product period types
     */
    public final static String PERIOD_TYPE_DAY = "day";
    public final static String PERIOD_TYPE_WEEK = "week";
    public final static String PERIOD_TYPE_MONTH = "month";
    public final static String PERIOD_TYPE_YEAR = "year";
    /**
     * @param string productId your internal product ID, e.g. product1
     * @param float amount product price, e.g. 9.99
     * @param string currencyCode ISO currency code, e.g. USD
     * @param string name product name
     * @param string productType product type, Paymentwall_Product::TYPE_SUBSCRIPTION for recurring billing, or Paymentwall_Product::TYPE_FIXED for one-time payments
     * @param int periodLength product period length, e.g. 3. Only required if product type is subscription
     * @param string periodType product period type, e.g. Paymentwall_Product::PERIOD_TYPE_MONTH. Only required if product type is subscription
     * @param bool recurring if the product recurring
     * @param PaymentwallProduct trialProduct trial product, product type should be subscription, recurring should be True
     */
    private String productId;
    private double amount;
    private String currencyCode;
    private String name;
    private String productType;
    private int periodLength;
    private String periodType;
    private boolean recurring;
    private PaymentwallProduct trialProduct;

    public PaymentwallProduct(String productId_, double amount_, String currencyCode_, String name_, String productType_, int periodLength_, String periodType_, boolean recurring_, PaymentwallProduct trialProduct_) {
        productId = productId_;
        amount = PaymentwallBase.round(amount_, 2);
        currencyCode = currencyCode_;
        name = name_;
        productType = productType_;
        periodLength = periodLength_;
        periodType = periodType_;
        recurring = recurring_;
        trialProduct = productType.equals(TYPE_SUBSCRIPTION) & recurring_ ? trialProduct_ : null;
    }

    /**
     * @return string product ID
     */
    public String getId() {
        return productId;
    }
    /**
     * @return float product price, e.g. 9.99
     */
    public double getAmount()
    {
        return amount;
    }
    /**
     * @return string ISO currency code, e.g. USD
     */
    public String getCurrencyCode()
    {
        return currencyCode;
    }
    /**
     * @return string product name
     */
    public String getName()
    {
        return name;
    }
    /**
     * @return string product type, Paymentwall_Product::TYPE_SUBSCRIPTION for recurring billing, Paymentwall_Product::TYPE_FIXED for one-time
     */
    public String getType()
    {
        return productType;
    }
    /**
     * @return string product period type, e.g. Paymentwall_Product::PERIOD_TYPE_MONTH
     */
    public String getPeriodType() {
        return periodType;
    }
    /**
     * @return string product period length, e.g. 3
     */
    public int getPeriodLength()
    {
        return periodLength;
    }
    /**
     * @return Paymentwall_Product trial product
     */
    public PaymentwallProduct getTrialProduct()
    {
        return trialProduct;
    }
    /**
     * @return bool if the product recurring
     */
    public boolean isRecurring()
    {
        return recurring;
    }
}