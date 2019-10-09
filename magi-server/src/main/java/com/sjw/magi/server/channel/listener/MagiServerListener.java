package com.sjw.magi.server.channel.listener;

import com.sjw.fastnetty.nettybase.listener.ChannelEventListener;
import com.sjw.fastnetty.utils.ChannelHelper;
import com.sjw.magi.server.manager.ClientNodeManager;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shijiawei
 * @version MagiServerListener.java, v 0.1
 * @date 2019/2/20
 */
@Slf4j
public class MagiServerListener implements ChannelEventListener {

    public MagiServerListener(ClientNodeManager clientNodeManager) {
        this.clientNodeManager = clientNodeManager;
    }

    /**
     * client节点管理器
     */
    private ClientNodeManager clientNodeManager = new ClientNodeManager();

    @Override
    public void onChannelConnect(String linkAddress, Channel channel) {
        log.info("【listener】magi system server listener receive a connect -> addr = {}", ChannelHelper.getRemoteAddr(channel));
    }

    @Override
    public void onChannelClose(String linkAddress, Channel channel) {
        //执行节点删除 (需要区别是服务器节点还是客户端节点)
        log.info("【listener】magi system server listener close a connect -> addr = {}", ChannelHelper.getRemoteAddr(channel));
        clientNodeManager.deleteNode(channel);
    }

    @Override
    public void onChannelException(String linkAddress, Channel channel) {
        //执行节点删除 (需要区别是服务器节点还是客户端节点)
        log.info("【listener】magi system server listener close a connect -> addr = {}", ChannelHelper.getRemoteAddr(channel));
        clientNodeManager.deleteNode(channel);
    }

    public void setClientNodeManager(ClientNodeManager clientNodeManager) {
        this.clientNodeManager = clientNodeManager;
    }
}
