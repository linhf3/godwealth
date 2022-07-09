package com.godwealth.designpatterns.strategymode;

/**
 * 骑车导航用时方法
 */
public class Bike implements Navigation{
    @Override
    // 骑车导航耗时方法
    public void elapsedTime() {
        System.out.println("骑车到达目的地需10分钟");
    }
}
