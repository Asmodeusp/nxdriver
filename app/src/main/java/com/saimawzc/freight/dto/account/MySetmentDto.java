package com.saimawzc.freight.dto.account;

/****
 * 我的结算
 * **/
public class MySetmentDto {
    private String settleNo;
    private String id;
    private String companyName;
    private String handComName;
    private String materialsName;
    private String settleWeight;
    private String settleAmount;
    private String startTime;
    private String endTime;
    private String fromUserAddress;
    private String  toUserAddress;

    private String wayBillNo;
    private String checkStatus;
    private String cysName;
    private String settlePrice;
    private String settleTotalPrice;
    private String settlePointPrice;
    private String deductFreight;
    private String businessType;
    private String businessTypeName;
    private String hzSettleName;
    private int unsettleNum;

    public int getUnsettleNum() {
        return unsettleNum;
    }

    public void setUnsettleNum(int unsettleNum) {
        this.unsettleNum = unsettleNum;
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

    public String getSettleNo() {
        return settleNo;
    }

    public void setSettleNo(String settleNo) {
        this.settleNo = settleNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSettleWeight() {
        return settleWeight;
    }

    public void setSettleWeight(String settleWeight) {
        this.settleWeight = settleWeight;
    }

    public String getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(String settleAmount) {
        this.settleAmount = settleAmount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWayBillNo() {
        return wayBillNo;
    }

    public void setWayBillNo(String wayBillNo) {
        this.wayBillNo = wayBillNo;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getCysName() {
        return cysName;
    }

    public void setCysName(String cysName) {
        this.cysName = cysName;
    }

    public String getSettlePrice() {
        return settlePrice;
    }

    public void setSettlePrice(String settlePrice) {
        this.settlePrice = settlePrice;
    }

    public String getSettleTotalPrice() {
        return settleTotalPrice;
    }

    public void setSettleTotalPrice(String settleTotalPrice) {
        this.settleTotalPrice = settleTotalPrice;
    }

    public String getSettlePointPrice() {
        return settlePointPrice;
    }

    public void setSettlePointPrice(String settlePointPrice) {
        this.settlePointPrice = settlePointPrice;
    }

    public String getDeductFreight() {
        return deductFreight;
    }

    public void setDeductFreight(String deductFreight) {
        this.deductFreight = deductFreight;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public String getHzSettleName() {
        return hzSettleName;
    }

    public void setHzSettleName(String hzSettleName) {
        this.hzSettleName = hzSettleName;
    }
}
