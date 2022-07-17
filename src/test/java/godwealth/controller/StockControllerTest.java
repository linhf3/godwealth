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
        futuresService.updateFuturesData();
    }
    @org.junit.Test
    public void test2(){
        redisUtils.set("sss_buy_log","3333");
        Long buy_log = redisUtils.deleteEndWith("_buy_log");
        log.debug("sss:{}",buy_log);
    }


}
