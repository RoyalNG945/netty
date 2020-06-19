package com.wuxin.study.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Emma Watson
 * @create 2020-06-19 18:45
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个Channel组 ， 管理所有的Channel
    //GlobalEventExecutor.INSTANCE 是全局的事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * handlerAdded  表示连接建立，一旦连接，第一个被执行
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        //将该客户加入聊天的信息推送给其他在线的客户
        /**
         * 该方法会将channelGroup中的所有的channel遍历，并发送消息
         * 我们不需要自己遍历
         */
        channelGroup.writeAndFlush("【客户端】"+channel.remoteAddress()+" "+sdf.format(new Date())+"加入聊天 \n");
        channelGroup.add(channel);
    }

    /**
     * handlerRemoved  客户端断开连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【客户端】"+channel.remoteAddress()+"断开连接\n");
        System.out.println("当前channelGroup的大小："+channelGroup.size());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+"上线了\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+"离线了\n");

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        Channel channel = ctx.channel();

        channelGroup.forEach(ch ->{

            if (ch != channel){
                ch.writeAndFlush("客户"+channel.remoteAddress()+" 说:"+msg+"\n");
            }else {
                ch.writeAndFlush("自己发送的消息"+msg);
            }
        });
    }
}
