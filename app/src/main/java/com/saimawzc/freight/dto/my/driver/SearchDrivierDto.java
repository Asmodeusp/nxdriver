package com.saimawzc.freight.dto.my.driver;

/**
 * Created by Administrator on 2020/8/4.
 * 搜索司机
 */

public class SearchDrivierDto {

    private String  picture;
    private String id;
    private String userAccount;
    private String userName;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
