package com.huake.hkis.hkis.model;

import java.util.List;


public class User {

    private String userId;
    private String loginName;
    private String realName;
    private String insertTime;
    private String insertUser;
    private String modifyTime;
    private String modifyUser;

    public User(){}

    public User(String userId, String loginName, String realName,String insertTime, String insertUser, String modifyTime, String modifyUser) {
        this.userId = userId;
        this.loginName = loginName;
        this.realName = realName;
        this.insertTime = insertTime;
        this.insertUser = insertUser;
        this.modifyTime = modifyTime;
        this.modifyUser = modifyUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getInsertUser() {
        return insertUser;
    }

    public void setInsertUser(String insertUser) {
        this.insertUser = insertUser;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
