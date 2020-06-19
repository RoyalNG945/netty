package com.wuxin.study.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * @author Emma Watson
 * @create 2020-06-09 16:27
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        //向管道加入处理器
        //得到管道
        ChannelPipeline pipeline = ch.pipeline();

        //加入一个netty提供的httpServerCodec  codec => [coder - decoder]
        //1.HttpServerCodec 是 netty 提供的处理http的 编 - 解码器
        pipeline.addLast("myHttpServerCodec",new HttpServerCodec());
        //2. 增加一个自定义的handler
        pipeline.addLast("myTestHttpServerHandler",new TestHttpServerHandler());


    }
}
