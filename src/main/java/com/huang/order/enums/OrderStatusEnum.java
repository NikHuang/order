package com.huang.order.enums;

import lombok.Getter;

/**
 * Created by Administrator on 2019/12/6.
 */
@Getter
public enum OrderStatusEnum {
    NEW(0,"新下单"),
    FINISHED(1,"完结"),
    CANCEL(2,"取消")
    ;

    private Integer code;

    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
