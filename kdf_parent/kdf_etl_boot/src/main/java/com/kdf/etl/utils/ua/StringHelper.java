package com.kdf.etl.utils.ua;

public class StringHelper {
    public static String getObjectValue(Object obj){
        return obj==null?"":obj.toString();
    }
    public static String toUpperCaseFirst(String old){   // 将单词的首字母大写
        return old.substring(0,1).toUpperCase() + old.substring(1);
    }
    public static boolean isEmpty(String str){
        boolean b = false;
        if(str == null || str.trim().length() <= 0){
            b = true;
        }
        return b;
    }
}