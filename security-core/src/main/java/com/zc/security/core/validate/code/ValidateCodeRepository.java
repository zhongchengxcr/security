package com.zc.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeRepository {

    /**
     * 获取验证码
     * @param request
     * @param validateCodeType
     * @return
     */
    ValidateCode get(ServletWebRequest request,ValidateCodeType validateCodeType);

    /**
     * 保存验证码
     * @param request
     * @param validateCode
     */
    void save(ServletWebRequest request, ValidateCode validateCode);

    /**
     * 移除验证码
     * @param request
     * @param codeType
     */
    void remove(ServletWebRequest request, ValidateCodeType codeType);

}
