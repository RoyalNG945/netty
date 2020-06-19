package com.wuxin.study.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author Emma Watson
 * @create 2020-06-09 16:27
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    // channelRead0  读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        //判断msg是不是httprequest请求
        if (msg instanceof HttpRequest){
            System.out.println("msg 类型="+msg.getClass());
            System.out.println("客户端地址:"+ctx.channel().remoteAddress());
        }

        //回复消息给浏览器[HTTP协议]
        ByteBuf content = Unpooled.copiedBuffer("hello ,i am server", CharsetUtil.UTF_8);

        //构造一个http的响应，即httpResponse
       FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);

       fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
       fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

       // 将构建好的response返回
       ctx.writeAndFlush(fullHttpResponse);

    }
}
