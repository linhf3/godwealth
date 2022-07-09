package com.godwealth.utils;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private Integer status;
    private String message;
    private T data;
    public CommonResult(int status,String message){
        this.status = status;
        this.message = message;
    }
}
