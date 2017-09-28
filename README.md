# Security
####security 安全校验SDK 

[![](https://img.shields.io/badge/JDK-1.8-green.svg)]()
[![](https://img.shields.io/badge/maven-3.5.0-green.svg)]()
[![](https://img.shields.io/badge/Powered_by-SpringBoot+SpringSecurity-green.svg)]()
[![](https://img.shields.io/badge/build-passing-green.svg)]()

[![](http://progressed.io/bar/40?title=completed)]()

Security是一个web安全校验SDK，配置简单、功能完善、界面流畅、开箱即用！security使用SpringBoot、SpringSecurity、SpringSocial编写，
能与springboot的web项目完美兼容。

欢迎fork、试用。目前还处于开发阶段，括号内有 "complete" 的已经完成开发完成(没有在正式环境跑过)。
Security的所提供所有功能,只需要进行简单的配置即可在自己的工程中使用,Security也提供了丰富接口供使用者定义个性化的业务逻辑。

* 表单登录(complete)
* 短信验证码登录(complete)
* 图形验证码校验(complete)
* 短信验证码校验(complete)
* 注册(processing)


* 微信登录(浏览器)(plan)
* 微信登录(app)(plan)
* qq登录(浏览器)(plan)
* qq登录(app)(plan)


* 集群Session管理(plan)
* 单点登录、登出(plan)


* 权限的管理与控制(plan)



安装
---
```
git clone git@github.com:meolu/zhongchengxcr/security.git
cd security
mvn clean compile -e -U install
```

在自己的工程的pom.xml中添加

```
<!-- 浏览器部分相关 -->
<dependency>
    <groupId>com.zc.security</groupId>
    <artifactId>ecurity-browser</artifactId>
    <version>${zc.security.version}</version>
 </dependency>

<!-- JWT部分 -->
<dependency>
    <groupId>com.zc.security</groupId>
    <artifactId>ecurity-browser</artifactId>
    <version>${zc.security.version}</version>
 </dependency>
```
安装
---

在springboot项目的application.yml 或 application.properties 中添加配置启用功能
```
#登录页面url
zc.security.browser.loginPage=/src/login_t.html
#登录完成是跳转页面还是返回JSON
zc.security.browser.loginType=JSON
#需要校验图片验证码的url,支持通配, 以","分割
zc.security.code.image.url=/user/1,/user/*
#需要校验短信验证码的url,支持通配, 以","分割
zc.security.code.sms.url=/user/3,/user/4


zc.security.code.image.width=200
zc.security.code.image.height=70
zc.security.code.image.length=6
zc.security.code.image.border=no
zc.security.code.image.fontColor=black
zc.security.code.image.textproducer=0123456789abcdefghijklmnopqrstuvwxyz
#图片验证码过期时间,单位秒
zc.security.code.image.expireIn=60


#短信验证码过期时间,单位秒
zc.security.code.sms.expireIn=60
zc.security.code.sms.length=6

#未完待续

```


Beans
```
//使用者必须实现的接口, 实现后 加入到spring的容器中,每个接口的注释在代码中可见,非常简单易懂
UserDetailsService
SmsCodeUserDetailsService


//使用者可以自定义接口
SimpleUrlAuthenticationFailureHandler
SimpleAuthenticationSuccessHandler
ValidateCodeProcessor
SmsValidateCodeSender
ValidateCodeGenerator


//未完待续
```


