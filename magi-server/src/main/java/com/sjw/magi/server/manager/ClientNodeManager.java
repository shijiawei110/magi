package com.sjw.magi.server.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sjw.fastnetty.utils.ChannelHelper;
import com.sjw.magi.common.pojo.MagiClientNode;
import com.sjw.magi.common.pojo.MagiClientNodeInfo;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author shijiawei
 * @version ClientNodeManager.java, v 0.1
 * @date 2019/2/14
 * 客户端节点注册管理器
 */
@Slf4j
public class ClientNodeManager {
    /**
     * 读写锁控制
     */
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 客户端管理
     */
    private HashMap<String/*zone*/, Map<String/*clientNodeName*/, MagiClientNode>> clientTable = Maps.newHashMap();


    /**
     * 新增节点
     *
     * @param magiClientNode
     */
    public void addNode(MagiClientNode magiClientNode) {
        try {
            lock.writeLock().lock();
            if (!checkNode(magiClientNode)) {
                log.info("magi system server add client node error -> because of params error");
                return;
            }
            Map<String, MagiClientNode> zoneMap = clientTable.get(magiClientNode.getZone());
            if (null == zoneMap) {
                zoneMap = Maps.newHashMap();
            }
            zoneMap.put(magiClientNode.getNodeName(), magiClientNode);
            clientTable.put(magiClientNode.getZone(), zoneMap);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 剔除节点
     */
    public void deleteNode(Channel deleteChannel) {
        boolean deleteSuccess = false;
        try {
            lock.writeLock().lock();
            if (MapUtils.isEmpty(clientTable)) {
                return;
            }
            for (Map<String, MagiClientNode> zoneMap : clientTable.values()) {
                Iterator<Map.Entry<String, MagiClientNode>> it = zoneMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, MagiClientNode> next = it.next();
                    MagiClientNode v = next.getValue();
                    if (ChannelHelper.isEquals(deleteChannel, v.getChannel())) {
                        it.remove();
                        log.info("magi server delete a client node success -> addr = {},nodeInfo = {}",
                                ChannelHelper.getRemoteAddr(deleteChannel), new MagiClientNodeInfo(v).toString());
                        deleteSuccess = true;
                    }
                }
            }
            if (!deleteSuccess) {
                log.warn("magi server delete client node fail -> because not found this node in table  -> addr = {}",
                        ChannelHelper.getRemoteAddr(deleteChannel));
            }
        } finally {
            lock.writeLock().unlock();
        }

    }

    /**
     * 获取所有节点信息
     */
    public List<MagiClientNode> getAllNodes() {
        List<MagiClientNode> result = Lists.newArrayList();
        try {
            lock.readLock().lock();
            if (null == clientTable) {
                return result;
            }
            clientTable.forEach((k, v) -> {
                if (null != v) {
                    v.forEach((dk, dv) -> {
                        result.add(dv);
                    });
                }
            });
            return result;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 校验参数
     *
     * @param magiClientNode
     * @return
     */
    private boolean checkNode(MagiClientNode magiClientNode) {
        if (StringUtils.isBlank(magiClientNode.getZone())) {
            return false;
        }

        if (StringUtils.isBlank(magiClientNode.getNodeName())) {
            return false;
        }

        if (StringUtils.isBlank(magiClientNode.getAddress())) {
            return false;
        }
        return true;
    }


}
