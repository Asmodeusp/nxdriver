package com.saimawzc.freight.dto.my.driver;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2020/8/8.
 * 我的司机
 */

public class MyDriverDto implements Serializable {

    private String id;
    private String userAccount;
    private String  roleName;
    private String picture;
    private String state;
    private int driverType;

    public int getDriverType() {
        return driverType;
    }

    public void setDriverType(int driverType) {
        this.driverType = driverType;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
