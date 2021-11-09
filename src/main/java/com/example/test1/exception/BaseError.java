package com.example.test1.exception;

/**
 * @author OuLa-test
 */
public interface BaseError {
    /**
     * 错误码
     * @return
     */
    String getResultCode();

    /**
     * 错误信息
     * @return
     */
    String getResultMeg();
}
