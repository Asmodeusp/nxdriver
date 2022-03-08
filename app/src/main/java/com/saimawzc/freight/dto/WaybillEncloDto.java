package com.saimawzc.freight.dto;

import java.io.Serializable;
import java.util.List;

/***
 * 是否有派车单以及派车单信息  运单围栏信息
 * ****/
public class WaybillEncloDto  {

    private String sjId;
    private String sjName;
    private String carNo;
    private List<yundanData>unStartData;
    private List<yundanData>startData;
    private List<highEnclosureRes>highEnclosureRes;

    public List<WaybillEncloDto.highEnclosureRes> getHighEnclosureRes() {
        return highEnclosureRes;
    }

    public void setHighEnclosureRes(List<WaybillEncloDto.highEnclosureRes> highEnclosureRes) {
        this.highEnclosureRes = highEnclosureRes;
    }

    public String getSjId() {
        return sjId;
    }

    public void setSjId(String sjId) {
        this.sjId = sjId;
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

    public List<yundanData> getUnStartData() {
        return unStartData;
    }

    public void setUnStartData(List<yundanData> unStartData) {
        this.unStartData = unStartData;
    }

    public List<yundanData> getStartData() {
        return startData;
    }

    public void setStartData(List<yundanData> startData) {
        this.startData = startData;
    }

    public class yundanData{
        private String id;//派车单ID
        private String status;//派车单状态 1待执行 2执行中 3已完成
        private List<wayBillId>wayBillId;
        private int sendorreceive;

        public int getSendorreceive() {
            return sendorreceive;
        }

        public void setSendorreceive(int sendorreceive) {
            this.sendorreceive = sendorreceive;
        }
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<WaybillEncloDto.wayBillId> getWayBillId() {
            return wayBillId;
        }

        public void setWayBillId(List<WaybillEncloDto.wayBillId> wayBillId) {
            this.wayBillId = wayBillId;
        }
    }

    public class wayBillId{
        private String id;
        private String wayBillId;
        private String transportStatus;
        private String fromUserAddress;
        private String fromLocation;
        private int fromEnclosureType;
        private int fromErrorRange;
        private int fromRadius;
        private String fromRegion;
        private String toUserAddress;
        private String toLocation;
        private int toEnclosureType;
        private int toErrorRange;
        private int toRadius;
        private String toRegion;
        private int sjSignIn;

        public int getSjSignIn() {
            return sjSignIn;
        }

        public void setSjSignIn(int sjSignIn) {
            this.sjSignIn = sjSignIn;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWayBillId() {
            return wayBillId;
        }

        public void setWayBillId(String wayBillId) {
            this.wayBillId = wayBillId;
        }

        public String getTransportStatus() {
            return transportStatus;
        }

        public void setTransportStatus(String transportStatus) {
            this.transportStatus = transportStatus;
        }

        public String getFromUserAddress() {
            return fromUserAddress;
        }

        public void setFromUserAddress(String fromUserAddress) {
            this.fromUserAddress = fromUserAddress;
        }

        public String getFromLocation() {
            return fromLocation;
        }

        public void setFromLocation(String fromLocation) {
            this.fromLocation = fromLocation;
        }

        public int getFromEnclosureType() {
            return fromEnclosureType;
        }

        public void setFromEnclosureType(int fromEnclosureType) {
            this.fromEnclosureType = fromEnclosureType;
        }

        public int getFromErrorRange() {
            return fromErrorRange;
        }

        public void setFromErrorRange(int fromErrorRange) {
            this.fromErrorRange = fromErrorRange;
        }

        public int getFromRadius() {
            return fromRadius;
        }

        public void setFromRadius(int fromRadius) {
            this.fromRadius = fromRadius;
        }

        public String getFromRegion() {
            return fromRegion;
        }

        public void setFromRegion(String fromRegion) {
            this.fromRegion = fromRegion;
        }

        public String getToUserAddress() {
            return toUserAddress;
        }

        public void setToUserAddress(String toUserAddress) {
            this.toUserAddress = toUserAddress;
        }

        public String getToLocation() {
            return toLocation;
        }

        public void setToLocation(String toLocation) {
            this.toLocation = toLocation;
        }

        public int getToEnclosureType() {
            return toEnclosureType;
        }

        public void setToEnclosureType(int toEnclosureType) {
            this.toEnclosureType = toEnclosureType;
        }

        public int getToErrorRange() {
            return toErrorRange;
        }

        public void setToErrorRange(int toErrorRange) {
            this.toErrorRange = toErrorRange;
        }

        public int getToRadius() {
            return toRadius;
        }

        public void setToRadius(int toRadius) {
            this.toRadius = toRadius;
        }

        public String getToRegion() {
            return toRegion;
        }

        public void setToRegion(String toRegion) {
            this.toRegion = toRegion;
        }
    }

    public class highEnclosureRes{
        private String id;
        private String enclosureName;
        private int errorRange;
        private  String location;
        private String enclosureType;
        private String roleName;
        private String proId;
        private int radius;
        private String companyId;
        private String waybillId;

        public String getWaybillId() {
            return waybillId;
        }

        public void setWaybillId(String waybillId) {
            this.waybillId = waybillId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEnclosureName() {
            return enclosureName;
        }

        public void setEnclosureName(String enclosureName) {
            this.enclosureName = enclosureName;
        }

        public int getErrorRange() {
            return errorRange;
        }

        public void setErrorRange(int errorRange) {
            this.errorRange = errorRange;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getEnclosureType() {
            return enclosureType;
        }

        public void setEnclosureType(String enclosureType) {
            this.enclosureType = enclosureType;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getProId() {
            return proId;
        }

        public void setProId(String proId) {
            this.proId = proId;
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }
    }
}
