package com.zc.security.web.async;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.zc.security.dto.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * Demo class
 *
 * @author zc
 * @date 2016/10/31
 */
//@Component
@SuppressWarnings("ALL")
public class Licener implements ApplicationListener<ContextRefreshedEvent> {


    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private MockQuene quene;

    @Resource
    private DeferredResultHolder holder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("监听开始 ");


        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(() -> {
            while (true) {

                if (StringUtils.isNotBlank(quene.getCompleteOrder())) {

                    logger.info("消息队列回调开始  >>>" + quene.getCompleteOrder());
                    User u = new User("zhongc", "123456");

                    holder.getMap().get(quene.getCompleteOrder()).setResult(u);

                    logger.info("消息队列回调结束  >>>" + quene.getCompleteOrder());
                    quene.setCompleteOrder(null);
                }
            }
        });
        singleThreadPool.shutdown();


    }


}
