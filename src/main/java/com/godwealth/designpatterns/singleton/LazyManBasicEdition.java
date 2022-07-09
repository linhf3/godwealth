package com.godwealth.designpatterns.singleton;


//单例模式有以下特点：
//
//        单例类只能有一个实例。
//        单例类必须自己创建自己的唯一实例。
//        单例类必须给所有其他对象提供这一实例。
//单例模式 懒汉式 基础版
// 懒汉式单例类 先声明 在第一次调用的时候实例化自己
public class LazyManBasicEdition {
    // 构造方法
    private LazyManBasicEdition() {}
    private static LazyManBasicEdition lazyManBasicEdition;

    // 静态工厂方法
    public static LazyManBasicEdition getIdler () {
        if (lazyManBasicEdition == null) {
            lazyManBasicEdition = new LazyManBasicEdition();
        }
        return lazyManBasicEdition;
    }
}
