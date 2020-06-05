package com.wuxin.study.nio;

import java.nio.IntBuffer;

/**
 * @author Emma Watson
 * @create 2020-05-30 17:53
 *
 * NIO 核心组件： selector 选择器  channel 通道  buffer 缓冲区
 */
public class BasicBuffer {

    public static void main(String[] args) {

        //创建一个buffer,容量位5，即可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        for (int i = 0; i < intBuffer.capacity(); i++) {

            intBuffer.put(i*2);
        }

        //如何从buffer读取数据
        //将buffer转换，读写切换
        intBuffer.flip();

        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
