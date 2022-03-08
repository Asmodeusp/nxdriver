package com.saimawzc.freight.dto.sendcar;

import java.util.List;

public class SendCarDelatiodto {
    private String  id;
    private String cysId;
    private String cysName;
    private String carNo;
    private String totalWeight;
    private String transportWeight;
    private String signWeight;
    private String thirdOrderNo;
    private String thirdDispatchNo;
    private String thirdDispatchPwd;
    private String startTime;
    private String endTime;
    private int provideLoad;
    private int provideUnload;
    private int provideInvoice;
    private String waybillIdList[];
    private List<materialsListDto>materialsList;
    private String  lcbh;
    private String lwxx;

    public String getLcbh() {
        if (lcbh!=null) {
            return lcbh;
        }else {
            return "";
        }



    }

    public void setLcbh(String lcbh) {
        this.lcbh = lcbh;
    }

    public String getLwxx() {
        if (lwxx!=null) {
            return lwxx;
        }else {
            return "";
        }

    }

    public void setLwxx(String lwxx) {
        this.lwxx = lwxx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCysId() {
        return cysId;
    }

    public void setCysId(String cysId) {
        this.cysId = cysId;
    }

    public String getCysName() {
        return cysName;
    }

    public void setCysName(String cysName) {
        this.cysName = cysName;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getTransportWeight() {
        return transportWeight;
    }

    public void setTransportWeight(String transportWeight) {
        this.transportWeight = transportWeight;
    }

    public String getSignWeight() {
        return signWeight;
    }

    public void setSignWeight(String signWeight) {
        this.signWeight = signWeight;
    }

    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
    }

    public String getThirdDispatchNo() {
        return thirdDispatchNo;
    }

    public void setThirdDispatchNo(String thirdDispatchNo) {
        this.thirdDispatchNo = thirdDispatchNo;
    }

    public String getThirdDispatchPwd() {
        return thirdDispatchPwd;
    }

    public void setThirdDispatchPwd(String thirdDispatchPwd) {
        this.thirdDispatchPwd = thirdDispatchPwd;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getProvideLoad() {
        return provideLoad;
    }

    public void setProvideLoad(int provideLoad) {
        this.provideLoad = provideLoad;
    }

    public int getProvideUnload() {
        return provideUnload;
    }

    public void setProvideUnload(int provideUnload) {
        this.provideUnload = provideUnload;
    }

    public int getProvideInvoice() {
        return provideInvoice;
    }

    public void setProvideInvoice(int provideInvoice) {
        this.provideInvoice = provideInvoice;
    }

    public String[] getWaybillIdList() {
        return waybillIdList;
    }

    public void setWaybillIdList(String[] waybillIdList) {
        this.waybillIdList = waybillIdList;
    }

    public List<materialsListDto> getMaterialsList() {
        return materialsList;
    }

    public void setMaterialsList(List<materialsListDto> materialsList) {
        this.materialsList = materialsList;
    }

    public class materialsListDto{
        private String id;
        private String materialsId;
        private String materialsName;
        private String weight;
        private String unit;
        private String unitValue;
        private String signWeight;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMaterialsId() {
            return materialsId;
        }

        public void setMaterialsId(String materialsId) {
            this.materialsId = materialsId;
        }

        public String getMaterialsName() {
            return materialsName;
        }

        public void setMaterialsName(String materialsName) {
            this.materialsName = materialsName;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getUnitValue() {
            return unitValue;
        }

        public void setUnitValue(String unitValue) {
            this.unitValue = unitValue;
        }

        public String getSignWeight() {
            return signWeight;
        }

        public void setSignWeight(String signWeight) {
            this.signWeight = signWeight;
        }
    }


}
