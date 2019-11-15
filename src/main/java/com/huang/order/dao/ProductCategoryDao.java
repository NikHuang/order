package com.huang.order.dao;

import com.huang.order.domain.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2019/11/14.
 */
@Mapper
public interface ProductCategoryDao {

    ProductCategory findOne(@Param("categoryId") Integer categoryId);

    void save(ProductCategory productCategory);

    void update(ProductCategory productCategory);

    List<ProductCategory> findList(@Param("typeList") List<Integer> typeList);

    List<ProductCategory> findAll();
}
