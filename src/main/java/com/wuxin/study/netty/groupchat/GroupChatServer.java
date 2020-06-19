package com.wuxin.study.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author Emma Watson
 * @create 2020-06-19 17:58
 * 群聊  服务端
 */
public class GroupChatServer {

    private int port;

    public GroupChatServer(int port){
        this.port = port;
    }

    public void run() throws Exception{

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {


                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            //获取到pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            //向pipeline添加解码器
                            pipeline.addLast("decoder",new StringDecoder());
                            //向pipeline添加编码器
                            pipeline.addLast("encoder",new StringEncoder());
                            //加入自定义业务处理handler
                            pipeline.addLast("myHandler",new GroupChatServerHandler());
                        }
                    });
            // 绑定端口
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            System.out.println("netty server is ready....");
            // 监听关闭事件
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{

        new GroupChatServer(7000).run();
    }

}
