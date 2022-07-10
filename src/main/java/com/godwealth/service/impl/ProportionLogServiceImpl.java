package com.godwealth.service.impl;

import com.alibaba.fastjson.JSON;
import com.godwealth.algorithm.CoreAlgorithmContet;
import com.godwealth.dao.ProportionLogMapper;
import com.godwealth.entity.ProportionLog;
import com.godwealth.service.ProportionLogService;
import com.godwealth.service.StockService;
import com.godwealth.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class ProportionLogServiceImpl implements ProportionLogService {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private ProportionLogMapper proportionLogMapper;

    @Autowired
    private CoreAlgorithmContet coreAlgorithmContet;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockService stockService;

    //爬数据
    @Override
    public void collectStockDeviationLogs() {
        String rx = restTemplate.getForObject(Constant.url,String.class);
        String substring = rx.substring(rx.indexOf("({")+1, rx.length()-2);
        Map node = (Map) JSON.parse(substring);
        Map data = (Map) node.get("data");
        List<Map<String,Object>> diff = (List) data.get("diff");

        if (null != diff && diff.size()>0 ) {

            List<ProportionLog> proportionLogList = new ArrayList<>();
            for(int i= 0 ; i<diff.size();i++){
                ProportionLog proportionLog = new ProportionLog();
                Map<String, Object> map = diff.get(i);
                String code = (String) map.get("f12");
                if (!code.startsWith("60")&&!code.startsWith("30")&&!code.startsWith("0")){
                    continue;
                }
                String name = (String) map.get("f14");
                String price = String.valueOf(map.get("f2"));
                proportionLog.setAddDate(new Date());
                proportionLog.setCode(code);
                proportionLog.setName(name);
                proportionLog.setPrice(price);
                Map<String, Object> vmap = coreAlgorithmContet.deviationRateCore("stockCoreAlgorithm",code);
                String fiveProportion = (String) vmap.get("fiveProportion");
                proportionLog.setProportion(fiveProportion);
                log.info("collectStockDeviationLogs方法：name:"+name+",code:"+code+",proportion:"+fiveProportion+",price:"+price);
                proportionLogList.add(proportionLog);
                //proportionLogMapper.insertSelective(proportionLog);
            }
            proportionLogMapper.insertList(proportionLogList);
        }
    }

    @Override
    public List<ProportionLog> selectByCondition(ProportionLog proportionLog) {
        return proportionLogMapper.selectByCondition(proportionLog);
    }





}
