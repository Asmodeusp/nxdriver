package com.saimawzc.freight.dto.my.car;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/8/4.
 * 搜索车辆
 */

public class SearchCarDto implements Serializable {
    private String carTypeName;//车牌
    private String carBrandId;
    private String carBrandName;
    private String carNo;
    private String carLoad;//载重
    private String carName;
    private String id;
    private String carId;
    private String userId;
    private String userName;
    private int ifRegister;//1是自己注册的  2 不是自己注册的

    private int carColor;
    private String carHeigth;
    private String carLength;
    private String carTypeId;
    private String carWigth;
    private String ifNetwork;
    private int allowAdd;

    private String boardHeight;
    private int isDangerCarType;
    private String licenseRegTime;
    private String rib1;
    private String rib2;
    private String rib3;
    private String rib4;
    private String rib5;
    private Integer isBlackList;
    private Integer ifDisable;
    private String hzUserName;

    public Integer getIsBlackList() {
        if (isBlackList!=null) {
            return isBlackList;
        }else {
            return 0;
        }

    }

    public void setIsBlackList(Integer isBlackList) {
        this.isBlackList = isBlackList;
    }

    public Integer getIfDisable() {
        if (ifDisable!=null) {
            return ifDisable;
        }else {
            return 1;
        }
    }

    public void setIfDisable(Integer ifDisable) {
        this.ifDisable = ifDisable;
    }

    public String getHzUserName() {
        if (ifDisable!=null) {
            return hzUserName;
        }else {
            return "";
        }
    }

    public void setHzUserName(String hzUserName) {
        this.hzUserName = hzUserName;
    }

    public String getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(String boardHeight) {
        this.boardHeight = boardHeight;
    }

    public int getIsDangerCarType() {
        return isDangerCarType;
    }

    public void setIsDangerCarType(int isDangerCarType) {
        this.isDangerCarType = isDangerCarType;
    }

    public String getLicenseRegTime() {
        return licenseRegTime;
    }

    public void setLicenseRegTime(String licenseRegTime) {
        this.licenseRegTime = licenseRegTime;
    }

    public String getRib1() {
        return rib1;
    }

    public void setRib1(String rib1) {
        this.rib1 = rib1;
    }

    public String getRib2() {
        return rib2;
    }

    public void setRib2(String rib2) {
        this.rib2 = rib2;
    }

    public String getRib3() {
        return rib3;
    }

    public void setRib3(String rib3) {
        this.rib3 = rib3;
    }

    public String getRib4() {
        return rib4;
    }

    public void setRib4(String rib4) {
        this.rib4 = rib4;
    }

    public String getRib5() {
        return rib5;
    }

    public void setRib5(String rib5) {
        this.rib5 = rib5;
    }

    private String oldUserName;
    private int checkStatus;
    private String checkOpinion;

    public String getTranCarNo() {
        return tranCarNo;
    }

    public void setTranCarNo(String tranCarNo) {
        this.tranCarNo = tranCarNo;
    }

    public String getTranCarName() {
        return tranCarName;
    }

    public void setTranCarName(String tranCarName) {
        this.tranCarName = tranCarName;
    }

    public String getPictureTranNo() {
        return pictureTranNo;
    }

    public void setPictureTranNo(String pictureTranNo) {
        this.pictureTranNo = pictureTranNo;
    }

    private String tranCarNo;
    private String tranCarName;
    private String pictureTranNo;


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

    public String getOldUserName() {
        return oldUserName;
    }

    public void setOldUserName(String oldUserName) {
        this.oldUserName = oldUserName;
    }

    public int getAllowAdd() {
        return allowAdd;
    }

    public void setAllowAdd(int allowAdd) {
        this.allowAdd = allowAdd;
    }

    public int getIfRegister() {
        return ifRegister;
    }

    public void setIfRegister(int ifRegister) {
        this.ifRegister = ifRegister;
    }

    private int ifQualification;
    private String invitEnter;
    private String operationLicense;
    private String registerArea;
    private String tranNo;
    private String vehicleSurvey;
    private String vehicleLicense;

    public String getCarBrandId() {
        return carBrandId;
    }

