package com.corn.netty;

import java.util.ArrayList;
import java.util.List;

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
