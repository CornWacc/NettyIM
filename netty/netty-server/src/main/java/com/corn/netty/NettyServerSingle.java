package com.corn.netty;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NettyServerSingle {

    private static final NettyServerSingle INSTANCE = new NettyServerSingle();

    /**
     * 连接数
     * */
    private volatile int connectCount;

    /**
     * 当前连接人员集合映射
     * */
    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public static NettyServerSingle getInstance(){
        return INSTANCE;
    }

    public int getConnectCount() {
        return connectCount;
    }

    public void setConnectCount(int connectCount) {
        this.connectCount = connectCount;
    }

}
