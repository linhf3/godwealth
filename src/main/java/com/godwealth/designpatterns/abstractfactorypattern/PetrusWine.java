package com.godwealth.designpatterns.abstractfactorypattern;

/**
 * Petrus葡萄酒具体实现类
 */
public class PetrusWine  extends Wine {
    @Override
    protected void sell() {
        System.out.println("Petrus葡萄酒真不错!");
    }
}
