package com.huake.hkis.hkis.model;

/**
 * Created by ysstech on 2017/6/7.
 */

public class Check {
    private String warehouseNO;//仓库号
    private String checkNO;//盘点单号
    private String rowProject;//行项目
    private String checkTime;//计划盘点时间
    private String checkUser;//盘点人
    private String superviseUser;//监盘人
    private String auditUser;//审核人
    private String status;//状态（是否结算）
    private String checkType;//盘点类型1.明盘，2.暗盘

    public Check(String warehouseNO, String checkNO, String rowProject, String checkTime, String checkUser, String superviseUser, String auditUser, String status, String checkType) {
        this.warehouseNO = warehouseNO;
        this.checkNO = checkNO;
        this.rowProject = rowProject;
        this.checkTime = checkTime;
        this.checkUser = checkUser;
        this.superviseUser = superviseUser;
        this.auditUser = auditUser;
        this.status = status;
        this.checkType = checkType;
    }

    public String getWarehouseNO() {
        return warehouseNO;
    }

    public void setWarehouseNO(String warehouseNO) {
        this.warehouseNO = warehouseNO;
    }

    public String getCheckNO() {
        return checkNO;
    }

    public void setCheckNO(String checkNO) {
        this.checkNO = checkNO;
    }

    public String getRowProject() {
        return rowProject;
    }

    public void setRowProject(String rowProject) {
        this.rowProject = rowProject;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    public String getSuperviseUser() {
        return superviseUser;
    }

    public void setSuperviseUser(String superviseUser) {
        this.superviseUser = superviseUser;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }
}
