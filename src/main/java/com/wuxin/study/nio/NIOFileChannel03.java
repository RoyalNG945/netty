package com.wuxin.study.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Emma Watson
 * @create 2020-06-03 17:28
 *
 * 将1.txt的内容通过一个Buffer 复制到另一个2.txt中
 */
public class NIOFileChannel03 {

    public static void main(String[] args) throws Exception{

        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true){

            /**
             * public final Buffer clear() {
                 position = 0;
                 limit = capacity;
                 mark = -1;
                 return this;
              }
             */
            byteBuffer.clear();//清空Buffer

            int read = fileChannel01.read(byteBuffer);

            if (read == -1){
                break;
            }

            byteBuffer.flip();

            fileChannel02.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
