package com.paymentwall.java;

import java.util.ArrayList;

public abstract class Instance extends Messages {
    protected Config config;
    protected ArrayList<String> errors = new ArrayList<String>();

    public enum httpMethod {GET, POST}

    protected Instance() {
        this(Config.getInstance());
    }

    public Instance(Config config) {
        this.config = config;
    }

    public String getErrorSummary() {
      String result = "";
      for (String each: getErrors()) {
          result += each + '\n';
      }
      return result;
    }

    protected Config getConfig() {
        return config;
    }

    protected String getApiBaseUrl() { return getConfig().getApiBaseUrl(); }

    protected int getApiType() { return getConfig().getLocalApiType(); }

    protected String getPublicKey() { return getConfig().getPublicKey(); }

    protected String getPrivateKey() { return getConfig().getPrivateKey(); }

    protected void appendToErrors(String error) { errors.add(error); }

    protected ArrayList<String> getErrors() { return errors; }

    protected ArrayList<String> sList(final String val) {
        return new ArrayList<String>(){{ add(val); }};
    }
}
