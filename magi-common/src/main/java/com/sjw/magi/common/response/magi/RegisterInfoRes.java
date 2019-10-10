package com.sjw.magi.common.response.magi;

import com.google.common.collect.Lists;
import com.sjw.magi.common.pojo.MagiClientNodeInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shijiawei
 * @version RegisterInfoRes.java, v 0.1
 * @date 2019/2/22
 * 服务端注册信息
 */
@Data
public class RegisterInfoRes {

    private List<MagiClientNodeInfo> nodes = Lists.newArrayList();

    public void addNode(MagiClientNodeInfo node) {
        nodes.add(node);
    }

}
