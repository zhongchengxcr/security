package com.zc.security.core.properties.validate.code;

/**
 * 图片验证码配置 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/09/25 下午8:40
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
public class ImageValidateCodeProperties {

    private String width = "200";

    private String height = "50";

    private String length = "4";

    private String border = "no";

    private String fontColor = "black";

    private String textproducer = "0123456789abcdefghijklmnopqrstuvwxyz";

    private int expireIn = 60;

    private String url;


    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getTextproducer() {
        return textproducer;
    }

    public void setTextproducer(String textproducer) {
        this.textproducer = textproducer;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
