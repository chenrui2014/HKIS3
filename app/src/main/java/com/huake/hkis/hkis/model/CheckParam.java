package com.huake.hkis.hkis.model;

/**
 * Created by ysstech on 2017/6/7.
 */

public class CheckParam {
    private String checkUser;
    private String checkType;
    private String warehouseNum;
    private String checkNO;

    public CheckParam(String checkUser, String checkType, String warehouseNum, String checkNO) {
        this.checkUser = checkUser;
        this.checkType = checkType;
        this.warehouseNum = warehouseNum;
        this.checkNO = checkNO;
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

    public String getCheckNO() {
        return checkNO;
    }

    public void setCheckNO(String checkNO) {
        this.checkNO = checkNO;
    }
}
