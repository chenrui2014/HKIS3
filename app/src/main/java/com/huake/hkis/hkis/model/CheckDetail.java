package com.huake.hkis.hkis.model;

/**
 * Created by ysstech on 2017/6/7.
 */

public class CheckDetail {
    private String checkDetailId;//盘点明细ID
    private String checkNO;//盘点单号
    private String warehouseNO;//仓库号
    private String storageSpace;//仓位
    private String materialNO;//物料号
    private String repertoryAmount;//库存量
    private String calculateUnit;//计量单位
    private String supplierNum;//供货商
    private String checkAmount;//实盘数量
    private String status;//状态

    public CheckDetail(String checkDetailId, String checkNO, String warehouseNO, String storageSpace, String materialNO, String repertoryAmount, String calculateUnit, String supplierNum, String checkAmount, String status) {
        this.checkDetailId = checkDetailId;
        this.checkNO = checkNO;
        this.warehouseNO = warehouseNO;
        this.storageSpace = storageSpace;
        this.materialNO = materialNO;
        this.repertoryAmount = repertoryAmount;
        this.calculateUnit = calculateUnit;
        this.supplierNum = supplierNum;
        this.checkAmount = checkAmount;
        this.status = status;
    }

    public String getCheckDetailId() {
        return checkDetailId;
    }

    public void setCheckDetailId(String checkDetailId) {
        this.checkDetailId = checkDetailId;
    }

    public String getCheckNO() {
        return checkNO;
    }

    public void setCheckNO(String checkNO) {
        this.checkNO = checkNO;
    }

    public String getWarehouseNO() {
        return warehouseNO;
    }

    public void setWarehouseNO(String warehouseNO) {
        this.warehouseNO = warehouseNO;
    }

    public String getStorageSpace() {
        return storageSpace;
    }

    public void setStorageSpace(String storageSpace) {
        this.storageSpace = storageSpace;
    }

    public String getMaterialNO() {
        return materialNO;
    }

    public void setMaterialNO(String materialNO) {
        this.materialNO = materialNO;
    }

    public String getRepertoryAmount() {
        return repertoryAmount;
    }

    public void setRepertoryAmount(String repertoryAmount) {
        this.repertoryAmount = repertoryAmount;
    }

    public String getCalculateUnit() {
        return calculateUnit;
    }

    public void setCalculateUnit(String calculateUnit) {
        this.calculateUnit = calculateUnit;
    }

    public String getSupplierNum() {
        return supplierNum;
    }

    public void setSupplierNum(String supplierNum) {
        this.supplierNum = supplierNum;
    }

    public String getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(String checkAmount) {
        this.checkAmount = checkAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
