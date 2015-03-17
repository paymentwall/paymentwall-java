package com.paymentwall.java;

import java.util.ArrayList;
import java.util.HashMap;

public class WidgetBuilder {
    String userId;
    String widgetCode;
    ArrayList<Product> products = new ArrayList<Product>();
    HashMap<String, String> extraParams = new HashMap<String, String>();

    public WidgetBuilder(String userId_, String widgetCode_) {
        userId = userId_;
        widgetCode = widgetCode_;
    }

    public Widget build() { return new Widget(userId,widgetCode,products,extraParams); }

    public void setProducts(ArrayList<Product> products_) { products = products_; }

    public void setProduct(final Product products_) { products = new ArrayList<Product>(){{add(products_);}}; }

    public void setExtraParams(HashMap<String,String> extraParams_) { extraParams = extraParams_; }
}
