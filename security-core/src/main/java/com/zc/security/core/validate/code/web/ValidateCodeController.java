package com.zc.security.core.validate.code.web;

import com.google.code.kaptcha.Producer;
import com.zc.security.core.validate.code.ValidateCodeFilter;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
 * 说明 . <br>
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
    private Producer producer;

    /**
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/validate/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String code = String.valueOf(RandomUtils.nextLong()).substring(0,4);

        System.out.println(code);

        BufferedImage bufferedImage = producer.createImage(code);

        request.getSession().setAttribute(ValidateCodeFilter.IMAGE_CODE_SESSION_KEY, code);

        OutputStream so = response.getOutputStream();

        ImageIO.write(bufferedImage, "JPEG", so);

    }
}
