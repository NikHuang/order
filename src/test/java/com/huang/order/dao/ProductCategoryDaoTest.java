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
import com.huang.order.test.MyFunction;
import com.huang.order.test.MyFunctionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
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
        List<ProductInfo> list1 = CommonFilterService.doFilterMethod(list,(item)-> (item.getCategoryType() == 2));
        Assert.assertNotEquals(0,list1.size());
        //排序 先比CategoryType 再比price
        Collections.sort(list,(item1,item2)->{
            if (!(item1.getCategoryType() == item2.getCategoryType())){
                return item1.getCategoryType().compareTo(item2.getCategoryType());
            }else {
                return item1.getProductPrice().compareTo(item2.getProductPrice());
            }
        });
        System.out.println("==================");
        String resultStr = MyFunctionService.stringConvert("abc",(s) -> s.toUpperCase());
        System.out.println("resultStr = " + resultStr);

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
        System.out.println("==================");
        int a =0;
        Runnable r = ()-> System.out.println("a:"+a);
        Consumer<String> con = t -> System.out.println(t);
        //只有一条语句的话 大括号和return 都可以省略
        Comparator<Integer> comparator = (x,y)-> 1;
        //1.8类型推断有所升级
        //lambada 表达式需要函数式接口的支持
        //若接口中只有一个抽象方法的借口，该接口就是函数式接口
        //函数式接口 可以用 @FunctionalInterface

    }
}