package com.godwealth.utils;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult{
    private Integer status;
    private String message;
    private Map<String,Object> resultMap;
    public CommonResult(int status,String message){
        this.status = status;
        this.message = message;
    }
    public CommonResult(int status, String message,Map<String,Object> resultMap){
        this.status = status;
        this.message = message;
        this.resultMap =resultMap;
    }

}
