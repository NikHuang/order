package com.huang.order;

import com.huang.order.dao.ProductCategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(MainApplication.class,args);
    }
}
