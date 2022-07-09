package com.godwealth.designpatterns.decoratorpattern.coffe;

/**
 * 定义修饰类
 */
public class Decorator extends Drink{
    public Drink drink;

    public Decorator(Drink obj) { //组合
        // TODO Auto-generated constructor stub
        this.drink = obj;
    }

    @Override
    public String getDesc() {
        // obj.getDes() 输出被装饰者的信息
        return desc + " " + getPrice() + " && " + drink.getDesc();
    }

    @Override
    public float cost() {
        return super.getPrice() + drink.cost();
    }
}
