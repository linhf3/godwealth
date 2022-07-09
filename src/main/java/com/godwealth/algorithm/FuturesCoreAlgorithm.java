package com.godwealth.algorithm;

import com.godwealth.utils.SortUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
/**
 * 期货
 * @author sie_linhongfei
 * @createDate 2022/07/09 10:27
 */
@Component
public class FuturesCoreAlgorithm implements CoreAlgorithm {

    @Override
    public Map<String, Object> deviationTheDayRate(Map map) {
        Map<String, Object> reMap = new HashMap<>();
        reMap.put("name", map.get("name"));
        reMap.put("code", map.get("code"));
        List trendsList = (List) map.get("trends");
        //存放正数数据
        Set plus = new HashSet<>();
        //存放负数数据
        Set negative = new HashSet();
        int curentNum = 0;//当前价格的值*100
        boolean curentType = true;//当前是正还是负偏离，匹配数组
        //设置价格是否存在上下波动，即存在0的情况，计算存在误差
        for (int j = 0; j < trendsList.size(); j++) {
            String s = (String) trendsList.get(j);
            String[] split = s.split(",");
            //当前
            double s1 = Double.valueOf(split[1]);
            //平均
            double s2 = Double.valueOf(split[2]);
            BigDecimal s3 = new BigDecimal((s1 - s2) * 100);
            int f1 = (int) s3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            if (s1 >= s2) {
                plus.add(f1);
            } else {

                negative.add(Math.abs(f1));
            }
            if (j == trendsList.size() - 1) {
                curentNum = Math.abs(f1);
                reMap.put("price", s1);
                double prePrice = Double.valueOf(String.valueOf(map.get("prePrice")));
                BigDecimal s4 = new BigDecimal((s1 - prePrice) * 100 / prePrice);
                double f2 = s4.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                if (s1 < s2) {
                    curentType = false;
                }
            }
        }
        if (!CollectionUtils.isEmpty(plus) && !CollectionUtils.isEmpty(negative)) {
            plus.add(0);
            negative.add(0);
        }
        Integer[] pluss = (Integer[]) plus.toArray(new Integer[plus.size()]);
        Integer[] negatives = (Integer[]) negative.toArray(new Integer[negative.size()]);
        SortUtils.shellInserSort(pluss);
        SortUtils.shellInserSort(negatives);
        double index = 0.0d;
        double proportion = 0.0d;
        if (curentType) {
            index = SortUtils.binarySearch(pluss, 0, pluss.length, curentNum);
            proportion = (index / pluss.length) * 100;
        } else {
            index = SortUtils.binarySearch(negatives, 0, negatives.length, curentNum);
            proportion = -(index / negatives.length) * 100;
        }
        reMap.put("proportion", proportion > 0 ? "+" + Math.round(proportion) + "%" : Math.round(proportion) + "%");
        return reMap;
    }

    @Override
    public String deviationManyDaysRate(List<List> list) {
        //合并list
        LinkedList<Object> trendsList = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            List ls = list.get(i);
            if (!CollectionUtils.isEmpty(ls)) {
                trendsList.addAll(ls);
            }
        }
        //获取五日偏离
        if (!CollectionUtils.isEmpty(trendsList)) {
            //存放正数数据
            Set plus = new HashSet<>();
            //存放负数数据
            Set negative = new HashSet();
            int curentNum = 0;//当前价格的值*100
            boolean curentType = true;//当前是正还是负偏离，匹配数组
            //设置价格是否存在上下波动，即存在0的情况，计算存在误差
            for (int j = 0; j < trendsList.size(); j++) {
                String s = (String) trendsList.get(j);
                String[] split = s.split(",");
                //当前
                double s1 = Double.valueOf(split[1]);
                //平均
                double s2 = Double.valueOf(split[2]);
                BigDecimal s3 = new BigDecimal((s1 - s2) * 100);
                int f1 = (int) s3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                if (s1 >= s2) {
                    plus.add(f1);
                } else {

                    negative.add(Math.abs(f1));
                }
                if (j == trendsList.size() - 1) {
                    curentNum = Math.abs(f1);
                    if (s1 < s2) {
                        curentType = false;
                    }
                }
            }
            if (!CollectionUtils.isEmpty(plus) && !CollectionUtils.isEmpty(negative)) {
                plus.add(0);
                negative.add(0);
            }
            Integer[] pluss = (Integer[]) plus.toArray(new Integer[plus.size()]);
            Integer[] negatives = (Integer[]) negative.toArray(new Integer[negative.size()]);
            SortUtils.shellInserSort(pluss);
            SortUtils.shellInserSort(negatives);
            double index = 0.0d;
            double proportion = 0.0d;
            if (curentType) {
                index = SortUtils.binarySearch(pluss, 0, pluss.length, curentNum);
                proportion = (index / pluss.length) * 100;
            } else {
                index = SortUtils.binarySearch(negatives, 0, negatives.length, curentNum);
                proportion = -(index / negatives.length) * 100;
            }
            return proportion > 0 ? "+" + Math.round(proportion) + "%" : Math.round(proportion) + "%";
        }
        return "";
    }
}
