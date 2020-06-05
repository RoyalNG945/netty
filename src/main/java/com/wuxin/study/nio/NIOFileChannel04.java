package com.wuxin.study.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author Emma Watson
 * @create 2020-06-03 17:42
 * channel transferFrom() 方法实现图片的拷贝
 */
public class NIOFileChannel04 {

    public static void main(String[] args) throws Exception{

        //创建相关流
        FileInputStream fileInputStream = new FileInputStream("a.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("b.jpg");

        //获取各个流对应的fileChannel
        FileChannel source = fileInputStream.getChannel();
        FileChannel destch = fileOutputStream.getChannel();

        //使用transferFrom完成拷贝
        destch.transferFrom(source,0,source.size());

        //关闭相关通道和流
        source.close();
        destch.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
