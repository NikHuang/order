package com.huang.order.dao;

import com.huang.order.domain.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator on 2019/12/10.
 */
@Mapper
public interface OrderDetailDao {

    //保存订单详情
    void save(OrderDetail orderDetail);
}
