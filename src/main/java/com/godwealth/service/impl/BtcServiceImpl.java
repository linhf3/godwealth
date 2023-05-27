package com.godwealth.service.impl;

import com.alibaba.fastjson.JSON;
import com.godwealth.service.BtcService;
import com.godwealth.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BtcServiceImpl implements BtcService {

    public Map<String,Object> getBtc(){
        Map<String, Object> respMap = new HashMap<>();
        String rx = null;
        try {
            rx = HttpUtils.doGet("https://www.yirongxinfuguanli.com/priapi/v5/market/candles?instId=BTC-USDT&bar=1m&after=&before=&limit=1&t=1684653157814", null);
            log.info(rx);
            Map node = (Map) JSON.parse(rx);
            List<List<String>> list = (List<List<String>>) node.get("data");
            //log.info("当前价格为：",list.get(0).get(4));
            respMap.put("price",list.get(0).get(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(respMap.get("price"));
        return respMap;
    }

//    public static void main(String[] args) throws InterruptedException {
//        while (true){
//            new BtcServiceImpl().getBtc();
//            Thread.sleep(6000);
//        }
//    }

}
