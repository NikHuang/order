package com.huang.order.test;

/**
 * Created by Administrator on 2019/11/26.
 */
@FunctionalInterface
public interface TestMyFunction<T,R> {

        R doTestMethod(T t1,T t2);
}
