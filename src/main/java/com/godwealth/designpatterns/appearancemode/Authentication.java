package com.godwealth.designpatterns.appearancemode;

/**
 * 外观模式就是提供一个统一的接口，用来访问子系统的一群接口。外观模式定义了一个高层接口，
 * 让子系统更容易使用。，外观模式也称门面模式，是一种对象结构型设计模式。
 * 原文链接：https://blog.csdn.net/Jiang_bug/article/details/118115192
 * 认证类
 * 专门认证
 */
class Authentication {
    public void getAuthentication(){
        System.out.println("证明我是我!");
    }
}

/**
 * 盖章类
 * 专门盖章
 */
class Seal {
    public void getSeal() {
        System.out.println("证明完了盖章走人!");
    }
}

/**
 * 制证类
 * 制作证件
 */
class Accreditation {
    public void getAccreditation() {
        System.out.println("制作证件，发给办证人");
    }
}

/**
 * 服务类
 * 专门服务
 */
class ServiceImpl {
    // 认证类
    private Authentication authentication = new Authentication();
    // 盖章类
    private Seal seal = new Seal();
    // 制证类
    private Accreditation accreditation = new Accreditation();

    // 服务方法
    public void getServiceImpl() {
        authentication.getAuthentication();
        seal.getSeal();
        accreditation.getAccreditation();
    }
}

// 客户制证类
class Controller{
    public static void main(String[] args) {
        ServiceImpl service = new ServiceImpl();
        service.getServiceImpl();
    }
}


