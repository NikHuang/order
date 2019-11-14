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

    ProductCategory findOneTest(@Param("categoryId") Integer categoryId);

    void saveTest(ProductCategory productCategory);

    void updateTest(ProductCategory productCategory);

    List<ProductCategory> findListTest(@Param("typeList") List<Integer> typeList);
}
