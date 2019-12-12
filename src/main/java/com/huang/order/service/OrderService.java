package com.huang.order.service;

import com.huang.order.dao.OrderDetailDao;
import com.huang.order.dao.OrderMasterDao;
import com.huang.order.domain.OrderDetail;
import com.huang.order.domain.OrderMaster;
import com.huang.order.domain.ProductInfo;
import com.huang.order.dto.OrderDto;
import com.huang.order.framework.exceptions.CommonException;
import com.huang.order.framework.utils.CommonUtil;
import com.huang.order.vo.CodeMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2019/12/11.
 */
@Service
public class OrderService {

    @Autowired
    OrderMasterDao orderMasterDao;

    @Autowired
    OrderDetailDao orderDetailDao;

    @Autowired
    ProductInfoService productInfoService;

    @Autowired
    OrderMasterService orderMasterService;

    //1.创建订单
    @Transactional
    public boolean createOrder(OrderDto orderDto){

        OrderMaster orderMaster = new OrderMaster();
        String orderId = CommonUtil.generateObjectId(orderMaster);
        //步骤
        //先取orderDetail并计算价格
        List<OrderDetail> orderDetails = orderDto.getOrderDetails();
        final BigDecimal[] priceAmount = {new BigDecimal(0)};

        orderDetails = orderDetails.stream().map(e->{
            ProductInfo p = productInfoService.findOneByProductId(e.getProductId());
            if (p == null){
                throw new CommonException(CodeMessage.NOSUCHGOODS);
            }
            e.setOrderId(orderId);
            e.setDetailId(CommonUtil.generateObjectId(e));
            BigDecimal productPrice = p.getProductPrice();
            //算该商品总价
            BigDecimal orderDetailPrice = productPrice.multiply(new BigDecimal(e.getProductQuantity()));
            priceAmount[0] = priceAmount[0].add(orderDetailPrice);
            e.setProductPrice(orderDetailPrice);
            e.setProductIcon(p.getProductIcon());
            e.setProductName(p.getProductName());
            //返回orderDetail
            return e;
        }).collect(Collectors.toList());

        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(priceAmount[0]);
        //保存orderMaster
        orderMasterDao.save(orderMaster);
        //减库存
        productInfoService.reduceStockBatch(orderDetails);
        //保存orderDetail
        orderDetailDao.saveBatch(orderDetails);
        return true;

    }
    //2.查询单个订单

    //3.查询订单列表

    //4.取消订单

    //5.完结订单

    //6.支付订单
}
