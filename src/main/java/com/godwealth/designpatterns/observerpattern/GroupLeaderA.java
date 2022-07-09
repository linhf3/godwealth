package com.godwealth.designpatterns.observerpattern;

/**
 * 原文链接：https://blog.csdn.net/Jiang_bug/article/details/118115192
 *
 * A组 组长类
 */
public class GroupLeaderA implements Leader {
    // 添加组员
    @Override
    public void addPerson(GroupObserver groupObserver) {
        System.out.println("添加组员");
        //observerList.add(groupObserver);
    }

    // 通知发工资
    @Override
    public void grantMoney() {
        System.out.println("通知发工资");
        //observerList.forEach(GroupObserver::getMoney);
    }
}
