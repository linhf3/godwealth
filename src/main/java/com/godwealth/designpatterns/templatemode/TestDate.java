package com.godwealth.designpatterns.templatemode;

public class TestDate {
    public static void main(String[] args) {
        // 穷男孩约会
        Date date = new PoorBoy();
        date.dateTemplate();

        // 富男孩约会
        date = new RichBoy();
        date.dateTemplate();
    }
}
