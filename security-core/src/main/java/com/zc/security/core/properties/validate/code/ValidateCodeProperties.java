package com.zc.security.core.properties.validate.code;

import com.zc.security.core.properties.validate.code.ImageValidateCodeProperties;
import com.zc.security.core.properties.validate.code.SmsValidateCodeProperties;

/**
 * 属性配置 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/09/24 下午3:01
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class ValidateCodeProperties {

    private ImageValidateCodeProperties image =new ImageValidateCodeProperties();

    private SmsValidateCodeProperties sms =new SmsValidateCodeProperties();

    public ImageValidateCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageValidateCodeProperties image) {
        this.image = image;
    }

    public SmsValidateCodeProperties getSms() {
        return sms;
    }

    public void setSms(SmsValidateCodeProperties sms) {
        this.sms = sms;
    }
}
