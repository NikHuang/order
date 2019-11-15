package com.huang.order.service;

import com.huang.order.dao.ProductCategoryDao;
import com.huang.order.domain.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    @Autowired
    ProductCategoryDao productCategoryDao;

    public ProductCategory findOne(Integer categoryId){
        return productCategoryDao.findOne(categoryId);
    }

    public List<ProductCategory> findAll(){

        return productCategoryDao.findAll();
    }

    public List<ProductCategory> findList(List<Integer> typeList){
        return productCategoryDao.findList(typeList);
    }

    public ProductCategory save(ProductCategory productCategory){
        productCategoryDao.save(productCategory);
        return productCategory;
    }

    public ProductCategory update(ProductCategory productCategory){
        productCategoryDao.update(productCategory);
        return productCategory;
    }


}
