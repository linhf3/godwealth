package com.godwealth.designpatterns.factorymethodpattern;
/**
 * 轮胎定制客户端，测试
 */
public class TestTyre {

    public static void main(String[] args) {
        // 声明需要生产轮胎
        Tyre tyre;

        // 生产米其林轮胎
        // 创建米其林生产轮胎的工厂
        TyreFactory tyreFactory = new MichelinFactory();
        tyre = tyreFactory.produceTyreFactory();
        tyre.produceTyre();

        // 生产玲珑轮胎
        // 创建玲珑轮胎工厂
        tyreFactory = new LingLongFactory();
        tyre = tyreFactory.produceTyreFactory();
        tyre.produceTyre();

    }

}
