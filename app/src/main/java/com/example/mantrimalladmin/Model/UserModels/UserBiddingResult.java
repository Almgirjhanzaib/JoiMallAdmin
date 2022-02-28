package com.example.mantrimalladmin.Model.UserModels;

public class UserBiddingResult {
    public UserBiddingResult() {
    }

    String lotteryId,number,color,loss,win,timestamp,tradePrice;

    public UserBiddingResult(String lotteryId, String number, String color, String loss, String win, String timestamp, String tradePrice) {
        this.lotteryId = lotteryId;
        this.number = number;
        this.color = color;
        this.loss = loss;
        this.win = win;
        this.timestamp = timestamp;
        this.tradePrice = tradePrice;
    }

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
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

    public String getLoss() {
        return loss;
    }

    public void setLoss(String loss) {
        this.loss = loss;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(String tradePrice) {
        this.tradePrice = tradePrice;
    }
}
