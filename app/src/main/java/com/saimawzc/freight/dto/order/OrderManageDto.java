package com.saimawzc.freight.dto.order;

public class OrderManageDto {

    private String manageId;
    private String creatTime;
    private String needCarModel;
    private String needCarWeight;
    private String unit;
    private String num;

    public String getManageId() {
        return manageId;
    }

    public void setManageId(String manageId) {
        this.manageId = manageId;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getNeedCarModel() {
        return needCarModel;
    }

    public void setNeedCarModel(String needCarModel) {
        this.needCarModel = needCarModel;
    }

    public String getNeedCarWeight() {
        return needCarWeight;
    }

    public void setNeedCarWeight(String needCarWeight) {
        this.needCarWeight = needCarWeight;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
