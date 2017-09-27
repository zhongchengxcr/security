package com.zc.security.core.validate.code.sms;

import com.zc.security.core.properties.SecurityProperties;
import com.zc.security.core.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 说明 . <br>
 * 默认短信验证码生成器
 * <p>
 * Copyright: Copyright (c) 2017/09/25 下午8:28
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public SmsValidateCode generator(ServletWebRequest request) {

        //过期时间
        int expireIn = securityProperties.getCode().getSms().getExpireIn();
        String mobile = request.getParameter("mobile");
        int length = securityProperties.getCode().getSms().getLength();
        String smsCode = RandomStringUtils.random(length);

        logger.info("短信验证码 >>> " + smsCode + "手机号 >>> mobile");

        return new SmsValidateCode(smsCode, mobile, expireIn);
    }


}
