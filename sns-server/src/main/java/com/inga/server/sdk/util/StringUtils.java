package com.inga.server.sdk.util;

/**
 * String 公共类
 * Created by abing on 2017/6/19.
 */
public class StringUtils {

    public static boolean isEmpty(String str){
        if (str == null || str.length() == 0 ){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isNotEmpty(String str){
        return  !isEmpty(str);
    }

}
