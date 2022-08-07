package com.Xie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Description:
 * 1.通过ModelAndView向请求域共享数据
 * 使用ModelAndView时，可以使用其Model功能向请求域共享数据
 * 使用View功能设置逻辑视图，但是控制器方法一定要将ModelAndView作为方法的返回值
 * 2.使用Model向请求域共享数据
 * 3.使用ModelMap向请求域共享数据
 * 4.使用Map向请求域共享数据
 * 5.Model和ModelMap和Map的关系：
 * 其实在底层中，这些类型的形参最终都是通过BindingAwareModelMap创建
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date:2022/8/7 11:49
 */
@Controller
public class TestScopeController {

    @RequestMapping("/test/mav")
    public ModelAndView testMAV() {
        /**
         * ModelAndView包含Model和View的功能
         * Model:向请求域中共享数据
         * view:设置逻辑视图实现页面跳转
         */
        ModelAndView mav = new ModelAndView();
        //设置逻辑视图实现页面跳转
        mav.setViewName("success");
        // 向请求域中共享数据
        mav.addObject("testRequestScope", "Hello,ModelAndView");
        return mav;
    }

    @RequestMapping("/test/model")
    public String testModel(Model model){
        System.out.println(model.getClass().getName());
        model.addAttribute("testRequestScope", "Hello,Model");
        return "success";
    }

    @RequestMapping("/test/modelMap")
    public String testModelMap(ModelMap modelMap){
        System.out.println(modelMap.getClass().getName());
        modelMap.addAttribute("testRequestScope", "Hello,ModelMap");
        return "success";
    }

    @RequestMapping("/test/map")
    public String testMap(Map<String, Object> map){
        System.out.println(map.getClass().getName());
        map.put("testRequestScope", "Hello,Map");
        return "success";
    }

    @RequestMapping("/test/session")
    public String testSession(HttpSession session){
        session.setAttribute("testSessionScope", "Hello,Session");
        return "success";
    }

    @RequestMapping("/test/application")
    public String testApplication(HttpSession session){
        ServletContext servletContext = session.getServletContext();
        servletContext.setAttribute("testApplicationScope", "Hello,Application");
        return "success";
    }
}
