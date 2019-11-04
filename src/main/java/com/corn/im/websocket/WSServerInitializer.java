package com.corn.im.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WSServerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();

        //webSocket基于http协议，需要有http的编解码器
        channelPipeline.addLast(new HttpServerCodec());

        //对写大数据流的支持
        channelPipeline.addLast(new ChunkedWriteHandler());

        //对HttpMessage进行聚合，聚合成一个FullHttpRequest或FullHttpResponse
        //几乎在netty中的编程，都会使用到此handler
        channelPipeline.addLast(new HttpObjectAggregator(1024*64));

        // ============ 以上是用于支持http协议 ==============

        //webSocket服务器处理的协议，用于指定给客户端连接访问的路由:/ws
        //本Handler会帮你处理一些繁重的复杂的事，会帮你处理握手动作(handshaking(close,ping,pong)) ping+pong = 心跳
        //对于webSocket来讲，都是以frames进行传输的，不同的数据类型对应frames也不同
        channelPipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        channelPipeline.addLast(new ChatHandler());
    }
}
