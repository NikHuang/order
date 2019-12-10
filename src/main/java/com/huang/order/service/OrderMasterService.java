package com.huang.order.service;

import com.github.pagehelper.PageInfo;
import com.huang.order.dao.OrderMasterDao;
import com.huang.order.domain.OrderMaster;
import com.huang.order.dto.PageInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/12/10.
 */
@Service
public class OrderMasterService {

    @Autowired
    OrderMasterDao orderMasterDao;

    public PageInfo<OrderMaster> findOrderMasterByOpenidWithPage(PageInfoDto pageInfoDto, String openId){
        List<OrderMaster> list = orderMasterDao.findOrderMasterByOpenidWithPage(openId);
        return new PageInfo<>(list);
    }
}
