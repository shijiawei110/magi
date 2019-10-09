package com.sjw.magi.common.pojo;

import io.netty.channel.Channel;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * @author shijiawei
 * @version MagiServerNode.java, v 0.1
 * @date 2019/2/14
 * 注册的客户端节点信息
 */
@Data
public class MagiClientNode {
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

    /**
     * 连接channel 同步到从节点的时候清空 从节点需要重新建立连接(注意:channel属性序列化问题无法网络传输)
     */
    private Channel channel;

    public MagiClientNode() {

    }

    public MagiClientNode(String zone, String nodeName, String address, Channel channel) {
        this.zone = zone;
        this.nodeName = nodeName;
        this.address = address;
        this.channel = channel;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
