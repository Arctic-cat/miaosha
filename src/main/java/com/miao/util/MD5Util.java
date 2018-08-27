package com.miao.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public class MD5Util {
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "0sf4gh12s6ha";

    public static String inputPassToFormPass(String inputPass) {
        String str= ""+salt.charAt(0) + salt.charAt(3) + inputPass + salt.charAt(7) + salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass,String saltDB) {
        String str= ""+saltDB.charAt(1) + saltDB.charAt(4) + formPass + saltDB.charAt(6) + saltDB.charAt(5);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass,String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        return formPassToDBPass(formPass,saltDB);
    }

    public static void main(String[] args) {

    }


}
