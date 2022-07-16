package godwealth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.godwealth.GodWealthApplication;
import com.godwealth.algorithm.CoreAlgorithm;
import com.godwealth.algorithm.FuturesCoreAlgorithm;
import com.godwealth.dao.StockCodeMapper;
import com.godwealth.entity.StockCode;
import com.godwealth.service.FuturesService;
import com.godwealth.service.ProportionLogService;
import com.godwealth.service.StockService;
import com.godwealth.service.impl.FuturesServiceImpl;
import com.godwealth.utils.Constant;
import com.godwealth.utils.HttpUtils;
import com.godwealth.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;


/**
 * 例：测试类
 *
 * @author sie_linhongfei
 * @createDate 2021/11/3 17:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GodWealthApplication.class)
@Slf4j
public class StockControllerTest {


    @Autowired
    private StockCodeMapper stockCodeMapper;
    @Autowired
    private FuturesService futuresService;
    @Autowired
    private StockService stockService;
    @Autowired
    private RedisUtils redisUtils;


    @Autowired
    private ProportionLogService proportionLogService;
    @Test
    public void test1() throws Exception{
        //StockCode stockCode = stockCodeMapper.selectByPrimaryKey(1013);
        //log.debug("对象信息：{}",stockCode);
        //List<Map<String, Object>> maps = futuresService.futuresData();
        //log.debug("对象信息：{}",maps);
        proportionLogService.collectStockDeviationLogs();
    }
    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    @Test
    public void test3() throws Exception{
        List<String> objects = new ArrayList<>();
        objects.add("245343454");
        redisUtils.set("name",JSON.toJSONString(objects));
        Object name = redisUtils.get("name");
        List<String> name1 = JSONObject.parseArray((String) name, String.class);
        System.out.println(name);
    }
    @org.junit.Test
    public void test2(){
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("113");
        linkedList.add("114");
        linkedList.add("115");
        LinkedList<StockCode> stockCodeLinkedList = new LinkedList<>();
        linkedList.forEach(s ->{
            Map urlMap = new HashMap<>();
            urlMap.put("place",s);
            //发送http请求
            String rx = null;
            try {
                rx = HttpUtils.doGet(new StrSubstitutor(urlMap).replace(Constant.FUTURESMAINFORCEURL), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Map node = (Map) JSON.parse(rx);
            List<Map<String,Object>> list = (List<Map<String, Object>>) node.get("list");
            //数据集
            System.out.println(list.toArray());
            list.forEach(map -> {
                String name = (String) map.get("name");
                if (name.contains("主力")&&!name.contains("次")){
                    StockCode stockCode = new StockCode();
                    stockCode.setName(name);
                    String dm = (String)map.get("dm");
                    stockCode.setStockCode(dm);
                    stockCode.setCategory("2");
                    stockCode.setSwEffective("无效");
                    stockCode.setMemo(name);
                    stockCode.setExchangeCode(new StringBuilder(s).append(".").append(dm).toString());
                    stockCode.setAddUser("admin");
                    stockCodeLinkedList.add(stockCode);
                }
            });
        });
        log.debug("list:{}",stockCodeLinkedList);
        System.out.println(stockCodeLinkedList.toArray());
        for (int i = 0; i < stockCodeLinkedList.size(); i++) {
            StockCode stockCode = stockCodeLinkedList.get(i);
            log.debug("llll:{}",stockCodeLinkedList.get(i).getMemo());
            StockCode quStockCode = stockCodeMapper.selectByName(stockCode.getName());
            if (null == quStockCode){
                stockCodeMapper.insert(quStockCode);
            }else {
                stockCodeMapper.updateByName(quStockCode);
            }
        }
        //stockCodeMapper.insertList(stockCodeLinkedList);
    }


}
