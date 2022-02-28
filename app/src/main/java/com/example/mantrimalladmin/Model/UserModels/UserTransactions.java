package com.example.mantrimalladmin.Model.UserModels;

public class UserTransactions {
    String transactionid,timeStamp,price,bank_number,bank_name;

    public UserTransactions() {
    }

    public UserTransactions(String transactionid, String timeStamp, String price, String bank_number, String bank_name) {
        this.transactionid = transactionid;
        this.timeStamp = timeStamp;
        this.price = price;
        this.bank_number = bank_number;
        this.bank_name = bank_name;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBank_number() {
        return bank_number;
    }

    public void setBank_number(String bank_number) {
        this.bank_number = bank_number;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }
}
