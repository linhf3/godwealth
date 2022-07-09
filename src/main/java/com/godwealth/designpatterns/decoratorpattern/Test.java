package com.godwealth.designpatterns.decoratorpattern;

public class Test {

    public static void main(String[] args) {
        DecoratorRobot decoratorRobot = new DecoratorRobot(new FirstRobot());
        decoratorRobot.sing();
        decoratorRobot.jump();
    }
}
