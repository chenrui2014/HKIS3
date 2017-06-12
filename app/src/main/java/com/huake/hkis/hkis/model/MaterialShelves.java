package com.huake.hkis.hkis.model;

/**
 * Created by ysstech on 2017/6/7.
 */

public class MaterialShelves {

    public String userId;
    public String taskType;
    public String materialNO;
    public String materialDesc;
    public String barcode;
    public String amount;
    public String calculateUnit;
    public String sysRecommendWarehouse;
    public String storageSpace;
    public String recommendAmount;

    public MaterialShelves(String userId, String taskType, String materialNO, String materialDesc, String barcode, String amount, String calculateUnit, String sysRecommendWarehouse, String storageSpace, String recommendAmount) {
        this.userId = userId;
        this.taskType = taskType;
        this.materialNO = materialNO;
        this.materialDesc = materialDesc;
        this.barcode = barcode;
        this.amount = amount;
        this.calculateUnit = calculateUnit;
        this.sysRecommendWarehouse = sysRecommendWarehouse;
        this.storageSpace = storageSpace;
        this.recommendAmount = recommendAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getMaterialNO() {
        return materialNO;
    }

    public void setMaterialNO(String materialNO) {
        this.materialNO = materialNO;
    }

    public String getMaterialDesc() {
        return materialDesc;
    }

    public void setMaterialDesc(String materialDesc) {
        this.materialDesc = materialDesc;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCalculateUnit() {
        return calculateUnit;
    }

    public void setCalculateUnit(String calculateUnit) {
        this.calculateUnit = calculateUnit;
    }

    public String getSysRecommendWarehouse() {
        return sysRecommendWarehouse;
    }

    public void setSysRecommendWarehouse(String sysRecommendWarehouse) {
        this.sysRecommendWarehouse = sysRecommendWarehouse;
    }

    public String getStorageSpace() {
        return storageSpace;
    }

    public void setStorageSpace(String storageSpace) {
        this.storageSpace = storageSpace;
    }

    public String getRecommendAmount() {
        return recommendAmount;
    }

    public void setRecommendAmount(String recommendAmount) {
        this.recommendAmount = recommendAmount;
    }
}
