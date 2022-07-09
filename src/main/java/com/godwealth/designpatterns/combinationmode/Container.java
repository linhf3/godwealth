package com.godwealth.designpatterns.combinationmode;


/**
 * 组合模式
 * 将对象组合成树形结构以表示“部分-整体”的层次结构。组合模式使得用户对单个对象和组合对象的使用具有一致性。
 * 统计接口
 */
public interface Container {
    // 统计方法
    public int count();
}
