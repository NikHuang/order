package com.huang.order;





import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.logging.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {

//    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void doLoggerTest(){
        log.info("info=================");
        log.debug("debug==================");
        log.error("error======================");
        //LogBack中使用 {} 做占位符
        log.info("name: {} , password: {}","123","345");
    }

}
