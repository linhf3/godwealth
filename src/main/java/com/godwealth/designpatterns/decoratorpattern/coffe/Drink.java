package com.godwealth.designpatterns.decoratorpattern.coffe;

import lombok.Data;

@Data
public abstract class Drink {
    /**
     * 描述
     */
    public String desc;
    /**
     * 价格
     */
    public float price = 0.0f;

    /**
     * 计算费用的抽象方法/子类来实现
     * @return
     */
    public abstract float cost();

}
