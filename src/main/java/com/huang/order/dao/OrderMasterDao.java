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

    List<OrderMaster> findOrderMasterByOpenid(@Param("openid") String openid);
}
