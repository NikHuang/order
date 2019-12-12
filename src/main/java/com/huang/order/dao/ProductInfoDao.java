package com.huang.order.dao;

import com.github.pagehelper.PageInfo;
import com.huang.order.domain.OrderDetail;
import com.huang.order.domain.ProductInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.*;

import java.util.List;

/**
 * Created by Administrator on 2019/11/15.
 */
@Mapper
public interface ProductInfoDao {

    void save(ProductInfo productInfo);

    List<ProductInfo> findByStatus(@Param("productStatus") Integer status);

    ProductInfo findOne(@Param("productId") String productId);

    List<ProductInfo> findAll();

    List<ProductInfo> findAllWithPage();

    //加库存
    void addStock(@Param("count")Integer count,@Param("productId") String productId);
    //减库存
    void reduceStock(@Param("count")Integer count,@Param("productId") String productId);

    //加库存 批量
    void addStockBatch(@Param("detailList") List<OrderDetail> orderDetailList);
    //加库存 批量
    void reduceStockBatch(@Param("detailList") List<OrderDetail> orderDetailList);

}
