package com.corn.im.hello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class HelloServer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args){


        //从线程组，会处理对应Channel的io操作
        EventLoopGroup workGroup = new NioEventLoopGroup(2);
        //主线程组，用于接受客户端的链接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);

        try{
            //netty服务器的创建
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(bossGroup,workGroup)            //设置主从线程组
                    .channel(NioServerSocketChannel.class) //设置Nio的双向通道
                    .childHandler(new HelloServerInitializer());

            //启动server，并且设置9292为启动端口号，同时启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(9292).sync();
            if(channelFuture.isSuccess()){
                System.out.println("启动成功！");
            }

//            监听关闭的channel，设置为同步方式
            channelFuture.channel().closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("服务器关闭");
                    }
                }
            }).sync();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(1);
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
