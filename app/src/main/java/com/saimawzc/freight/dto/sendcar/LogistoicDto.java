package com.saimawzc.freight.dto.sendcar;

import java.util.List;

public class LogistoicDto {


    private String wayBillNo;
    private String sjName;
    private String carNo;
    private String id;
    private int weighingDoubt;
    private String poundReMark;

    public String getPoundReMark() {
        return poundReMark;
    }

    public void setPoundReMark(String poundReMark) {
        this.poundReMark = poundReMark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWeighingDoubt() {
        return weighingDoubt;
    }

    public void setWeighingDoubt(int weighingDoubt) {
        this.weighingDoubt = weighingDoubt;
    }

    private List<transportLog>transportLogList;

    public String getWayBillNo() {
        return wayBillNo;
    }

    public void setWayBillNo(String wayBillNo) {
        this.wayBillNo = wayBillNo;
    }

    public String getSjName() {
        return sjName;
    }

    public void setSjName(String sjName) {
        this.sjName = sjName;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public List<transportLog> getTransportLogList() {
        return transportLogList;
    }

    public void setTransportLogList(List<transportLog> transportLogList) {
        this.transportLogList = transportLogList;
    }

    public class transportLog{
        int transportType;
        private String name;
        String picture;
        String createTime;

        public int getTransportType() {
            return transportType;
        }

        public void setTransportType(int transportType) {
            this.transportType = transportType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }



}
