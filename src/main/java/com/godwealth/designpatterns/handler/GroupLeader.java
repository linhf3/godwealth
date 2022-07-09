package com.godwealth.designpatterns.handler;

/**
 * 组长领导类
 */
public class GroupLeader extends Handler {
    @Override
    void approve(int info) {
        if (info < 10) {
            System.out.println("组长审批通过！");
            return;
        }
        nextHandler.approve(info);
    }
}
