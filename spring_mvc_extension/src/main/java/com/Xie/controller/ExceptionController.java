package com.Xie.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Description:
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date: 2022/8/13 10:20
 */
//将当前类标识位异常处理的组件
@ControllerAdvice
public class ExceptionController {

    //设置要处理的异常信息
    @ExceptionHandler(value = ArithmeticException.class)
    public String handleException(Throwable ex, Model model){
        //ex表示控制器方法所处闲的异常
        model.addAttribute("ex",ex);
        return "error";

    }
}
