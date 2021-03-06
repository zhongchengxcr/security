package com.zc.security.core.validate.code.image;

import com.zc.security.core.validate.code.AbstractValidateCodeProcessor;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 说明 . <br>
 * 图片验证码处理器
 * <p>
 * Copyright: Copyright (c) 2017/09/27 下午1:26
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class ImageValidateCodeProcessor extends AbstractValidateCodeProcessor<ImageValidateCode> {

    @Override
    protected void send(ServletWebRequest request, ImageValidateCode imageValidateCode) throws IOException {

        HttpServletResponse response = request.getResponse();
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        ImageIO.write(imageValidateCode.getBufferedImage(),
                "JPEG",
                response.getOutputStream());
    }
}
