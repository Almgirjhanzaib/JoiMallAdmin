package com.example.mantrimalladmin.Model.UserModels;

public class UsersComplaints {
    String id;
    String msg;
    String status, timeStamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UsersComplaints() {
    }

    public UsersComplaints(String id, String msg, String timeStamp, String status) {
        this.id = id;
        this.msg = msg;
        this.timeStamp = timeStamp;
        this.status = status;
    }
}
