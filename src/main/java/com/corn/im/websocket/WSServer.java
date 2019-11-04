package com.corn.im.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.apache.el.stream.Optional;

public class WSServer {

    public static void main(String[] args) {

        NioEventLoopGroup bossLoopGroup = new NioEventLoopGroup();
        NioEventLoopGroup supLoopGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(bossLoopGroup,supLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WSServerInitializer());

            ChannelFuture sync = serverBootstrap.bind(9099).sync();
            sync.addListener(new GenericFutureListener<Future<? super Void>>() {
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if(future.isSuccess()){
                        System.out.println("连接成功！");
                    }else{
                        System.out.println("连接失败！");
                    }
                }
            });

            //            监听关闭的channel，设置为同步方式
            sync.channel().closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("服务器关闭");
                    }
                }
            }).sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(1);
            bossLoopGroup.shutdownGracefully();
            supLoopGroup.shutdownGracefully();
        }
    }
}
