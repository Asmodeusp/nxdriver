package com.saimawzc.freight.dto.my;

/**
 * Created by Administrator on 2020/8/7.
 */

public class PersonCenterDto {
    private int authState;//0 未认证 1已认证 2 认证中 3 认证失败
    private String companyName;
    private int cysType;//1个人承运商  2 一般纳税人  3 小规模企业
    private String name;
    private String picture;
    private int roleType;
    private  int driverType;//1司机2船员
    private String idCardNum;
    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public int getDriverType() {
        return driverType;
    }

    public void setDriverType(int driverType) {
        this.driverType = driverType;
    }

    public int getAuthState() {
        return authState;
    }

    public void setAuthState(int authState) {
        this.authState = authState;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCysType() {
        return cysType;
    }

    public void setCysType(int cysType) {
        this.cysType = cysType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }
}
