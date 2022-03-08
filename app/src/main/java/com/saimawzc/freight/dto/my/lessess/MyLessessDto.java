package com.saimawzc.freight.dto.my.lessess;

/**
 * Created by Administrator on 2020/8/8.
 * 我的承租人
 */

public class MyLessessDto {

    private String carId;
    private String carLoad;
    private String carName;
    private String carNo;
    private String carTypeName;
    private String id;
    private String operationLicense;
    private String roleId;
    private String roleName;
    private String roleType;
    private String status;
    private String vehicleLicense;
    private String vehicleSurvey;
    private String phone;
    private String userPicture;
    private  int type;//1 车 2 船

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarLoad() {
        return carLoad;
    }

    public void setCarLoad(String carLoad) {
        this.carLoad = carLoad;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperationLicense() {
        return operationLicense;
    }

    public void setOperationLicense(String operationLicense) {
        this.operationLicense = operationLicense;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVehicleLicense() {
        return vehicleLicense;
    }

    public void setVehicleLicense(String vehicleLicense) {
        this.vehicleLicense = vehicleLicense;
    }

    public String getVehicleSurvey() {
        return vehicleSurvey;
    }

    public void setVehicleSurvey(String vehicleSurvey) {
        this.vehicleSurvey = vehicleSurvey;
    }
}
