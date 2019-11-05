package com.corn.im.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.lang.String.valueOf;


/**
 * 处理消息的handler
 * TextWebSocketFram:在netty中，是用于为webSocket专门处理文本的对象，frame是消息的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //用于记录和管理所有客户端的channle
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        //获取客户端传输过来的消息
        String content = msg.text();
        System.out.println("接收到的数据" + content);


        for (Channel channel : channels) {

            if(channel.id().equals(ctx.channel().id())){
                continue;
            }

            ByteBuf byteBuffer = Unpooled.copiedBuffer(valueOf("[服务器接收到消息:]" + LocalDateTime.now() + "接收到消息,消息为:" + content).getBytes());
            byteBuffer.retain();
            channel.writeAndFlush(new TextWebSocketFrame(byteBuffer)).addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("发送成功！");

                    } else {
                        future.cause().printStackTrace();
                        System.out.println("发送失败");
                    }
                }
            });
        }
    }

    /**
     * 当客户端连接服务端之后(打开链接)
     * 获取客户端的channel,并且放到ChannelGroup中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        channels.add(ctx.channel());
        System.out.println(channels.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        //当触发handlerRemoved，ChannelGroup会自动移除对应客户端的Channel
        channels.remove(ctx.channel());
        System.out.println("客户端断开,channel对应的长Id为" + ctx.channel().id().asLongText());
        System.out.println("客户端断开,channel对应的短Id为" + ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println("异常");
    }
}
