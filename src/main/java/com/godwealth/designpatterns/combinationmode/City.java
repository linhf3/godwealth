package com.godwealth.designpatterns.combinationmode;

/**
 * 城市 节点
 */
public class City implements Container{
    // 城市人口数量
    private Integer sum;

    // 构造方法
    public City(Integer sum) {
        this.sum = sum;
    }

    @Override
    public int count() {
        System.out.println("统计完毕返回人数！");
        return sum;
    }
}
