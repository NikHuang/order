package com.huang.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CartDto {

    private String productId;

    private String productName;

    private Integer productQuantity;

    public CartDto(String productId, String productName, Integer productQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.productQuantity = productQuantity;
    }
}
