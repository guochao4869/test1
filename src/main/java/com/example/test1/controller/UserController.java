package com.example.test1.controller;

import com.example.test1.pojo.Result;
import com.example.test1.pojo.User;
import com.example.test1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/test")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody(required = false) User user) {
        return userService.login(user);
    }

    @GetMapping("/getUser")
    public Result getUser() {
        return userService.getUser();
    }

    /**
     * 获取登入信息
     */
    public String getMsg(){
        return null;
    }

}
