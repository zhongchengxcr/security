package com.zc.security.core.validate.code.sms;

import com.zc.security.core.validate.code.ValidateCode;
import sun.util.resources.LocaleData;

import java.time.LocalDateTime;
import java.util.Locale;

/**
 * 短信验证码
 * <p>
 * Copyright: Copyright (c) 2017/09/24 下午3:00
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class SmsValidateCode extends ValidateCode {

    public SmsValidateCode(String code, int expireIn) {
        super(code, LocalDateTime.now().plusSeconds(expireIn));
    }
}
