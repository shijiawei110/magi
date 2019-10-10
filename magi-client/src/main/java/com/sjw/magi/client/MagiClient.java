package com.sjw.magi.client;

import com.sjw.fastnetty.client.NettyClient;
import com.sjw.fastnetty.client.NettyClientBuilder;
import com.sjw.fastnetty.protocol.CmdPackage;
import com.sjw.fastnetty.protocol.ResponseCodeType;
import com.sjw.fastnetty.utils.IpUtil;
import com.sjw.fastnetty.utils.ThreadNameUtil;
import com.sjw.magi.client.channel.listener.DefaultMagiClientListener;
import com.sjw.magi.client.manager.MagiServerCacheManager;
import com.sjw.magi.common.constant.MagiCmdCodeConstant;
import com.sjw.magi.common.expection.MagiException;
import com.sjw.magi.common.pojo.MagiClientNode;
import com.sjw.magi.common.pojo.MagiClientNodeInfo;
import com.sjw.magi.common.request.magi.ClientRegisterReq;
import com.sjw.magi.common.response.magi.RegisterInfoRes;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author shijiawei
 * @version MagiClient.java -> v 1.0
 * @date 2019/3/29
 * magi客户端工具
 */
@Slf4j
public class MagiClient {

    private NettyClient nettyClient;

    private MagiClientNode myselfNode = new MagiClientNode();

    /**
     * magi server 缓存
     */
    private MagiServerCacheManager magiServerCacheManager = new MagiServerCacheManager();

    /**
     * 心跳请求任务线程
     */
    private ScheduledExecutorService heartBeatScheduler;


    public MagiClient(NettyClientBuilder nettyClientBuilder, String zone) {
        nettyClient = nettyClientBuilder.build(new DefaultMagiClientListener());
        //随机获取node name作为唯一标识
        myselfNode.setNodeName("test-node");
        myselfNode.setZone(zone);
        myselfNode.setAddress(IpUtil.getIpv4());
        magiServerCacheManager.addServerNode(nettyClientBuilder.getServerHost(), nettyClientBuilder.getServerPort());
    }

    public void start() throws InterruptedException {
        nettyClient.start();
        //注册服务到magi server
        registerToServer();
        //启动心跳
        startHeartBeat();
        //注册钩子
        registerJvmShutDownHook();

    }

    public void close() {
        nettyClient.shutdown();
    }

    public void ping() throws InterruptedException {
        CmdPackage request = CmdPackage.createReq(MagiCmdCodeConstant.DEFAULT, null);
        CmdPackage response = nettyClient.cmdSync(magiServerCacheManager.getMainNode(), request);
        log.info("ping result = {}", response.getResponse().toString());
    }

    /**
     * 将自己注册到server 的主节点(主节点再去同步从节点)
     *
     * @throws InterruptedException
     */
    private void registerToServer() throws InterruptedException {
        ClientRegisterReq clientRegisterReq = new ClientRegisterReq();
        clientRegisterReq.setAddress(myselfNode.getAddress());
        clientRegisterReq.setNodeName(myselfNode.getNodeName());
        clientRegisterReq.setZone(myselfNode.getZone());
        CmdPackage request = CmdPackage.createReq(MagiCmdCodeConstant.SERVER_REGISTER, clientRegisterReq);
        log.info("magi client init  register");
        CmdPackage response = nettyClient.cmdSync(magiServerCacheManager.getMainNode(), request);
        ResponseCodeType responseStatus = response.getResponseCodeType();
        if (null == responseStatus) {
            throw MagiException.CLIENT_INIT_REGISTER_FAIL;
        }
        if (ResponseCodeType.SUCCESS != responseStatus) {
            throw MagiException.CLIENT_INIT_REGISTER_FAIL;
        }
        log.info("magi client init register success");
    }

    /**
     * 获取注册信息
     */
    public List<MagiClientNodeInfo> getRegisterInfo() throws InterruptedException {
        CmdPackage request = CmdPackage.createReq(MagiCmdCodeConstant.QUERY_REGISTER_INFO, null);
        log.info("magi client request a query register -> req = {}", request);
        CmdPackage response = nettyClient.cmdSync(magiServerCacheManager.getMainNode(), request);
        log.info("magi client query register qreceive a res -> res = {}", response);
        RegisterInfoRes res = (RegisterInfoRes) response.getResponse();
        return res.getNodes();
    }

    /**
     * 开启长连接心跳 , 每3秒发送一次心跳请求 和所有集群服务节点保持心跳,以便在主节点挂了后恢复通讯
     */
    private void startHeartBeat() {
        heartBeatScheduler = Executors.newSingleThreadScheduledExecutor(
                runnable -> new Thread(runnable, ThreadNameUtil.getName("magi-clienthb")));
        heartBeatScheduler.scheduleAtFixedRate(() -> {
            List<String> addrs = magiServerCacheManager.getAllNodesAddress();
            addrs.parallelStream().forEach(this::sendHeartBeat);
        }, 100, 3000, TimeUnit.MILLISECONDS);
    }

    /**
     * 发送心跳请求
     */
    private void sendHeartBeat(String address) {
        CmdPackage request = CmdPackage.createOneWayReq(MagiCmdCodeConstant.CLIENT_HERAT_BEAT, null);
        try {
            nettyClient.cmdOneWay(address, request);
        } catch (InterruptedException e) {
            log.error("magi client send heart beat take a InterruptedException -> server address={}", address);
        }
    }

    /**
     * 注册jvm关闭钩子
     */
    private void registerJvmShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("magi client do jvm shutdown hook start.");
            close();
            log.info("magi client do jvm shutdown hook end.");
        }));
    }
}
