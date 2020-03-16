package com.xuecheng.manage_cms.dao;

/**
 * @author tpc
 * @date 2020/3/16 21:08
 */
public class demo {
    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable,"线程");
        thread.start();
    }
}
