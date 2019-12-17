package com.huang.order.service;

import com.huang.order.dao.OrderDetailDao;
import com.huang.order.dao.OrderMasterDao;
import com.huang.order.domain.OrderDetail;
import com.huang.order.domain.OrderMaster;
import com.huang.order.domain.ProductInfo;
import com.huang.order.dto.CartDto;
import com.huang.order.dto.OrderDto;
import com.huang.order.enums.OrderStatusEnum;
import com.huang.order.enums.ProductStatusEnum;
import com.huang.order.framework.exceptions.CommonException;
import com.huang.order.framework.utils.CommonUtil;
import com.huang.order.vo.CodeMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
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

        //创建cartDto
        List<CartDto> cartDtoList = new ArrayList<>();

        orderDetails = orderDetails.stream().map(e->{
            ProductInfo p = productInfoService.findOneByProductId(e.getProductId());
            if (p == null){
                throw new CommonException(CodeMessage.NOSUCHGOODS.fillArgs(e.getProductName()));
            }
            if (p.getProductStatus() == ProductStatusEnum.OFFSALE.getCode()){
                throw new CommonException(CodeMessage.GOODSOFFSALE.fillArgs(e.getProductName()));
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

            CartDto cartDto = new CartDto(e.getProductId(),e.getProductName(),e.getProductQuantity());
            cartDtoList.add(cartDto);
            //返回orderDetail
            return e;
        }).collect(Collectors.toList());

        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(priceAmount[0]);


        //保存orderMaster
        orderMasterDao.save(orderMaster);
        //减库存
        productInfoService.reduceStockBatch(cartDtoList);
        //保存orderDetail
        orderDetailDao.saveBatch(orderDetails);
        return true;

    }
    //2.查询单个订单
    public OrderMaster searchOrder(String productId){
        OrderMaster orderMaster = orderMasterDao.findOneByOrderId(productId);
        if (orderMaster == null){
            throw new CommonException(CodeMessage.NOSUCHORDER);
        }
        return orderMaster;
    }
    //3.查询订单列表

    //4.取消订单
    @Transactional
    public boolean cancelOrder(String orderId){
        //查询 要取消的订单
        OrderMaster orderMaster = orderMasterDao.findOneByOrderId(orderId);
        //判断是否能被取消 只有新下单的可以被取消
        if (orderMaster.getOrderStatus() != OrderStatusEnum.NEW.getCode()){
            throw new CommonException(CodeMessage.CANNOTCANCEL);
        }
        //可以取消
        //修改 orderMaster 状态为已取消
        orderMasterDao.changeOrderStatusByOrderId(OrderStatusEnum.CANCEL.getCode(),orderId);
        //把已经扣除的库存 加回来
        //先取orderDetail
        List<OrderDetail> orderDetails = orderDetailDao.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetails)){
            throw new CommonException(CodeMessage.NOORDERDETAILS);
        }
        List<CartDto> cartDtoList = orderDetails.stream().map(e->
             new CartDto(e.getProductId(),e.getProductName(),e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.addStockBatch(cartDtoList);
        //退款
        // TODO: 2019/12/14

        return true;
    }

    //5.完结订单

    //6.支付订单
}
