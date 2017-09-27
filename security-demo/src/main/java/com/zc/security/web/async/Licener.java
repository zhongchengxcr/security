package com.zc.security.web.async;

import com.zc.security.dto.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/9/22 0022.
 */
//@Component
public class Licener implements ApplicationListener<ContextRefreshedEvent> {


    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private MockQuene quene;

    @Resource
    private DeferredResultHolder holder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("监听开始 ");
        new Thread(() -> {

            while (true) {

                if (StringUtils.isNotBlank(quene.getCompleteOrder())) {

                    logger.info("消息队列回调开始  >>>" + quene.getCompleteOrder());
                    User u = new User("zhongc", "123456");

                    holder.getMap().get(quene.getCompleteOrder()).setResult(u);

                    logger.info("消息队列回调结束  >>>" + quene.getCompleteOrder());
                    quene.setCompleteOrder(null);
                }
            }

        }).start();


    }


}
