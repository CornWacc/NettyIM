package com.corn.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;


/**
 * 仅用于读取数据
 * */
public class ReadHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {



        String[] strings = String.valueOf(msg).split("/");
        String type = strings[0]; //init/say
        String chatType = strings[1];//single/all
        String userName = strings[2]; //当前用户
        String toUser = strings[3]; //接受用户
        String addition = strings[4]; //区分是正文内容或者是起始校验码

        if(type.equals("init")){

            System.out.println(new Date()+":与服务端连接建立成功");
            System.out.println("********** 聊天室创建成功,可以开始聊天 **********");

            System.out.println(new Date()+":"+addition);
        }else {
            System.out.println("用户"+userName +" say");
            System.out.println(new Date()+":"+addition);
            System.out.println();
        }

    }
}
