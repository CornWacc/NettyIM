package com.corn.netty;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Scanner;

public class ActiveHandler extends ChannelInboundHandlerAdapter {

    private String userName;

    private String toUserName;


    public ActiveHandler(String userName,String toUserName) {
        this.userName = userName;
        this.toUserName = toUserName;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        String md = "init/single/"+userName+"/"+toUserName+"/addition"; //生成对应校验字符串
        ByteBuf byteBuf = getByteBuf(ctx,md);
        ctx.writeAndFlush(byteBuf);

    }

    /**
     * 1.该逻辑处理器里面仅获取开局校验码
     * 2.该处理器也处理连接成功接收开局校验码后的信息输入
     * */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String[] strings = String.valueOf(msg).split("/");

        System.out.println(new Date()+":与服务端连接建立成功,"+strings[strings.length-1]);
        System.out.println("********** 聊天室创建成功,可以开始聊天 **********");

        while(true){
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();

            if(line.equals("") || line == null){
                System.out.println(new Date()+":信息不能输入为空!");
            }else {
                String next = "say/single/"+userName+"/"+toUserName+"/"+line; //区分校验码,防止list空指针,发送给服务端进行解析

                ByteBuf to = getByteBuf(ctx,next);
                ctx.writeAndFlush(to);
            }

        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }



    private ByteBuf getByteBuf(ChannelHandlerContext ch, String next){

        ByteBuf byteBuf = ch.alloc().buffer();
        byte[] bytes = next.getBytes(Charset.forName("UTF-8"));
        byteBuf.writeBytes(bytes);

        return byteBuf;

    }


}
