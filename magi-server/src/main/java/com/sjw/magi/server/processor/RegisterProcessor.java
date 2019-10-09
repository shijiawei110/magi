package com.sjw.magi.server.processor;

import com.sjw.fastnetty.common.ReqCmdProcessor;
import com.sjw.fastnetty.protocol.CmdPackage;
import com.sjw.fastnetty.utils.ChannelHelper;
import com.sjw.magi.common.enums.ErrorMsgEnum;
import com.sjw.magi.common.pojo.MagiClientNode;
import com.sjw.magi.common.request.ClientRegisterReq;
import com.sjw.magi.server.manager.ClientNodeManager;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @author shijiawei
 * @version RegisterProcessor.java, v 0.1
 * @date 2019/2/21
 * 服务注册处理器
 */
@Slf4j
public class RegisterProcessor implements ReqCmdProcessor {

    private ClientNodeManager clientNodeManager;

    public RegisterProcessor(ClientNodeManager clientNodeManager) {
        this.clientNodeManager = clientNodeManager;
    }

    @Override
    public CmdPackage executeRequest(Channel channel, CmdPackage requset) {
        ClientRegisterReq clientRegisterReq = (ClientRegisterReq) requset.getRequest();
        if (null == clientRegisterReq) {
            ErrorMsgEnum error = ErrorMsgEnum.REGISTER_PARAM_ERROR;
            return CmdPackage.errorRes(requset.getSn(), error.getCode(), error.getMsg());
        }
        String zone = clientRegisterReq.getZone();
        String address = clientRegisterReq.getAddress();
        String nodeName = clientRegisterReq.getNodeName();
        if (StringUtils.isBlank(zone) || StringUtils.isBlank(address) || StringUtils.isBlank(nodeName)) {
            ErrorMsgEnum error = ErrorMsgEnum.REGISTER_PARAM_ERROR;
            return CmdPackage.errorRes(requset.getSn(), error.getCode(), error.getMsg());
        }
        //执行节点注册
        try {
            clientNodeManager.addNode(new MagiClientNode(zone, nodeName, address, channel));
            return CmdPackage.successRes(requset.getSn());
        } catch (Exception e) {
            log.error("magi system server register processor error -> addr={} , request={} , stack={}"
                    , ChannelHelper.getRemoteAddr(channel), requset.toString(), ExceptionUtils.getStackTrace(e));
            return CmdPackage.errorRes(requset.getSn(), ErrorMsgEnum.SYSTEM_ERROR.getCode(), ExceptionUtils.getMessage(e));
        }


    }
}
