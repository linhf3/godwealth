package com.godwealth.designpatterns.abstractfactorypattern;

/**
 * 工厂接口
 */
public interface Factory {

    // 葡萄酒类生产工厂方法
    Liquor productWine();
    // 果酒类生产工厂方法
    Liquor productFruitWine();

}
