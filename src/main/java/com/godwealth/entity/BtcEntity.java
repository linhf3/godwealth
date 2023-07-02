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
