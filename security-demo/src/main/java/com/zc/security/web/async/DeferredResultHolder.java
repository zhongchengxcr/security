package com.zc.security.web.async;

import com.zc.security.dto.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/9/22 0022.
 */
@Component
public class DeferredResultHolder {

    private Map<String, DeferredResult<User>> map = new ConcurrentHashMap<>();


    public Map<String, DeferredResult<User>> getMap() {
        return map;
    }

    public void setMap(Map<String, DeferredResult<User>> map) {
        this.map = map;
    }
}
