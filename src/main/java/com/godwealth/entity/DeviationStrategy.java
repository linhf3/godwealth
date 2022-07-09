package com.godwealth.entity;

import java.io.Serializable;
import java.util.Date;

public class DeviationStrategy implements Serializable {
    private Integer id;

    private Double positiveDeviation;

    private Double negativeDeviation;

    private String addUser;

    private Date addDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPositiveDeviation() {
        return positiveDeviation;
    }

    public void setPositiveDeviation(Double positiveDeviation) {
        this.positiveDeviation = positiveDeviation;
    }

    public Double getNegativeDeviation() {
        return negativeDeviation;
    }

    public void setNegativeDeviation(Double negativeDeviation) {
        this.negativeDeviation = negativeDeviation;
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
}