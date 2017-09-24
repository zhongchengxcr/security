package com.zc.security.core.validate.code.image;

import com.zc.security.core.validate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 说明 . <br>
 * 图片验证码
 * <p>
 * Copyright: Copyright (c) 2017/09/24 下午3:00
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class ImageCode extends ValidateCode {

    private BufferedImage bufferedImage;

    public ImageCode(String code, int expireIn, BufferedImage bufferedImage) {
        super(code, expireIn);
        this.bufferedImage = bufferedImage;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

}
