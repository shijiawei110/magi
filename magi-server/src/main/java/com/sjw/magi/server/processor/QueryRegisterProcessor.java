package com.sjw.magi.server.processor;

import com.sjw.fastnetty.common.ReqCmdProcessor;
import com.sjw.fastnetty.protocol.CmdPackage;
import com.sjw.magi.common.pojo.MagiClientNode;
import com.sjw.magi.common.pojo.MagiClientNodeInfo;
import com.sjw.magi.common.response.magi.RegisterInfoRes;
import com.sjw.magi.server.manager.ClientNodeManager;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author shijiawei
 * @version RegisterProcessor.java, v 0.1
 * @date 2019/2/21
 * 服务注册处理器
 */
@Slf4j
public class QueryRegisterProcessor implements ReqCmdProcessor {

    private ClientNodeManager clientNodeManager;

    public QueryRegisterProcessor(ClientNodeManager clientNodeManager) {
        this.clientNodeManager = clientNodeManager;
    }

    @Override
    public CmdPackage executeRequest(Channel channel, CmdPackage request) {
        List<MagiClientNode> nodesInfo = clientNodeManager.getAllNodes();
        RegisterInfoRes res = new RegisterInfoRes();
        CmdPackage response = CmdPackage.successRes(request.getSn(), res);
        if (CollectionUtils.isEmpty(nodesInfo)) {
            return response;
        }
        nodesInfo.stream().forEach(p -> {
            res.addNode(new MagiClientNodeInfo(p));
        });

        return response;
    }
}
