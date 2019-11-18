package com.huang.order.enums;


import lombok.Getter;

@Getter
public enum ProductStatusEnum {
    ONSALE(0,"在架"),
    OFFSALE(1,"已下架")
    ;
    private Integer code;

    private String msg;

    ProductStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
