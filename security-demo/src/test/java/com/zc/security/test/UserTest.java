package com.zc.security.test;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zc.security.dto.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private WebApplicationContext webc;

    private MockMvc mockMvc;


    private ObjectMapper jackson = new ObjectMapper();


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webc).build();
    }


    @Test
    public void usersTest() throws Exception {
        mockMvc.perform(
                get("/users")
                        .param("id", "1")
                        .param("username", "username")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andDo(print());
    }

    @Test
    public void getUserSuccess() throws Exception {
        mockMvc.perform(
                get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username")
                        .value("zhongc"))
                .andDo(print());

    }


    @Test
    public void getUserFailed() throws Exception {
        mockMvc.perform(
                get("/user/a")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }


    @Test
    public void addUser() throws Exception {


        User usr = new User();
        usr.setUsername("zhongc");
        usr.setPassword("");
        usr.setId("1");
        usr.setBirthday(new Date());
        // Convert object to JSON string
        String json = jackson.writeValueAsString(usr);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void updateUser() throws Exception {


        User usr = new User();
        usr.setUsername("zhongc");
        usr.setPassword("");
        usr.setId("1");
        usr.setBirthday(new Date("2018/1/1"));
        // Convert object to JSON string
        String json = jackson.writeValueAsString(usr);

        mockMvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andDo(print());
    }


    @Test
    public void deleteUser() throws Exception {

        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
