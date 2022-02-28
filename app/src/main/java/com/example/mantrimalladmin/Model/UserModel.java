package com.example.mantrimalladmin.Model;

import com.example.mantrimalladmin.Model.UserModels.UserBiddingResult;
import com.example.mantrimalladmin.Model.UserModels.UserTransactions;
import com.example.mantrimalladmin.Model.UserModels.UserWithdraw;
import com.example.mantrimalladmin.Model.UserModels.UsersComplaints;

public class UserModel {
    String id, name, password, phoneNumber;
    String balance;
    UserTransactions transactionsList;
    UserBiddingResult biddingResults;
    UsersComplaints usersComplaints;
    UserWithdraw userWithdraws;

    public UserModel() {
    }

    public UserModel(String id, String name, String password, String phoneNumber, String balance, UserTransactions transactionsList, UserBiddingResult biddingResults, UsersComplaints usersComplaints, UserWithdraw userWithdraws) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.transactionsList = transactionsList;
        this.biddingResults = biddingResults;
        this.usersComplaints = usersComplaints;
        this.userWithdraws = userWithdraws;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public UserTransactions getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(UserTransactions transactionsList) {
        this.transactionsList = transactionsList;
    }

    public UserBiddingResult getBiddingResults() {
        return biddingResults;
    }

    public void setBiddingResults(UserBiddingResult biddingResults) {
        this.biddingResults = biddingResults;
    }

    public UsersComplaints getUsersComplaints() {
        return usersComplaints;
    }

    public void setUsersComplaints(UsersComplaints usersComplaints) {
        this.usersComplaints = usersComplaints;
    }

    public UserWithdraw getUserWithdraws() {
        return userWithdraws;
    }

    public void setUserWithdraws(UserWithdraw userWithdraws) {
        this.userWithdraws = userWithdraws;
    }
}
