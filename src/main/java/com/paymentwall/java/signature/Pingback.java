package com.paymentwall.java.signature;

import com.paymentwall.java.Config;
import com.paymentwall.java.utils.HashUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Pingback extends Abstract  {
    public String process(LinkedHashMap<String,ArrayList<String>> params, int version) {
        String baseString = "";

        params.remove(PARAM_SIG);

        if (version==VERSION_TWO || version==VERSION_THREE) params = sortMultiDimensional(params);

        baseString = prepareParams(params, baseString);

        baseString += getConfig().getPrivateKey();

        if (version == Abstract.VERSION_THREE) return HashUtils.sha256String(baseString);
        else return HashUtils.md5String(baseString);
    }

    public String prepareParams(HashMap<String,ArrayList<String>> params, String baseString) {
        for (Map.Entry<String, ArrayList<String>> pair : params.entrySet())
            if ( pair.getValue().size()>1 || (pair.getKey().equals(PARAM_GOODSID)&&getApiType() == Config.API_CART) ) {
                int i = 0;
                for (String each : pair.getValue()) {
                    baseString += pair.getKey() + "[" + i + "]" + "=" + each;
                    i++;
                }
            }
            else baseString += pair.getKey() + "=" + pair.getValue().get(0);
        return baseString;
    }
}
