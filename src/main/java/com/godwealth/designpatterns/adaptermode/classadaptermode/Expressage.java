package com.godwealth.designpatterns.adaptermode.classadaptermode;

/**
 * 1.类适配器模式
 * 将某个类的接口转换为接口客户所需的类型。换句话说，适配器模式解决的问题是，使得原本由于接口不兼容而不能一起工作、不能统一管理的那些类可以在一起工作、可以进行统一管理。
 * 原有业务类
 * 快递员只发送顺丰快递
 */
public class Expressage {
    public void send(){
        System.out.println("快递员发顺丰快递!");
    }
}
