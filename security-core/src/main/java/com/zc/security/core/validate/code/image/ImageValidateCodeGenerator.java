package com.zc.security.core.validate.code.image;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.zc.security.core.properties.SecurityProperties;
import com.zc.security.core.validate.code.ValidateCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.PostConstruct;
import java.awt.image.BufferedImage;
import java.util.Properties;

/**
 * 说明 . <br>
 * 图片验证码生成器
 * <p>
 * Copyright: Copyright (c) 2017/09/25 下午8:28
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class ImageValidateCodeGenerator implements ValidateCodeGenerator {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;


    private DefaultKaptcha producer = new DefaultKaptcha();

    @Override
    public ImageValidateCode generator(ServletWebRequest request) {
        configKaptcha(request);
        String text = producer.createText();
        BufferedImage bufferedImage = producer.createImage(text);
        ImageValidateCode imageValidateCode = new ImageValidateCode(text, bufferedImage, securityProperties.getCode().getImage().getExpireIn());
        intiKaptcha();
        return imageValidateCode;
    }


    /**
     * 初始化验证码生产器
     *
     * @param request
     * @return
     */
    private void configKaptcha(ServletWebRequest request) {

        String width = ServletRequestUtils.getStringParameter(request.getRequest(), "width",
                securityProperties.getCode().getImage().getWidth());
        String height = ServletRequestUtils.getStringParameter(request.getRequest(), "height",
                securityProperties.getCode().getImage().getHeight());
        String length = ServletRequestUtils.getStringParameter(request.getRequest(), "length",
                securityProperties.getCode().getImage().getLength());

        logger.info("对验证码做请求级别的配置 width >>> {} , height >>> {}  , length >>> {}", width, height, length);

        Properties properties = producer.getConfig().getProperties();
        properties.setProperty("kaptcha.image.width", width);
        properties.setProperty("kaptcha.image.height", height);
        properties.setProperty("kaptcha.textproducer.char.length", length);
    }


    @PostConstruct
    public void intiKaptcha() {

        logger.info("配置验证码生成器 !");

        String width = securityProperties.getCode().getImage().getWidth();
        String height = securityProperties.getCode().getImage().getHeight();
        String length = securityProperties.getCode().getImage().getLength();
        String border = securityProperties.getCode().getImage().getBorder();
        String textproducer = securityProperties.getCode().getImage().getTextproducer();
        String fontColor = securityProperties.getCode().getImage().getFontColor();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width", width);
        properties.setProperty("kaptcha.image.height", height);
        properties.setProperty("kkaptcha.border", border);
        properties.setProperty("kaptcha.textproducer.char.string", textproducer);
        properties.setProperty("kaptcha.textproducer.char.length", length);
        properties.setProperty("kaptcha.textproducer.font.color", fontColor);
        Config config = new Config(properties);
        producer.setConfig(config);
        //kaptcha.border	图片边框，合法值：yes , no	yes
        //kaptcha.border.color	边框颜色，合法值： r,g,b (and optional alpha) 或者 white,black,blue.	black
        //kaptcha.border.thickness	边框厚度，合法值：>0	1
        //kaptcha.image.width	图片宽	200
        //kaptcha.image.height	图片高	50
        //kaptcha.producer.impl	图片实现类	com.google.code.kaptcha.impl.DefaultKaptcha
        //kaptcha.textproducer.impl	文本实现类	com.google.code.kaptcha.text.impl.DefaultTextCreator
        //kaptcha.textproducer.char.string	文本集合，验证码值从此集合中获取	abcde2345678gfynmnpwx
        //kaptcha.textproducer.char.length	验证码长度	5
        //kaptcha.textproducer.font.names	字体	Arial, Courier
        //kaptcha.textproducer.font.size	字体大小	40px.
        //kaptcha.textproducer.font.color	字体颜色，合法值： r,g,b  或者 white,black,blue.	black
        //kaptcha.textproducer.char.space	文字间隔	2
        //kaptcha.noise.impl	干扰实现类	com.google.code.kaptcha.impl.DefaultNoise
        // kaptcha.noise.color	干扰颜色，合法值： r,g,b 或者 white,black,blue.	black
        //kaptcha.obscurificator.impl	图片样式：
        //水纹com.google.code.kaptcha.impl.WaterRipple
        //鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
        //阴影com.google.code.kaptcha.impl.ShadowGimpy	com.google.code.kaptcha.impl.WaterRipple
        //kaptcha.background.impl	背景实现类	com.google.code.kaptcha.impl.DefaultBackground
        //kaptcha.background.clear.from	背景颜色渐变，开始颜色	light grey
        //kaptcha.background.clear.to	背景颜色渐变，结束颜色	white
        //kaptcha.word.impl	文字渲染器	com.google.code.kaptcha.text.impl.DefaultWordRenderer
        //kaptcha.session.key	session key	KAPTCHA_SESSION_KEY
        //kaptcha.session.date	session date	KAPTCHA_SESSION_DATE
    }


}
