package com.huake.hkis.hkis.model;

/**
 * Created by ysstech on 2017/6/7.
 */

public class MaterialDetails {
    public String wareHouseNum;//仓库号
    public String resourceStorageSpace;//原仓位
    public String storageSpace;//仓位
    public String supplierNum;//供货商
    public String storageType;//存储类型
    public String storageArea;//存储区
    public String storageSpaceCode;//仓位条码
    public String inStorageTime;//入库日期
    public String repertoryStatus;//库存状态
    public String materialNum;//物料号
    public String materialDesc;//物料描述
    public String calculateUnit;//计量单位
    public String repertoryAmount;//库存量
    public String materBatch;//批次

    public MaterialDetails(String materBatch,String wareHouseNum, String resourceStorageSpace, String storageSpace, String supplierNum, String storageType, String storageArea, String storageSpaceCode, String inStorageTime, String repertoryStatus, String materialNum, String materialDesc, String calculateUnit, String repertoryAmount) {
       this.materBatch = materBatch;
        this.wareHouseNum = wareHouseNum;
        this.resourceStorageSpace = resourceStorageSpace;
        this.storageSpace = storageSpace;
        this.supplierNum = supplierNum;
        this.storageType = storageType;
        this.storageArea = storageArea;
        this.storageSpaceCode = storageSpaceCode;
        this.inStorageTime = inStorageTime;
        this.repertoryStatus = repertoryStatus;
        this.materialNum = materialNum;
        this.materialDesc = materialDesc;
        this.calculateUnit = calculateUnit;
        this.repertoryAmount = repertoryAmount;
    }

    public String getWareHouseNum() {
        return wareHouseNum;
    }

    public void setWareHouseNum(String wareHouseNum) {
        this.wareHouseNum = wareHouseNum;
    }

    public String getResourceStorageSpace() {
        return resourceStorageSpace;
    }

    public void setResourceStorageSpace(String resourceStorageSpace) {
        this.resourceStorageSpace = resourceStorageSpace;
    }

    public String getStorageSpace() {
        return storageSpace;
    }

    public void setStorageSpace(String storageSpace) {
        this.storageSpace = storageSpace;
    }

    public String getSupplierNum() {
        return supplierNum;
    }

    public void setSupplierNum(String supplierNum) {
        this.supplierNum = supplierNum;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getStorageArea() {
        return storageArea;
    }

    public void setStorageArea(String storageArea) {
        this.storageArea = storageArea;
    }

    public String getStorageSpaceCode() {
        return storageSpaceCode;
    }

    public void setStorageSpaceCode(String storageSpaceCode) {
        this.storageSpaceCode = storageSpaceCode;
    }

    public String getInStorageTime() {
        return inStorageTime;
    }

    public void setInStorageTime(String inStorageTime) {
        this.inStorageTime = inStorageTime;
    }

    public String getRepertoryStatus() {
        return repertoryStatus;
    }

    public void setRepertoryStatus(String repertoryStatus) {
        this.repertoryStatus = repertoryStatus;
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

    public String getMaterBatch() {
        return materBatch;
    }

    public void setMaterBatch(String materBatch) {
        this.materBatch = materBatch;
    }
}
