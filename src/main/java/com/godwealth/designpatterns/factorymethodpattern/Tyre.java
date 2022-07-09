package com.godwealth.designpatterns.factorymethodpattern;


/**
 * 工厂方法模式(Factory Method Pattern)：定义一个用于创建对象的接口，让子类决定将哪一个类实例化。
 * 工厂方法模式让一个类的实例化延迟到其子类。工厂方法模式又简称为工厂模式(Factory Pattern)，
 * 又可称作虚拟构造器模式(Virtual Constructor Pattern)或多态工厂模式(Polymorphic Factory Pattern)。
 * 原文链接：https://blog.csdn.net/Jiang_bug/article/details/118115192
 */
/**
 * 轮胎接口
 */
public interface Tyre {
    // 生产轮胎方法
    void produceTyre();
}
