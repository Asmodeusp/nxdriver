package com.saimawzc.freight.dto.order;

public class CancelOrderDto {
    private String id;
    private String waybillNo;
    private String sjName;
    private String carNo;
    private String cancelOrderReason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getSjName() {
        return sjName;
    }

    public void setSjName(String sjName) {
        this.sjName = sjName;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCancelOrderReason() {
        return cancelOrderReason;
    }

    public void setCancelOrderReason(String cancelOrderReason) {
        this.cancelOrderReason = cancelOrderReason;
    }
}
