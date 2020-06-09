package com.wuxin.study.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Emma Watson
 * @create 2020-06-07 16:51
 */
public class NettyServer{

    public static void main(String[] args) throws Exception{

        //创建BossGroup 和 workGroup
        /**
         * 说明：
         * 1.创建两个线程组 bossgroup和workergroup
         * 2.bossGroup只是处理连接请求，真正的和客户端业务处理，会交给workGroup完成
         * 3.两个都是无限循环
         * 4.bossGroup 和 workerGroup 含有的子线程(NioEventLoop)的个数
         *  默认实际   cpu核数 * 2
         *  */
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            //使用链式编程来进行设置
            bootstrap.group(bossGroup,workerGroup) //设置两个线程组
                    .channel(NioServerSocketChannel.class) //使用NioServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG,128) //设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { //创建一个通道初始化对象(匿名对象)
                        //给pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //可以使用一个集合管理SocketChannel，在推送消息时，可以将业务加入到各个
                            //channel 对应的NIOEventLoop的taskQueue 或者 scheduleTaskQueue
                            System.out.println("客户socketchannel hashcode ="+ch.hashCode());
                            // 给我们的workGroup的EventLoop 对应的管道设置处理器
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });

            System.out.println("**************server is ready********************");

            //绑定一个端口并且同步，生成了一个ChannelFuture对象
            //启动服务器(并绑定端口)
            ChannelFuture cf = bootstrap.bind(6668).sync();
            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
