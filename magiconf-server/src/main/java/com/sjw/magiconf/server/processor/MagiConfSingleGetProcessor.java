package com.sjw.magiconf.server.processor;

import com.sjw.fastnetty.common.ReqCmdProcessor;
import com.sjw.fastnetty.protocol.CmdPackage;
import com.sjw.fastnetty.utils.ChannelHelper;
import com.sjw.magi.common.enums.MagiConfErrorMsgEnum;
import com.sjw.magiconf.server.store.MemoryConf;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @author shijiawei
 * @version MagiConfSingleGetProcessor.java -> v 1.0
 * @date 2019/10/10
 */
@Slf4j
public class MagiConfSingleGetProcessor implements ReqCmdProcessor {

    private MemoryConf memoryConf;

    public MagiConfSingleGetProcessor(MemoryConf memoryConf) {
        this.memoryConf = memoryConf;
    }

    @Override
    public CmdPackage executeRequest(Channel channel, CmdPackage request) {
        try {
            String key = (String) request.getRequest();
            return CmdPackage.successRes(request.getSn(), memoryConf.get(key));
        } catch (Exception e) {
            log.error("magi system server register processor error -> addr={} , request={} , stack={}"
                    , ChannelHelper.getRemoteAddr(channel), request.toString(), ExceptionUtils.getStackTrace(e));
            return CmdPackage.errorRes(request.getSn(), MagiConfErrorMsgEnum.SYSTEM_ERROR.getCode(), ExceptionUtils.getMessage(e));
        }
    }
}
