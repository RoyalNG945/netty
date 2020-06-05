package com.wuxin.study.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author Emma Watson
 * @create 2020-06-02 16:39
 */
public class JoinTest {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t 休眠3s");
            try {TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) {e.printStackTrace();}
            System.out.println(Thread.currentThread().getName());
        }, "T1");

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }, "T2");

        Thread t3 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }, "T3");

        t1.start();
        t2.start();
        t3.start();
    }
}
