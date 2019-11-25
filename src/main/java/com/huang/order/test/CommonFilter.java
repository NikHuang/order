package com.huang.order.test;

/**
 * Created by Administrator on 2019/11/22.
 */
@FunctionalInterface
public interface CommonFilter<T> {

    boolean filterMethod(T t);
}
