package com.sjw.magi.common.response;

import com.google.common.collect.Lists;
import com.sjw.magi.common.pojo.MagiClientNodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shijiawei
 * @version RegisterInfoRes.java, v 0.1
 * @date 2019/2/22
 * 服务端注册信息
 */
public class RegisterInfoRes {

    private List<MagiClientNodeInfo> nodes = Lists.newArrayList();

    public void addNode(MagiClientNodeInfo node) {
        nodes.add(node);
    }

}
