package com.huang.order.framework.annotations;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2019/12/9.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GenerateKey {
}
