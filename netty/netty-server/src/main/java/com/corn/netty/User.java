package com.corn.netty;

import io.netty.channel.ChannelHandlerContext;

/**
 * 用户映射
 * */
public class User {

    private String userName;

    private ChannelHandlerContext channelHandlerContext;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", channelHandlerContext=" + channelHandlerContext +
                '}';
    }
}
