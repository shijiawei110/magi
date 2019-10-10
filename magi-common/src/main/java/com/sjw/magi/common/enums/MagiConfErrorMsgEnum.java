package com.sjw.magi.common.enums;

/**
 * @author shijiawei
 * @version MagiConfErrorMsgEnum.java, v 0.1
 * @date 2019/2/22
 * 统一错误码
 */
public enum MagiConfErrorMsgEnum {
    /**
     * 系统报错
     */
    SYSTEM_ERROR(99999,"");

    private final Integer code;

    private final String msg;

    MagiConfErrorMsgEnum(int code, String msg) {
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
