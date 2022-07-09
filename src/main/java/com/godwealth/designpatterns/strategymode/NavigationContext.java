package com.godwealth.designpatterns.strategymode;


/**
 * 策略上下文
 */
public class NavigationContext {

    // 声明接口对象
    private Navigation navigation;

    // 使用构造器注入具体的策略类
    public NavigationContext(Navigation navigation) {
        this.navigation = navigation;
    }

    // 也可以通过set方法注入具体策略
    public void setNavigation(Navigation navigation) {
        this.navigation = navigation;
    }

    // 调用策略实现的方法
    public void elapsedTime() {
        navigation.elapsedTime();
    }
}
