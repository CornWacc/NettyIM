package com.corn.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入连接地址:");
        String host = scanner.nextLine();

        System.out.println("请输入连接端口:");
        String port = scanner.nextLine();

        System.out.println("请输入个人用户名:");
        String userName = scanner.nextLine();

        System.out.println("请输入接受人用户名:");
        String toUserName = scanner.nextLine();

        System.out.println("确定连接:"+host+":"+port+"吗? Y/N");
        String connectSure = scanner.nextLine();

        boolean sure = connectSure.toUpperCase().equals("Y") ? true : false;

        if(sure){

            //netty连接
            NioEventLoopGroup listenGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(listenGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        protected void initChannel(NioSocketChannel ch) throws Exception {

                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new ActiveHandler(userName,toUserName));

                        }
                    }).connect(host, Integer.parseInt(port)).addListener(future -> {

                        if(future.isSuccess()){

                            System.out.println(new Date()+"********** 连接成功!! **********");
                        }else {

                            System.out.println(new Date()+"********** 连接失败!! **********");
                        }
            });


        }else{

            System.out.println("重新输入请输入[1],退出请输入[2]");
            String retry = scanner.nextLine();
            boolean re = retry.equals("1") ? true : false;

            if(re){

                main(args);
            }else {

                return;
            }
        }
    }
}
