package com.huang.order.dao;

import com.huang.order.domain.ProductCategory;
import com.huang.order.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2019/11/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryDaoTest {

    @Autowired
    ProductCategoryDao productCategoryDao;


    @Test
    public void findOneTest(){
        ProductCategory productCategory = productCategoryDao.findOneTest(1);
        System.out.println(productCategory);
    }

    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("测试2");
        productCategory.setCategoryType(2);
        productCategoryDao.saveTest(productCategory);
        int testId = productCategory.getCategoryId();
        log.info(testId+"==================");

    }

    @Test
    public void updateTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(1);
        productCategory.setCategoryName("测试9");
        productCategory.setCategoryType(9);
        productCategoryDao.updateTest(productCategory);

    }
    @Test
    public void findListTest(){
        List<Integer> typeList = Arrays.asList(1,2);
        List<ProductCategory> l = productCategoryDao.findListTest(typeList);
        Assert.assertNotEquals(0,l.size());
    }

}