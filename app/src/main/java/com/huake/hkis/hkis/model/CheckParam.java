package com.huake.hkis.hkis.model;

/**
 * Created by ysstech on 2017/6/7.
 */

public class CheckParam {
    private String userId;
    private String checkType;
    private String warehouseNum;
    private String checkNum;

    public CheckParam(String userId, String checkType, String warehouseNum, String checkNum) {
        this.userId = userId;
        this.checkType = checkType;
        this.warehouseNum = warehouseNum;
        this.checkNum = checkNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
