package com.godwealth.designpatterns.strategymode;


public class Car implements Navigation {
    @Override
    // 开车导航耗时方法
    public void elapsedTime() {
        System.out.println("开车到达目的地需2分钟");
    }
}
