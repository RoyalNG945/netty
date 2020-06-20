package com.wuxin.study.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author Emma Watson
 * @create 2020-06-20 18:06
 *
 * netty 心跳机制
 */
public class MyServer {


    public static void main(String[] args) throws Exception{

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{

            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ChannelPipeline pipeline = ch.pipeline();
                            //加入一个netty提供的IdleStateHandler
                            /**
                             *  说明：
                             *  1.IdleStateHandler 是netty提供的处理空闲状态的处理器
                             *  2.long readerIdleTime ：表示多长时间没有读，就会发送一个心跳检测包检测是否连接
                             *  3.long writerIdleTime ：表示多长时间没有写，就会发送一个心跳检测包检测是否连接
                             *  4.long allIdleTime ：表示多长时间没有读写，就会发送一个心跳检测包检测是否连接
                             *
                             *  文档说明：
                             *  Triggers an {@link IdleStateEvent} when a {@link Channel} has not performed
                             *  read, write, or both operation for a while.
                             *
                             *  当 IdleStateEvent 触发后，就会传递给管道的下一个handler去处理，
                             *  通过调用下一个handler的userEventTriggered,在该方法中处理 IdleStateEvent
                             *  （读空闲，写空闲，读写空闲）
                             */
                            pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                            //加入一个对空间空闲检测进一步处理的handler(自定义)
                            pipeline.addLast(new MyServerHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(7000).sync();
            System.out.println("netty server is ready....");
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
