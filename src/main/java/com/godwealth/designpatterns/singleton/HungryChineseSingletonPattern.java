package com.godwealth.designpatterns.singleton;

//设计模式参考 https://blog.csdn.net/Jiang_bug/article/details/118115192
//饿汉式 单例模式
//饿汉式在类创建的同时就已经创建好一个静态的对象供系统使用，以后不再改变，所以天生是线程安全的。
// 饿汉单例类 初始化时实例化自己
public class HungryChineseSingletonPattern {

    // 构造方法
    private HungryChineseSingletonPattern () {}
    private static HungryChineseSingletonPattern hungryChineseSingletonPattern = new HungryChineseSingletonPattern();

    // 静态工厂方法
    public static HungryChineseSingletonPattern getIdler () {
        return hungryChineseSingletonPattern;
    }
}
