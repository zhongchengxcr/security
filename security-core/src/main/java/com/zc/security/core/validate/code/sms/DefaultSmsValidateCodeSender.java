package com.zc.security.core.validate.code.sms;

import com.zc.security.core.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/09/27 下午1:34
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class DefaultSmsValidateCodeSender implements SmsValidateCodeSender {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void send(String mobile, String code) throws Exception {
        logger.warn("请配置真实的短信验证码发送器(SmsCodeSender)");
        logger.info("向手机 " + mobile + "发送短信验证码 " + code);
    }
}
