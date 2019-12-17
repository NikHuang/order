package com.huang.order.dao;

import com.huang.order.domain.OrderMaster;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Primary;

import java.util.List;

/**
 * Created by Administrator on 2019/12/6.
 */
@Mapper
public interface OrderMasterDao {

    //通过 openid查询orderMaster分页信息
    List<OrderMaster> findOrderMasterByOpenidWithPage(@Param("openid") String openid);

    //保存orderMaster信息
    void save(OrderMaster orderMaster);

    //通过orderId查询单个orderMaster
    OrderMaster findOneByOrderId(@Param("orderId") String orderId);

    //修改订单状态
    void changeOrderStatusByOrderId(@Param("orderStatus") Integer orderStatus,
                                    @Param("orderId") String orderId);


}
