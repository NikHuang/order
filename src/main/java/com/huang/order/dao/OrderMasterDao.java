package com.huang.order.dao;

import com.huang.order.domain.OrderMaster;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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



}
