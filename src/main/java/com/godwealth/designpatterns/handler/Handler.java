package com.godwealth.designpatterns.handler;

/**
 * 责任链模式
 * 领导抽象类 每一个具体领导实现类都应继承该类
 */
public abstract class Handler {

    // 私有下一级领导
    protected Handler nextHandler;

    // 设置下一级领导
    public void setNextHandler(Handler nextHandler){
        this.nextHandler = nextHandler;
    }

    // 审批方法
    abstract void approve(int info);
}
