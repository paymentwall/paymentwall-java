package com.paymentwall.java;

public class PaymentwallProductBuilder {

    private double amount = 0.0;
    private String currencyCode = "";
    private String name = "";
    private String productType = PaymentwallProduct.TYPE_FIXED;
    private int periodLength = 0;
    private String periodType = PaymentwallProduct.PERIOD_TYPE_DAY;
    private boolean recurring = false;
    private PaymentwallProduct trialProduct = null;
    private String productId;

    public PaymentwallProductBuilder(String productId_) { productId = productId_; }

    public PaymentwallProduct buildPaymentwallProduct() { return new PaymentwallProduct(productId, amount, currencyCode, name, productType, periodLength, periodType, recurring, trialProduct); }

    public void setAmount(double amount_) { amount = amount_; }
    public void setCurrencyCode(String currencyCode_) { currencyCode = currencyCode_; }
    public void setName(String name_) { name = name_; }
    public void setProductType(String productType_) { productType = productType_; }
    public void setPeriodLength(int periodLength_) { periodLength = periodLength_; }
    public void setPeriodType(String periodType_) { periodType = periodType_; }
    public void setRecurring(boolean recurring_) { recurring = recurring_; }
}
