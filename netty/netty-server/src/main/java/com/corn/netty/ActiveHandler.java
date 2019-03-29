package com.corn.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ActiveHandler extends ChannelInboundHandlerAdapter {


    NettyServerSingle nettyServerSingle = NettyServerSingle.getInstance();

    /**
     * 监听客户端连接
     * */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        nettyServerSingle.setConnectCount(nettyServerSingle.getConnectCount()+1);//连接总数
        System.out.println(new Date() + ":客户端连接成功:" + ctx.channel().remoteAddress()+",当前总共连接数为:"+nettyServerSingle.getConnectCount());


    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String[] strings = String.valueOf(msg).split("/");

        String type = strings[0];
        String chatType = strings[1];
        String userName = strings[2];
        String toUserName= strings[3];

        //如果解析出来的数据type是初始化则新增上线用户
        if(type.equals("init")){

            increase(ctx,userName);
            //每有一次用户连接服务器则向全部发送心跳包
            List<User> list = nettyServerSingle.getUsers(); //赋值,并没有直接遍历该集合

            StringBuffer upUser = new StringBuffer();
            upUser.append("当前在线用户列表:");

            Iterator<User> iterator = list.iterator();

            while(iterator.hasNext()){

                User user = iterator.next();
                if(user.getChannelHandlerContext().channel().isActive()){
                    upUser.append(user.getUserName());
                    upUser.append(",");

                } else {
                    nettyServerSingle.setConnectCount(nettyServerSingle.getConnectCount()-1); //减少连接数
                    user.getChannelHandlerContext().close(); //关闭连接
                    iterator.remove(); //剔除当前下线用户,这里删除完了原来的list里面的东西也没了??
                }
            }
            System.out.println(list.toString());


            System.out.println(nettyServerSingle.getUsers().size());

        }else{
            System.out.println(new Date()+" 获取到信息 "+ctx.channel().remoteAddress()+":"+msg);
        }

        //转发信息

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        System.out.println(new Date()+":"+ctx.channel().remoteAddress() +" 下线");

        nettyServerSingle.getUsers().remove(ctx);//用户列表删除该用户
        nettyServerSingle.setConnectCount(nettyServerSingle.getConnectCount()-1);//从总连接数里删除一个
        System.out.println(nettyServerSingle.getUsers().toString());

        ctx.close(); //出现异常时需要关闭当前流
    }

    private void increase(ChannelHandlerContext channelHandlerContext,String userName){

        addPeople(userName,channelHandlerContext);//新增上线用户
    }

    private void addPeople(String name,ChannelHandlerContext channelHandlerContext){

        User user = new User();
        user.setChannelHandlerContext(channelHandlerContext);
        user.setUserName(name);
        //往全局人员管理里面新增一个用户上线
        nettyServerSingle.getUsers().add(user);
    }

    /**
     * @param ctx
     * */
    private void Forward(ChannelHandlerContext ctx){

    }


}
