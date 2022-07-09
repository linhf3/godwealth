package com.godwealth.designpatterns.adaptermode.objectadaptermode;


import com.godwealth.designpatterns.adaptermode.classadaptermode.BusinessInterface;
import com.godwealth.designpatterns.adaptermode.classadaptermode.Expressage;

/**
 * 原文链接：https://blog.csdn.net/Jiang_bug/article/details/118115192
 */
public class Test {
    public static void main(String[] args) {

        Expressage expressage = new Expressage();
        BusinessInterface businessInterface = new BusinessAdapter(expressage);
        // 旧业务
        expressage.send();
        // 新业务
        businessInterface.newSend();

    }

}
