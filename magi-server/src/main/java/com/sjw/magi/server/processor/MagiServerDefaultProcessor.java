package com.sjw.magi.server.processor;

import com.sjw.fastnetty.common.ReqCmdProcessor;
import com.sjw.fastnetty.protocol.CmdPackage;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shijiawei
 * @version RegisterProcessor.java, v 0.1
 * @date 2019/2/21
 * 测试处理器
 */
@Slf4j
public class MagiServerDefaultProcessor implements ReqCmdProcessor {

    @Override
    public CmdPackage executeRequest(Channel channel, CmdPackage requset) {
        return CmdPackage.successRes(requset.getSn(), "magi default processor succeed");
    }
}
