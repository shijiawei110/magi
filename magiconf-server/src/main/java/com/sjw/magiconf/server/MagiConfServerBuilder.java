package com.sjw.magiconf.server;

import com.sjw.fastnetty.utils.IpUtil;
import lombok.Data;

/**
 * @author shijiawei
 * @version MagiConfServerBuilder.java, v 0.1
 * @date 2019/2/14
 */
@Data
public class MagiConfServerBuilder {

    /**
     * 是否开启集群模式
     */
    private boolean isClusterMode = false;
    /**
     * 是否是主机
     */
    private boolean isMainNode = false;

    /**
     * 机器ip 不设置的话自动获取
     */
    private String host = IpUtil.getIpv4();

}
