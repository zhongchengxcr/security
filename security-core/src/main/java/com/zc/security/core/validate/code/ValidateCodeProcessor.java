package com.zc.security.core.validate.code;


import org.springframework.web.context.request.ServletWebRequest;


/**
 * 验证码处理器 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/09/24 下午3:27
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public interface ValidateCodeProcessor {

    //创建
    void create(ServletWebRequest request) throws Exception;

    //验证
    void validate(ServletWebRequest request);


}
