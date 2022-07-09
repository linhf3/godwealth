package com.godwealth.designpatterns.decoratorpattern.coffe;

/**
 * 具体实现类，由于实现类很多，因此抽出一个接口
 */
public class Coffee  extends Drink  {
    @Override
    public float cost() {
        return super.getPrice();
    }
}
