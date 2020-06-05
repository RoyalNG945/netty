package com.wuxin.study.nio;

import java.nio.ByteBuffer;

/**
 * @author Emma Watson
 * @create 2020-06-03 17:52
 *
 * ByteBuffer put什么类型的数据，就get什么类型的数据 否则会报异常
 */
public class NIOByteBufferPutGet {

    public static void main(String[] args) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(124);

        //类型化方式放入数据
        byteBuffer.putInt(2);
        byteBuffer.putLong(7L);
        byteBuffer.putChar('无');
        byteBuffer.putShort((short)1);

        //取出数据
        byteBuffer.flip(); //先反转
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
    }
}
