package com.huake.hkis.hkis.model;

/**
 * Created by ysstech on 2017/6/7.
 */

public class UpdateDetailParam {
    private String userId;
    private String materialNum;
    private String checkAmount;
    private String calculateUnit;

    public UpdateDetailParam(String userId, String materialNum, String checkAmount, String calculateUnit) {
        this.userId = userId;
        this.materialNum = materialNum;
        this.checkAmount = checkAmount;
        this.calculateUnit = calculateUnit;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(String materialNum) {
        this.materialNum = materialNum;
    }

    public String getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(String checkAmount) {
        this.checkAmount = checkAmount;
    }

    public String getCalculateUnit() {
        return calculateUnit;
    }

    public void setCalculateUnit(String calculateUnit) {
        this.calculateUnit = calculateUnit;
    }
}
