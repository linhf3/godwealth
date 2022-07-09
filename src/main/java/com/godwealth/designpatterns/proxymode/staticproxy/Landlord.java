package com.godwealth.designpatterns.proxymode.staticproxy;

/**
 * 房东类
 */
public class Landlord implements RentInterface {
    @Override
    public void rent() {
        System.out.println("我是房东我要出售一套房子价格200大洋");
    }
}
