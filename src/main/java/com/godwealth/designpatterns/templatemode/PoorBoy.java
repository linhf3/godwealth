package com.godwealth.designpatterns.templatemode;

import org.springframework.stereotype.Service;

/**
 * 穷男孩的约会方式流程
 */
@Service
public class PoorBoy extends Date{

    // 重写搞钱方法
    @Override
    public void money() {
        System.out.println("找好兄弟东拼西凑");
    }
    // 重写约会地点方法
    @Override
    public void site() {
        System.out.println("带女朋友吃烧烤");
    }

}
