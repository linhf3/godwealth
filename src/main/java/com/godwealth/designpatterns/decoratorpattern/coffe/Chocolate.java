package com.godwealth.designpatterns.decoratorpattern.coffe;

public class Chocolate extends Decorator {
    public Chocolate(Drink obj) {
        super(obj);
        setDesc(" 巧克力 ");
        setPrice(3.0f);
    }
}
