package com.paymentwall.java;

import java.util.ArrayList;

public abstract class Instance extends Messages {
    protected Config config = Config.getInstance();
    protected ArrayList<String> errors = new ArrayList<String>();

    public enum httpMethod {GET, POST}

    public String getErrorSummary() {
      String result = "";
      for (String each: getErrors()) {
          result += each + '\n';
      }
      return result;
    }

    protected Config getConfig() {
        if (config==null) { config = Config.getInstance(); }
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
