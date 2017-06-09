package com.huake.hkis.hkis.model;

/**
 * Created by ysstech on 2017/6/7.
 */

public class Task {
    private String taskNum;
    private String wareHouseNum;
    private String userId;
    private String taskType;
    private String inDate;

    public Task(String taskNum, String wareHouseNum, String userId,String taskType,String inDate) {
        this.taskNum = taskNum;
        this.wareHouseNum = wareHouseNum;
        this.userId = userId;
        this.taskType = taskType;
        this.inDate = inDate;
    }

    public String getTaskNum() {
        return taskNum;
    }

    public String getWareHouseNum() {
        return wareHouseNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    public void setWareHouseNum(String wareHouseNum) {
        this.wareHouseNum = wareHouseNum;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }
}
