package com.example.mantrimalladmin.Model;

public class LotteryWinners {
    String name,amount,userId;

    public LotteryWinners() {
    }

    public LotteryWinners(String name, String amount, String userId) {
        this.name = name;
        this.amount = amount;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
