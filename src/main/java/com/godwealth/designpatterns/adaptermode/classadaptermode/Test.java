package com.godwealth.designpatterns.adaptermode.classadaptermode;

/**
 * 1.类适配器模式
 * 原文链接：https://blog.csdn.net/Jiang_bug/article/details/118115192
 */
public class Test {
    public static void main(String[] args) {
        // 旧业务
        BusinessInterface businessInterface = new BusinessAdapter();
        businessInterface.newSend();
        // 新业务
        BusinessInterface businessInterface1 = new BusinessNewSend();
        businessInterface1.newSend();

    }
}
