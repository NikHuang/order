package com.huang.order.domain;

import com.huang.order.enums.OrderStatusEnum;
import com.huang.order.enums.PayStatusEnum;
import com.huang.order.framework.annotations.GenerateKey;
import com.huang.order.framework.utils.CommonUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2019/12/6.
 */
@Data
public class OrderMaster {

    //默认 生成一个ID
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    //默认为 新下单 状态 0
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    //默认为 未支付 状态 0
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    private Date createTime;

    private Date updateTime;


}
