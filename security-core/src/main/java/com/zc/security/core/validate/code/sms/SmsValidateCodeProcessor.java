package com.zc.security.core.validate.code.sms;

import com.zc.security.core.SecurityConstants;
import com.zc.security.core.validate.code.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 说明 . <br>
 * 短信验证码处理器
 * <p>
 * Copyright: Copyright (c) 2017/09/27 下午1:30
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor<SmsValidateCode> {

    @Autowired
    private SmsValidateCodeSender smsValidateCodeSender;

    @Override
    protected void send(ServletWebRequest request, SmsValidateCode smsValidateCode) throws Exception {
        String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        String code = smsValidateCode.getCode();
        smsValidateCodeSender.send(mobile, code);
    }
}
