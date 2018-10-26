package com.paymentwall.java;

public class ProductBuilder {
    private double amount = 0.0;
    private String currencyCode = "";
    private String name = "";
    private String productType = Product.TYPE_FIXED;
    private int periodLength = 0;
    private String periodType = Product.PERIOD_TYPE_DAY;
    private boolean recurring = false;
    private Product trialProduct = null;
    private String productId;

    public ProductBuilder(String productId_) { productId = productId_; }

    public Product build() { return new Product(productId, amount, currencyCode, name, productType, periodLength, periodType, recurring, trialProduct); }

    public void setAmount(double amount_) { amount = amount_; }
    public void setCurrencyCode(String currencyCode_) { currencyCode = currencyCode_; }
    public void setName(String name_) { name = name_; }
    public void setProductType(String productType_) { productType = productType_; }
    public void setPeriodLength(int periodLength_) { periodLength = periodLength_; }
    public void setPeriodType(String periodType_) { periodType = periodType_; }
    public void setRecurring(boolean recurring_) { recurring = recurring_; }
    public void setTrialProduct(Product trialProduct_) { trialProduct = trialProduct_; }
}
