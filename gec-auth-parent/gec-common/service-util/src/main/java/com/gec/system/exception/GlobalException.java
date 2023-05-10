package com.gec.system.exception;

import com.gec.system.common.Result;

import com.gec.system.common.ResultCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalException {
    //全局异常 拦截所有的异常类型
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        System.out.println("public Result error(Exception e){");
        e.printStackTrace();
        return Result.fail();
    }

    //特定异常  (只拦截具体的某种异常类型)
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        System.out.println("public Result error(ArithmeticException e){");
        e.printStackTrace();
        return Result.fail();
    }

    //自定义异常
    @ExceptionHandler(MyCustomerException.class)
    @ResponseBody
    public Result error(MyCustomerException e) {
        // System.out.println("public Result error(CustomerException e){");
        e.printStackTrace();
        return Result.fail().code(e.getCode()).message(e.getMsg());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result error(AccessDeniedException e) {
        // System.out.println("public Result error(CustomerException e){");
        return Result.fail().code(ResultCodeEnum.PERMISSION.getCode()).message("没有当前操作权限");
    }

}

