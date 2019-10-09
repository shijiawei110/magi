package com.sjw.magi.common.expection;

import java.text.MessageFormat;

/**
 * @author shijw
 * @version MagiException.java, v 0.1 2018-10-07 20:05 shijw
 */
public class MagiException extends RuntimeException {

    public static final MagiException CLIENT_ADD_SERVER_NODE_PARAMS_ERROR= new MagiException(10001, "客户端添加服务缓存信息失败(参数不正确)");


    /**
     * 异常信息
     */
    protected String msg;

    /**
     * 具体异常码
     */
    protected int code;

    /**
     * 异常构造器
     *
     * @param code      代码
     * @param msgFormat 消息模板,内部会用MessageFormat拼接，模板类似：userid={0},message={1},date{2}
     * @param args      具体参数的值
     */
    private MagiException(int code, String msgFormat, Object... args) {
        super(MessageFormat.format(msgFormat, args));
        this.code = code;
        this.msg = MessageFormat.format(msgFormat, args);
    }

    /**
     * 默认构造器
     */
    private MagiException() {
        super();
    }

    /**
     * 异常构造器
     *
     * @param message
     * @param cause
     */
    private MagiException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 异常构造器
     *
     * @param cause
     */
    private MagiException(Throwable cause) {
        super(cause);
    }

    /**
     * 异常构造器
     *
     * @param message
     */
    private MagiException(String message) {
        super(message);
    }

    /**
     * 实例化异常
     *
     * @return 异常类
     */
    public MagiException newInstance(String msgFormat, Object... args) {
        return new MagiException(this.code, msgFormat, args);
    }


}
