package com.huang.order.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huang.order.dao.ProductInfoDao;
import com.huang.order.domain.ProductInfo;
import com.huang.order.dto.PageInfoDto;
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

}
