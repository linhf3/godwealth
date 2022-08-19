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
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.element.VariableElement;
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
    private RedissonClient redisson;

    @Autowired
    private RedisTemplate redisTemplate;


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
        RScoredSortedSet<String> set = redisson.getScoredSortedSet("simple");
        set.add(0.13, "111");
        set.add(0.12, "131");
        set.add(0.12, "134");
        set.add(0.15, "121");
        System.out.println(set.getScore("131"));
        System.out.println(set.getScore("134"));
        //futuresService.updateFuturesSourceData();
    }
    @org.junit.Test
    public void test2() throws IOException {
        Object o = redisUtils.get("113.fum");
        System.out.println(o);
        //futuresService.updateFuturesData();
//        futuresService.setFiveDayTotal();
//        Object fu0 = redisUtils.get("FG0");
//        System.out.println(fu0);
        //redisUtils.flushDb();

    }


}
