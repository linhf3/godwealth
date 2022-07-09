package com.godwealth.designpatterns.templatemode;

import org.springframework.stereotype.Service;

/**
 * 富男孩的约会流程
 */
@Service
public class RichBoy extends Date{
    // 重写搞钱方法
    @Override
    public void money() {
        System.out.println("打电话给老爹，让其打钱");
    }

    // 重写约会地点方法
    @Override
    public void site() {
        System.out.println("去高档餐厅吃西餐");
    }

}
