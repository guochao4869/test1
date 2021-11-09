package com.example.test1.pojo;


import lombok.Data;

import java.io.Serializable;

/**
 * @author gc
 */
@Data
public class Result implements Serializable {
    private Boolean result;
    private String meg;
    private Object data;
    private Object code;

    public Result(Boolean result, String meg, Object data, Object code) {
        this.result = result;
        this.meg = meg;
        this.data = data;
        this.code = code;
    }

    public Result(Boolean result, String meg, Object data) {
        this.result = result;
        this.meg = meg;
        this.data = data;
    }
}
