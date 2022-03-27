package com.saimawzc.freight.dto.sendcar;

import java.io.Serializable;
import java.util.List;

public class WaitExecuteDto {

    private boolean isLastPage;

    private List<WaitExecuteData>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<WaitExecuteData> getList() {
        return list;
    }

    public void setList(List<WaitExecuteData> list) {
        this.list = list;
    }

    public  class  WaitExecuteData implements Serializable {
        private String id;
        private String dispatchCarNo;
        private String fromCityName;
        private String fromProName;
        private String fromLocation;
        private String toUserAddress;
        private String toCityName;
        private String toProName;
        private String toLocation;
        private String fromUserAddress;
        private int tranType;
        private String fromName;
        private String toName;
        private String billNum;
        private String unitName;
        private String companyId;
        private String lcbh;
        private String lcResult;

        public String getLcbh() {
            if (lcbh!=null) {
                return lcbh;
            }
            else {
                return "";
            }
        }

        public void setLcbh(String cysId) {
            this.lcbh = lcbh;
        }

        public String getLcResult() {
            if (lcResult!=null) {
                return lcResult;
            }
            else {
                return "0";
            }
        }

        public void setLcResult(String lcResult) {
            this.lcResult = lcResult;
        }



        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getBillNum() {
            return billNum;
        }

        public void setBillNum(String billNum) {
            this.billNum = billNum;
        }

        public String getFromName() {
            return fromName;
        }

        public void setFromName(String fromName) {
            this.fromName = fromName;
        }

        public String getToName() {
            return toName;
        }

        public void setToName(String toName) {
            this.toName = toName;
        }

        public int getTranType() {
            return tranType;
        }

        public void setTranType(int tranType) {
            this.tranType = tranType;
        }

        public String getFromUserAddress() {
            return fromUserAddress;
        }

        public void setFromUserAddress(String fromUserAddress) {
            this.fromUserAddress = fromUserAddress;
        }

        private String cysId;
        private String cysName;
        private String carNo;
        private String taskStartTime;
        private String taskEndTime;
        private int status;//1-待执行，2-运输中,3-已完成
        private String statusValue;
        private String totalWeight;
        private String phone;
        private String materialsNames;
        private String resTxt2;

        public String getResTxt2() {
            return resTxt2;
        }

        public void setResTxt2(String resTxt2) {
            this.resTxt2 = resTxt2;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDispatchCarNo() {
            return dispatchCarNo;
        }

        public void setDispatchCarNo(String dispatchCarNo) {
            this.dispatchCarNo = dispatchCarNo;
        }

        public String getFromCityName() {
            return fromCityName;
        }

        public void setFromCityName(String fromCityName) {
            this.fromCityName = fromCityName;
        }

        public String getFromProName() {
            return fromProName;
        }

        public void setFromProName(String fromProName) {
            this.fromProName = fromProName;
        }

        public String getFromLocation() {
            return fromLocation;
        }

        public void setFromLocation(String fromLocation) {
            this.fromLocation = fromLocation;
        }

        public String getToUserAddress() {
            return toUserAddress;
        }

        public void setToUserAddress(String toUserAddress) {
            this.toUserAddress = toUserAddress;
        }

        public String getToCityName() {
            return toCityName;
        }

        public void setToCityName(String toCityName) {
            this.toCityName = toCityName;
        }

        public String getToProName() {
            return toProName;
        }

        public void setToProName(String toProName) {
            this.toProName = toProName;
        }

        public String getToLocation() {
            return toLocation;
        }

        public void setToLocation(String toLocation) {
            this.toLocation = toLocation;
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

        public String getTaskStartTime() {
            return taskStartTime;
        }

        public void setTaskStartTime(String taskStartTime) {
            this.taskStartTime = taskStartTime;
        }

        public String getTaskEndTime() {
            return taskEndTime;
        }

        public void setTaskEndTime(String taskEndTime) {
            this.taskEndTime = taskEndTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatusValue() {
            return statusValue;
        }

        public void setStatusValue(String statusValue) {
            this.statusValue = statusValue;
        }

        public String getTotalWeight() {
            return totalWeight;
        }

        public void setTotalWeight(String totalWeight) {
            this.totalWeight = totalWeight;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMaterialsNames() {
            return materialsNames;
        }

        public void setMaterialsNames(String materialsNames) {
            this.materialsNames = materialsNames;
        }
    }

}
