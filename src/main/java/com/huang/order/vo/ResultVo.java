package com.huang.order.vo;

import lombok.Data;

import java.util.function.Function;
import java.util.function.Supplier;

@Data
public class ResultVo<T> {

    private Integer code;

    private String msg;

    private T data;

    public ResultVo(CodeMessage codeMessage) {
        this.code = codeMessage.getCode();
        this.msg = codeMessage.getMsg();
    }

    public ResultVo(CodeMessage codeMessage, T data) {
        this(codeMessage);
        this.data = data;
    }

    public static ResultVo success(){
        return new ResultVo(CodeMessage.SUCCESS);
    }
    public static <T> ResultVo success(T data){
        return new ResultVo(CodeMessage.SUCCESS,data);
    }
    public static ResultVo error(CodeMessage codeMessage){
        return new ResultVo(codeMessage);
    }
    //一般发生错误的情况不会有返回数据 所以这个应该用不到
    public static <T> ResultVo error(CodeMessage codeMessage,T data){
        return new ResultVo(codeMessage,data);
    }

}
