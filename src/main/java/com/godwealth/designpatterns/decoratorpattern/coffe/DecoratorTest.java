package com.godwealth.designpatterns.decoratorpattern.coffe;


public class DecoratorTest {
    public static void main(String[] args) {
        System.out.println("=======执行下面测试======");
        // 单点一杯意大利咖啡
        Drink coffee = new Espresso();
        System.out.println("当前饮品的描述为：" + coffee.getDesc() + ";价格为:" + coffee.cost());

        // 单点一杯意大利咖啡 + 一份牛奶
        coffee = new Milk(coffee);
        System.out.println("当前饮品的描述为：" + coffee.getDesc() + ";价格为:" + coffee.cost());

        // 单点一杯意大利咖啡 + 一份牛奶 + 一份巧克力
        coffee = new Chocolate(coffee);
        System.out.println("当前饮品的描述为：" + coffee.getDesc() + ";价格为:" + coffee.cost());

        // 单点一杯意大利咖啡 + 一份牛奶 + 一份巧克力 + 一份巧克力
        coffee = new Chocolate(coffee);
        System.out.println("当前饮品的描述为：" + coffee.getDesc() + ";价格为:" + coffee.cost());
    }

}
