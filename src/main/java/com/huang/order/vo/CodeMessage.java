package com.huang.order.vo;

import lombok.Data;

/**
 * Created by Administrator on 2019/12/5.
 */
@Data
public class CodeMessage {

    private Integer code;

    private String msg;

    public CodeMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    //按模块划分
    //通用模块
    public static CodeMessage SUCCESS = new CodeMessage(0,"操作成功");
}
