package com.godwealth.service;

import com.godwealth.entity.ProportionLog;
import com.godwealth.entity.StockCode;
import com.godwealth.entity.StockLog;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

/**
 * 股票接口
 * @author sie_linhongfei
 * @createDate 2022/07/09 10:27
 */
@Service
public interface StockService {

    List<Map<String,Object>> stockData() throws GeneralSecurityException, MessagingException;

    void insert(String code);

    void delete(String code);

    List<StockCode> query();

    int insertSelective(StockCode stockCode);

    List<StockCode> querySockCodeList();

    int updateById(StockCode stockCode);

}