package com.Xie.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * 拦截器的三个方法：
 * preHandle()：在控制器方法执行之前执行，其返回值表示对控制器方法的拦截(false)或放行（true）
 * postHandle():在控制器方法执行之后执行
 * afterCompletion():在控制器方法执行之后，在视图渲染完毕之后执行
 *
 * 多个拦截器的执行顺序和在SpringMVC的配置文件配置的顺序有关
 * preHandle()按照配置的顺序执行，而postHandle()和afterCompletion()按照配置的反序执行
 *
 * 若拦截器中有某个拦截器的preHandle()返回false和他之前的拦截器的preHandle()方法都会执行
 * 所有的拦截器的postHandle()都不执行
 * 拦截器的preHandle()返回false之前的拦截器的afterCompletion()会执行
 * @author: XieFeiYu
 * @eamil: 32096231@qq.com
 * @date: 2022/8/12 21:58
 */
@Component
public class SecondInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("SecondInterceptor-->preHandle");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("SecondInterceptor-->postHandle");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("SecondInterceptor-->afterCompletion");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
