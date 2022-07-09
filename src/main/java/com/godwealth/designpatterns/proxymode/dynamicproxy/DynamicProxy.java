package com.godwealth.designpatterns.proxymode.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 改造静态代理模式例子 摒弃中介代理类创建动态代理类
 *
 *
 */
public class DynamicProxy implements InvocationHandler {

    // 声明需要代理的类
    private Object proxy;

    // 提供set
    public void setProxy(Object proxy) {
        this.proxy = proxy;
    }

    // 提供get  方法
    public Object getProxy() {
        // 三个参数固定写死 一个是获取getClassLoader 第二个获取代理类接口 第三个是动态代理类本身
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), proxy.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        message(method.getName());
        Object result = method.invoke(proxy, args);
        return result;
    }

    // 打印代理类执行了真实对象什么方法
    private void message(String msg) {
        System.out.println(msg);
    }

}
