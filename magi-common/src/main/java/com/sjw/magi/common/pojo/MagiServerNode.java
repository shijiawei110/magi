package com.sjw.magi.common.pojo;

import com.sjw.fastnetty.utils.IpUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author shijiawei
 * @version MagiServerNode.java, v 0.1
 * @date 2019/2/14
 * 服务应用节点信息
 */
@Data
public class MagiServerNode {
    /**
     * 是否为主节点
     **/
    private boolean isMainNode = false;
    /**
     * address
     */
    private String address;
    /**
     * host
     **/
    private String host;
    /**
     * port(netty服务端口,不是jvm服务端口)
     **/
    private int port;


    public MagiServerNode() {
    }

    public void initAddress() {
        if (StringUtils.isBlank(host) || port == 0) {
            return;
        }
        address = IpUtil.hostAndPortToAddress(host, port);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
