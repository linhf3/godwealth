package com.godwealth.designpatterns.factorymethodpattern;
/**
 * 玲珑轮胎工厂
 */
public class LingLongTyre implements Tyre {
    @Override
    // 玲珑轮胎生产中
    public void produceTyre() {
        System.out.println("玲珑轮胎生产中!");
    }
}
