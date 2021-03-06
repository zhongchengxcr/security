package com.zc.security.core.validate.code.web;

import com.zc.security.core.SecurityConstants;
import com.zc.security.core.validate.code.ValidateCodeGenerator;
import com.zc.security.core.validate.code.ValidateCodeProcessorHolder;
import com.zc.security.core.validate.code.image.ImageValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取验证码控制器 . <br>
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
@RestController
public class ValidateCodeController {


    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;


    /**
     * @param request
     * @param response
     * @throws Exception
     */

    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public void createCode(@PathVariable("type")String type,HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request,response));
    }
}