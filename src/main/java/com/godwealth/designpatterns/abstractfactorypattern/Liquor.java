package com.godwealth.designpatterns.abstractfactorypattern;

/**
 * 抽象工厂模式
 * 抽象工厂模式 是指提供一个创建一系列相关或相互依赖对象的接口，无须指定他们具体的类。
 * 酒的抽象类
 * 原文地址：https://blog.csdn.net/Jiang_bug/article/details/118115192
 */
public abstract class Liquor {
    protected abstract void sell();
}
