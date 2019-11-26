package com.huang.order.test;

import com.huang.order.domain.ProductInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by Administrator on 2019/11/25.
 */
@Service
public class MyFunctionService {

    public static String stringConvert(String s,MyFunction myFunction){
        return myFunction.stringConvertHandler(s);
    }

    public static Long doTestMethod(Long t1,Long t2,TestMyFunction<Long,Long> trTestMyFunction){
           return trTestMyFunction.doTestMethod(t1,t2);
    }

    public static void doConsumerTest(String str, Consumer<String> consumer){
        consumer.accept(str);
    }
    //num决定数量 Supplier决定规则
    public static List<Integer>  doSupplierTest(Integer num,Supplier<Integer> su){
      List<Integer> l = new ArrayList<>();
        for (Integer i = 0; i < num; i++) {
            l.add(su.get());
        }
        return l;
    }
    //Function处理
    public static Integer doFunctionTest(String str, Function<String,Integer> function){
        return function.apply(str);
    }

    //Predicate处理
    public static boolean doPredicateTest(String str, Predicate<String> pre){
        return pre.test(str);
    }
    public static List<String> doPredicateList(List<String> l, Predicate<String> pre){
        List<String> l1 = new ArrayList<>();
        l.forEach((x)->{
            if(pre.test(x)){
                l1.add(x);
            }
        });
        return l1;
    }
    public static List<ProductInfo> filterProductInfo(List<ProductInfo> lp,Predicate<ProductInfo> pre){
        List<ProductInfo> lp1 = new ArrayList<>();
        lp.forEach((item)->{
            if(pre.test(item)){
                lp1.add(item);
            }
        });
        return lp1;
    }
    public static<T>  List<T> filterByT(List<T> listT,Predicate<T> pre){
        List<T> lp1 = new ArrayList<>();
        listT.forEach((item)->{
            if(pre.test(item)){
                lp1.add(item);
            }
        });
        return lp1;
    }
}
