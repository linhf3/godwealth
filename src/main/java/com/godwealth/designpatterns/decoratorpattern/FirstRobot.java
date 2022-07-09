package com.godwealth.designpatterns.decoratorpattern;

/**
 * 第一代机器人
 */
public class FirstRobot implements Robot {
    @Override
    public void sing() {
        System.out.println("我会唱歌,a b c d e f g~");
    }
}
