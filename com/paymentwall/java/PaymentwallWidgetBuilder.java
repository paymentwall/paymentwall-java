package com.paymentwall.java;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class PaymentwallWidgetBuilder {
    String userId;
    String widgetCode;
    ArrayList<PaymentwallProduct> products = new ArrayList<PaymentwallProduct>();
    LinkedHashMap<String,ArrayList<String>> extraParams = new LinkedHashMap<String, ArrayList<String>>();

    public PaymentwallWidgetBuilder(String userId_, String widgetCode_) {
        userId = userId_;
        widgetCode = widgetCode_;
    }

    public PaymentwallWidget build() {
        return new PaymentwallWidget(userId,widgetCode,products,extraParams);
    }

    public void setProducts(ArrayList<PaymentwallProduct> products_) {
        products = products_;
    }

    public void setProduct(final PaymentwallProduct products_) { products = new ArrayList<PaymentwallProduct>(){{add(products_);}}; }

    public void setExtraParams(LinkedHashMap<String,ArrayList<String>> extraParams_) {
        extraParams = extraParams_;
    }
}
