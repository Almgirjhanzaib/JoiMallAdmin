package com.example.mantrimalladmin.Model;

import java.util.List;

public class BiddingUser {
    String userId;
    String name;
    String number;
    String color;
    String tradePrice;

    public String getUserId() {
        return userId;
    }

    public BiddingUser() {
    }

    public BiddingUser(String userId, String name, String number, String color, String tradePrice) {
        this.userId = userId;
        this.name = name;
        this.number = number;
        this.color = color;
        this.tradePrice = tradePrice;
    }

    public String getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(String tradePrice) {
        this.tradePrice = tradePrice;
    }



    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
