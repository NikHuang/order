package com.huang.order.dto;

import com.huang.order.domain.OrderDetail;
import com.huang.order.domain.OrderMaster;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2019/12/11.
 */
@Data
public class OrderDto extends OrderMaster {

    List<OrderDetail> orderDetails;
}
