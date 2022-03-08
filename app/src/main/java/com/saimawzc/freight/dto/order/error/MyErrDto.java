package com.saimawzc.freight.dto.order.error;

public class MyErrDto {
    private String id;
    private String tmsWaybillId;
    private String omsWaybillId;
    private String exceptionTypeId;
    private String typeName;
    private String exceptionName;
    private String exceptionImage;
    private String location;
    private String createTime;
    private int exceStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTmsWaybillId() {
        return tmsWaybillId;
    }

    public void setTmsWaybillId(String tmsWaybillId) {
        this.tmsWaybillId = tmsWaybillId;
    }

    public String getOmsWaybillId() {
        return omsWaybillId;
    }

    public void setOmsWaybillId(String omsWaybillId) {
        this.omsWaybillId = omsWaybillId;
    }

    public String getExceptionTypeId() {
        return exceptionTypeId;
    }

    public void setExceptionTypeId(String exceptionTypeId) {
        this.exceptionTypeId = exceptionTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getExceptionImage() {
        return exceptionImage;
    }

    public void setExceptionImage(String exceptionImage) {
        this.exceptionImage = exceptionImage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getExceStatus() {
        return exceStatus;
    }

    public void setExceStatus(int exceStatus) {
        this.exceStatus = exceStatus;
    }
}
