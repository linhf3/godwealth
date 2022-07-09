package com.godwealth.designpatterns.abstractfactorypattern;

/**
 * Petrus酒类生产工厂
 */
public class PetrusFactory implements Factory {
    // 葡萄酒
    @Override
    public Liquor productWine() {
        return new PetrusWine();
    }

    // 果酒
    @Override
    public Liquor productFruitWine() {
        return new PetrusFruitWine();
    }
}
