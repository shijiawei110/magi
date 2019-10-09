package com.sjw.magi.common.enums;

/**
 * @author shijiawei
 * @version ErrorMsgEnum.java, v 0.1
 * @date 2019/2/22
 * 统一错误码
 */
public enum ErrorMsgEnum {
    /**
     * 系统报错
     */
    SYSTEM_ERROR(99999,""),
    /**
     * 注册信息错误
     */
    REGISTER_PARAM_ERROR(10001, "注册信息错误"),;

    private final Integer code;

    private final String msg;

    ErrorMsgEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
