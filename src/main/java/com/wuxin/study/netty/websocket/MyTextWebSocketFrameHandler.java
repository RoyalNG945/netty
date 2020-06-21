package com.wuxin.study.netty.websocket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.EventExecutorGroup;

import java.time.LocalDateTime;

/**
 * @author Emma Watson
 * @create 2020-06-21 13:41
 * 这里 TextWebSocketFrame 类型，表示一个文本帧（frame）
 */
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    // 回复消息
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        System.out.println("服务器接收到消息："+msg.text());
        // 回复消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间"+ LocalDateTime.now()+" "+msg.text()));
    }


    // 处理异常的方法
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        System.out.println("异常发生："+cause.getMessage());
        ctx.close();
    }

    // 当web客户端连接后，触发方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        //id 表示唯一的值，LongText是唯一的，ShortText不是唯一的
        System.out.println("handlerAdded 被调用："+ctx.channel().id().asLongText());
        System.out.println("handlerAdded 被调用："+ctx.channel().id().asShortText());
    }

    // 当连接断开，触发该方法
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        System.out.println("handlerRemoved 被调用"+ctx.channel().id().asLongText());
    }


}
