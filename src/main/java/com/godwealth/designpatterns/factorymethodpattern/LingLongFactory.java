package com.godwealth.designpatterns.factorymethodpattern;

/**
 * 玲珑轮胎工厂
 */
public class LingLongFactory implements TyreFactory {
    @Override
    // 生产玲珑轮胎
    public Tyre produceTyreFactory() {
        return new LingLongTyre();
    }
}
