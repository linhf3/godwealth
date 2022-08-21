package com.godwealth.datastructuresandalgorithms.datastructure;

import java.util.Scanner;

/**
 * 数组模拟环形队列
 */
public class ArrayQueueDemo2 {
    public static void main(String[] args){
        //创建一个队列
        ArrayQueue2 arrayQueue2 = new ArrayQueue2(3);
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
                    arrayQueue2.showQueue();
                    break;
                case 'a':
                    System.out.println("请输入一个数");
                    int value= s.nextInt();
                    arrayQueue2.addQueue(value);
                    break;
                case 'g':
                    try{
                        int res = arrayQueue2.getQueue();
                        System.out.println("去除的数据为："+res);
                    }catch (Exception e){
                        System.out.println(e.getMessage());//捕捉异常信息
                    }
                    break;
                case 'h':
                    try{
                        int res = arrayQueue2.headQueue();
                        System.out.println("头元素为"+res);
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
class ArrayQueue2{
    private int maxSize;
    private int front;
    private int rear;
    private int[] arr;

    public ArrayQueue2() {
    }

    public ArrayQueue2(int maxSize, int front, int rear, int[] arr) {
        this.maxSize = maxSize;
        this.front = front;
        this.rear = rear;
        this.arr = arr;
    }

    //船舰队列构造器
    public ArrayQueue2(int maxSize){
        this.maxSize = maxSize;
        arr = new int[this.maxSize];
    }
    //判断是否为空
    public boolean isEmpty(){
        return rear == front;
    }
    //判断是否满
    public boolean isFull(){
        return (rear+1) % maxSize == front;
    }
    public void addQueue(int n){
        if (isFull()){
            System.out.println("队列满不能加入数据");
            return;
        }else {
            arr[rear] = n;
            //将rear后移
            rear = (rear+1)%maxSize;
        }
    }
    public int getQueue(){
        if (isEmpty()){
            throw new RuntimeException("队列空，不能取出");
        }
        int value  = arr[front];
        front = (front+1)%maxSize;
        return value;
    }
    //显示队列
    public void showQueue(){
        if (isEmpty()){
            throw new RuntimeException("队列空");
        }
        for (int i = front; i <front + (rear + maxSize - front) %maxSize; i++){
            System.out.println(arr[i% maxSize]+ " " + i% maxSize);
        }
    }
    //显示队列的头数据
    public int headQueue(){
        if (isEmpty()){
            throw new RuntimeException("队列空");
        }else {
            return arr[front];
        }
    }
}
