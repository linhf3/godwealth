package com.godwealth.designpatterns.abstractfactorypattern;


/**
 * 不同品牌工厂继承酒类生产工厂
 * 张裕酒类生产工厂
 */
public class ZhangYuFactory implements Factory{
    // 葡萄酒
    @Override
    public Liquor productWine() {
        return new ZhangYuWine();
    }
    // 果酒
    @Override
    public Liquor productFruitWine() {
        return new ZhangYuFruitWine();
    }
}
