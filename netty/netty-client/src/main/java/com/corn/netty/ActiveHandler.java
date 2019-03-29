package com.corn.netty;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
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

        String md = "init/single/"+userName+"/"+toUserName; //生成对应校验字符串
        ByteBuf byteBuf = getByteBuf(ctx,md);
        ctx.writeAndFlush(byteBuf);

        System.out.println("********** 聊天室建立成功,可以开始聊天 **********");
        Scanner scanner = new Scanner(System.in);

        while(true){

            String next = "say/single/userName/"+scanner.nextLine(); //区分校验码,防止list空指针

            ByteBuf to = getByteBuf(ctx,next);
            ctx.writeAndFlush(to);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println(msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause);
        System.out.println("close");
    }



    private ByteBuf getByteBuf(ChannelHandlerContext ch, String next){

        ByteBuf byteBuf = ch.alloc().buffer();
        byte[] bytes = next.getBytes(Charset.forName("UTF-8"));
        byteBuf.writeBytes(bytes);

        return byteBuf;

    }


}
