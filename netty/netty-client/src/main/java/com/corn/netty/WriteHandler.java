package com.corn.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Scanner;

public class WriteHandler extends ChannelOutboundHandlerAdapter {

    private String userName;

    private String toUserName;


    public WriteHandler(String userName,String toUserName) {
        this.userName = userName;
        this.toUserName = toUserName;
    }


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//        while(true){
//            Scanner scanner = new Scanner(System.in);
//            String line = scanner.nextLine();
//
//            if(line.equals("") || line == null){
//                System.out.println(new Date()+":信息不能输入为空!");
//            }else {
//                String next = "say/single/"+userName+"/"+toUserName+"/"+line; //区分校验码,防止list空指针,发送给服务端进行解析
//
//                ByteBuf to = getByteBuf(ctx,next);
//                ctx.writeAndFlush(to);
//            }
//
//        }
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ch, String next){

        ByteBuf byteBuf = ch.alloc().buffer();
        byte[] bytes = next.getBytes(Charset.forName("UTF-8"));
        byteBuf.writeBytes(bytes);

        return byteBuf;

    }
}
