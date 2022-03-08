package com.saimawzc.freight.dto.order.mainindex;

import com.saimawzc.freight.ui.my.PersonCenterActivity;

import java.io.Serializable;
import java.util.List;

/**
 * 抢单
 * **/
public class RobOrderDto {

    private boolean isLastPage;

    private List<robOrderData>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<robOrderData> getList() {
        return list;
    }

    public void setList(List<robOrderData> list) {
        this.list = list;
    }

    public class robOrderData implements Serializable {
        private String id;
        private String waybillId;
        private String waybillNo;
        private String companyName;
        private String materialsName;
        private String fromUserAddress;
        private String fromProName;
        private String fromCityName;
        private String toUserAddress;
        private String toProName;
        private String toCityName;
        private String companyLogo;
        private String pointWeight;
        private String startTime;
        private String endTime;
        private String biddNum;
        private String carTypeName;
        private int waybillType;
        private int tranType;
        private String fromName;
        private String toName;
        private String taskStartTime;
        private String taskEndTime;
        private int roleType;

        public int getRoleType() {
            return roleType;
        }

        public void setRoleType(int roleType) {
            this.roleType = roleType;
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

        public int getTranType() {
            return tranType;
        }

        public void setTranType(int tranType) {
            this.tranType = tranType;
        }

        public String getId() {
            return id;
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

        public String getBiddNum() {
            return biddNum;
        }

        public void setBiddNum(String biddNum) {
            this.biddNum = biddNum;
        }

        public String getCarTypeName() {
            return carTypeName;
        }

        public void setCarTypeName(String carTypeName) {
            this.carTypeName = carTypeName;
        }

        public int getWaybillType() {
            return waybillType;
        }

        public void setWaybillType(int waybillType) {
            this.waybillType = waybillType;
        }
    }



   private String adress;
   private String weight;
   private String time;
   private String num;
   private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
