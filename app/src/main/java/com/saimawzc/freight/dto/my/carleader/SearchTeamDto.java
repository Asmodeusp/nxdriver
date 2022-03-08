package com.saimawzc.freight.dto.my.carleader;

import java.io.Serializable;

public class SearchTeamDto implements Serializable {
    private String carId;
    private String carNo;
    private String sjId;
    private String sjName;
    private String sjPhone;
    private String picture;

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getSjId() {
        return sjId;
    }

    public void setSjId(String sjId) {
        this.sjId = sjId;
    }

    public String getSjName() {
        return sjName;
    }

    public void setSjName(String sjName) {
        this.sjName = sjName;
    }

    public String getSjPhone() {
        return sjPhone;
    }

    public void setSjPhone(String sjPhone) {
        this.sjPhone = sjPhone;
    }
}
