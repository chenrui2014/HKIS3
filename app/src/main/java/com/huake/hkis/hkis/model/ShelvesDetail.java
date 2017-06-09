package com.huake.hkis.hkis.model;

import java.io.Serializable;

/**
 * Created by ysstech on 2017/6/7.
 */

public class ShelvesDetail implements Serializable {
    public String taskNum;
    public String wareHouseNum;
    public String materialNum;
    public String materialDesc;
    public int amount;
    public String sysRecommendWarehouse;

    public ShelvesDetail(String taskNum, String wareHouseNum, String materialNum, String materialDesc, int amount, String sysRecommendWarehouse) {
        this.taskNum = taskNum;
        this.wareHouseNum = wareHouseNum;
        this.materialNum = materialNum;
        this.materialDesc = materialDesc;
        this.amount = amount;
        this.sysRecommendWarehouse = sysRecommendWarehouse;
    }

    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    public String getWareHouseNum() {
        return wareHouseNum;
    }

    public void setWareHouseNum(String wareHouseNum) {
        this.wareHouseNum = wareHouseNum;
    }

    public String getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(String materialNum) {
        this.materialNum = materialNum;
    }

    public String getMaterialDesc() {
        return materialDesc;
    }

    public void setMaterialDesc(String materialDesc) {
        this.materialDesc = materialDesc;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSysRecommendWarehouse() {
        return sysRecommendWarehouse;
    }

    public void setSysRecommendWarehouse(String sysRecommendWarehouse) {
        this.sysRecommendWarehouse = sysRecommendWarehouse;
    }
}
