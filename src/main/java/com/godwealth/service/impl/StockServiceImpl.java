package com.godwealth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.godwealth.dao.DeviationStrategyMapper;
import com.godwealth.dao.StockCodeMapper;
import com.godwealth.dao.StockLogMapper;
import com.godwealth.entity.DeviationStrategy;
import com.godwealth.entity.StockCode;
import com.godwealth.entity.StockLog;
import com.godwealth.service.StockService;
import com.godwealth.utils.Constant;
import com.godwealth.utils.RedisUtils;
import com.godwealth.utils.SendMail;
import com.godwealth.utils.SortUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.*;


/**
 * 股票
 * @author sie_linhongfei
 * @createDate 2022/07/09 10:27
 */
@Repository
@Slf4j
public class StockServiceImpl implements StockService {

    @Autowired
    private StockCodeMapper stockCodeMapper;

    @Autowired
    private DeviationStrategyMapper deviationStrategyMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockLogMapper stockLogMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<Map<String, Object>> stockData() throws GeneralSecurityException, MessagingException {
        StockCode stockCode = new StockCode();
        stockCode.setSwEffective("有效");
        stockCode.setCategory("1");
        List<StockCode> stockCodes = stockCodeMapper.selectByCondition(stockCode);
        List<Map<String,Object>> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder("");
        //查询策略
        Object deviationStrategyString = redisUtils.get("deviationStrategyString");
        DeviationStrategy deviationStrategy  = null;
        if (ObjectUtils.isEmpty(deviationStrategyString)){
            deviationStrategy = deviationStrategyMapper.selectByPrimaryKey(1);
            redisUtils.set("allStocksDataList",JSON.toJSONString(deviationStrategy));
        }else {
            deviationStrategy = JSONObject.parseObject((String) deviationStrategyString, DeviationStrategy.class);
        }

        for (int i = 0; i < stockCodes.size(); i++) {
            HashMap<String, Object> hash = new HashMap<>();
            String code = stockCodes.get(i).getStockCode();
            StringBuilder sbUrl = new StringBuilder(Constant.fiveDaysUrl);
            if(code.startsWith("6")){
                sbUrl.append("1."+code);
            }else{
                sbUrl.append("0."+code);
            }
            String rx = restTemplate.getForObject(sbUrl.toString(),String.class);
            Map node = (Map) JSON.parse(rx);
            //System.out.println("node = " + node);
            //数据集
            Map data = (Map) node.get("data");
            //hash.put("name",data.get("name"));
            hash.put("prePrice",data.get("prePrice"));
            hash.put("name",data.get("name"));
            hash.put("code",data.get("code"));
            List data2 = (List) data.get("trends");
            //存放正数数据
            List plus = new ArrayList();
            //存放负数数据
            List negative = new ArrayList();
            int curentNum = 0;
            boolean curentType = true;
            for (int j = 0; j < data2.size(); j++) {
                String  s= (String) data2.get(j);
                String[] split = s.split(",");
                //当前
                double s1 = Double.valueOf(split[2]);
                //平均
                double s2 = Double.valueOf(split[7]);
                BigDecimal s3 = new BigDecimal((s1-s2)*100);
                int f1 = (int)s3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                if(s1>=s2){
                    plus.add(f1);
                }else {
                    negative.add(Math.abs(f1));
                }
                if(j == data2.size()-1){
                    curentNum = Math.abs(f1);
                    hash.put("price",s1);
                    double prePrice = Double.valueOf(String.valueOf(data.get("prePrice")));
                    BigDecimal s4 = new BigDecimal((s1-prePrice)*100/prePrice);
                    double f2 = s4.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    //hash.put("amplitude",String.valueOf(f2)+"%");
                    if(f1<=0){
                        curentType = false; }
                }
            }
            Integer[] pluss = (Integer[]) plus.toArray(new Integer[plus.size()]);
            Integer[] negatives = (Integer[]) negative.toArray(new Integer[negative.size()]);
            SortUtils.shellInserSort(pluss);
            SortUtils.shellInserSort(negatives);
            double index = 0.0d;
            double proportion = 0.0d;
            if(curentType){
                index = SortUtils.binarySearch(pluss, 0, pluss.length, curentNum);
                proportion = (index/pluss.length)*100;
            }else{
                index = SortUtils.binarySearch(negatives,0,negatives.length,curentNum);
                proportion = -(index/negatives.length)*100;
            }
            hash.put("proportion",proportion>0?"+"+Math.round(proportion)+"%":Math.round(proportion)+"%");

            //查询结果，是否存在买有的数据
            StockLog qStockLog = new StockLog();
            qStockLog.setCategory("1");
            qStockLog.setSwEffective("有效");
            qStockLog.setType("买");
            qStockLog.setStockCode((String) hash.get("code"));

            List<StockLog> stockLogs = stockLogMapper.selectByCondition(qStockLog);

            StockLog selectStockLog = null;
            if (!CollectionUtils.isEmpty(stockLogs)){
                selectStockLog = stockLogs.get(0);
            }
            //买
            if(Math.round(proportion) <= deviationStrategy.getNegativeDeviation() && null == selectStockLog){
                //如果偏离小于-55，是买入点，则查询是否已经买了，如果没有买，则买入，插入一条数据 stock_log ，也就是增加买入记录
                StockLog stockLog = new StockLog();
                stockLog.setStockCode((String) hash.get("code"));
                stockLog.setCreateDate(new Date());
                stockLog.setPrice(String.valueOf(hash.get("price")));
                stockLog.setBuySaleDate(new Date());
                stockLog.setCategory("1");
                stockLog.setSwEffective("有效");
                stockLog.setType("买");
                stockLog.setDifference("0");
                stockLogMapper.insert(stockLog);
                if(!"".equals(sb.toString())){
                    sb.append(";");
                }
                sb.append("请注意："+hash.get("name")+" "+hash.get("code")+"<="+Math.round(proportion));
            }

            //卖
            if(Math.round(proportion) >= deviationStrategy.getPositiveDeviation() && null != selectStockLog){
                //如果偏离小于-65，是买入点，则查询是否已经买了，如果没有买，则买入，插入一条数据 stock_log ，也就是增加买入记录
                //1.先修改为无效,设置差价，此时卖了
                selectStockLog.setSwEffective("无效");
                double prePrice = Double.parseDouble(selectStockLog.getPrice());
                double afPrice = (double) hash.get("price");
                selectStockLog.setDifference(String.valueOf(Math.round(afPrice-prePrice)));
                stockLogMapper.updateByPrimaryKey(selectStockLog);
                //2.插入当前卖点数据
                StockLog nStockLog = new StockLog();
                nStockLog.setStockCode((String) hash.get("code"));
                nStockLog.setCreateDate(new Date());
                nStockLog.setPrice(String.valueOf(hash.get("price")));
                nStockLog.setBuySaleDate(new Date());
                nStockLog.setCategory("1");
                nStockLog.setSwEffective("无效");
                nStockLog.setType("卖");
                nStockLog.setDifference(String.valueOf(Math.round(afPrice-prePrice)));
                stockLogMapper.insert(nStockLog);
                if(!"".equals(sb.toString())){
                    sb.append(";");
                }
                sb.append("请注意："+hash.get("name")+" "+hash.get("code")+">="+Math.round(proportion));
            }
            result.add(hash);
        }
        if(!"".equals(sb.toString())){
            SendMail.sendQqMail(sb.toString());
        }
        System.out.println("result.toString() = " + result.toString());
        return result;
    }

