package com.huake.hkis.hkis.model;

import java.util.List;


public class User {

    private String userId;
    private String loginName;
    private String realName;
    private String sex;
    private String telPhone;
    private String email;

    public User(String userId, String loginName, String realName, String sex, String telPhone, String email) {
        this.userId = userId;
        this.loginName = loginName;
        this.realName = realName;
        this.sex = sex;
        this.telPhone = telPhone;
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
