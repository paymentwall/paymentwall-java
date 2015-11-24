package com.paymentwall.java.Signature;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Widget extends Abstract {
    public String process(LinkedHashMap<String,ArrayList<String>> params, int version) {
        String baseString = "";
        MessageDigest md;
        MessageDigest sha;

        if (version == VERSION_ONE) {
            baseString += params.containsKey(PARAM_UID) ? params.get(PARAM_UID).get(0) : "";
            baseString += getConfig().getPrivateKey();
            try {
                md = MessageDigest.getInstance("MD5");
                md.update(baseString.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return "";
            }
            return String.format("%032X", new BigInteger(1, md.digest())).toLowerCase();
        } else {
            params = sortMultiDimensional(params);
            baseString = prepareParams(params, baseString);
            baseString += getConfig().getPrivateKey();

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

            if (version == Abstract.VERSION_THREE)
                return String.format("%064X", new BigInteger(1, sha.digest())).toLowerCase();

            return String.format("%032X", new BigInteger(1, md.digest())).toLowerCase();
        }
    }

    public String prepareParams(HashMap<String,ArrayList<String>> params, String baseString) {
        for (Map.Entry<String, ArrayList<String>> pair : params.entrySet())
            if ( pair.getValue().size()>1 ) {
                int i = 0;
                for (String each : pair.getValue()) {
                    baseString += pair.getKey() + "[" + i + "]" + "=" + (each.equals("false") ? "0" : each);
                    i++;
                }
            }
            else baseString += pair.getKey() + "=" + (pair.getValue().get(0).equals("false") ? "0" : pair.getValue().get(0));
        return baseString;
    }
}
