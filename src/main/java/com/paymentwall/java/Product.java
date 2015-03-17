package com.paymentwall.java;

public class Product extends Messages {
    /**
     * param string productId your internal product ID, e.g. product1
     * param float amount product price, e.g. 9.99
     * param string currencyCode ISO currency code, e.g. USD
     * param string name product name
     * param string productType product type, Product.TYPE_SUBSCRIPTION for recurring billing, or Product.TYPE_FIXED for one-time payments
     * param int periodLength product period length, e.g. 3. Only required if product type is subscription
     * param string periodType product period type, e.g. Product.PERIOD_TYPE_MONTH. Only required if product type is subscription
     * param bool recurring if the product recurring
     * param Product trialProduct trial product, product type should be subscription, recurring should be True
     */
    private String productId;
    private double amount;
    private String currencyCode;
    private String name;
    private String productType;
    private int periodLength;
    private String periodType;
    private boolean recurring;
    private Product trialProduct;

    public Product(String productId_, double amount_, String currencyCode_, String name_, String productType_, int periodLength_, String periodType_, boolean recurring_, Product trialProduct_) {
        productId = productId_;
        amount = Base.round(amount_, 2);
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
    public double getAmount() {
        return amount;
    }
    /**
     * @return string ISO currency code, e.g. USD
     */
    public String getCurrencyCode() {
        return currencyCode;
    }
    /**
     * @return string product name
     */
    public String getName() {
        return name;
    }
    /**
     * @return string product type, Product.TYPE_SUBSCRIPTION for recurring billing, Product.TYPE_FIXED for one-time
     */
    public String getType() {
        return productType;
    }
    /**
     * @return string product period type, e.g. Product.PERIOD_TYPE_MONTH
     */
    public String getPeriodType() {
        return periodType;
    }
    /**
     * @return string product period length, e.g. 3
     */
    public int getPeriodLength() {
        return periodLength;
    }
    /**
     * @return Product trial product
     */
    public Product getTrialProduct() {
        return trialProduct;
    }
    /**
     * @return bool if the product recurring
     */
    public boolean isRecurring() {
        return recurring;
    }
}