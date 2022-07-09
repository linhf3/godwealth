package com.godwealth.designpatterns.handler;


/**
 * 组员测试类
 */
public class ZhangSan {
    public static void main(String[] args) {
        // 创建组长领导类
        Handler handlerGroup = new GroupLeader();
        // 创建老板领导类
        Handler handlerBoss = new BossHandler();
        // 为组长领导设置下一级领导
        handlerGroup.setNextHandler(handlerBoss);
        // 请假理由
        handlerGroup.approve(9);
        //handlerGroup.approve(11);
    }
}
