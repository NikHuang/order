package com.huang.order.dao;

import com.huang.order.domain.ProductCategory;
import com.huang.order.domain.ProductInfo;
import com.huang.order.framework.utils.CommonUtil;
import com.huang.order.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    ProductCategoryService productCategoryService;

    @Autowired
    ProductInfoDao productInfoDao;


    @Test
    public void findOneTest(){
        ProductCategory productCategory = productCategoryService.findOne(1);
        Assert.assertNotEquals(null,productCategory);
    }

    @Test
    @Transactional
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("测试3");
        productCategory.setCategoryType(3);
        productCategoryService.save(productCategory);
    }

    @Test
    @Transactional
    public void updateTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(2);
        productCategory.setCategoryName("儿童专享");
        productCategory.setCategoryType(2);
        productCategoryService.update(productCategory);

    }
    @Test
    public void findListTest(){
        List<Integer> typeList = Arrays.asList(1,2,3);
        List<ProductCategory> list = productCategoryService.findList(typeList);
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void saveProductInfo(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(CommonUtil.generateObjectId(productInfo));
        productInfo.setProductName("酸奶");
        productInfo.setProductPrice(BigDecimal.valueOf(6.9));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("原味酸奶");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);
        productInfoDao.save(productInfo);
    }
    @Test
    public void findProductInfoByStatus(){
        List<ProductInfo> list = productInfoDao.findByStatus(0);
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findOne(){
        ProductInfo productInfo = productInfoDao.findOne("aabdd9bab9254d363344567eec0395a8");
        Assert.assertNotEquals(null,productInfo);
    }
}