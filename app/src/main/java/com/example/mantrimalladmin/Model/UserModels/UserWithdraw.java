package com.example.mantrimalladmin.Model.UserModels;

public class UserWithdraw {
    String amount,timeStamp,userNumber,userId,status,accountNumber;

    public UserWithdraw() {
    }

    public UserWithdraw(String amount, String timeStamp, String userNumber, String userId, String status, String accountNumber) {
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.userNumber = userNumber;
        this.userId = userId;
        this.status = status;
        this.accountNumber = accountNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
