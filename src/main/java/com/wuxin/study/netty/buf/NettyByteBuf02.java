package com.wuxin.study.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @author Emma Watson
 * @create 2020-06-10 15:19
 */
public class NettyByteBuf02 {

    public static void main(String[] args) {

        // 创建ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,无心法师", Charset.forName("utf-8"));

        //使用相关的方法
        if (byteBuf.hasArray()){

            byte[] content = byteBuf.array();

            // 将 content 转换成字符串
            System.out.println(new String(content,Charset.forName("utf-8")));

            System.out.println("byteBuf ="+byteBuf);

            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());

            System.out.println(byteBuf.getByte(0));

            int len = byteBuf.readableBytes();

            for (int i = 0; i < len; i++) {
                System.out.println((char) byteBuf.getByte(i));
            }

            System.out.println(byteBuf.getCharSequence(0,4,Charset.forName("utf-8")));

        }
    }
}
