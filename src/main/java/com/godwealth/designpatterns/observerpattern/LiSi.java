package com.godwealth.designpatterns.observerpattern;

// 组员李四
public class LiSi implements GroupObserver {
    @Override
    public void getMoney() {
        System.out.println("请给我李四发这个月工资!");
    }
}
