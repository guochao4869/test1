package com.example.test1.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author gc
 */
@Data
public class User implements Serializable {
    private String id;
    private String username;
    private String password;
    private String role;
}
