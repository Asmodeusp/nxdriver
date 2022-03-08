package com.saimawzc.freight.dto.wallet;

public class BindBankDto {

    private String sfzfm;
    private String sfzzm;
    private String userName;
    private String yyzzbh;
    private String name;




    /***
     *0-机构 1-个人
     * */
    private int clientType;
    /***
     *会员真实姓名
     * */
    private String clientName;
    /***
     *证件号码,clientType:为1身份证号码,为0营业执照编号
     * */
    private String idCode;

    /***
     *银行预留手机号/经办人手机号码
     * */
    private String mobile;
    /***
     *银行编号,个人会员通过卡Bin校验获取,机构会员通过查询民生银行银行列表接口模糊匹配
     * */
    private String bankNo;
    /***
     *银行卡号
     * */
    private String bankAcc;
    /***
     *经办人姓名
     * */
    private String actorName;

    /***
     *经办人证件号码
     * */
    private String actorIdCode;
    /***
     *法人姓名
     * */
    private String reprName;

    /***
     *性别
     * */
    private String sex;
    /***
     *法人证件号码,clientType为0必输
     * */
    private String reprIdCode;
    private String openBranch;

    public String getOpenBranch() {
        return openBranch;
    }

    public void setOpenBranch(String openBranch) {
        this.openBranch = openBranch;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankAcc() {
        return bankAcc;
    }

    public void setBankAcc(String bankAcc) {
        this.bankAcc = bankAcc;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActorIdCode() {
        return actorIdCode;
    }

    public void setActorIdCode(String actorIdCode) {
        this.actorIdCode = actorIdCode;
    }

    public String getReprName() {
        return reprName;
    }

    public void setReprName(String reprName) {
        this.reprName = reprName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getReprIdCode() {
        return reprIdCode;
    }

    public void setReprIdCode(String reprIdCode) {
        this.reprIdCode = reprIdCode;
    }

    public String getSfzfm() {
        return sfzfm;
    }

    public void setSfzfm(String sfzfm) {
        this.sfzfm = sfzfm;
    }

    public String getSfzzm() {
        return sfzzm;
    }

    public void setSfzzm(String sfzzm) {
        this.sfzzm = sfzzm;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getYyzzbh() {
        return yyzzbh;
    }

    public void setYyzzbh(String yyzzbh) {
        this.yyzzbh = yyzzbh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
