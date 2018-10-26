package com.paymentwall.java.signature;


import com.paymentwall.java.utils.HashUtils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Widget extends Abstract {
    public String process(LinkedHashMap<String, ArrayList<String>> params, int version) {
        String baseString = "";
        MessageDigest md;
        MessageDigest sha;

        if (version == VERSION_ONE) {
            baseString += params.containsKey(PARAM_UID) ? params.get(PARAM_UID).get(0) : "";
            baseString += getConfig().getPrivateKey();
            return HashUtils.md5String(baseString);
        } else {
            params = sortMultiDimensional(params);
            baseString = prepareParams(params, baseString);
            baseString += getConfig().getPrivateKey();

            if (version == Abstract.VERSION_THREE) return HashUtils.sha256String(baseString);
            else return HashUtils.md5String(baseString);
        }
    }

    public String prepareParams(HashMap<String, ArrayList<String>> params, String baseString) {
        for (Map.Entry<String, ArrayList<String>> pair : params.entrySet())
            if (pair.getValue().size() > 1) {
                int i = 0;
                for (String each : pair.getValue()) {
                    baseString += pair.getKey() + "[" + i + "]" + "=" + (each.equals("false") ? "0" : each);
                    i++;
                }
            } else
                baseString += pair.getKey() + "=" + (pair.getValue().get(0).equals("false") ? "0" : pair.getValue().get(0));
        return baseString;
    }
}
