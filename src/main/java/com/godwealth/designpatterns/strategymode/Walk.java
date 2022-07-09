package com.godwealth.designpatterns.strategymode;

public class Walk implements Navigation{
    @Override
    // 步行导航消耗时间方法
    public void elapsedTime() {
        System.out.println("步行到达目的地需20分钟");
    }
}
