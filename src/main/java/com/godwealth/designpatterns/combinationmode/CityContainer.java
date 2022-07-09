package com.godwealth.designpatterns.combinationmode;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市容器
 */
public class CityContainer implements  Container{
    // 城市集合
    private List<Container> containerList = new ArrayList<>();

    // 添加城市
    public void addCity(Container container) {
        containerList.add(container);
    }

    // 删除城市
    public void deleteCity(Container container) {
        containerList.remove(container);
    }

    @Override
    public int count() {
        int sumPerson = 0;
        for (Container c: containerList) {
            sumPerson += c.count();
        }

        return sumPerson;
    }

    // 查询城市集和
    public List<Container> getContainerList() {
        return containerList;
    }
}
