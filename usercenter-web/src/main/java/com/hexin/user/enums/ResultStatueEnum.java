package com.hexin.user.enums;

/**
 * 返回状态.
 * @author liyaohua@hexindai.com
 */
public enum ResultStatueEnum {
    /**
     * 成功返回信息
     * */
    SUCCESS("00", "处理成功"),
    /**
     * 失败返回信息
     * */
    ERROE("01", "处理失败"),
    PARAMETER_ERROR("02","参数有误"),
    LOGIN_ERROR("03","用户名或者密码错误" ),

    SERIAL_ERROR("04","串口配置异常" ),
    SERIAL_PORT_ERROR("06","串口异常" ),
    SERIAL_SEND_ERROR("05","串口数据发送异常" ),
    USER_ERROR("07","用户名信息不存在" ),
    BATCH_DATA_NOT_EXISTS("08", "批次数据不存在！请录入"),
    POUND_DATA_NOT_EXISTS("07", "地磅数据不存在！请录入");

    private String code;
    private String msg;

    ResultStatueEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
