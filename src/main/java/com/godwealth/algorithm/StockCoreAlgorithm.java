package com.godwealth.algorithm;

import com.alibaba.fastjson.JSON;
import com.godwealth.utils.Constant;
import com.godwealth.utils.SortUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 股票
 * @author sie_linhongfei
 * @createDate 2022/07/09 10:27
 */
@Component
@Slf4j
public class StockCoreAlgorithm implements CoreAlgorithm{

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Map<String, Object> deviationTheDayRate(Map map) {
        return null;
    }

    @Override
    public String deviationManyDaysRate(List<List> list) {
        return null;
    }

    @Override
    public Map<String, Object> deviationRateCore(String code) {
        StringBuilder sbUrl = new StringBuilder(Constant.fiveDaysUrl);
        if(code.startsWith("6")){
            sbUrl.append("1."+code);
        }else{
            sbUrl.append("0."+code);
        }
        String rx = restTemplate.getForObject(sbUrl.toString(),String.class);
        Map node = (Map) JSON.parse(rx);
        //数据集
        Map data = (Map) node.get("data");
        List list = (List) data.get("trends");
        HashMap<String, Object> map = new HashMap<>();
        double preClose = Double.valueOf(data.get("preClose").toString());
        map.put("preClose",preClose);//昨日收盘价格
        map.put("name",data.get("name"));//股票名
        map.put("code",data.get("code"));//股票代码
        Date date = new Date();
        String formatDate = sdf.format(date);
        //存放五日正数数据
        HashSet fivePlus = new HashSet();
        //存放五日负数数据
        HashSet fiveNegative = new HashSet();
        //存放当日正数数据
        HashSet onePlus = new HashSet();
        //存放当日负数数据
        HashSet oneNegative = new HashSet();
        //存放当日价格，去重后
        TreeSet<Double> curentPriceList = new TreeSet<>();
        int curentNum = 0;
        //用于判断当前价格是走正偏离还是负偏离数组排序确定位置
        boolean curentType = true;

        for (int j = 0; j < list.size(); j++) {
            String  s= (String) list.get(j);
            String[] split = s.split(",");
            String dateString = split[0].substring(0,10);
            //当前
            double s1 = Double.valueOf(split[2]);
            //平均
            double s2 = Double.valueOf(split[7]);
            BigDecimal s3 = new BigDecimal((s1-s2)*100);
            int f1 = (int)s3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            //
            String currentDate = list.get(list.size()-1).toString().split(",")[0].substring(0,10);
            if(s1>=s2){
                fivePlus.add(f1);
                if (dateString.startsWith(currentDate)){//当日时间，说明是当日价格
                    curentPriceList.add(s1);//当日价格
                    onePlus.add(f1);//当日偏离
                }
            }else {
                fiveNegative.add(Math.abs(f1));
                if (dateString.startsWith(currentDate)){//当日时间，说明是当日价格
                    curentPriceList.add(s1);//当日价格
                    oneNegative.add(Math.abs(f1));
                }
            }
            if(j == list.size()-1){//最后的数据，说明是当前最新价格
                curentNum = Math.abs(f1);//当前偏离值
                map.put("price",s1);//股票当前价格
                //计算涨跌幅
                double upsAndDowns = s1/ preClose;
                upsAndDowns = Math.round((upsAndDowns>=1?(1-upsAndDowns)*100:(upsAndDowns-1)*100)*100)/100.0;
                if(f1<=0){
                    curentType = false; }
            }
        }
        Integer[] fivePluArrays = (Integer[]) fivePlus.toArray(new Integer[fivePlus.size()]);
        Integer[] fivenegativeArrays = (Integer[]) fiveNegative.toArray(new Integer[fiveNegative.size()]);
        Integer[] onePlusArrays = (Integer[]) onePlus.toArray(new Integer[onePlus.size()]);
        Integer[] oneNegativeArrays = (Integer[]) oneNegative.toArray(new Integer[oneNegative.size()]);
        //当日价格，在原来价格的基础上*100，要获取原来价格可除以100即可

        //Integer[] curentPriceArrays = (Integer[]) curentPriceList.toArray(new Integer[curentPriceList.size()]);
        SortUtils.shellInserSort(fivePluArrays);
        SortUtils.shellInserSort(fivenegativeArrays);
        SortUtils.shellInserSort(onePlusArrays);
        SortUtils.shellInserSort(oneNegativeArrays);
        log.debug("fivePlusArrays"+fivePlus.toString());
        log.debug("fivenegativeArrays"+fivenegativeArrays.toString());
        log.debug("onePlusArrays"+onePlusArrays.toString());
        log.debug("oneNegativeArrays"+oneNegativeArrays.toString());
        double fiveIndex = 0.0d;
        double oneIndex = 0.0d;
        double fiveProportion = 0.0d;
        double oneProportion = 0.0d;
        if(curentType){
            if (fivePluArrays.length == 0){
                map.put("fiveProportion",0.00);
                map.put("oneProportion",0.00);
                return map;
            }
            fiveIndex = SortUtils.binarySearch(fivePluArrays, 0, fivePluArrays.length, curentNum);
            fiveProportion = (fiveIndex/fivePluArrays.length)*100;
            oneIndex = SortUtils.binarySearch(onePlusArrays, 0, onePlusArrays.length, curentNum);
            oneProportion = (oneIndex/onePlusArrays.length)*100;
        }else{
            if (fivenegativeArrays.length == 0){
                map.put("fiveProportion",0.00);
                map.put("oneProportion",0.00);
                return map;
            }
            fiveIndex = SortUtils.binarySearch(fivenegativeArrays,0,fivenegativeArrays.length,curentNum);
            fiveProportion = -(fiveIndex/fivenegativeArrays.length)*100;
            oneIndex = SortUtils.binarySearch(oneNegativeArrays,0,oneNegativeArrays.length,curentNum);
            oneProportion = -(oneIndex/oneNegativeArrays.length)*100;
        }
        map.put("lowestPrice",curentPriceList.first());
        map.put("highestPrice",curentPriceList.last());
        map.put("priceDifference",curentPriceList.last()-curentPriceList.first());
        map.put("fiveProportion",Math.round(fiveProportion));
        map.put("oneProportion",Math.round(oneProportion));
        return map;
    }
}
