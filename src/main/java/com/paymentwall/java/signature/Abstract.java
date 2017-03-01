package com.paymentwall.java.signature;

import java.util.*;

public abstract class Abstract extends com.paymentwall.java.Instance {
    public static final int VERSION_ONE = 1;
    public static final int VERSION_TWO = 2;
    public static final int VERSION_THREE = 3;
    public static final int DEFAULT_VERSION = VERSION_THREE;

    abstract String process(LinkedHashMap<String,ArrayList<String>> params, int version);

    abstract String prepareParams(HashMap<String,ArrayList<String>> params, String baseString);

    public String calculate(LinkedHashMap<String,ArrayList<String>> params, int version) {
        return process(params, version);
    }

    protected LinkedHashMap<String,ArrayList<String>> sortMultiDimensional(LinkedHashMap<String,ArrayList<String>> params) {
        TreeMap<String,ArrayList<String>> sorted = new TreeMap<String, ArrayList<String>>();

        sorted.putAll(params);
        params = new LinkedHashMap<String, ArrayList<String>>();
        params.putAll(sorted);

        return params;
    }
}
