package com.godwealth.test;

public class MyTest {
    public static void main(String[] args) {
        //1:创建线程池类对象;
        MyThreadPool pool = new MyThreadPool(4,5,20);
        //2: 提交多个任务
        for (int i = 0; i <30 ; i++) {
            //3:创建任务对象,并提交给线程池
            MyTask my = new MyTask(i);
            pool.submit(my);
        }
    }
}
