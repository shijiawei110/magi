package com.sjw.magi.common.pojo;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * @author shijiawei
 * @version MagiClientNodeInfo.java, v 0.1
 * @date 2019/2/14
 * 注册的客户端节点信息,用于传输使用,在序列化channel类在进行netty传输会出现问题
 */
@Data
public class MagiClientNodeInfo {
    /**
     * 注册的空间
     */
    private String zone;
    /**
     * 客户端唯一名称
     */
    private String nodeName;
    /**
     * address
     **/
    private String address;


    public MagiClientNodeInfo() {
    }

    public MagiClientNodeInfo(MagiClientNode magiClientNode) {
        this.zone = magiClientNode.getZone();
        this.nodeName = magiClientNode.getNodeName();
        this.address = magiClientNode.getAddress();
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
