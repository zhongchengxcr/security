package com.zc.security.support;

import com.zc.security.core.validate.code.ValidateCode;
import com.zc.security.core.validate.code.ValidateCodeRepository;
import com.zc.security.core.validate.code.ValidateCodeType;
import com.zc.security.core.validate.code.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * 基于redis的验证码存取器，避免由于没有session导致无法存取验证码的问题 . <br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017/10/07 下午4:04
 * <p>
 * Company: 百趣
 * <p>
 *
 * @author zhongcheng_m@yeah.net
 * @version 1.0.0
 */
@Component
public class RedisValidateCodeRepository  implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /*
     * (non-Javadoc)
     *
     * @see
     * com.imooc.security.core.validate.code.ValidateCodeRepository#save(org.
     * springframework.web.context.request.ServletWebRequest,
     * com.imooc.security.core.validate.code.ValidateCode,
     * com.imooc.security.core.validate.code.ValidateCodeType)
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
        redisTemplate.opsForValue().set(buildKey(request, type), code, 30, TimeUnit.MINUTES);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.imooc.security.core.validate.code.ValidateCodeRepository#get(org.
     * springframework.web.context.request.ServletWebRequest,
     * com.imooc.security.core.validate.code.ValidateCodeType)
     */
    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
        Object value = redisTemplate.opsForValue().get(buildKey(request, type));
        if (value == null) {
            return null;
        }
        return (ValidateCode) value;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.imooc.security.core.validate.code.ValidateCodeRepository#remove(org.
     * springframework.web.context.request.ServletWebRequest,
     * com.imooc.security.core.validate.code.ValidateCodeType)
     */
    @Override
    public void remove(ServletWebRequest request, ValidateCodeType type) {
        redisTemplate.delete(buildKey(request, type));
    }

    /**
     * @param request
     * @param type
     * @return
     */
    private String buildKey(ServletWebRequest request, ValidateCodeType type) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }
        return "code:" + type.toString().toLowerCase() + ":" + deviceId;
    }

}
