package com.sjw.magi.server;

import com.sjw.fastnetty.common.ReqCmdProcessor;
import com.sjw.fastnetty.common.ReqCmdProcessorHolder;
import com.sjw.fastnetty.nettybase.listener.ChannelEventListener;
import com.sjw.fastnetty.server.NettyServer;
import com.sjw.fastnetty.server.NettyServerBuilder;
import com.sjw.fastnetty.utils.ThreadPoolUtil;
import com.sjw.magi.common.constant.ProcessorPoolNameConstant;
import com.sjw.magi.common.constant.ReqCmdProcessorCodeConstant;
import com.sjw.magi.server.channel.listener.MagiServerListener;
import com.sjw.magi.server.manager.ClientNodeManager;
import com.sjw.magi.common.pojo.MagiServerNode;
import com.sjw.magi.server.processor.MagiServerDefaultProcessor;
import com.sjw.magi.server.processor.QueryRegisterProcessor;
import com.sjw.magi.server.processor.RegisterProcessor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

/**
 * @author shijiawei
 * @version MagiServer.java, v 0.1
 * @date 2019/2/14
 * magi服务类  用main或者springboot进行启动
 */
@Slf4j
public class MagiServer {
    /**
     * netty服务实例
     **/
    private NettyServer nettyServer;
    /**
     * 自己节点的信息
     */
    private MagiServerNode myselfNode;

    /**
     * client节点管理器
     */
    private ClientNodeManager clientNodeManager;


    public MagiServer(MagiServerBuilder magiServerBuilder, NettyServerBuilder nettyServerBuilder) {
        MagiServerNode me = new MagiServerNode();
        me.setHost(magiServerBuilder.getHost());
        me.setPort(nettyServerBuilder.getListenerPort());
        me.initAddress();
        me.setMainNode(magiServerBuilder.isMainNode());
        myselfNode = me;
        clientNodeManager = new ClientNodeManager();
        ChannelEventListener serverListener = new MagiServerListener(clientNodeManager);
        nettyServer = nettyServerBuilder.build(serverListener);
        //如果是集群模式，渲染其他服务器节点
//        if(magiServerBuilder.isClusterMode()){
//
//        }
    }

    public void start() {
        if (null == nettyServer) {
            return;
        }
        nettyServer.start();
        //注册处理器
        registerProcessor();
        //注册jvm钩子
        registerJvmShutDownHook();
    }

    public void close() {
        if (null == nettyServer) {
            return;
        }
        nettyServer.shutdown();
    }


    private void registerProcessor() {
        if (null == nettyServer) {
            return;
        }
        //测试处理器
        ReqCmdProcessor defaultProcessor = new MagiServerDefaultProcessor();
        ExecutorService defaultProcessorPool = ThreadPoolUtil.createSinglePool(ProcessorPoolNameConstant.SERVER_DEFAULT_POOL_NAME);
        ReqCmdProcessorHolder defaultProcessorHolder = new ReqCmdProcessorHolder(defaultProcessor, defaultProcessorPool);
        nettyServer.registerCmdProcessor(ReqCmdProcessorCodeConstant.DEFAULT, defaultProcessorHolder);
        // 服务注册处理器
        ReqCmdProcessor registerProcessor = new RegisterProcessor(this.clientNodeManager);
        ExecutorService registerProcessorPool = ThreadPoolUtil.createDefaultPool(ProcessorPoolNameConstant.SERVER_REGISTER_POOL_NAME);
        ReqCmdProcessorHolder registerProcessorHolder = new ReqCmdProcessorHolder(registerProcessor, registerProcessorPool);
        nettyServer.registerCmdProcessor(ReqCmdProcessorCodeConstant.SERVER_REGISTER, registerProcessorHolder);
        //服务查询处理器
        ReqCmdProcessor queryRegisterProcessor = new QueryRegisterProcessor(this.clientNodeManager);
        ExecutorService queryRegisterProcessorPool = ThreadPoolUtil.createDefaultPool(ProcessorPoolNameConstant.SERVER_QUERY_REGISTER_POOL_NAME);
        ReqCmdProcessorHolder queryRegisterProcessorHolder = new ReqCmdProcessorHolder(queryRegisterProcessor, queryRegisterProcessorPool);
        nettyServer.registerCmdProcessor(ReqCmdProcessorCodeConstant.QUERY_REGISTER_INFO, queryRegisterProcessorHolder);
    }


    /**
     * 注册jvm关闭钩子
     */
    private void registerJvmShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("magi server do jvm shutdown hook start.");
            close();
            log.info("magi server do jvm shutdown hook end.");
        }));
    }


}
