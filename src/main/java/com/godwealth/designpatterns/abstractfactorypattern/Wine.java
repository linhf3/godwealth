package com.godwealth.designpatterns.abstractfactorypattern;

/**
 * 葡萄酒抽象类
 */
public abstract class Wine extends Liquor{
    @Override
    protected abstract void sell();
}
