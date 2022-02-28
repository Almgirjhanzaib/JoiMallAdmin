package com.example.mantrimalladmin.Model;

public class RequestModel {
    String accountNumber,amount,status,timeStamp,userId,userNumber;

    public RequestModel() {
    }

    public RequestModel(String accountNumber, String amount, String status, String timeStamp, String userId, String userNumber) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.status = status;
        this.timeStamp = timeStamp;
        this.userId = userId;
        this.userNumber = userNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }
}