    public void setCarBrandId(String carBrandId) {
        this.carBrandId = carBrandId;
    }

    public int getCarColor() {
        return carColor;
    }

    public void setCarColor(int carColor) {
        this.carColor = carColor;
    }

    public String getCarHeigth() {
        return carHeigth;
    }

    public void setCarHeigth(String carHeigth) {
        this.carHeigth = carHeigth;
    }

    public String getCarLength() {
        return carLength;
    }

    public void setCarLength(String carLength) {
        this.carLength = carLength;
    }

    public String getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(String carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getCarWigth() {
        return carWigth;
    }

    public void setCarWigth(String carWigth) {
        this.carWigth = carWigth;
    }

    public String getIfNetwork() {
        return ifNetwork;
    }

    public void setIfNetwork(String ifNetwork) {
        this.ifNetwork = ifNetwork;
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

    public String getOperationLicense() {
        return operationLicense;
    }

    public void setOperationLicense(String operationLicense) {
        this.operationLicense = operationLicense;
    }

    public String getRegisterArea() {
        return registerArea;
    }

    public void setRegisterArea(String registerArea) {
        this.registerArea = registerArea;
    }

    public String getTranNo() {
        return tranNo;
    }

    public void setTranNo(String tranNo) {
        this.tranNo = tranNo;
    }

    public String getVehicleLicense() {
        return vehicleLicense;
    }

    public void setVehicleLicense(String vehicleLicense) {
        this.vehicleLicense = vehicleLicense;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCarName() {
        return carName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getCarBrandName() {
        return carBrandName;
    }

    public void setCarBrandName(String carBrandName) {
        this.carBrandName = carBrandName;
    }

    public String getVehicleSurvey() {
        return vehicleSurvey;
    }

    public void setVehicleSurvey(String vehicleSurvey) {
        this.vehicleSurvey = vehicleSurvey;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCarLoad() {
        return carLoad;
    }

    public void setCarLoad(String carLoad) {
        this.carLoad = carLoad;
    }

    @Override
    public String toString() {
        return "SearchCarDto{" +
                "carTypeName='" + carTypeName + '\'' +
                ", carBrandId='" + carBrandId + '\'' +
                ", carBrandName='" + carBrandName + '\'' +
                ", carNo='" + carNo + '\'' +
                ", carLoad='" + carLoad + '\'' +
                ", carName='" + carName + '\'' +
                ", id='" + id + '\'' +
                ", carId='" + carId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", ifRegister=" + ifRegister +
                ", carColor=" + carColor +
                ", carHeigth='" + carHeigth + '\'' +
                ", carLength='" + carLength + '\'' +
                ", carTypeId='" + carTypeId + '\'' +
                ", carWigth='" + carWigth + '\'' +
                ", ifNetwork='" + ifNetwork + '\'' +
                ", allowAdd=" + allowAdd +
                ", boardHeight='" + boardHeight + '\'' +
                ", isDangerCarType=" + isDangerCarType +
                ", licenseRegTime='" + licenseRegTime + '\'' +
                ", rib1='" + rib1 + '\'' +
                ", rib2='" + rib2 + '\'' +
                ", rib3='" + rib3 + '\'' +
                ", rib4='" + rib4 + '\'' +
                ", rib5='" + rib5 + '\'' +
                ", isBlackList=" + isBlackList +
                ", ifDisable=" + ifDisable +
                ", hzUserName='" + hzUserName + '\'' +
                ", oldUserName='" + oldUserName + '\'' +
                ", checkStatus=" + checkStatus +
                ", checkOpinion='" + checkOpinion + '\'' +
                ", tranCarNo='" + tranCarNo + '\'' +
                ", tranCarName='" + tranCarName + '\'' +
                ", pictureTranNo='" + pictureTranNo + '\'' +
                ", ifQualification=" + ifQualification +
                ", invitEnter='" + invitEnter + '\'' +
                ", operationLicense='" + operationLicense + '\'' +
                ", registerArea='" + registerArea + '\'' +
                ", tranNo='" + tranNo + '\'' +
                ", vehicleSurvey='" + vehicleSurvey + '\'' +
                ", vehicleLicense='" + vehicleLicense + '\'' +
                '}';
    }
}
