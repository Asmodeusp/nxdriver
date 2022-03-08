package com.saimawzc.freight.dto;

public class VersonDto {

    private int appSource;
    private  int appType;
    private String downloadLink;
    private String id;
    private int mandatoryUpdate;
    private String updateContent;
    private String versionNum;
    private int isSHowNo;

    public int getIsSHowNo() {
        return isSHowNo;
    }

    public void setIsSHowNo(int isSHowNo) {
        this.isSHowNo = isSHowNo;
    }
    public int getAppSource() {
        return appSource;
    }

    public void setAppSource(int appSource) {
        this.appSource = appSource;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMandatoryUpdate() {
        return mandatoryUpdate;
    }

    public void setMandatoryUpdate(int mandatoryUpdate) {
        this.mandatoryUpdate = mandatoryUpdate;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }
}
