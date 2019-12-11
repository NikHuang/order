package com.huang.order.framework.exceptions;

import com.huang.order.vo.CodeMessage;
import com.huang.order.vo.ResultVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * Created by Administrator on 2019/12/11.
 */
@ControllerAdvice
@ResponseBody
public class CommonExceptionHandler  {

    @ExceptionHandler(value = Exception.class)
    public ResultVo handlerExceptionMethod(Exception e, HttpServletRequest request, HttpServletResponse response){

        if (e instanceof CommonException){
            CodeMessage codeMessage = ((CommonException) e).getCodeMessage();
            return ResultVo.error(codeMessage);
        }else {
            return ResultVo.error(CodeMessage.SERVERERROR);
        }

    }

}
