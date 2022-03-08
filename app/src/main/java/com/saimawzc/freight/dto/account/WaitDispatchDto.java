package com.saimawzc.freight.dto.account;

/**
 * 待结算派车单
 * **/
public class WaitDispatchDto {
    private String id;
    private String wayBillNo;
    private String carNo;
    private String unsettleWeight;
    private String unsettlePrice;
    private String pointPrice;
    private String pointTotalPrice;
    private String signWeight;
    private String wayBillId;

    public String getWayBillId() {
        return wayBillId;
    }

    public void setWayBillId(String wayBillId) {
        this.wayBillId = wayBillId;
    }

    private String weighing;
    private String signType;
    private String signTypeName;
    private String signTime;
    private String companyName;
    private String handComName;

    private String materialsName;
    private String totalWeight;
    private String cysName;
    private String dispatchCarNo;

    private String sjName;
    private String distance;
    private String fromUserAddress;
    private String toUserAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWayBillNo() {
        return wayBillNo;
    }

    public void setWayBillNo(String wayBillNo) {
        this.wayBillNo = wayBillNo;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getUnsettleWeight() {
        return unsettleWeight;
    }

    public void setUnsettleWeight(String unsettleWeight) {
        this.unsettleWeight = unsettleWeight;
    }

    public String getUnsettlePrice() {
        return unsettlePrice;
    }

    public void setUnsettlePrice(String unsettlePrice) {
        this.unsettlePrice = unsettlePrice;
    }

    public String getPointPrice() {
        return pointPrice;
    }

    public void setPointPrice(String pointPrice) {
        this.pointPrice = pointPrice;
    }

    public String getPointTotalPrice() {
        return pointTotalPrice;
    }

    public void setPointTotalPrice(String pointTotalPrice) {
        this.pointTotalPrice = pointTotalPrice;
    }

    public String getSignWeight() {
        return signWeight;
    }

    public void setSignWeight(String signWeight) {
        this.signWeight = signWeight;
    }

    public String getWeighing() {
        return weighing;
    }

    public void setWeighing(String weighing) {
        this.weighing = weighing;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignTypeName() {
        return signTypeName;
    }

    public void setSignTypeName(String signTypeName) {
        this.signTypeName = signTypeName;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getHandComName() {
        return handComName;
    }

    public void setHandComName(String handComName) {
        this.handComName = handComName;
    }

    public String getMaterialsName() {
        return materialsName;
    }

    public void setMaterialsName(String materialsName) {
        this.materialsName = materialsName;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getCysName() {
        return cysName;
    }

    public void setCysName(String cysName) {
        this.cysName = cysName;
    }

    public String getDispatchCarNo() {
        return dispatchCarNo;
    }

    public void setDispatchCarNo(String dispatchCarNo) {
        this.dispatchCarNo = dispatchCarNo;
    }

    public String getSjName() {
        return sjName;
    }

    public void setSjName(String sjName) {
        this.sjName = sjName;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getFromUserAddress() {
        return fromUserAddress;
    }

    public void setFromUserAddress(String fromUserAddress) {
        this.fromUserAddress = fromUserAddress;
    }

    public String getToUserAddress() {
        return toUserAddress;
    }

    public void setToUserAddress(String toUserAddress) {
        this.toUserAddress = toUserAddress;
    }
}
