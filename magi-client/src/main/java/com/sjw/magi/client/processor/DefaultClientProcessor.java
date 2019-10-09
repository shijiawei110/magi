package com.sjw.magi.client.processor;

import com.sjw.fastnetty.common.ReqCmdProcessor;
import com.sjw.fastnetty.protocol.CmdPackage;
import com.sjw.fastnetty.utils.ChannelHelper;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shijiawei
 * @version DefaultClientProcessor.java, v 0.1
 * @date 2019/2/21
 * 默认客户端处理器
 */
@Slf4j
public class DefaultClientProcessor implements ReqCmdProcessor {

    @Override
    public CmdPackage executeRequest(Channel channel, CmdPackage requset) {
        log.info("client default do a request -> addr={} , request={}", ChannelHelper.getRemoteAddr(channel), requset.toString());
        return CmdPackage.successRes(requset.getSn());
    }
}
