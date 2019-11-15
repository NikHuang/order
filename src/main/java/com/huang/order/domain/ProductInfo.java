package com.huang.order.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2019/11/15.
 */
@Data
public class ProductInfo {

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer productStatus;

    private Integer categoryType;
}
