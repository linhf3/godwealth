package com.godwealth.designpatterns.proxymode.staticproxy;

/**
 * 租客
 */
public class Tenant {
    public static void main(String[] args) {
        // 找到中介租房子
        Proxy proxy = new Proxy();
        proxy.rent();

    }
}
