package com.godwealth.designpatterns.combinationmode;

public class Test {

    public static void main(String[] args) {
        // 中国
        CityContainer china = new CityContainer();

        // 上海
        Container shangHai = new City(10);
        // 山东
        CityContainer shanDong = new CityContainer();

        // 青岛
        Container qingDao = new City(20);
        // 烟台
        Container yanTai = new City(30);
        // 为山东添加城市
        shanDong.addCity(qingDao);
        shanDong.addCity(yanTai);

        // 为中国添加城市
        china.addCity(shangHai);
        china.addCity(shanDong);
        System.out.println("中国人口" + china.count());

    }
}
