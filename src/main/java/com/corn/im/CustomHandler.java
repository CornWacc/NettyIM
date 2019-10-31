package com.corn.im;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;


/**
 * SimpleChannelInboundHandler：对于请求来讲，其实相当于[入站，入境]
 * */
public class CustomHandler extends SimpleChannelInboundHandler<HttpObject> {

    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        //获取channel
        Channel channel = ctx.channel();

        //显示客户端的远程地址
        System.out.println(channel.remoteAddress());

        //定义发送的数据消息
        ByteBuf byteBuffer = Unpooled.copiedBuffer("Hello netty~", CharsetUtil.UTF_8);

        //构建一个http response
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,byteBuffer);

        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuffer.readableBytes());
    }
}
