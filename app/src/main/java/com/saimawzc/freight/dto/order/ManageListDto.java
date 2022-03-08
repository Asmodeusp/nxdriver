package com.saimawzc.freight.dto.order;

import java.util.List;

public class ManageListDto {

    private boolean isLastPage;
    private List<ManageOrderData>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<ManageOrderData> getList() {
        return list;
    }

    public void setList(List<ManageOrderData> list) {
        this.list = list;
    }

    public class  ManageOrderData{
        private String id;
        private String dispatchNo;
        private String createTime;
        private String carTypeName;
        private String totalWeight;
        private String fromUserAddress;
        private String fromProName;
        private String fromCityName;
        private String toUserAddress;
        private String toProName;
        private String toCityName;
        private String companyLogo;
        private int wayBillStatus;
        private int sendType;
        private int tranType;//1派车 2派车
        private String fromName;
        private String toName;
        private int bindSmartLock;

        public int getBindSmartLock() {
            return bindSmartLock;
        }

        public void setBindSmartLock(int bindSmartLock) {
            this.bindSmartLock = bindSmartLock;
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

        public int getWayBillStatus() {
            return wayBillStatus;
        }

        public void setWayBillStatus(int wayBillStatus) {
            this.wayBillStatus = wayBillStatus;
        }

        public int getSendType() {
            return sendType;
        }

        public void setSendType(int sendType) {
            this.sendType = sendType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDispatchNo() {
            return dispatchNo;
        }

        public void setDispatchNo(String dispatchNo) {
            this.dispatchNo = dispatchNo;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCarTypeName() {
            return carTypeName;
        }

        public void setCarTypeName(String carTypeName) {
            this.carTypeName = carTypeName;
        }

        public String getTotalWeight() {
            return totalWeight;
        }

        public void setTotalWeight(String totalWeight) {
            this.totalWeight = totalWeight;
        }

        public String getFromUserAddress() {
            return fromUserAddress;
        }

        public void setFromUserAddress(String fromUserAddress) {
            this.fromUserAddress = fromUserAddress;
        }

        public String getFromProName() {
            return fromProName;
        }

        public void setFromProName(String fromProName) {
            this.fromProName = fromProName;
        }

        public String getFromCityName() {
            return fromCityName;
        }

        public void setFromCityName(String fromCityName) {
            this.fromCityName = fromCityName;
        }

        public String getToUserAddress() {
            return toUserAddress;
        }

        public void setToUserAddress(String toUserAddress) {
            this.toUserAddress = toUserAddress;
        }

        public String getToProName() {
            return toProName;
        }

        public void setToProName(String toProName) {
            this.toProName = toProName;
        }

        public String getToCityName() {
            return toCityName;
        }

        public void setToCityName(String toCityName) {
            this.toCityName = toCityName;
        }

        public String getCompanyLogo() {
            return companyLogo;
        }

        public void setCompanyLogo(String companyLogo) {
            this.companyLogo = companyLogo;
        }
    }






}
