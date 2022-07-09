package com.godwealth.designpatterns.abstractfactorypattern;

/**
 * 张裕果酒实现类
 */
public class ZhangYuFruitWine extends FruitWine {
    @Override
    protected void sell() {
        System.out.println("张裕果酒你值得拥有!");
    }
}
