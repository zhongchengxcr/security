package com.zc.security.core.validate.code;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 说明 . <br>
 * 抽象验证码处理器
 * <p>
 * Copyright: Copyright (c) 2017/09/27 上午11:27
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public abstract class AbstractValidateCodeProcessor<T extends ValidateCode> implements ValidateCodeProcessor {

    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGeneratorMap;

    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        //生成验证码
        T t = generate(request);
        //保存
        save(request, t);
        //发送
        send(request,t);
    }


    @Override
    public void validate(ServletWebRequest request) {

    }


    /**
     * 发送验证码
     *
     * @param request
     * @param t
     */
    protected abstract void send(ServletWebRequest request, T t) throws Exception;

    /**
     * 保存验证码
     *
     * @param request
     * @param t
     */
    private void save(ServletWebRequest request, T t) {
        ValidateCode validateCode = new ValidateCode(t.getCode(), t.getExpireTime());
        validateCodeRepository.save(request, validateCode);
    }


    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    private T generate(ServletWebRequest request) {
        String type = getValidateCodeType().toString().toLowerCase();
        String validateCodeGeneratorBeanName = type + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorMap.get(validateCodeGeneratorBeanName);
        ValidateCode validateCode = validateCodeGenerator.generator(request);

        return (T) validateCode;
    }


    /**
     * 根据请求的url获取校验码的类型
     *
     * @return
     */
    private ValidateCodeType getValidateCodeType() {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "ValidateCodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }


}
