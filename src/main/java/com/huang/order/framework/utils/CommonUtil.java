package com.huang.order.framework.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

/**
 * Created by Administrator on 2019/11/15.
 */
public class CommonUtil {
    //UUID
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        return uuid;
    }

    //md5加密
    private static String md5(String input){
        return  DigestUtils.md5Hex(input);
    }

    public static <T> String generateObjectId(T object){
        String objectName = object.getClass().getSimpleName();
        String uid = getUUID();
        String plaintextId = objectName+uid;
        return md5(plaintextId);
    }
}
