package com.example.mantrimalladmin.Model;

import java.util.List;

public class LotteryResults {
    String period,price,number;
    List <String> resultList;
    String id;
    List<BiddingUser> winnerList;
    List<BiddingUser> lostList;

    public String getId() {
        return id;
    }

    public LotteryResults(String period, String price, String number, List <String> resultList, String id, List <BiddingUser> winnerList, List <BiddingUser> lostList) {
        this.period = period;
        this.price = price;
        this.number = number;
        this.resultList = resultList;
        this.id = id;
        this.winnerList = winnerList;
        this.lostList = lostList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List <BiddingUser> getWinnerList() {
        return winnerList;
    }

    public void setWinnerList(List <BiddingUser> winnerList) {
        this.winnerList = winnerList;
    }

    public List <BiddingUser> getLostList() {
        return lostList;
    }

    public void setLostList(List <BiddingUser> lostList) {
        this.lostList = lostList;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List <String> getResultList() {
        return resultList;
    }

    public void setResultList(List <String> resultList) {
        this.resultList = resultList;
    }

    public LotteryResults() {
    }

    public LotteryResults(String period, String price, String number, List<String> resultList) {
        this.period = period;
        this.price = price;
        this.number = number;
        this.resultList = resultList;
    }
}
