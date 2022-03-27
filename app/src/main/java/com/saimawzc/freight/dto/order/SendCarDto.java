package com.saimawzc.freight.dto.order;

import java.util.List;

/***
 *
 * */
public class SendCarDto {

    private  boolean isLastPage;
    private List<SendCarData>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<SendCarData> getList() {
        return list;
    }

    public void setList(List<SendCarData> list) {
        this.list = list;
    }

    public class SendCarData{
        private String id;
        private String toProName;
        private String toCityName;
        private String fromProName;
        private String fromCityName;
        private String createTime;
        private String carTypeName;
        private double totalWeight;
        private String companyLogo;
        private String sjId;
        private double startTime;
        private double endTime;
        private int tranType;
        private String sjName;
        private String carNo;
        private String materialsName;
        private String fromName;
        private String toName;
        private String hzSignInWeight;
        private String fromUserAddress;
        private String toUserAddress;
        private int isCancel;
        private String resTxt2;

        public String getResTxt2() {
            return resTxt2;
        }

        public void setResTxt2(String resTxt2) {
            this.resTxt2 = resTxt2;
        }

        public int getIsCancel() {
            return isCancel;
        }

        public void setIsCancel(int isCancel) {
            this.isCancel = isCancel;
        }

        public String getFromUserAddress() {
            return fromUserAddress;
        }

        public void setFromUserAddress(String fromUserAddress) {
            this.fromUserAddress = fromUserAddress;
        }

        public String getToUserAddress() {
            return toUserAddress;
        }

        public void setToUserAddress(String toUserAddress) {
            this.toUserAddress = toUserAddress;
        }

        public String getHzSignInWeight() {
            return hzSignInWeight;
        }

        public void setHzSignInWeight(String hzSignInWeight) {
            this.hzSignInWeight = hzSignInWeight;
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

        public String getMaterialsName() {
            return materialsName;
        }

        public void setMaterialsName(String materialsName) {
            this.materialsName = materialsName;
        }

        public String getCarNo() {
            return carNo;
        }

        public void setCarNo(String carNo) {
            this.carNo = carNo;
        }

        public String getSjName() {
            return sjName;
        }

        public void setSjName(String sjName) {
            this.sjName = sjName;
        }

        private String dispatchCarNo;

        public String getDispatchCarNo() {
            return dispatchCarNo;
        }

        public void setDispatchCarNo(String dispatchCarNo) {
            this.dispatchCarNo = dispatchCarNo;
        }

        public int getTranType() {
            return tranType;
        }

        public void setTranType(int tranType) {
            this.tranType = tranType;
        }

        public double getStartTime() {
            return startTime;
        }

        public void setStartTime(double startTime) {
            this.startTime = startTime;
        }

        public double getEndTime() {
            return endTime;
        }

        public void setEndTime(double endTime) {
            this.endTime = endTime;
        }

        public String getSjId() {
            return sjId;
        }

        public void setSjId(String sjId) {
            this.sjId = sjId;
        }

        public String getCompanyLogo() {
            return companyLogo;
        }

        public void setCompanyLogo(String companyLogo) {
            this.companyLogo = companyLogo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public double getTotalWeight() {
            return totalWeight;
        }

        public void setTotalWeight(double totalWeight) {
            this.totalWeight = totalWeight;
        }
    }
}
