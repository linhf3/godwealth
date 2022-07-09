package com.godwealth.designpatterns.decoratorpattern;

/**
 * 装饰器与适配器的区别是 装饰器是实现旧业务接口 为旧业务增加新方法 适配器是实现新业务在新业务中调用旧业务
 * 是指在不改变原有对象的基础之上,将功能附加到对象上.提供了比继承更灵活的替代方法 属于结构型模式.
 * 机器人接口
 */
public interface Robot {
    // 会唱歌
    void sing();
}
