package com.godwealth.utils;

/**
 *
 * @author sie_linhongfei
 * @createDate 2022/04/21 22:43
 */
public class ConvertUtils {
    /**
     * double转换string
     */
    public static String ConvertDoubleToString(double var){
        StringBuilder stringBuilder = new StringBuilder("");
        if (var > 0){
            stringBuilder.append("+").append(String.valueOf(var));
        }else if (var < 0){
            stringBuilder.append("-").append(String.valueOf(var));
        }else {
            stringBuilder.append("0");
        }
        return stringBuilder.append("%").toString();
    }
}
