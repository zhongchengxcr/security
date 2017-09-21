package com.zc.security.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import com.zc.security.dto.User;
import com.fasterxml.jackson.annotation.JsonView;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    @PostMapping("/user")
    public void create(@RequestBody User u){
        System.out.println(">>> "+u.toString());
    }


    @DeleteMapping("user/{id}")
    public String delete(@PathVariable("id") Integer i){
        return "delete success";
    }

    @PutMapping("/user")
    public String update(@RequestBody User u){
        return "update";
    }

    @GetMapping("/user/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User get(@PathVariable("id") String i){
        System.out.println("PathVariable >>> "+i);
        User u =new User("zhongc","123456");
        return u;
    }

    @GetMapping("/users")
    @JsonView(User.UserSimpleView.class)
    public List<User> users(User usr){
        System.out.println(usr.toString());

        User u0 = new User("zhongc","123456");
        User u1 = new User("kanyn","123456");
        User u2 = new User("unknow","123456");

        User[] users = new User[3];
        users[0]=u0;
        users[1]=u1;
        users[2]=u2;

        return Arrays.asList(users);
    }


}
