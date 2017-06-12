package com.huake.hkis.hkis.model;

/**
 * Created by chen on 2017/6/12.
 */

public class AboutUs {

    private String projectName;
    private String projectInfo;
    private String webchatServer;
    private String officialSite;
    private String offocoalEmail;
    private String officialTel;

    public AboutUs(String projectName, String projectInfo, String webchatServer, String officialSite, String offocoalEmail, String officialTel) {
        this.projectName = projectName;
        this.projectInfo = projectInfo;
        this.webchatServer = webchatServer;
        this.officialSite = officialSite;
        this.offocoalEmail = offocoalEmail;
        this.officialTel = officialTel;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(String projectInfo) {
        this.projectInfo = projectInfo;
    }

    public String getWebchatServer() {
        return webchatServer;
    }

    public void setWebchatServer(String webchatServer) {
        this.webchatServer = webchatServer;
    }

    public String getOfficialSite() {
        return officialSite;
    }

    public void setOfficialSite(String officialSite) {
        this.officialSite = officialSite;
    }

    public String getOffocoalEmail() {
        return offocoalEmail;
    }

    public void setOffocoalEmail(String offocoalEmail) {
        this.offocoalEmail = offocoalEmail;
    }

    public String getOfficialTel() {
        return officialTel;
    }

    public void setOfficialTel(String officialTel) {
        this.officialTel = officialTel;
    }
}
