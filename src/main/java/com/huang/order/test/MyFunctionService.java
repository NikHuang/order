package com.huang.order.test;

import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/11/25.
 */
@Service
public class MyFunctionService {

    public static String stringConvert(String s,MyFunction myFunction){
        return myFunction.stringConvertHandler(s);
    }
}
