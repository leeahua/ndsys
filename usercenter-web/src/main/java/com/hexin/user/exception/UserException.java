package com.hexin.user.exception;


import com.hexin.user.enums.ResultStatueEnum;

/**
 * 业务异常处理类.
 */


public class UserException extends RuntimeException {

    private String code;

    private String messge;

    public UserException(ResultStatueEnum resultStatueEnum) {
        super(resultStatueEnum.getMsg());
        this.code = resultStatueEnum.getCode();
    }

    public String getCode() {
        return code;
    }



    public String getMessge() {
        return messge;
    }
}
