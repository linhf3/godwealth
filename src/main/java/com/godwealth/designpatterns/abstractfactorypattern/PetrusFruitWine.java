package com.godwealth.designpatterns.abstractfactorypattern;

/**
 * Petrus果酒实现类
 */
public class PetrusFruitWine extends FruitWine{
    @Override
    protected void sell() {
        System.out.println("Petrus果酒真不错!");
    }
}
