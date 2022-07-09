package com.godwealth.designpatterns.decoratorpattern;

/**
 * 装饰类
 * 装饰机器人
 */
public class DecoratorRobot implements Robot {
    private Robot robot;

    public DecoratorRobot(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void sing() {
        robot.sing();
    }

    // 新方法
    public void jump() {
        System.out.println("我还会跳高高!");
    }
}
