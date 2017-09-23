package com.zc.security.web.login;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 出来登录业务逻辑
 */


@Controller
public class LoginController {

    @GetMapping("/zc/login")
    public String goLogin(){
        return "/error/login.html";
    }
}
