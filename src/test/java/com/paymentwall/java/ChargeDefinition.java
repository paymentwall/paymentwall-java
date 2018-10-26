package com.paymentwall.java;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class ChargeDefinition {
    private String cvv;
    private String token;
    private String chargeId;

    public ChargeDefinition() {
        token = "";
        chargeId = "";
        cvv = "123";
    }

    @Given("^Brick Public key \"([^\"]*)\"$")
    public void Brick_Public_key(final String public_key) {
        Config.getInstance().set(new HashMap<String, String>(){{
            put("public_key", public_key);
        }});
    }

    @And("^Brick Private key \"([^\"]*)\"$")
    public void Brick_Private_key(final String private_key) {
        Config.getInstance().set(new HashMap<String, String>(){{
            put("private_key", private_key);
        }});
    }

    @When("^test token is retrieved$")
    public void test_token_is_retrieved() throws Exception {
        OneTimeToken token = new OneTimeToken();
        token = (OneTimeToken)(token.create(getTestDetailsForOneTimeToken()));
        this.token = token.getToken();
        if (!token.getToken().startsWith("ot_")) {
            throw new Exception(token.getPublicData().toString());
        }
    }

    private HashMap<String, String> getTestDetailsForOneTimeToken() {
        return new HashMap<String, String>(){{
            put("public_key",Config.getInstance().getPublicKey());
            putAll(getTestCardDetails());
        }};
    }

    private HashMap<String,String> getTestCardDetails() {
        return new HashMap<String, String>(){{
            put("card[number]" , "4242424242424242");
            put("card[exp_month]" , "11");
            put("card[exp_year]" , "19");
            put("card[cvv]" , cvv);
        }};
    }

    @Then("^charge should be successful$")
    public void charge_should_be_successful() throws Exception {
        Charge charge = getChargeObject();
        if (!charge.isSuccessful()) {
            throw new Exception(charge.getPublicData().toString());
        }
    }

    protected Charge getChargeObject() throws Exception {
        Charge charge = new Charge();
        return (Charge)charge.create(getTestDetailsForCharge());
    }

    private HashMap<String, String> getTestDetailsForCharge() {
        return new HashMap<String, String>(){{
            put("token" , token);
            put("email" , "test@user.com");
            put("currency" , "USD");
            put("amount" , "9.99");
            put("browser_domain" , "https://www.paymentwall.com");
            put("browser_ip" , "72.229.28.185");
            put("description" , "Test Charge");
        }};
    }

    @Then("^charge should be refunded$")
    public void charge_should_be_refunded() throws Exception {
        Charge chargeToBeRefunded = (Charge)(new Charge(chargeId).refund());
        if (!chargeToBeRefunded.isRefunded()) {
            throw new Exception(chargeToBeRefunded.getPublicData().toString());
        }
    }

    @And("^CVV code \"([^\"]*)\"$")
    public void CVV_code(String cvv) {
        this.cvv = cvv;
    }

    @Then("^I see this error message \"([^\"]*)\"$")
    public void I_see_this_error_message(String msg) throws Exception {
        Charge charge = getChargeObject();
        JSONObject errors = charge.getPublicData();
        if (errors.containsKey("error"))
            if (((JSONObject)errors.get("error")).containsKey("message"))
                if (((JSONObject) errors.get("error")).get("message").equals(msg))
                    throw new Exception(charge.getPublicData().toString());
    }

    @And("^chargeID \"([^\"]*)\"$")
    public void chargeID(String chargeId) {
        this.chargeId = chargeId;
    }
}
