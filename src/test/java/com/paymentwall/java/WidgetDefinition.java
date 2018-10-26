package com.paymentwall.java;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.HashMap;

public class WidgetDefinition {
    private String productId = "id";
    private String languageCode = "";
    private String widgetCode = "p10";
    private String widgetSignatureVersion = "";
    private String productName = "";
    private String widgetHtmlContent;
    private Widget widget;

    public WidgetDefinition() {}

    @And("^Widget HTML content is loaded$")
    public void Widget_HTML_content_is_loaded() throws Exception {
        this.widgetHtmlContent = new HttpAction(new Charge(), new HashMap<String, String>(), new ArrayList<String>()).get(widget.getUrl());
    }

    @Given("^Public key \"([^\"]*)\"$")
    public void App_key(String appKey) {
        Base.setAppKey(appKey);
    }

    @And("^Secret key \"([^\"]*)\"$")
    public void Secret_key(String secretKey) {
        Base.setSecretKey(secretKey);
    }

    @And("^API type \"([^\"]*)\"$")
    public void API_type(String type) {
        Base.setApiType(
                Integer.parseInt(type)
        );
    }

    @And("^Widget signature version \"([^\"]*)\"$")
    public void Widget_signature_version(String widgetSignature) {
        this.widgetSignatureVersion = widgetSignature;
    }

    @When("^Widget is constructed$")
    public void Widget_is_constructed() {
        this.widget = new Widget(
                getUserId(),
                getWidgetCode(),
                getProduct(),
                new HashMap<String, String>() {{
                    put("email", "user@hostname.com");
                    put("sign_version", getWidgetSignatureVersion());
                    put("lang", getLanguageCode());
                }}
        );
    }

    @Then("^Widget HTML content should not contain \"([^\"]*)\"$")
    public void Widget_HTML_content_should_not_contain(String str) throws Exception {
        if (this.widgetHtmlContent.contains(str)) {
            throw new Exception(
                    "Widget HTML content contains \"" + str + "\" (URL: " + this.widget.getUrl() +")"
            );
        }
    }

    @And("^Widget HTML content should contain \"([^\"]*)\"$")
    public void Widget_HTML_content_should_contain(String str) throws Exception {
        if (!this.widgetHtmlContent.contains(str)) {
            throw new Exception(
                    "Widget HTML content doesn\'t contain \"" + str + "\" (URL: " + this.widget.getUrl() +")"
            );
        }
    }

    @And("^Widget code \"([^\"]*)\"$")
    public void Widget_code(String widgetCode) {
        this.widgetCode = widgetCode;
    }

    @And("^Language code \"([^\"]*)\"$")
    public void Language_code(String languageCode) {
        this.languageCode = languageCode;
    }

    @Then("^Widget URL should contain \"([^\"]*)\"$")
    public void Widget_URL_should_contain(String str) throws Exception {
        if (!this.widget.getUrl().contains(str)) {
            throw new Exception(
                    "Widget URL doesn\'t contain \"" + str + "\" (URL: " + this.widget.getUrl() + ")"
            );
        }
    }

    public String getUserId() {
        return "test user";
    }

    public String getWidgetCode() {
        return widgetCode;
    }

    public ArrayList<Product> getProduct() {
        return new ArrayList<Product>(){{ add(new Product(productId, 1.0, "USD", productName, "fixed", 0, "", false, null)); }};
    }

    public String getWidgetSignatureVersion() {
        return widgetSignatureVersion;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    @And("^Product name \"([^\"]*)\"$")
    public void Product_name(String str) {
        this.productName = str;
    }

    @And("^Product id \"([^\"]*)\"$")
    public void Product_id(String id) {
        this.productId = id;
    }
}
