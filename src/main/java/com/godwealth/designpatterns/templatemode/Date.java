package com.godwealth.designpatterns.templatemode;

import org.springframework.stereotype.Service;

/**
 * 模板方法模式(穷富男孩约会流程为例)
 * 定义一个操作中的算法的框架，将一些步骤延迟到子类中，使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。
 * 可以定义抽象类，让子类必须实现
 *
 */
@Service
public class Date {

    // 洗澡 洗香香方法
    protected void bath() {
        System.out.println("给自己洗澡");
    }

    // 筹备约会资金
    protected void money() {
        System.out.println("给自己搞点钱");
    }

    // 定好约会地点
    protected void site() {
        System.out.println("定好位置");
    }

    // 约会总流程方法
    public final void dateTemplate() {
        System.out.println("打电话约女朋友");
        bath();
        money();
        site();
        System.out.println("约会结束，爽歪歪");
    }
}
