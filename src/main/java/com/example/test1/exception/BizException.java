package com.example.test1.exception;

import com.example.test1.common.CommonEnum;
import com.example.test1.pojo.Result;

/**
 * @author gc
 */
public abstract class  BizException extends RuntimeException{
    private static long serialVersionUID = 1L;

    protected String errorCode;

    protected String errorMeg;

    public BizException(BaseError baseError) {
        super(baseError.getResultCode());
        this.errorCode = baseError.getResultCode();
        this.errorMeg = baseError.getResultMeg();
    }

    public BizException(BaseError baseError, Throwable throwable) {
        super(baseError.getResultCode(), throwable);
        this.errorCode = baseError.getResultCode();
        this.errorMeg = baseError.getResultMeg();
    }

    public BizException(String errorCode, String errorMeg) {
        this.errorCode = errorCode;
        this.errorMeg = errorMeg;
    }

    public BizException(String message, String errorCode, String errorMeg) {
        super(message);
        this.errorCode = errorCode;
        this.errorMeg = errorMeg;
    }

    public BizException(String message, Throwable cause, String errorCode, String errorMeg) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMeg = errorMeg;
    }

    public BizException(Throwable cause, String errorCode, String errorMeg) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMeg = errorMeg;
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorCode, String errorMeg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
        this.errorMeg = errorMeg;
    }

    public void handler(Result result){
        result.setResult(false);
        result.setCode("400");
        result.setMeg(this.errorMeg);
    }
}
