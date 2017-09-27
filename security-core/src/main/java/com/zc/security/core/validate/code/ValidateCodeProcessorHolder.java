package com.zc.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 说明 . <br>
 * 校验码处理器管理器
 * <p>
 * Copyright: Copyright (c) 2017/09/27 上午11:03
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Component
public class ValidateCodeProcessorHolder {


    @Autowired
    private Map<String,ValidateCodeProcessor> validateCodeProcessorMap;


    public ValidateCodeProcessor findValidateCodeProcessor(String type){

       return validateCodeProcessorMap.get(type+ValidateCodeProcessor.class.getSimpleName());
    }


    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type){

       return this.findValidateCodeProcessor(type.toString().toLowerCase());
    }


}
