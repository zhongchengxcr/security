package com.zc.security.core.validate.code.sms;

import com.zc.security.core.validate.code.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 说明 . <br>
 * 短信发送器
 * <p>
 * Copyright: Copyright (c) 2017/09/27 下午1:33
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public interface SmsValidateCodeSender {

    void send(String mobile, String code) throws Exception;
}
