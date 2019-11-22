package com.huang.order.dao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huang.order.domain.ProductCategory;
import com.huang.order.domain.ProductInfo;
import com.huang.order.dto.PageInfoDto;
import com.huang.order.enums.ProductStatusEnum;
import com.huang.order.framework.utils.CommonUtil;
import com.huang.order.service.ProductCategoryService;
import com.huang.order.service.ProductInfoService;
import com.huang.order.test.CommonFilterService;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;

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
    ProductInfoService productInfoService;

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
        productInfo.setProductName("M豆");
        productInfo.setProductPrice(BigDecimal.valueOf(5.0));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("高钙牛奶");
        productInfo.setProductIcon("http://zzzzz.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);
        productInfoDao.save(productInfo);
    }
    @Test
    public void findProductInfoByStatus(){
        List<ProductInfo> list = productInfoDao.findByStatus(ProductStatusEnum.ONSALE.getCode());
//        list.forEach(item -> System.out.println(item.toString()));
        Collections.sort(list,(item1,item2) ->{
            if (item1.getCategoryType() < item2.getCategoryType()){
                return -1;
            }else if (item1.getCategoryType() > item2.getCategoryType()){
                return 1;
            }else {
                return item2.getProductPrice().compareTo(item1.getProductPrice());
            }
        } );
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findOne(){
        List<ProductInfo> list = productInfoDao.findAll();
        Assert.assertNotEquals(0,list.size());
        List<ProductInfo> list1 = CommonFilterService.doFilterMethod(list,(item)->{
            return item.getCategoryType() == 2;
        });
        Assert.assertNotEquals(0,list1.size());

    }
    @Test
    public void findAll(){
        PageInfo<ProductInfo> item = productInfoService.getPageInfoWithPage(new PageInfoDto(3,2));

        Assert.assertNotEquals(null,item);
    }
    @Test
    public void testSth(){
        List<Integer> list = Arrays.asList(1,3,2,4,5);
        Collections.sort(list);
        list.forEach(a -> System.out.println(a));
    }
}