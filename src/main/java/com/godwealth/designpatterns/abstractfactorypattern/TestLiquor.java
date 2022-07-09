package com.godwealth.designpatterns.abstractfactorypattern;

/**
 * 测试类进行测试
 */
public class TestLiquor {
    public static void main(String[] args) {
        // 定制张裕酒类
        Factory zhangYuFactory = new ZhangYuFactory();
        // 葡萄酒
        zhangYuFactory.productWine().sell();
        // 果酒
        zhangYuFactory.productFruitWine().sell();
        // 定制Petrus酒类
        Factory petrusFactory = new PetrusFactory();
        // 葡萄酒
        petrusFactory.productWine().sell();
        // 果酒
        petrusFactory.productFruitWine().sell();
    }
}
