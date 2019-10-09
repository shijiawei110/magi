package com.sjw.magi.client.channel.listener;

import com.sjw.fastnetty.nettybase.listener.ChannelEventListener;
import com.sjw.fastnetty.utils.ChannelHelper;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shijiawei
 * @version DefaultMagiClientListener.java -> v 1.0
 * @date 2019/3/29
 * desc : 默认只打印日志的client监听
 */
@Slf4j
public class DefaultMagiClientListener implements ChannelEventListener {
    @Override
    public void onChannelConnect(String linkAddress, Channel channel) {
        log.info("magi system client listener receive a connect -> addr = {}", ChannelHelper.getRemoteAddr(channel));
    }

    @Override
    public void onChannelClose(String linkAddress, Channel channel) {
        log.info("magi system client listener close a connect -> addr = {}", ChannelHelper.getRemoteAddr(channel));
    }

    @Override
    public void onChannelException(String linkAddress, Channel channel) {
        log.info("magi system client listener exception a connect -> addr = {}", ChannelHelper.getRemoteAddr(channel));
    }
}
