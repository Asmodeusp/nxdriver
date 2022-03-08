package com.saimawzc.freight.dto.order;

/***
 *
 * **/
public class StopTrantDelationDto {

    private String dispatchNo;
    private String offRoleName;
    private String offTime;
    private String offOpinion;

    public String getDispatchNo() {
        return dispatchNo;
    }

    public void setDispatchNo(String dispatchNo) {
        this.dispatchNo = dispatchNo;
    }

    public String getOffRoleName() {
        return offRoleName;
    }

    public void setOffRoleName(String offRoleName) {
        this.offRoleName = offRoleName;
    }

    public String getOffTime() {
        return offTime;
    }

    public void setOffTime(String offTime) {
        this.offTime = offTime;
    }

    public String getOffOpinion() {
        return offOpinion;
    }

    public void setOffOpinion(String offOpinion) {
        this.offOpinion = offOpinion;
    }
}
