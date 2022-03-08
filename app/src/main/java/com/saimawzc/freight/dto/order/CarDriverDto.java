package com.saimawzc.freight.dto.order;

import java.io.Serializable;

/***
 * 获取车型
 * **/
public class CarDriverDto implements Serializable {

    private String sjName;
    private String id;
    private String userAccount;
    private String sjCode;
    private Integer status;
    private Integer ifDisable;
    private String hzUserName;

    public String getHzUserName() {
        return hzUserName;
    }

    public void setHzUserName(String hzUserName) {
        this.hzUserName = hzUserName;
    }

    public Integer getIfDisable() {
        return ifDisable;
    }

    public void setIfDisable(Integer ifDisable) {
        this.ifDisable = ifDisable;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSjName() {
        return sjName;
    }

    public void setSjName(String sjName) {
        this.sjName = sjName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getSjCode() {
        return sjCode;
    }

    public void setSjCode(String sjCode) {
        this.sjCode = sjCode;
    }
}
