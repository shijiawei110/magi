package com.sjw.magi.common.request;


import lombok.Data;

/**
 * @author shijiawei
 * @version ClientRegisterReq.java, v 0.1
 * @date 2019/2/22
 * 客户端注册信息
 */
@Data
public class ClientRegisterReq {
    private String zone;
    private String address;
    private String nodeName;
}
