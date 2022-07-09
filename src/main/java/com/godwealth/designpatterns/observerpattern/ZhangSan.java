package com.godwealth.designpatterns.observerpattern;

// 组员张三
public class ZhangSan implements GroupObserver {
    @Override
    public void getMoney() {
        System.out.println("请给我张三发本月工资!");
    }
}
