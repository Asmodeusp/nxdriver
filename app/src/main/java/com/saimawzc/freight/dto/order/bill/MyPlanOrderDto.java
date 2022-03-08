package com.saimawzc.freight.dto.order.bill;

import java.io.Serializable;
import java.util.List;

/***
 * 竞价
 * */
public class MyPlanOrderDto {

    private boolean isLastPage;
    private List<planOrderData>list;


    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<planOrderData> getList() {
        return list;
    }

    public void setList(List<planOrderData> list) {
        this.list = list;
    }

    public class  planOrderData implements Serializable {
        private String id;
        private String companyName;
        private String companyId;
        private String companyLogo;
        private String materialsName;
        private String fromCityName;
        private String fromProName;
        private String fromUserAddress;
        private String toCityName;
        private String toProName;
        private double overWeight;//剩余分配余量
        private int weightUnit;
        private int tranType;//1派车  2派车
        private String fromName;
        private String toName;
        private int planStatus;
        private String planWayBillNo;
        private int bindSmartLock;//是否需要绑锁 (1-是 2-否)
        private String takeCardWeight;//取卡量
        private String underWay;//在途量
        private String consult;//参考量
        private int beiDouStatus;
        private int isSx;//是否刷新
        private String weightUnitName;
        private  int isPrice;
        private String price;



        public int isPrice() {
            return isPrice;
        }

        public void setPrice(int price) {
            isPrice = price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getUnitName() {
            return weightUnitName;
        }

        public void setUnitName(String unitName) {
            this.weightUnitName = unitName;
        }

        public int getBeiDouStatus() {
            return beiDouStatus;
        }

        public void setBeiDouStatus(int beiDouStatus) {
            this.beiDouStatus = beiDouStatus;
        }

        public String getTakeCardWeight() {
            return takeCardWeight;
        }

        public void setTakeCardWeight(String takeCardWeight) {
            this.takeCardWeight = takeCardWeight;
        }

        public int getIsSx() {
            return isSx;
        }

        public void setIsSx(int isSx) {
            this.isSx = isSx;
        }

        public String getUnderWay() {
            return underWay;
        }

        public void setUnderWay(String underWay) {
            this.underWay = underWay;
        }

        public String getConsult() {
            return consult;
        }

        public void setConsult(String consult) {
            this.consult = consult;
        }

        public int getBindSmartLock() {
            return bindSmartLock;
        }

        public void setBindSmartLock(int bindSmartLock) {
            this.bindSmartLock = bindSmartLock;
        }

        public String getPlanWayBillNo() {
            return planWayBillNo;
        }

        public void setPlanWayBillNo(String planWayBillNo) {
            this.planWayBillNo = planWayBillNo;
        }

        public int getPlanStatus() {
            return planStatus;
        }

        public void setPlanStatus(int planStatus) {
            this.planStatus = planStatus;
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

        public int getWeightUnit() {
            return weightUnit;
        }

        public void setWeightUnit(int weightUnit) {
            this.weightUnit = weightUnit;
        }

        private String toUserAddress;
        private double pointWeight;

        public double getOverWeight() {
            return overWeight;
        }

        public void setOverWeight(double overWeight) {
            this.overWeight = overWeight;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyLogo() {
            return companyLogo;
        }

        public void setCompanyLogo(String companyLogo) {
            this.companyLogo = companyLogo;
        }

        public String getMaterialsName() {
            return materialsName;
        }

        public void setMaterialsName(String materialsName) {
            this.materialsName = materialsName;
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

        public double getPointWeight() {
            return pointWeight;
        }

        public void setPointWeight(double pointWeight) {
            this.pointWeight = pointWeight;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }
    }



}
