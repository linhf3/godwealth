package com.godwealth.designpatterns.factorymethodpattern;

public class MichelinTyre implements Tyre {
    @Override
    // 米其林轮胎生产中
    public void produceTyre() {
        System.out.println("米其林轮胎生产中!");
    }
}
