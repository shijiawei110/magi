package com.sjw.magiconf.client;

/**
 * @author shijiawei
 * @version MagiConfKv.java -> v 1.0
 * @date 2019/10/22
 * magiconf获取具体key数据的单例对象
 * todo 另外一种方法是弄一个配置文件直接 用配置文件的成员变量
 */
public class MagiConfKv {

    private MagiConfKv (){}
    public static final MagiConfKv getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final MagiConfKv INSTANCE = new MagiConfKv();
    }

    public void get(String key){

    }
}
