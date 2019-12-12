package com.huang.order.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huang.order.dao.ProductInfoDao;
import com.huang.order.domain.OrderDetail;
import com.huang.order.domain.OrderMaster;
import com.huang.order.domain.ProductInfo;
import com.huang.order.dto.PageInfoDto;
import com.huang.order.framework.exceptions.CommonException;
import com.huang.order.vo.CodeMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/11/15.
 */
@Service
public class ProductInfoService {

    @Autowired
    ProductInfoDao productInfoDao;

    public PageInfo<ProductInfo> getPageInfoWithPage(PageInfoDto pageInfoDto){
        List<ProductInfo> list = productInfoDao.findAllWithPage();
        return new PageInfo<>(list);
    }

    public List<ProductInfo> getUpAll(Integer status){
        return productInfoDao.findByStatus(status);
    }

    public ProductInfo findOneByProductId(String productId){
        return productInfoDao.findOne(productId);
    }

    public void reduceStock(Integer count,String productId){
         productInfoDao.reduceStock(count,productId);
    }

    public void reduceStockBatch(List<OrderDetail> orderDetailList){
        //先判断物品是否存在 再判断库存是否足够 再减库存
        orderDetailList.stream().forEach(e->{
            String productId = e.getProductId();
            ProductInfo p = productInfoDao.findOne(productId);
            if (p == null){
                throw new CommonException(CodeMessage.NOSUCHGOODS);
            }
            if (e.getProductQuantity() > p.getProductStock()){
                throw new CommonException(CodeMessage.NOSTOCK);
            }
        });
        productInfoDao.reduceStockBatch(orderDetailList);
    }


}
