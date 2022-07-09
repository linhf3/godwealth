package com.godwealth.designpatterns.adaptermode.classadaptermode;

/**
 * 1.类适配器模式
 * 新业务实现类
 */
public class BusinessNewSend implements BusinessInterface{
    // 实现新业务
    @Override
    public void newSend() {
        System.out.println("新业务发送其他快递!");
    }
}
