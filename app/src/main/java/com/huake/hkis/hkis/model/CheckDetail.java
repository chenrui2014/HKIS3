package com.huake.hkis.hkis.model;

/**
 * Created by ysstech on 2017/6/7.
 */

public class CheckDetail {
    private String materialNum;
    private String checkAmount;
    private String calculateUnit;
    private String checkDetailId;

    public CheckDetail(String materialNum, String checkAmount, String calculateUnit, String checkDetailId) {
        this.materialNum = materialNum;
        this.checkAmount = checkAmount;
        this.calculateUnit = calculateUnit;
        this.checkDetailId = checkDetailId;
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

    public String getCheckDetailId() {
        return checkDetailId;
    }

    public void setCheckDetailId(String checkDetailId) {
        this.checkDetailId = checkDetailId;
    }
}
