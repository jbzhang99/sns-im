package com.inga.util;

/**
 *
 * Date  2018/1/15
 * Time  上午10:17
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
