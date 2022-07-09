package com.godwealth.designpatterns.factorymethodpattern;
/**
 * 米其林轮胎生产工厂
 */
public class MichelinFactory implements TyreFactory{
    @Override
    public Tyre produceTyreFactory() {
        return new MichelinTyre();
    }
}
