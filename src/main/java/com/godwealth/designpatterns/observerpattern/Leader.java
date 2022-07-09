package com.godwealth.designpatterns.observerpattern;

/**
 * 观察者模式
 * 它定义了一种一对多的依赖关系，让多个观察者对象同时监听某一个主题对象。
 * 这个主题对象在状态变化时，会通知所有的观察者对象，使他们能够自动更新自己。
 * 领导接口
 * 抽象主题
 * https://blog.csdn.net/Jiang_bug/article/details/118115192
 */
public interface Leader {
    // 添加员工入组方法
    void addPerson(GroupObserver groupObserver);
    // 给组员发工资
    void grantMoney();
}
