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

    //商品模块
    public static CodeMessage NOSTOCK = new CodeMessage(1001,"库存不足");
    public static CodeMessage NOSUCHGOODS = new CodeMessage(1002,"商品 %s 不存在");
    public static CodeMessage GOODSOFFSALE = new CodeMessage(1003,"商品 %s 已下架");

    //订单模块
    public static CodeMessage NOSUCHORDER =new CodeMessage(2001,"无该订单信息");
    public static CodeMessage CANNOTCANCEL = new CodeMessage(2002,"不能取消该订单，该订单已完结或已被取消");
    public static CodeMessage NOORDERDETAILS = new CodeMessage(2003,"无订单详情，无法取消订单");
    //系统异常
    public static CodeMessage SERVERERROR = new CodeMessage(9001,"系统异常");

    public CodeMessage fillArgs(Object... args){
        Integer code = this.getCode();
        String msg = String.format(this.getMsg(), args);
        return new CodeMessage(code,msg);
    }
}
