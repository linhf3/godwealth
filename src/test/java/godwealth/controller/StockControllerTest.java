package godwealth.controller;

import com.godwealth.GodWealthApplication;
import com.godwealth.dao.StockCodeMapper;
import com.godwealth.service.FuturesService;
import com.godwealth.service.ProportionLogService;
import com.godwealth.service.SinaFuturesService;
import com.godwealth.service.StockService;
import com.godwealth.utils.Mqutils;
import com.godwealth.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;
import java.util.concurrent.*;


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
        futuresService.updateFuturesSourceData();
    }
    @Autowired
    private Mqutils mqutils;
    @Autowired
    private SinaFuturesService sinaFuturesService;
    @org.junit.Test
    public void test2() throws IOException, InterruptedException {
//        Object o = redisUtils.get("113.fum");
//        System.out.println(o);
//        futuresService.updateFuturesData();
//        futuresService.setFiveDayTotal();
//        Object fu0 = redisUtils.get("FG0");
//        System.out.println(fu0);
       redisUtils.flushDb();
        //sinaFuturesService.getSinaData();
        //futuresService.updateFuturesData();
       // futuresService.setFiveDayTotal();
        //LinkedList<Object> objects = new LinkedList<>();


    }
    @Autowired
    private SinaFuturesService sFuturesData;
    @org.junit.Test
    public void test4() throws IOException {
        sFuturesData.sFuturesData();


    }


}
