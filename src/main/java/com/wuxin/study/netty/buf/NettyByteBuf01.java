package com.wuxin.study.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author Emma Watson
 * @create 2020-06-10 11:05
 */
public class NettyByteBuf01 {

    public static void main(String[] args) {

        //创建一个ByteBuf
        /**
         * 说明：
         * 1. 创建对象，该对象包含一个数组arr,是一个byte[10]
         * 2. 在netty的buffer中，不需要使用filp 进行读写的反转
         *    因为 底层维护了 readerIndex 和 writerIndex
         * 3. 通过readerIndex 和 writerIndex 和 capacity，将buffer分成三个区域
         *    0 ---- readerIndex  已经读取的区域
         *    readerIndex ---- writerIndex  可读的区域
         *    writerIndex ---- capacity   可写的区域
         */
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            // 写数据
            buffer.writeByte(i);
        }

        System.out.println("capacity = "+buffer.capacity());

        for (int i = 0; i < buffer.capacity(); i++) {
            //读数据
            System.out.println(buffer.readByte());
        }


    }
}
