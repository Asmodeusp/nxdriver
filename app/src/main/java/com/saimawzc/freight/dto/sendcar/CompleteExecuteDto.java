package com.saimawzc.freight.dto.sendcar;

import java.util.List;

public class CompleteExecuteDto {

    private boolean isLastPage;

    private List<ComeletaExecuteData>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<ComeletaExecuteData> getList() {
        return list;
    }

    public void setList(List<ComeletaExecuteData> list) {
        this.list = list;
    }

    public  class  ComeletaExecuteData{
        private String id;
        private String dispatchCarNo;
        private String fromCityName;
        private String fromProName;
        private String fromLocation;
        private String toUserAddress;
        private String toCityName;
        private String toProName;
        private String toLocation;
        private String cysId;
        private String cysName;
        private String carNo;
        private String taskStartTime;
        private String taskEndTime;
        private int completeStatus;
        private String completeStatusValue;
        private String transportWeight;
        private String signWeight;
        private String materialsNames;
        private String fromUserAddress;
        private double startTime;
        private double endTime;
        private String toName;
        private String fromName;
        private double weighingDoubt;

        public double getWeighingDoubt() {
            return weighingDoubt;
        }

        public void setWeighingDoubt(double weighingDoubt) {
            this.weighingDoubt = weighingDoubt;
        }

        public String getToName() {
            return toName;
        }

        public void setToName(String toName) {
            this.toName = toName;
        }

        public String getFromName() {
            return fromName;
        }

        public void setFromName(String fromName) {
            this.fromName = fromName;
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

        public String getFromUserAddress() {
            return fromUserAddress;
        }

        public void setFromUserAddress(String fromUserAddress) {
            this.fromUserAddress = fromUserAddress;
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

        public int getCompleteStatus() {
            return completeStatus;
        }

        public void setCompleteStatus(int completeStatus) {
            this.completeStatus = completeStatus;
        }

        public String getCompleteStatusValue() {
            return completeStatusValue;
        }

        public void setCompleteStatusValue(String completeStatusValue) {
            this.completeStatusValue = completeStatusValue;
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

        public String getMaterialsNames() {
            return materialsNames;
        }

        public void setMaterialsNames(String materialsNames) {
            this.materialsNames = materialsNames;
        }
    }

}
