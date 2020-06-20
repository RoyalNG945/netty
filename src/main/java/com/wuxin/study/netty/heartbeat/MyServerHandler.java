package com.wuxin.study.netty.heartbeat;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author Emma Watson
 * @create 2020-06-20 18:17
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if(evt instanceof IdleStateEvent){

            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()) {

                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
                default:
                    eventType = "未知状态";
                    break;
            }

            System.out.println(ctx.channel().remoteAddress()+"***超时时间***"+eventType);


        }



    }
}
