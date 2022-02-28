package com.example.mantrimalladmin.Model;

public class ComplaintModel {
    String msg,status,userId,timeStamp;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ComplaintModel() {
    }

    public ComplaintModel(String msg, String status, String userId, String timeStamp) {
        this.msg = msg;
        this.status = status;
        this.userId = userId;
        this.timeStamp = timeStamp;
    }
}
