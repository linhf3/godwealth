package com.godwealth.datastructuresandalgorithms.datastructure;

import java.util.Scanner;

/**
 * 数组模拟队列，一次就没用了
 */
public class ArrayQueueDemo {
    public static void main(String[] args){
        //创建一个队列
        ArrayQueue arrayQueue = new ArrayQueue(3);
        char Key = ' ';//接口用户输入
        Scanner s= new Scanner(System.in);
        boolean loop = true;
        //输出菜单
        while(loop){
            System.out.println("s:显示队列");
            System.out.println("e:退出程序");
            System.out.println("a:添加数据到队列");
            System.out.println("g:从队列取出数据");
            System.out.println("h:查看队列头部数据");
            Key = s.next().charAt(0);//接收一个字符
            switch (Key){
                case  's':
                    arrayQueue.showQueue();
                    break;
                case 'a':
                    System.out.println("请输入一个数");
                    int value= s.nextInt();
                    arrayQueue.addQueue(value);
                    break;
                case 'g':
                    try{
                        int res = arrayQueue.gerQueue();
                        System.out.println("去除的数据为："+res);
                    }catch (Exception e){
                        System.out.println(e.getMessage());//捕捉异常信息
                    }
                    break;
                case 'h':
                    try{
                        int res = arrayQueue.headQueue();
                        System.out.println("去除的数据为："+res);
                    }catch (Exception e){
                        System.out.println(e.getMessage());//捕捉异常信息
                    }
                    break;
                case 'e':
                    s.close();
                    loop = false;
                    break;
                default:
                    break;
            }

        }
        System.out.println("程序退出");
    }

}

//使用数组模拟队列
class ArrayQueue{
    private int maxSize;//表述数组最大容量
    private int front;//头
    private int rear;//尾
    private int[] arr;//用于存放数据

    //创建队列构造器
    public ArrayQueue(int arrmaxSize){
        maxSize = arrmaxSize;
        arr = new int[maxSize];
        front = -1;//头部，指向队列头的前一个位置
        rear = -1; // 尾部，指向队列尾部

    }
    //判断队列是否满
    public boolean isFull(){
        return rear == maxSize -1;
    }
    //判断队列是否为空
    public boolean isEmpty(){
        return rear == front;
    }

    //添加数据到队列
    public void addQueue(int n){
        //判断队列是否满
        if(isFull()){
            System.out.println("队列满不能加入数据");
            return;
        }
        rear++;
        arr[rear] = n;
    }
    //出队列
    public int gerQueue() {
        //判断队列是否为空
        if (isEmpty()){
            //通过抛出异常来处理
            throw new RuntimeException("队列空，不能取出");
        }
        front++;
        return arr[front];
    }
    //显示队列
    public void showQueue(){
        //遍历
        if(isEmpty()){
            System.out.println("队列为空");
        }
        for (int i = 0; i<arr.length;i++){
            System.out.print(arr[i] + " ");
        }
    }
    //显示队列的头数据
    public int headQueue(){
        //判断
        if (isEmpty()){
            throw new RuntimeException("队列空");
        }else {
            return arr[++front];
        }
    }
}
