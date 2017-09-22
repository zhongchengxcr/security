package com.zc.security.web.async;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
public class AsyncController {

    @GetMapping("/order")
    public Callable<String> order() {

        System.out.println("主线程开始" + System.currentTimeMillis());
        Callable<String> res = () -> {
            System.out.println("副线程开始" + System.currentTimeMillis());
            Thread.sleep(1000);
            System.out.println("副线程开始" + System.currentTimeMillis());
            return "success";
        };
        System.out.println("主线程结束" + System.currentTimeMillis());

        return res;
    }

}
