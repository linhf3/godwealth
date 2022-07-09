package com.godwealth.designpatterns.singleton;
//懒汉式 双重校验锁版
//防止多线程创建单例模式时 造成多次被实例化
//懒汉式单例类 先声明 在第一次调用的时候实例化自己
public class LazyManDoubleCheckLockEdition {
    // 构造方法
    private LazyManDoubleCheckLockEdition () {}
    private static LazyManDoubleCheckLockEdition lazyManDoubleCheckLockEdition ;

    // 静态工厂方法
    public static LazyManDoubleCheckLockEdition getIdler () {
        if (lazyManDoubleCheckLockEdition == null) {
            synchronized (LazyManDoubleCheckLockEdition.class) {
                if (lazyManDoubleCheckLockEdition == null) {
                    lazyManDoubleCheckLockEdition= new LazyManDoubleCheckLockEdition();
                }
            }
        }
        return lazyManDoubleCheckLockEdition;
    }

}
