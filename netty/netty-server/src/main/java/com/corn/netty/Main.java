package com.corn.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.util.Date;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {

        System.out.println("请输入连接端口:");
        String port = scanner.nextLine();

        System.out.println("确定绑定:"+port+"吗? Y/N");
        String connectSure = scanner.nextLine();
        boolean sure = connectSure.toUpperCase().equals("Y") ? true : false;

        if(sure){

            System.out.println(new Date()+": ********** 连接开始! **********");

            //使用netty连接
            NioEventLoopGroup portListenGrop = new NioEventLoopGroup(); //端口监听线程组
            NioEventLoopGroup ioListenGrop = new NioEventLoopGroup(); //读写监听线程组

            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(portListenGrop,ioListenGrop)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        protected void initChannel(NioSocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new StringDecoder());//设置字符编码
                            channel.pipeline().addLast(new ActiveHandler());
                        }
                    }).bind(Integer.parseInt(port)).addListener(future -> {
                        if(future.isSuccess()){
                            System.out.println(new Date()+": ********** 绑定成功! **********");
                        }else {
                            System.out.println(new Date()+": ********** 绑定失败! **********");
                        }
            });


        }else{

            System.out.println("重新输入请输入[1],退出请输入[2]");
            String retry = scanner.nextLine();
            boolean re = retry.equals("1") ? true : false;

            if(re){

                //重新连接
                main(args);
            }else {

                return;
            }
        }
    }
}
