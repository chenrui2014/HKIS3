package com.huake.hkis.hkis.model;

import java.io.Serializable;

/**
 * Created by ysstech on 2017/6/7.
 */

public class ShelvesDetail implements Serializable {

    public String materBatch;
    public String taskDetailId;
    public String taskNO;
    public String wareHouseNum;
    public String materialNO;
    public String materialCode;
    public String foxAmount;
    public String supplierNum;
    public String storageSpaceCode;
    public String amount;
    public String recommendAmount;
    public String handleStatus;
    public String recommendStorageSpace;

    public String calculateUnit;
    public String finishAmount;
    public String surplusAmount;
    public String materialDesc;

    public ShelvesDetail(String materBatch,String taskDetailId,String taskNO, String wareHouseNum, String materialNO,String materialCode, String foxAmount, String supplierNum, String storageSpaceCode, String amount, String recommendAmount, String handleStatus, String recommendStorageSpace,String finishAmount,String surplusAmount,String materialDesc,String calculateUnit) {

        this.materBatch = materBatch;
        this.taskDetailId = taskDetailId;
        this.taskNO = taskNO;
        this.wareHouseNum = wareHouseNum;
        this.materialNO = materialNO;
        this.materialCode = materialCode;
        this.foxAmount = foxAmount;
        this.supplierNum = supplierNum;
        this.storageSpaceCode = storageSpaceCode;
        this.amount = amount;
        this.recommendAmount = recommendAmount;
        this.handleStatus = handleStatus;
        this.recommendStorageSpace = recommendStorageSpace;
        this.finishAmount = finishAmount;
        this.surplusAmount = surplusAmount;
        this.materialDesc = materialDesc;
        this.calculateUnit = calculateUnit;
    }

    public String getTaskNO() {
        return taskNO;
    }

    public void setTaskNO(String taskNO) {
        this.taskNO = taskNO;
    }

    public String getWareHouseNum() {
        return wareHouseNum;
    }

    public void setWareHouseNum(String wareHouseNum) {
        this.wareHouseNum = wareHouseNum;
    }

    public String getMaterialNO() {
        return materialNO;
    }

    public void setMaterialNO(String materialNO) {
        this.materialNO = materialNO;
    }

    public String getFoxAmount() {
        return foxAmount;
    }

    public void setFoxAmount(String foxAmount) {
        this.foxAmount = foxAmount;
    }

    public String getSupplierNum() {
        return supplierNum;
    }

    public void setSupplierNum(String supplierNum) {
        this.supplierNum = supplierNum;
    }

    public String getStorageSpaceCode() {
        return storageSpaceCode;
    }

    public void setStorageSpaceCode(String storageSpaceCode) {
        this.storageSpaceCode = storageSpaceCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRecommendAmount() {
        return recommendAmount;
    }

    public void setRecommendAmount(String recommendAmount) {
        this.recommendAmount = recommendAmount;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getRecommendStorageSpace() {
        return recommendStorageSpace;
    }

    public void setRecommendStorageSpace(String recommendStorageSpace) {
        this.recommendStorageSpace = recommendStorageSpace;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getFinishAmount() {
        return finishAmount;
    }

    public void setFinishAmount(String finishAmount) {
        this.finishAmount = finishAmount;
    }

    public String getSurplusAmount() {
        return surplusAmount;
    }

    public void setSurplusAmount(String surplusAmount) {
        this.surplusAmount = surplusAmount;
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

    public String getTaskDetailId() {
        return taskDetailId;
    }

    public void setTaskDetailId(String taskDetailId) {
        this.taskDetailId = taskDetailId;
    }

    public String getMaterBatch() {
        return materBatch;
    }

    public void setMaterBatch(String materBatch) {
        this.materBatch = materBatch;
    }
}
