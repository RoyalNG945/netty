package com.wuxin.study.nio;

import java.nio.ByteBuffer;

/**
 * @author Emma Watson
 * @create 2020-06-03 18:00
 */
public class ReadOnlyBuffer {

    public static void main(String[] args) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        for (int i = 0; i < 60; i++) {
            byteBuffer.put((byte)i);
        }

        //反转读取
        byteBuffer.flip();

        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();

        while (byteBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }

       // readOnlyBuffer.put((byte)23);  只读的buffer无法写入数据
    }
}
