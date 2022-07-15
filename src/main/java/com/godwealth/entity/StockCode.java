package com.godwealth.entity;

import java.io.Serializable;
import java.util.Date;

public class StockCode implements Serializable {
    private Integer id;

    private String name;

    private String stockCode;

    private String category;

    private String swEffective;

    private String memo;

    private String exchangeCode;

    private String addUser;

    private Date addDate;

    private Integer addUserId;

    private Integer downwardDeviation;

    private Integer deviation;

    public Integer getDownwardDeviation() {
        return downwardDeviation;
    }

    public void setDownwardDeviation(Integer downwardDeviation) {
        this.downwardDeviation = downwardDeviation;
    }

    public Integer getDeviation() {
        return deviation;
    }

    public void setDeviation(Integer deviation) {
        this.deviation = deviation;
    }



    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode == null ? null : stockCode.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getSwEffective() {
        return swEffective;
    }

    public void setSwEffective(String swEffective) {
        this.swEffective = swEffective == null ? null : swEffective.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode == null ? null : exchangeCode.trim();
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser == null ? null : addUser.trim();
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Integer getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Integer addUserId) {
        this.addUserId = addUserId;
    }
}