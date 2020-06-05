package com.wuxin.study.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Emma Watson
 * @create 2020-05-30 16:50
 */
public class BioServer {

    public static void main(String[] args) throws IOException {

        ExecutorService threadPool = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("服务器启动了");

        while(true){

            //监听，等待客户端连接
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");
            threadPool.execute(()->{
                //可以和客户端通讯
                handler(socket);
            });
        }
    }

    /**
     * 与客户端通讯的方法
     */
    public static void handler(Socket socket){


        try {
            byte[] bytes = new byte[1024];
            //通过socket获取输入流
            InputStream inputStream = socket.getInputStream();
            while (true){
                int read = inputStream.read(bytes);
                if (read!=-1){
                    System.out.println(new String(bytes,0,read));
                }else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                System.out.println("关闭和client的连接");
                if (socket!=null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
