package com.wuxin.study.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author Emma Watson
 * @create 2020-06-07 17:06
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取数据实际(这里我们可以读取客户端发送的消息)
     * @param ctx  上下文对象，含有 管道 pipeline,通道 channel,地址
     * @param msg   就是客户端发送的数据  默认Object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Channel channel = ctx.channel();
        System.out.println("服务器读取线程 "+Thread.currentThread().getName()
                            + "channel "+ channel);

        System.out.println("server ctx = "+ctx);
        System.out.println("看看channel 和 pipeline的关系");
        ChannelPipeline pipeline = ctx.pipeline();//本质是一个双向链表，出栈入栈

        //将 msg转成一个ByteBuf
        //ByteBuf 是 Netty 提供的，不是NIO的ByteBuffer
        ByteBuf buf = (ByteBuf)msg;
        System.out.println("客户端发送消息是："+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址："+channel.remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        //writeAndFlush 是 write + flush
        //将数据吸入到缓存，并刷新
        //一般讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端~",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
