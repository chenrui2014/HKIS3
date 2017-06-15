package com.huake.hkis.hkis.model;

/**
 * Created by chen on 2017/6/16.
 */

public class UpgradeVersion {
    private String versionId;
    private String versionCode;
    private String appType;
    private String isForceUp;
    private String versionDesc;
    private String downloadUrl;

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getIsForceUp() {
        return isForceUp;
    }

    public void setIsForceUp(String isForceUp) {
        this.isForceUp = isForceUp;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
