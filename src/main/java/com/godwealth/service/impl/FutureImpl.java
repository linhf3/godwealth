package com.godwealth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.Callable;

@Service
@Slf4j
public class FutureImpl implements Callable<Map<String,Object>> {


    @Override
    public Map<String, Object> call() throws Exception {
        return null;
    }
}
