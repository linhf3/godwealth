package com.godwealth.entity;

import lombok.Data;

@Data
public class BtcEntity implements Comparable<BtcEntity>{

    private String name;

    private double price;

    private double sort;

    private double max;

    private double min;
    //涨幅
    private String zhangfu;
    //振幅
    private String zf;
    //当前价格上振幅
    private String sz;
    //当前价格下振幅
    private String xz;
    //点差
    private double dc;
    //当日偏离
    private String proportion;
    //当日偏离绝对值是否大于等于90：1 是，0 否
    private String proportionAbs;

    @Override
    public int compareTo(BtcEntity o) {
        if (Math.abs(o.getSort()) > Math.abs(this.sort)){
            return 1;
        }else if (Math.abs(o.getSort()) < Math.abs(this.sort)){
            return -1;
        }
        return 0;
    }
}
