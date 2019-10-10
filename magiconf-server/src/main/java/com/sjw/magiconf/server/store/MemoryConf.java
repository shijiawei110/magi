package com.sjw.magiconf.server.store;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ConcurrentMap;

/**
 * @author shijiawei
 * @version MemoryConf.java -> v 1.0
 * @date 2019/10/10
 */
public class MemoryConf {

    /**
     * 内存数据map
     */
    private ConcurrentMap<String, Object> table = Maps.newConcurrentMap();

    public void put(String k, Object v) {
        if (StringUtils.isBlank(k)) {
            return;
        }
        if (null == v) {
            return;
        }
        table.put(k, v);
    }

    public Object get(String k) {
        if (StringUtils.isBlank(k)) {
            return null;
        }
        return table.get(k);
    }


}
