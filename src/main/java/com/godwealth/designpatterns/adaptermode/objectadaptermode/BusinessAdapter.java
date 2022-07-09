package com.godwealth.designpatterns.adaptermode.objectadaptermode;

import com.godwealth.designpatterns.adaptermode.classadaptermode.BusinessInterface;
import com.godwealth.designpatterns.adaptermode.classadaptermode.Expressage;

/**
 * 2.对象适配器
 */
public class BusinessAdapter implements BusinessInterface {
    private Expressage expressage;

    public BusinessAdapter(Expressage expressage){
        this.expressage = expressage;
    }

    // 实现新业务
    @Override
    public void newSend() {
        expressage.send();
        System.out.println("新业务发送其他快递!");
    }
}
