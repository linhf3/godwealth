package com.godwealth.designpatterns.handler;


/**
 * 老板领导类
 */
public class BossHandler extends Handler{
    @Override
    void approve(int info) {
        System.out.println("老板审批通过！");
    }
}
