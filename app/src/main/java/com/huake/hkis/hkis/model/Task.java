package com.huake.hkis.hkis.model;

/**
 * Created by ysstech on 2017/6/7.
 */

public class Task {

    private String taskId;
    private String taskNO;

    private String resourceStorageSpace;
    private String targetStorageSpace;
    private String resourceStorageSpaceCode;
    private String targetStorageSpaceCode;
    private String wareHouseNum;
    private String clientName;
    private String documentsType;
    private String contractNum;
    private String rowProject;
    private String taskType;
    private String handleUser;
    private String shipmentsTime;
    private String inStorageTime;
    private String logisticsType;
    private String referenceNo;

    public Task(String taskId, String taskNO, String resourceStorageSpace, String targetStorageSpace, String resourceStorageSpaceCode, String targetStorageSpaceCode, String clientName, String documentsType,String contractNum, String rowProject, String taskType, String handleUser, String shipmentsTime, String inStorageTime, String logisticsType,String referenceNo,String wareHouseNum) {
        this.taskId = taskId;
        this.taskNO = taskNO;
        this.resourceStorageSpace = resourceStorageSpace;
        this.targetStorageSpace = targetStorageSpace;
        this.resourceStorageSpaceCode = resourceStorageSpaceCode;
        this.targetStorageSpaceCode = targetStorageSpaceCode;
        this.clientName = clientName;
        this.documentsType = documentsType;
        this.contractNum = contractNum;
        this.rowProject = rowProject;
        this.taskType = taskType;
        this.handleUser = handleUser;
        this.shipmentsTime = shipmentsTime;
        this.inStorageTime = inStorageTime;
        this.logisticsType = logisticsType;
        this.referenceNo = referenceNo;
        this.wareHouseNum = wareHouseNum;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskNO() {
        return taskNO;
    }

    public void setTaskNO(String taskNO) {
        this.taskNO = taskNO;
    }

    public String getResourceStorageSpace() {
        return resourceStorageSpace;
    }

    public void setResourceStorageSpace(String resourceStorageSpace) {
        this.resourceStorageSpace = resourceStorageSpace;
    }

    public String getTargetStorageSpace() {
        return targetStorageSpace;
    }

    public void setTargetStorageSpace(String targetStorageSpace) {
        this.targetStorageSpace = targetStorageSpace;
    }

    public String getResourceStorageSpaceCode() {
        return resourceStorageSpaceCode;
    }

    public void setResourceStorageSpaceCode(String resourceStorageSpaceCode) {
        this.resourceStorageSpaceCode = resourceStorageSpaceCode;
    }

    public String getTargetStorageSpaceCode() {
        return targetStorageSpaceCode;
    }

    public void setTargetStorageSpaceCode(String targetStorageSpaceCode) {
        this.targetStorageSpaceCode = targetStorageSpaceCode;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getRowProject() {
        return rowProject;
    }

    public void setRowProject(String rowProject) {
        this.rowProject = rowProject;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(String handleUser) {
        this.handleUser = handleUser;
    }

    public String getShipmentsTime() {
        return shipmentsTime;
    }

    public void setShipmentsTime(String shipmentsTime) {
        this.shipmentsTime = shipmentsTime;
    }

    public String getInStorageTime() {
        return inStorageTime;
    }

    public void setInStorageTime(String inStorageTime) {
        this.inStorageTime = inStorageTime;
    }

    public String getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(String logisticsType) {
        this.logisticsType = logisticsType;
    }

    public String getDocumentsType() {
        return documentsType;
    }

    public void setDocumentsType(String documentsType) {
        this.documentsType = documentsType;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getWareHouseNum() {
        return wareHouseNum;
    }

    public void setWareHouseNum(String wareHouseNum) {
        this.wareHouseNum = wareHouseNum;
    }
}
