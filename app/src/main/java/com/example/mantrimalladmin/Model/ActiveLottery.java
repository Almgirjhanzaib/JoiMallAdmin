package com.example.mantrimalladmin.Model;

import java.util.List;

public class ActiveLottery {
    String currentId, lastId, winNumber;
    List<String> resultColors;
    BiddingUser biddingUsers;
    String price;

    public ActiveLottery() {
    }

    public ActiveLottery(String currentId, String lastId, String winNumber, List<String> resultColors, BiddingUser biddingUsers, String price) {
        this.currentId = currentId;
        this.lastId = lastId;
        this.winNumber = winNumber;
        this.resultColors = resultColors;
        this.biddingUsers = biddingUsers;
        this.price = price;
    }
    public ActiveLottery(String currentId, String lastId, String winNumber, List<String> resultColors, String price) {
        this.currentId = currentId;
        this.lastId = lastId;
        this.winNumber = winNumber;
        this.resultColors = resultColors;
        this.price = price;
    }

    public String getCurrentId() {
        return currentId;
    }

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public String getWinNumber() {
        return winNumber;
    }

    public void setWinNumber(String winNumber) {
        this.winNumber = winNumber;
    }

    public List<String> getResultColors() {
        return resultColors;
    }

    public void setResultColors(List<String> resultColors) {
        this.resultColors = resultColors;
    }

    public BiddingUser getBiddingUsers() {
        return biddingUsers;
    }

    public void setBiddingUsers(BiddingUser biddingUsers) {
        this.biddingUsers = biddingUsers;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
