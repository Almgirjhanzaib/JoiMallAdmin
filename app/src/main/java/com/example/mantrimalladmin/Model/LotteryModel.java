package com.example.mantrimalladmin.Model;

import java.util.List;

public class LotteryModel {
    String number,period,price;
    List<String> resultList;

    public LotteryModel() {
    }

    public LotteryModel(String number, String period, String price, List<String> resultList) {
        this.number = number;
        this.period = period;
        this.price = price;
        this.resultList = resultList;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getResultList() {
        return resultList;
    }

    public void setResultList(List<String> resultList) {
        this.resultList = resultList;
    }
}
