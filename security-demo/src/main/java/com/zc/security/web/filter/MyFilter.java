package com.zc.security.web.filter;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Administrator on 2017/9/21 0021.
 */
@Component
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter.init");
    }

    @Override
    public void doFilter(ServletRequest resp, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("My filter start!");
        long start = System.currentTimeMillis();
        filterChain.doFilter(resp,res);
        long end = System.currentTimeMillis();
        System.out.println("My filter finish!");

        System.out.println("time filter >>> " + String.valueOf(end - start) +"ms" );
    }

    @Override
    public void destroy() {

        System.out.println("My filter destroy!");

    }
}
