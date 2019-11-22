package com.huang.order.test;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/22.
 */
@Service
public class CommonFilterService {

    public static  <T> List<T> doFilterMethod(List<T> list,CommonFilter<T> c){
          List<T> list1 = new ArrayList<T>();
          list.forEach((item)->{
              if(c.filterMethod(item)){
                  list1.add(item);
              }
          });
          return list1;
    }
}
