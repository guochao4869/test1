package com.example.test1.service;

import com.example.test1.pojo.Result;
import com.example.test1.pojo.User;

import java.util.Map;

/**
 * @author gc
 */
public interface UserService {

    Result login(User user);

    Result getUser();

    Result getOneUser(String username);
}
