package com.wuxin.study.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Emma Watson
 * @create 2020-06-03 16:53
 *
 * FileChannel Study
 */
public class NIOFileChannel01 {

    public static void main(String[] args) throws Exception {

        String str = "hello 叱咤无名英雄";
        //创建输出流 -> channel
        FileOutputStream fileOutputStream = new FileOutputStream("test.txt");

        //通过fileOutputStream 获取对应的FileChannel
        //这个FileChannel真实类型式 FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        //创建一个缓冲区ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //将str放入byteBuffer
        byteBuffer.put(str.getBytes());

        //对byteBuffer 进行filp
        byteBuffer.flip();

        //将byteBuffer数据写入到fileChannel
        fileChannel.write(byteBuffer);

        fileOutputStream.close();



    }
}
