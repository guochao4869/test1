package com.example.test1.exception;

public class MyExceotion extends BizException{
    public MyExceotion(BaseError baseError) {
        super(baseError);
    }

    public MyExceotion(BaseError baseError, Throwable throwable) {
        super(baseError, throwable);
    }

    public MyExceotion(String errorCode, String errorMeg) {
        super(errorCode, errorMeg);
    }

    public MyExceotion(String message, String errorCode, String errorMeg) {
        super(message, errorCode, errorMeg);
    }

    public MyExceotion(String message, Throwable cause, String errorCode, String errorMeg) {
        super(message, cause, errorCode, errorMeg);
    }

    public MyExceotion(Throwable cause, String errorCode, String errorMeg) {
        super(cause, errorCode, errorMeg);
    }

    public MyExceotion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorCode, String errorMeg) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode, errorMeg);
    }
}
