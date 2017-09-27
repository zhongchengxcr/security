package com.zc.security.web.intercepter;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/21 0021.
 */
//@Component
public class MyIntercrpter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        System.out.println("preHandle");

        System.out.println(((HandlerMethod) handler).getBean().getClass().getName());
        System.out.println(((HandlerMethod) handler).getMethod().getName());

        request.setAttribute("startTime", new Date().getTime());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
        Long start = (Long) request.getAttribute("startTime");
        System.out.println("time interceptor 耗时:" + (new Date().getTime() - start));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception ex) throws Exception {
        System.out.println("afterCompletion");
        Long start = (Long) request.getAttribute("startTime");
        System.out.println("time interceptor 耗时:" + (new Date().getTime() - start));
        System.out.println("ex is " + ex);
    }
}
