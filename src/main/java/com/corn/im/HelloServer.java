package com.corn.im;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class HelloServer {

    public static void main(String[] args) throws InterruptedException {

        //从线程组，会处理对应Channel的io操作
        EventLoopGroup workGroup = new NioEventLoopGroup();
        //主线程组，用于接受客户端的链接
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        try{
            //netty服务器的创建
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(bossGroup,workGroup)            //设置主从线程组
                    .channel(NioServerSocketChannel.class) //设置Nio的双向通道
                    .childHandler(new ChannelInitializer<NioServerSocketChannel>() {
                        protected void initChannel(NioServerSocketChannel nioServerSocketChannel) throws Exception {

                        }
                    });

            //启动server，并且设置9292为启动端口号，同时启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(9292).sync();

            //监听关闭的channel，设置为同步方式
            channelFuture.channel().close().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
