package com.paymentwall.java.Signature;

import com.paymentwall.java.Config;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

        MessageDigest sha;
        MessageDigest md;
        try {
            sha = MessageDigest.getInstance("SHA-256");
            sha.update(baseString.getBytes("UTF-8"));
            md = MessageDigest.getInstance("MD5");
            md.update(baseString.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
        if (version == Abstract.VERSION_THREE) return String.format("%064X", new BigInteger(1, sha.digest())).toLowerCase();

        return String.format("%032X", new BigInteger(1, md.digest())).toLowerCase();
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
