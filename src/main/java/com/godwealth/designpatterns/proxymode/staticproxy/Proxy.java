package com.godwealth.designpatterns.proxymode.staticproxy;
/**
 * 中介代理类
 */
public class Proxy implements RentInterface {
    private Landlord landlord;
    void price() {
        System.out.println("收个20大洋中介费");
    }
    @Override
    public void rent() {
        price();
        landlord = new Landlord();
        landlord.rent();
    }
}
