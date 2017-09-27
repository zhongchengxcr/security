package com.zc.security.core.validate.code;

import com.zc.security.core.SecurityConstants;

/**
 * 说明 . <br>
 * 验证码类型枚举
 * <p>
 * Copyright: Copyright (c) 2017/09/27 上午11:05
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public enum ValidateCodeType {


    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    },


    SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    };


    /**
     * 校验时从请求中获取的参数的名字
     *
     * @return
     */
    public abstract String getParamNameOnValidate();


}
