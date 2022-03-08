package com.saimawzc.freight.dto.my.car.ship;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/8/4.
 * 搜索车辆
 */

public class SearchShipDto implements Serializable {
    private int allowAdd;
    private String businessName;
    private String drivingLicense;
    private String id;
    private int ifQualification;
    private String invitEnter;
    private  double shipHeight;
    private double shipLength;
    private  String shipName;
    private  String shipNumberId;
    private String shipType;
    private String shipTypeName;
    private  double shipVolume;
    private double shipWidth;
    private String sideView;
    private String tranNo;
    private String tranPermit;
    private int checkStatus;
    private int ifRegister;//1是自己注册的 2不是
    private String checkOpinion;

    public String getCheckOpinion() {
        return checkOpinion;
    }

    public void setCheckOpinion(String checkOpinion) {
        this.checkOpinion = checkOpinion;
    }

    public int getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

    public int getAllowAdd() {
        return allowAdd;
    }

    public void setAllowAdd(int allowAdd) {
        this.allowAdd = allowAdd;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIfQualification() {
        return ifQualification;
    }

    public void setIfQualification(int ifQualification) {
        this.ifQualification = ifQualification;
    }

    public String getInvitEnter() {
        return invitEnter;
    }

    public void setInvitEnter(String invitEnter) {
        this.invitEnter = invitEnter;
    }

    public double getShipHeight() {
        return shipHeight;
    }

    public void setShipHeight(double shipHeight) {
        this.shipHeight = shipHeight;
    }

    public double getShipLength() {
        return shipLength;
    }

    public void setShipLength(double shipLength) {
        this.shipLength = shipLength;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipNumberId() {
        return shipNumberId;
    }

    public void setShipNumberId(String shipNumberId) {
        this.shipNumberId = shipNumberId;
    }

    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public String getShipTypeName() {
        return shipTypeName;
    }

    public void setShipTypeName(String shipTypeName) {
        this.shipTypeName = shipTypeName;
    }

    public double getShipVolume() {
        return shipVolume;
    }

    public void setShipVolume(double shipVolume) {
        this.shipVolume = shipVolume;
    }

    public double getShipWidth() {
        return shipWidth;
    }

    public void setShipWidth(double shipWidth) {
        this.shipWidth = shipWidth;
    }

    public String getSideView() {
        return sideView;
    }

    public void setSideView(String sideView) {
        this.sideView = sideView;
    }

    public String getTranNo() {
        return tranNo;
    }

    public void setTranNo(String tranNo) {
        this.tranNo = tranNo;
    }

    public String getTranPermit() {
        return tranPermit;
    }

    public void setTranPermit(String tranPermit) {
        this.tranPermit = tranPermit;
    }

    public int getIfRegister() {
        return ifRegister;
    }

    public void setIfRegister(int ifRegister) {
        this.ifRegister = ifRegister;
    }
}
