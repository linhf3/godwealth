package com.godwealth.designpatterns.strategymode;

/**
 * 业务测试类
 * 设计模式参考 https://blog.csdn.net/Jiang_bug/article/details/118115192
 */
public class TestNavigation {
    public static void main(String[] args) {
        // 传入步行导航策略
        Walk walk = new Walk();
        NavigationContext navigationContext = new NavigationContext(walk);
        navigationContext.elapsedTime();

        // 传入骑车导航策略
        navigationContext.setNavigation(new Bike());
        navigationContext.elapsedTime();

        // 传入开车导航策略
        navigationContext.setNavigation(new Car());
        navigationContext.elapsedTime();

    }
}
