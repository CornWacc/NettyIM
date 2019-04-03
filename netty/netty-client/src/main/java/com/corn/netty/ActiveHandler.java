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
//        new Thread(){
//            @Override
//            public void run() {
//                while(true){
//                    Scanner scanner = new Scanner(System.in);
//                    String line = scanner.nextLine();
//
//                    if(line.equals("") || line == null){
//                        System.out.println(new Date()+":信息不能输入为空!");
//                    }else {
//                        String next = "say/single/"+userName+"/"+toUserName+"/"+line; //区分校验码,防止list空指针,发送给服务端进行解析
//
//                        ByteBuf to = getByteBuf(ctx,next);
//                        ctx.writeAndFlush(to);
//                    }
//
//                }
//            }
//        }.start();

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
