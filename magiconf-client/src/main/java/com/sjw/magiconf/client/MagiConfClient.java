package com.sjw.magiconf.client;

import com.sjw.fastnetty.client.NettyClient;
import com.sjw.fastnetty.client.NettyClientBuilder;
import com.sjw.fastnetty.protocol.CmdPackage;
import com.sjw.fastnetty.utils.ThreadNameUtil;
import com.sjw.magi.common.constant.MagiConfCmdCodeConstant;
import com.sjw.magiconf.client.listener.DefaultMagiConfClientListener;
import com.sjw.magiconf.client.manager.ServerCacheManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author shijiawei
 * @version MagiConfClient.java -> v 1.0
 * @date 2019/3/29
 * magiconf客户端工具
 */
@Slf4j
public class MagiConfClient {

    private NettyClient nettyClient;

    /**
     * magi server 缓存
     */
    private ServerCacheManager serverCacheManager = new ServerCacheManager();

    /**
     * 心跳请求任务线程
     */
    private ScheduledExecutorService heartBeatScheduler;


    public MagiConfClient(NettyClientBuilder nettyClientBuilder, String zone) {
        nettyClient = nettyClientBuilder.build(new DefaultMagiConfClientListener());
        serverCacheManager.addServerNode(nettyClientBuilder.getServerHost(), nettyClientBuilder.getServerPort());
    }

    public void start() throws InterruptedException {
        nettyClient.start();
        //启动心跳
        startHeartBeat();
    }

    public void close() {
        nettyClient.shutdown();
    }

    public void ping() throws InterruptedException {
        CmdPackage request = CmdPackage.createReq(MagiConfCmdCodeConstant.DEFAULT, null);
        CmdPackage response = nettyClient.cmdSync(serverCacheManager.getMainNode(), request);
        log.info("ping result = {}", response.getResponse().toString());
    }


    /**
     * 获取注册信息
     */
    public Object getSingleValue(String key) throws InterruptedException {
        CmdPackage request = CmdPackage.createReq(MagiConfCmdCodeConstant.SINGLE_GET, null);
        CmdPackage response = nettyClient.cmdSync(serverCacheManager.getMainNode(), request);
        log.info("magiconf client get single value | receive a res -> res = {}", response);
        Object res = response.getResponse();
        return res;
    }

    /**
     * 开启长连接心跳 , 每3秒发送一次心跳请求 和所有集群服务节点保持心跳,以便在主节点挂了后恢复通讯
     */
    private void startHeartBeat() {
        heartBeatScheduler = Executors.newSingleThreadScheduledExecutor(
                runnable -> new Thread(runnable, ThreadNameUtil.getName("magi-clienthb")));
        heartBeatScheduler.scheduleAtFixedRate(() -> {
            List<String> addrs = serverCacheManager.getAllNodesAddress();
            addrs.parallelStream().forEach(this::sendHeartBeat);
        }, 100, 3000, TimeUnit.MILLISECONDS);
    }

    /**
     * 发送心跳请求
     */
    private void sendHeartBeat(String address) {
        CmdPackage request = CmdPackage.createOneWayReq(MagiConfCmdCodeConstant.CLIENT_HERAT_BEAT, null);
        try {
            nettyClient.cmdOneWay(address, request);
        } catch (InterruptedException e) {
            log.error("magi client send heart beat take a InterruptedException -> server address={}", address);
        }
    }
}
