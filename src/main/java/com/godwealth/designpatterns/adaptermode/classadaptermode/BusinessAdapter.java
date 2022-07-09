package com.godwealth.designpatterns.adaptermode.classadaptermode;

/**
 * 1.类适配器模式
 * 适配器类
 * 实现旧业务
 */
public class BusinessAdapter extends Expressage implements BusinessInterface {
    // 调用旧业务
    @Override
    public void newSend() {
        super.send();
    }
}
