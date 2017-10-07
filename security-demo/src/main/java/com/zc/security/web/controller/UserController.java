package com.zc.security.web.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zc.security.dto.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    private ProviderSignInUtils providerSignInUtils;


    @PostMapping("/user/regist")
    public void regist(User u, HttpServletRequest request) throws JsonProcessingException {

        logger.info("regist user : {}", objectMapper.writeValueAsString(u));

        String userId = u.getUsername();

        //插入数据库
        providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));


        //---------------------注册逻辑----------------------

    }


    @ApiOperation("测试异常")
    @PostMapping("/user/f")
    @JsonView(User.UserSimpleView.class)
    public User test() {
        throw new RuntimeException();
    }

    @ApiOperation("测试异常")
    @PostMapping("/lol/op")
    @JsonView(User.UserSimpleView.class)
    public User test1() {
        System.out.print("lol");
        return new User();
    }


    @ApiOperation("创建用户")
    @PostMapping("/user")
    @JsonView(User.UserSimpleView.class)
    public User create(@Valid @RequestBody User u) {
       /* if(errs.hasErrors()){
            errs.getAllErrors().stream().forEach(err -> System.out.println(err));
        }*/

        System.out.println(">>> " + u.toString());

        return u;
    }


    @ApiOperation("删除用户")
    @DeleteMapping("user/{id}")
    public void delete(@PathVariable("id") Integer i) {
        System.out.println("delete");
    }


    @ApiOperation("更新用户")
    @PutMapping("/user/{id}")
    @JsonView(User.UserSimpleView.class)
    public User update(@PathVariable("id") Integer i, @Valid @RequestBody User u, BindingResult errs) {

        if (errs.hasErrors()) {
            errs.getAllErrors().stream().forEach(err -> {
                FieldError fieldError = (FieldError) err;

                String field = fieldError.getField();

                String message = fieldError.getDefaultMessage();

                System.out.println("field >> " + field);
                System.out.println("message >> " + message);

            });
        }

        System.out.println(i);

        System.out.println();

        System.out.println(">>> " + u.toString());

        return u;
    }


    @ApiOperation("查询用户")
    @GetMapping("/user/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User get(@ApiParam("用户id") @PathVariable("id") String i) {
        System.out.println("PathVariable >>> " + i);
        User u = new User("zhongc", "123456");
        return u;
    }

    @ApiOperation("查询用户列表")
    @GetMapping("/users")
    @JsonView(User.UserSimpleView.class)
    public List<User> users(User usr) {
        System.out.println(usr.toString());

        User u0 = new User("zhongc", "123456");
        User u1 = new User("kanyn", "123456");
        User u2 = new User("unknow", "123456");

        User[] users = new User[3];
        users[0] = u0;
        users[1] = u1;
        users[2] = u2;

        return Arrays.asList(users);
    }


}
