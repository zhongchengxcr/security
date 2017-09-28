package com.zc.security.core.validate.code.config;

import com.zc.security.core.authentication.sms.SmsCodeUserDetailsService;
import com.zc.security.core.authentication.sms.completion.NoneSmsCodeUserDetailsService;
import com.zc.security.core.validate.code.ValidateCodeGenerator;
import com.zc.security.core.validate.code.ValidateCodeProcessor;
import com.zc.security.core.validate.code.filter.ValidateCodeFilter;
import com.zc.security.core.validate.code.image.ImageValidateCodeGenerator;
import com.zc.security.core.validate.code.image.ImageValidateCodeProcessor;
import com.zc.security.core.validate.code.sms.DefaultSmsValidateCodeSender;
import com.zc.security.core.validate.code.sms.SmsValidateCodeGenerator;
import com.zc.security.core.validate.code.sms.SmsValidateCodeProcessor;
import com.zc.security.core.validate.code.sms.SmsValidateCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 说明 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/09/25 22:55
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Configuration
public class ValidateCodeBeansConfig {

    /**
     * 可以被覆盖重写
     * 图片验证码生成器
     *
     * @return
     */
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    @Bean
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        return new ImageValidateCodeGenerator();
    }

    /**
     * 可以被覆盖重写
     * 短信验证码生成器
     *
     * @return
     */
    @ConditionalOnMissingBean(name = "smsValidateCodeGenerator")
    @Bean
    public ValidateCodeGenerator smsValidateCodeGenerator() {
        return new SmsValidateCodeGenerator();
    }

    /**
     * 可以被覆盖重写
     * 短信验证码处理器
     *
     * @return
     */
    @ConditionalOnMissingBean(name = "smsValidateCodeProcessor")
    @Bean
    public ValidateCodeProcessor smsValidateCodeProcessor() {
        return new SmsValidateCodeProcessor();
    }

    /**
     * 可以被覆盖重写
     * 图片验证码处理器
     *
     * @return
     */
    @ConditionalOnMissingBean(name = "imageValidateCodeProcessor")
    @Bean
    public ValidateCodeProcessor imageValidateCodeProcessor() {
        return new ImageValidateCodeProcessor();
    }

    /**
     * 可以被覆盖重写
     * 短信验证码发送器
     *
     * @return
     */
    @ConditionalOnMissingBean(SmsValidateCodeSender.class)
    @Bean
    public SmsValidateCodeSender smsValidateCodeSender() {
        return new DefaultSmsValidateCodeSender();
    }


    @Bean
    public ValidateCodeFilter validateCodeFilter() {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        return validateCodeFilter;
    }


    @ConditionalOnMissingBean(SmsCodeUserDetailsService.class)
    @Bean
    public SmsCodeUserDetailsService smsCodeUserDetailsService() {
        return new NoneSmsCodeUserDetailsService();
    }

}
