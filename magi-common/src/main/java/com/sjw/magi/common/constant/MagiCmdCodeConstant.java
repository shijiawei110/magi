package com.sjw.magi.common.constant;

/**
 * @author shijiawei
 * @version MagiCmdCodeConstant.java, v 0.1
 * @date 2019/1/24
 * 注册器码全局变量
 */
public class MagiCmdCodeConstant {
    /**
     * 默认处理器
     **/
    public static final int DEFAULT = 0;

    /**
     * 测试请求
     */
    public static final int TEST = -999;


    /**
     * 服务注册
     **/
    public static final int SERVER_REGISTER = 101;
    /**
     * 钉钉机器人消息
     **/
    public static final int DING_ROBOT_MSG = 102;
    /**
     * 配置变更
     **/
    public static final int CONF_CHANGE = 103;
    /**
     * 消息队列
     **/
    public static final int MSG_NORMAL = 110;
    /**
     * 分布式事务MQ消息(最终一致性)
     **/
    public static final int MSG_TRANSACTION = 111;


    /**
     * 查询注册的服务信息
     */
    public static final int QUERY_REGISTER_INFO = 201;
    /**
     * 查询配置信息
     */
    public static final int QUERY_CONF_INFO = 202;

    /**
     * 客户端心跳请求
     **/
    public static final int CLIENT_HERAT_BEAT = 999;
    /**
     * 服务端从节点心跳请求
     **/
    public static final int SERVER_NODE_HERAT_BEAT = 998;
    /**
     * 服务端回应心跳失效探针请求
     **/
    public static final int SERVER_TRIGGER_TRY = 997;

}
