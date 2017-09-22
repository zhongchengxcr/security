package com.zc.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.zc.security.web.intercepter.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Administrator on 2017/9/21 0021.
 */

@Configuration
public class MyConfig extends WebMvcConfigurerAdapter {


    /**
     * Filter.init （启动时执行）
     * Filter.doFilter
     * Intercrpter.preHandle
     * Aspect
     * Controller
     * HandlerAdvice（有异常执行）
     * Intercrpter.postHandle(有异常不走)
     * Intercrpter.afterCompletion
     */
    //@SuppressWarnings("unused")
    //@Autowired
    private MyIntercrpter myIntercrpter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // registry.addInterceptor(myIntercrpter);
    }

//    //	@Bean
//    public FilterRegistrationBean timeFilter() {
//
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//
//        TimeFilter timeFilter = new TimeFilter();
//        registrationBean.setFilter(timeFilter);
//
//        List<String> urls = new ArrayList<>();
//        urls.add("/*");
//        registrationBean.setUrlPatterns(urls);
//
//        return registrationBean;
//
//    }
}
