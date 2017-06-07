package com.huake.hkis.hkis.model;

/**
 * Created by ysstech on 2017/6/7.
 */

public class Check {
    private String checkNum;
    private String wareHouseNum;
    private String checkId;

    public Check(String checkNum, String wareHouseNum, String checkId) {
        this.checkNum = checkNum;
        this.wareHouseNum = wareHouseNum;
        this.checkId = checkId;
    }

    public String getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(String checkNum) {
        this.checkNum = checkNum;
    }

    public String getWareHouseNum() {
        return wareHouseNum;
    }

    public void setWareHouseNum(String wareHouseNum) {
        this.wareHouseNum = wareHouseNum;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }
}
