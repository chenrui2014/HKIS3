package com.huake.hkis.hkis.model;

/**
 * Created by ysstech on 2017/6/7.
 */

public class MaterialDetails {
    public String taskNum;
    public String materialNum;
    public String materialDesc;
    public String calculateUnit;
    public String repertoryAmount;

    public MaterialDetails(String taskNum, String materialNum, String materialDesc, String calculateUnit, String repertoryAmount) {
        this.taskNum = taskNum;
        this.materialNum = materialNum;
        this.materialDesc = materialDesc;
        this.calculateUnit = calculateUnit;
        this.repertoryAmount = repertoryAmount;
    }

    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
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

    public String getCalculateUnit() {
        return calculateUnit;
    }

    public void setCalculateUnit(String calculateUnit) {
        this.calculateUnit = calculateUnit;
    }

    public String getRepertoryAmount() {
        return repertoryAmount;
    }

    public void setRepertoryAmount(String repertoryAmount) {
        this.repertoryAmount = repertoryAmount;
    }
}
