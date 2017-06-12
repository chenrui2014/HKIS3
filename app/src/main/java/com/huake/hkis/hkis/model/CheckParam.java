package com.huake.hkis.hkis.model;

/**
 * Created by ysstech on 2017/6/7.
 */

public class CheckParam {
    private String checkUser;
    private String checkType;
    private String warehouseNum;
    private String checkNum;

    public CheckParam(String checkUser, String checkType, String warehouseNum, String checkNum) {
        this.checkUser = checkUser;
        this.checkType = checkType;
        this.warehouseNum = warehouseNum;
        this.checkNum = checkNum;
    }

    public String getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getWarehouseNum() {
        return warehouseNum;
    }

    public void setWarehouseNum(String warehouseNum) {
        this.warehouseNum = warehouseNum;
    }

    public String getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(String checkNum) {
        this.checkNum = checkNum;
    }
}
