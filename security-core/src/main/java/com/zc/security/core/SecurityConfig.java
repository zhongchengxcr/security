package com.zc.security.core;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.zc.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig {

    @Bean
    public Producer imageCode() {

        Properties properties = new Properties();

        properties.setProperty("kaptcha.image.width", "100");
        properties.setProperty("kaptcha.image.height", "100");
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789abcdefghijklmnopqrstuvwxyz");

        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 是否有边框
        properties.setProperty("kaptcha.border", "no");
        //设置边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        //获取中文
        properties.setProperty("kaptcha.textproducer.impl", "org.cric.util.ChineseText");
        //设置字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "black");

        //设置字体样式
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");

        Config config = new Config(properties);
        DefaultKaptcha producer = new DefaultKaptcha();
        producer.setConfig(config);
        return producer;
    }
}
