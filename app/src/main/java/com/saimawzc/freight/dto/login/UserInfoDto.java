package com.saimawzc.freight.dto.login;

/**
 * Created by Administrator on 2020/7/31.
 */
public class UserInfoDto {
    private String token;
    private String id;
    private String userCode;
    private String userAccount;
    private int role;//1货主 2承运商 3司机 4收货人
    private String name;
    private String roleId;
    private String idCardNum;

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    private String authState;//0 未认证 1已认证 2 认证中 3 认证失败
    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getAuthState() {
        return authState;
    }

    public void setAuthState(String authState) {
        this.authState = authState;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "token='" + token + '\'' +
                ", id='" + id + '\'' +
                ", userCode='" + userCode + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", role=" + role +
                ", name='" + name + '\'' +
                ", authState='" + authState + '\'' +
                '}';
    }
}
