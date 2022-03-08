package com.saimawzc.freight.dto.order;

import java.util.List;

/****
 *
 * **/
public class OrderManageRoleDto {

    private String id;
    private String fromUserAddress;
    private String toUserAddress;

    private String fromLocation;
    private String toLocation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public List<mapData> getList() {
        return list;
    }

    public void setList(List<mapData> list) {
        this.list = list;
    }

    private List<mapData>list;
    private List<totalPosition>locations;

    public List<totalPosition> getLocations() {
        return locations;
    }

    public void setLocations(List<totalPosition> locations) {
        this.locations = locations;
    }

    public class mapData{
        private String id;
        private String wayBillNo;
        private String hzName;
        private String materialsName;
        private double totalWeight;
        private String fromUserAddress;
        private String toUserAddress;
        private String fromLocation;
        private String toLocation;
        private List<totalPosition>locations;

        public List<totalPosition> getLocations() {
            return locations;
        }

        public void setLocations(List<totalPosition> locations) {
            this.locations = locations;
        }

        public String getFromLocation() {
            return fromLocation;
        }

        public void setFromLocation(String fromLocation) {
            this.fromLocation = fromLocation;
        }

        public String getToLocation() {
            return toLocation;
        }

        public void setToLocation(String toLocation) {
            this.toLocation = toLocation;
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

        public String getMaterialsName() {
            return materialsName;
        }

        public void setMaterialsName(String materialsName) {
            this.materialsName = materialsName;
        }

        public double getTotalWeight() {
            return totalWeight;
        }

        public void setTotalWeight(double totalWeight) {
            this.totalWeight = totalWeight;
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
    }

    public class totalPosition{

        private double latitude;
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
