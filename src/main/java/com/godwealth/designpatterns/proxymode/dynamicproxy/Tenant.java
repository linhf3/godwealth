package com.godwealth.designpatterns.proxymode.dynamicproxy;

import com.godwealth.designpatterns.proxymode.staticproxy.Landlord;
import com.godwealth.designpatterns.proxymode.staticproxy.RentInterface;

/**
 * 租客
 */
public class Tenant {

    public static void main(String[] args) {
        // 创建房东对象
       // Landlord landlord = new Landlord();
        // 创建动态代理对象
        //DynamicProxy dynamicProxy = new DynamicProxy();
        //dynamicProxy.setProxy(landlord);

        // 拿到代理对象
        //RentInterface rentInterface = (RentInterface) dynamicProxy.getProxy();

        //jdk动态代理
        RentInterface proxyInstance = (RentInterface) new JDKproxyFactory(new Landlord()).getProxyInstance();
        proxyInstance.rent();

    }
}
