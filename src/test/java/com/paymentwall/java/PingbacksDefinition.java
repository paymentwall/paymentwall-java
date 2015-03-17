package com.paymentwall.java;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class PingbacksDefinition extends Instance {
    private LinkedHashMap<String, String[]> pingbackParameters = new LinkedHashMap<String, String[]>();
    private String pingbackIpAddress = "";
    private Pingback pingback;

    @And("^Pingback method \"([^\"]*)\" should return \"([^\"]*)\"$")
    public void Pingback_method_should_return(String method, String value) throws Exception {
        if (!this.pingback.getClass().getMethod(method).invoke(pingback).toString().equals(value)) {
            throw new Exception(
                    "Pingback method " + method + " returned " + this.pingback.getClass().getMethod(method).invoke(pingback).toString()
            );
        }
    }

    @And("^Pingback IP address \"([^\"]*)\"$")
    public void Pingback_IP_address(String ipAddress) {
        this.pingbackIpAddress = ipAddress;
    }

    @When("^Pingback is constructed$")
    public void Pingback_is_constructed() {
        this.pingback = new Pingback(this.pingbackParameters, this.pingbackIpAddress);
    }

    @Then("^Pingback validation result should be \"([^\"]*)\"$")
    public void Pingback_validation_result_should_be(String bool) throws Exception {
        boolean validate = this.pingback.validate();
        if (validate != bool.equals("true")) {
            throw new Exception(
                    "Pingback Validation returns "+ (validate?"true":"false")+ (!validate ? ("\r\nErrors:"+this.pingback.getErrorSummary()):"")
            );
        }
    }

    @And("^Pingback GET parameters \"([^\"]*)\"$")
    public void Pingback_GET_parameters(String query) throws URISyntaxException {
        String url = "http://www.example.com/?" + query;
        List<NameValuePair> params = URLEncodedUtils.parse(
                new URI(url), "UTF-8");
        for (NameValuePair param : params) {
            final LinkedList<String> l = new LinkedList<String>();
            if (this.pingbackParameters.containsKey(param.getName()))
                for (int i = 0; i<pingbackParameters.get(param.getName()).length; i++)
                    l.add(pingbackParameters.get(param.getName())[i]);
            l.add(l.size(), param.getValue());
            this.pingbackParameters.put(param.getName(), new ArrayList<String>() {{
                addAll(l);
            }}.toArray(new String[l.size()]));
        }
    }
}
