package com.godwealth.designpatterns.abstractfactorypattern;

/**
 * 张裕葡萄酒具体实现类
 */
public class ZhangYuWine extends Wine {
    @Override
    protected void sell() {
        System.out.println("张裕葡萄酒你值得拥有!");
    }
}
