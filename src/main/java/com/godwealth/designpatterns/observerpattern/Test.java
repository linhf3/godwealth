package com.godwealth.designpatterns.observerpattern;

//原文链接：https://blog.csdn.net/Jiang_bug/article/details/118115192
public class Test {
    public static void main(String[] args) {

        // 创建A组 组长
        Leader leader = new GroupLeaderA();
        // 添加组员
        leader.addPerson(new ZhangSan());
        leader.addPerson(new LiSi());
        // 发放工资
        leader.grantMoney();

    }
}
