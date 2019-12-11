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

    //1.创建订单
    @Transactional
    public boolean createOrder(OrderDto orderDto){

        OrderMaster orderMaster = new OrderMaster();
        String orderId = CommonUtil.generateObjectId(orderMaster);
        //步骤
        //先取orderDetail并计算价格
        List<OrderDetail> orderDetails = orderDto.getOrderDetails();
        final BigDecimal[] priceAmount = {new BigDecimal(0)};
        orderDetails.stream().forEach(e->{
            ProductInfo p = productInfoService.findOneByProductId(e.getProductId());
            if (p == null){
                throw new CommonException(CodeMessage.NOSUCHGOODS);
            }
            Integer stock = p.getProductStock();
            Integer productQuantity = e.getProductQuantity();
            //判断库存是否足够
            if (stock < productQuantity){
                throw new CommonException(CodeMessage.NOSTOCK);
            }
            e.setOrderId(orderId);
            e.setDetailId(CommonUtil.generateObjectId(e));
            BigDecimal productPrice = p.getProductPrice();
            //算该商品总价
            BigDecimal orderDetailPrice = productPrice.multiply(new BigDecimal(productQuantity));
            priceAmount[0] = priceAmount[0].add(orderDetailPrice);
            e.setProductPrice(orderDetailPrice);
            e.setProductIcon(p.getProductIcon());
            //保存orderDetail
            orderDetailDao.save(e);
            //减库存
            productInfoService.reduceStock(productQuantity,e.getProductId());
        });
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(priceAmount[0]);
        orderMasterDao.save(orderMaster);
        return true;

    }
    //2.查询单个订单

    //3.查询订单列表

    //4.取消订单

    //5.完结订单

    //6.支付订单
}
