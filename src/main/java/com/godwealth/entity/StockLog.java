package com.godwealth.entity;

import java.io.Serializable;
import java.util.Date;

public class StockLog implements Serializable {
    private Integer id;

    private String stockCode;

    private String swEffective;

    private Date createDate;

    private String price;

    private String type;

    private Date buySaleDate;

    private String difference;

    private String category;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode == null ? null : stockCode.trim();
    }

    public String getSwEffective() {
        return swEffective;
    }

    public void setSwEffective(String swEffective) {
        this.swEffective = swEffective == null ? null : swEffective.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getBuySaleDate() {
        return buySaleDate;
    }

    public void setBuySaleDate(Date buySaleDate) {
        this.buySaleDate = buySaleDate;
    }

    public String getDifference() {
        return difference;
    }

    public void setDifference(String difference) {
        this.difference = difference == null ? null : difference.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }
}