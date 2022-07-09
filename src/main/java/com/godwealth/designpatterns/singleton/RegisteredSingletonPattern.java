package com.godwealth.designpatterns.singleton;

import java.util.HashMap;
import java.util.Map;

/**
 * 登记式 单例模式
 * 登记式单例实际上维护的是一组单例类的实例，将这些实例存储到一个Map(登记簿)
 * 中，对于已经登记过的单例，则从工厂直接返回，对于没有登记的，则先登记，而后
 * 返回
 *
 */
public class RegisteredSingletonPattern {


    /**
     * 登记簿，用来存放所有登记的实例
     */
    private static Map<String, RegisteredSingletonPattern> map = new HashMap<String, RegisteredSingletonPattern>();
    //在类加载时添加一个实例到登记簿
    static {
        RegisteredSingletonPattern registeredSingletonPattern= new RegisteredSingletonPattern();
        map.put(registeredSingletonPattern.getClass().getName(), registeredSingletonPattern);//运用了反射
    }
    /**
     * 受保护的默认构造方法
     */
    protected RegisteredSingletonPattern() {

    }
    /**
     * 静态工厂方法，返回指定登记对象的唯一实例
     * 对于已经登记的直接取出返回，对于还未登记的先登记，然后取出返回
     *
     */
    public static RegisteredSingletonPattern getExample(String name){
        if(name == null){
            name="firstRegisteredSingletonPattern";
        }
        if(map.get(name) == null){
            try {
                map.put(name, (RegisteredSingletonPattern) Class.forName(name).newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map.get(name);
    }
}
