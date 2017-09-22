package com.zc.security.web.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.zc.security.dto.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {


    @PostMapping("/user/f")
    @JsonView(User.UserSimpleView.class)
    public User test() {
        throw new RuntimeException();
    }

    @PostMapping("/user")
    @JsonView(User.UserSimpleView.class)
    public User create(@Valid @RequestBody User u) {
       /* if(errs.hasErrors()){
            errs.getAllErrors().stream().forEach(err -> System.out.println(err));
        }*/

        System.out.println(">>> " + u.toString());

        return u;
    }


    @DeleteMapping("user/{id}")
    public void delete(@PathVariable("id") Integer i) {
        System.out.println("delete");
    }


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


    @GetMapping("/user/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User get(@PathVariable("id") String i) {
        System.out.println("PathVariable >>> " + i);
        User u = new User("zhongc", "123456");
        return u;
    }

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
