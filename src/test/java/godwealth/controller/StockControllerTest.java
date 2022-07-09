package godwealth.controller;

import com.godwealth.GodWealthApplication;
import com.godwealth.dao.StockCodeMapper;
import com.godwealth.entity.StockCode;
import com.godwealth.service.FuturesService;
import com.godwealth.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;


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
    @Test
    public void test1() throws Exception{
        StockCode stockCode = stockCodeMapper.selectByPrimaryKey(1013);
        log.debug("对象信息：{}",stockCode);
        List<Map<String, Object>> maps = futuresService.futuresData();
        log.debug("对象信息：{}",maps);
    }

    @Test
    public void test2() throws Exception{
        List<Map<String, Object>> maps = stockService.stockData();
        log.debug("对象信息：{}",maps);
    }


}
