package com.sjw.magiconf.server.listener;

import com.sjw.fastnetty.nettybase.listener.ChannelEventListener;
import com.sjw.fastnetty.utils.ChannelHelper;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shijiawei
 * @version MagiConfServerListener.java, v 0.1
 * @date 2019/2/20
 * 默认监听 只打日志
 */
@Slf4j
public class DefaultMagiConfServerListener implements ChannelEventListener {

    @Override
    public void onChannelConnect(String linkAddress, Channel channel) {
        log.info("【listener】magiconf system server listener receive a connect -> addr = {}", ChannelHelper.getRemoteAddr(channel));
    }

    @Override
    public void onChannelClose(String linkAddress, Channel channel) {
        log.info("【listener】magiconf system server listener close a connect -> addr = {}", ChannelHelper.getRemoteAddr(channel));
    }

    @Override
    public void onChannelException(String linkAddress, Channel channel) {
        log.info("【listener】magiconf system server listener exception a connect -> addr = {}", ChannelHelper.getRemoteAddr(channel));
    }
}
