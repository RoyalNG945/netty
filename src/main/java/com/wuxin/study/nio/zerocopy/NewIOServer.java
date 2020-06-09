package com.wuxin.study.nio.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author Emma Watson
 * @create 2020-06-06 19:49
 */
public class NewIOServer {

    public static void main(String[] args) throws Exception{

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        InetSocketAddress inetSocketAddress = new InetSocketAddress(7001);

        ServerSocket serverSocket = serverSocketChannel.socket();

        serverSocket.bind(inetSocketAddress);

        //创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true){

            SocketChannel socketChannel = serverSocketChannel.accept();

            int readCount = 0;

            while (readCount != -1){

                try {
                   readCount = socketChannel.read(byteBuffer);
                }catch (Exception e){
                    //e.printStackTrace();
                    break;
                }
                // 倒带 ： position = 0 ,mark =-1 作废
                byteBuffer.rewind();
            }



        }

    }


}
