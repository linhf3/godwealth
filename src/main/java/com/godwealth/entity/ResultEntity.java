package com.godwealth.entity;

import lombok.Data;

@Data
public class ResultEntity implements Comparable<ResultEntity>{

    private String name;

    private double price;

    private int positiveNegativeFlag;

    private String proportion;

    private String fProportion;

    private double dailySpread;

    private String fiveDailySpread;

    private String d8;

    private String d14;

    private double sort;

    private double max;

    private double min;
    //涨幅
    private String zhangfu;
    //振幅
    private String zf;
    //当前价格振幅
    private String bzf;
    @Override
    public int compareTo(ResultEntity o) {
        if (Math.abs(o.getSort()) > Math.abs(this.sort)){
            return 1;
        }else if (Math.abs(o.getSort()) < Math.abs(this.sort)){
            return -1;
        }
        return 0;
    }
}
