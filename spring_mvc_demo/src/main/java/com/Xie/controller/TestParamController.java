package com.Xie.controller;

import com.Xie.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description:
 * 获取请求参数的方式：
 * 1.通过servletAPI的getParameter()方法获取请求参数；
 * 只需要在控制器方法的形参位置设置HttpServletRequest类型的形参
 * 就可以在控制器方法中使用request对象获取请求参数；
 * 2.通过控制器的形参获取请求参数；
 * 只需要在控制器方法的形参位置，设置一个形参，形参的名字和请求参数的名字一致即可
 * 3.@RequestParam:将请求参数和控制器方法的形参进行绑定；
 * @RequestParam注解的三个属性：value, required, defaultValue
 * value：设置和形参绑定的请求参数的名字；
 * required：是否必须要有请求参数，默认为true；
 * defaultValue：如果请求参数没有传递，则使用默认值；
 * 4.@RequestValue：将请求头信息和控制器方法的形参进行绑定；
 * 5.@CookieValue：将cookie数据和控制器方法的形参进行绑定；
 * 6.通过控制器方法的实体类类型的形参获取请求参数；
 * 需要在控制器方法的形参位置设置一个实体类类型的形参，要保证实体类中的属性和请求参数的名字一致；
 * 可以通过实体类的形参获取请求参数；
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/8/7 9:48
 */
@Controller
public class TestParamController {

    @RequestMapping("/param/servletAPI")
    public String getParamByServletAPI(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username: " + username + ", password: " + password);
        return "success";
    }

    @RequestMapping("/param")
    public String getParam(
            @RequestParam(value = "userName", required = false, defaultValue = "hello") String username,
            String password,
            @RequestHeader("referer") String referer,
            @CookieValue("JSESSIONID") String jsessionId
    ) {
        System.out.println("username: " + username + ", password: " + password );
        System.out.println("referer: " + referer + ", jsessionId: " + jsessionId);
        return "success";
    }

    @RequestMapping("/param/pojo")
    public String getParamByPojo(User user) {
        System.out.println(user);
        return "success";
    }

    }


