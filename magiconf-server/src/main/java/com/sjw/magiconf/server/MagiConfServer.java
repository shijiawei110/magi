package com.sjw.magiconf.server;

import com.sjw.fastnetty.common.ReqCmdProcessor;
import com.sjw.fastnetty.common.ReqCmdProcessorHolder;
import com.sjw.fastnetty.nettybase.listener.ChannelEventListener;
import com.sjw.fastnetty.server.NettyServer;
import com.sjw.fastnetty.server.NettyServerBuilder;
import com.sjw.fastnetty.utils.ThreadPoolUtil;
import com.sjw.magi.common.constant.MagiConfCmdCodeConstant;
import com.sjw.magi.common.constant.MagiConfCmdPoolNameConstant;
import com.sjw.magiconf.server.listener.DefaultMagiConfServerListener;
import com.sjw.magiconf.server.processor.MagiConfServerDefaultProcessor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

/**
 * @author shijiawei
 * @version MagiConfServer.java, v 0.1
 * @date 2019/2/14
 * magi服务类  用main或者springboot进行启动
 */
@Slf4j
public class MagiConfServer {

    /**
     * netty服务实例
     **/
    private NettyServer nettyServer;

    public MagiConfServer(MagiConfServerBuilder magiConfServerBuilder, NettyServerBuilder nettyServerBuilder) {
        ChannelEventListener serverListener = new DefaultMagiConfServerListener();
        nettyServer = nettyServerBuilder.build(serverListener);
    }

    public void start() {
        if (null == nettyServer) {
            return;
        }
        nettyServer.start();
        //注册处理器
        registerProcessor();
        //注册jvm钩子
//        registerJvmShutDownHook();
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
        ReqCmdProcessor defaultProcessor = new MagiConfServerDefaultProcessor();
        ExecutorService defaultProcessorPool = ThreadPoolUtil.createSinglePool(MagiConfCmdPoolNameConstant.SERVER_DEFAULT_POOL_NAME);
        ReqCmdProcessorHolder defaultProcessorHolder = new ReqCmdProcessorHolder(defaultProcessor, defaultProcessorPool);
        nettyServer.registerCmdProcessor(MagiConfCmdCodeConstant.DEFAULT, defaultProcessorHolder);
    }


}
