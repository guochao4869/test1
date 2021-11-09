package com.example.test1.common;

import com.example.test1.exception.BaseError;

/**
 * @author GC
 */

public enum CommonEnum implements BaseError {

    /**
     * 定义错误码
     */
    OK("200","成功"),
    ERROR("400","失败")
    ;

    /**
     * 错误码
     */
    private String errorCode;
    /**
     * 错误内容
     */
    private String errorMeg;

    CommonEnum(String errorCode, String errorMeg) {
        this.errorCode = errorCode;
        this.errorMeg = errorMeg;
    }


    @Override
    public String getResultCode() {
        return errorCode;
    }

    @Override
    public String getResultMeg() {
        return errorMeg;
    }
}
