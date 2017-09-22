package com.zc.security.web.async;


import com.zc.security.dto.User;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import java.util.concurrent.Callable;

@RequestMapping("/order")
@RestController
public class AsyncController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private DeferredResultHolder deferredResultHolder;

    @Resource
    private MockQuene quene;


    @GetMapping("/2")
    public DeferredResult<User> deffed() throws InterruptedException {
        logger.info("主线程开始" + System.currentTimeMillis());
        String randomCode = String.valueOf(RandomUtils.nextLong());
        logger.info("发送消息到队列" + randomCode);
        quene.setPlaceOrder(randomCode);
        DeferredResult<User> deferredResult = new DeferredResult<>();
        deferredResultHolder.getMap().put(randomCode, deferredResult);
        logger.info("主线程结束" + System.currentTimeMillis());
        return deferredResult;
    }


    @GetMapping("/1")
    public Callable<String> callable() {

        logger.info("主线程开始" + System.currentTimeMillis());

        Callable<String> res = () -> {
            logger.info("副线程开始" + System.currentTimeMillis());
            Thread.sleep(1000);
            logger.info("副线程开始" + System.currentTimeMillis());
            return "success";
        };

        logger.info("主线程结束" + System.currentTimeMillis());

        return res;
    }


}
