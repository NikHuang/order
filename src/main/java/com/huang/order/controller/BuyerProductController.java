package com.huang.order.controller;

import com.huang.order.domain.ProductCategory;
import com.huang.order.domain.ProductInfo;
import com.huang.order.enums.ProductStatusEnum;
import com.huang.order.service.ProductCategoryService;
import com.huang.order.service.ProductInfoService;
import com.huang.order.vo.ProductInfoVo;
import com.huang.order.vo.ProductVo;
import com.huang.order.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    ProductInfoService productInfoService;

    @Autowired
    ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ResultVo list(){
        //1.查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.getUpAll(ProductStatusEnum.ONSALE.getCode());
        //2.查询所有类目
        List<Integer> categoryList = productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = productCategoryService.findList(categoryList);
        //3.以类目为基准拼接数据
       List<ProductVo> productVoList =  productCategoryList.stream().map( e->{
            ProductVo productVo= new ProductVo();
            productVo.setCategoryName(e.getCategoryName());
            productVo.setCategoryType(e.getCategoryType());
            List<ProductInfoVo> productInfoVos = productInfoList.stream().filter(x->x.getCategoryType() == e.getCategoryType()).map( y->{
                ProductInfoVo productInfoVo = new ProductInfoVo();
                //复制对象
                BeanUtils.copyProperties(y,productInfoVo);
                return productInfoVo;
            }).collect(Collectors.toList());
            productVo.setProductInfoVoList(productInfoVos);
            return productVo;
        }).collect(Collectors.toList());
        return ResultVo.success(productVoList);
    }
}
