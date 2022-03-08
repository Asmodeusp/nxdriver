package com.saimawzc.freight.dto.sendcar;

import java.io.Serializable;

/***
 * 派车搜索司机
 * **/
public class ScSearchDriverDto implements Serializable {

    private String id;
    private String phone;
    private String sjName;
    private String sjCode;
    private String picture;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSjName() {
        return sjName;
    }

    public void setSjName(String sjName) {
        this.sjName = sjName;
    }

    public String getSjCode() {
        return sjCode;
    }

    public void setSjCode(String sjCode) {
        this.sjCode = sjCode;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "ScSearchDriverDto{" +
                "id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", sjName='" + sjName + '\'' +
                ", sjCode='" + sjCode + '\'' +
                ", picture='" + picture + '\'' +
                ", isBlackList=" + isBlackList +
                ", ifDisable=" + ifDisable +
                ", hzUserName='" + hzUserName + '\'' +
                '}';
    }
}
