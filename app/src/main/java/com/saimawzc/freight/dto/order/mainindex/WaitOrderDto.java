package com.saimawzc.freight.dto.order.mainindex;

import android.widget.ScrollView;

import java.io.Serializable;
import java.util.List;

/***
 * 待确认
 * **/
public class WaitOrderDto {


    private boolean isLastPage;
    private List<waitOrderData>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<waitOrderData> getList() {
        return list;
    }

    public void setList(List<waitOrderData> list) {
        this.list = list;
    }

    public class waitOrderData implements Serializable {
        private  String id;
        private  String waybillId;
        private  String waybillNo;
        private  String companyName;
        private  String materialsName;
        private  String fromUserAddress;
        private  String fromProName;
        private  String fromCityName;
        private  String toUserAddress;
        private  String toProName;
        private  String toCityName;
        private String companyLogo;
        private String fromName;
        private String toName;

        private String pointWeight;
        private int weightUnit;
        private String carTypeName;

        private String createTime;
        private int waybillType;
        private int tranType;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWaybillId() {
            return waybillId;
        }

        public void setWaybillId(String waybillId) {
            this.waybillId = waybillId;
        }

        public String getWaybillNo() {
            return waybillNo;
        }

        public void setWaybillNo(String waybillNo) {
            this.waybillNo = waybillNo;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getMaterialsName() {
            return materialsName;
        }

        public void setMaterialsName(String materialsName) {
            this.materialsName = materialsName;
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

        public String getPointWeight() {
            return pointWeight;
        }

        public void setPointWeight(String pointWeight) {
            this.pointWeight = pointWeight;
        }

        public int getWeightUnit() {
            return weightUnit;
        }

        public void setWeightUnit(int weightUnit) {
            this.weightUnit = weightUnit;
        }

        public String getCarTypeName() {
            return carTypeName;
        }

        public void setCarTypeName(String carTypeName) {
            this.carTypeName = carTypeName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getWaybillType() {
            return waybillType;
        }

        public void setWaybillType(int waybillType) {
            this.waybillType = waybillType;
        }
    }


}
