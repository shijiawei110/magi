package com.sjw.magi.client.manager;

import com.google.common.collect.Lists;
import com.sjw.magi.common.expection.MagiException;
import com.sjw.magi.common.pojo.MagiServerNode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author shijiawei
 * @version MagiServerCacheManager.java, v 0.1
 * @date 2019/2/14
 * 服务节点缓存
 */
public class MagiServerCacheManager {
    /**
     * 读写锁控制 (默认非公平锁)
     */
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    /**
     * 集群节点列表
     */
    private List<MagiServerNode> magiServerNodes = new ArrayList<MagiServerNode>(10);


    public void addServerNode(String ip, int port) {
        if (StringUtils.isBlank(ip) || port == 0) {
            throw MagiException.CLIENT_ADD_SERVER_NODE_PARAMS_ERROR;
        }
        MagiServerNode magiServerNode = new MagiServerNode();
        magiServerNode.setHost(ip);
        magiServerNode.setPort(port);
        magiServerNode.initAddress();
        try {
            lock.writeLock().lock();
            magiServerNodes.add(magiServerNode);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 获取所有节点的address信息
     *
     * @return
     */
    public List<String> getAllNodesAddress() {
        try {
            List<String> addressList = Lists.newArrayList();
            lock.readLock().lock();
            if (CollectionUtils.isEmpty(magiServerNodes)) {
                return addressList;
            }
            for (MagiServerNode node : magiServerNodes) {
                addressList.add(node.getAddress());
            }
            return addressList;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 获取主节点address(单节点的时候默认就是index0位置)
     */
    public String getMainNode() {
        try {
            lock.readLock().lock();
            String address = magiServerNodes.get(0).getAddress();
            return address;
        } finally {
            lock.readLock().unlock();
        }
    }

}
