package com.saimawzc.freight.dto.order.bill;

import java.io.Serializable;
import java.util.List;

public class WayBillDto {

    private boolean isLastPage;
    private List<OrderBillData>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<OrderBillData> getList() {
        return list;
    }

    public void setList(List<OrderBillData> list) {
        this.list = list;
    }

    public class  OrderBillData implements Serializable {
        private String id;
        private String wayBillNo;
        private String hzName;
        private String companyLogo;
        private String fromCityName;
        private String companyId;

        private String fromProName;
        private String fromUserAddress;
        private String toCityName;

        private String toProName;
        private String toUserAddress;
        private double totalWeight;
        private int wayBillStatus;
        private int sendType;
        private int tranType;//1派车  2派车
        private String fromName;
        private String toName;
        private int bindSmartLock;//是否绑定智能物流锁(1-是 2-否)
        private int beiDouStatus;

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public int getBeiDouStatus() {
            return beiDouStatus;
        }

        public void setBeiDouStatus(int beiDouStatus) {
            this.beiDouStatus = beiDouStatus;
        }

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

        public String getWayBillNo() {
            return wayBillNo;
        }

        public void setWayBillNo(String wayBillNo) {
            this.wayBillNo = wayBillNo;
        }

        public String getHzName() {
            return hzName;
        }

        public void setHzName(String hzName) {
            this.hzName = hzName;
        }

        public String getCompanyLogo() {
            return companyLogo;
        }

        public void setCompanyLogo(String companyLogo) {
            this.companyLogo = companyLogo;
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

        public String getFromUserAddress() {
            return fromUserAddress;
        }

        public void setFromUserAddress(String fromUserAddress) {
            this.fromUserAddress = fromUserAddress;
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

        public String getToUserAddress() {
            return toUserAddress;
        }

        public void setToUserAddress(String toUserAddress) {
            this.toUserAddress = toUserAddress;
        }

        public double getTotalWeight() {
            return totalWeight;
        }

        public void setTotalWeight(double totalWeight) {
            this.totalWeight = totalWeight;
        }

        public int getWayBillStatus() {
            return wayBillStatus;
        }

        public void setWayBillStatus(int wayBillStatus) {
            this.wayBillStatus = wayBillStatus;
        }
    }



}