    @Override
    public void insert(String code) {
        StringBuilder sbUrl = new StringBuilder(Constant.fiveDaysUrl);
        if(code.startsWith("6")){
            sbUrl.append("1."+code);
        }else{
            sbUrl.append("0."+code);
        }
        String rx = restTemplate.getForObject(sbUrl.toString(),String.class);
        Map node = (Map) JSON.parse(rx);
        Map data = (Map) node.get("data");
        StockCode stockCode = new StockCode();
        stockCode.setName((String) data.get("name"));
        stockCode.setStockCode(code);
        stockCode.setCategory("1");
        stockCode.setSwEffective("有效");
        stockCodeMapper.insert(stockCode);
    }

    @Override
    public void delete(String code) {
        //根据编码删除数据
        stockCodeMapper.deleteBystockCode(code);
    }

    @Override
    public List<StockCode> query() {
        //查询结果，是否存在买有的数据
        StockCode stockCode = new StockCode();
        stockCode.setSwEffective("有效");
        stockCode.setCategory("1");
        return stockCodeMapper.selectByCondition(stockCode);
    }

    @Override
    public int insertSelective(StockCode stockCode) {
        return stockCodeMapper.insertSelective(stockCode);
    }

    @Override
    public List<StockCode> querySockCodeList() {
        StockCode stockCode = new StockCode();
        return stockCodeMapper.selectByCondition(stockCode);
    }

    @Override
    public int updateById(StockCode stockCode) {
        StringBuilder sbUrl = new StringBuilder(Constant.fiveDaysUrl);
        if(stockCode.getStockCode().startsWith("6")){
            sbUrl.append("1."+stockCode.getStockCode());
        }else{
            sbUrl.append("0."+stockCode.getStockCode());
        }
        String rx = restTemplate.getForObject(sbUrl.toString(),String.class);
        Map node = (Map) JSON.parse(rx);
        Map data = (Map) node.get("data");
        stockCode.setName((String) data.get("name"));
        return stockCodeMapper.updateByPrimaryKey(stockCode);
    }
}
