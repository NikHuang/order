package com.huang.order.framework.aspects;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huang.order.dto.PageInfoDto;
import com.huang.order.framework.annotations.GenerateKey;
import com.huang.order.framework.context.PageInfoContext;
import com.huang.order.framework.utils.CommonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.rmi.activation.Activator;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2019/11/18.
 */
@Component
@Aspect
public class CommonAspect {

     @Before("execution(public * com.huang.order.service.*.*WithPage(..))")
     public void pageContextAop(JoinPoint point) throws Throwable {
         //反射获取参数
         Object[] os = point.getArgs();
         for (Object o : os) {
           if (o.getClass() == PageInfoDto.class){
               PageInfoDto pageInfoDto = (PageInfoDto) o;
               PageInfoContext.setPageInfo(pageInfoDto);
               break;
           }
         }
     }

    @Around("execution(public * com.huang.order.dao.*.*WithPage(..))")
    public Object queryWithPageAop(ProceedingJoinPoint point) throws Throwable {
        PageInfoDto pageInfoDto = PageInfoContext.getPageInfo();
        if (pageInfoDto == null){
            //这里应该抛个异常
            return null;
        }
        PageHelper.startPage(pageInfoDto.getCurrentPage(),pageInfoDto.getPageSize());
        return point.proceed();
    }

    @Around("execution(public * com.huang.order.dao.*.*WithGenerateId(..))")
    public Object queryWithGenerateId(ProceedingJoinPoint point) throws Throwable {
        Object o = point.getArgs()[0];
        Class<?> clazz = o.getClass();
        String commonId = CommonUtil.generateObjectId(o);
        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields).filter(e-> e.getAnnotation(GenerateKey.class) != null).map(e->e).forEach(
                e->{
                    e.setAccessible(true);
                    try {
                        System.out.println("==_____))))");
                        e.set(o,commonId);
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    }
                }
        );


        return point.proceed();
    }
}
