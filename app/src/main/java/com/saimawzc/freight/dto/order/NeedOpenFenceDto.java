package com.saimawzc.freight.dto.order;

import java.util.List;

/***
 *
 * **/
public class NeedOpenFenceDto {


    private String sjId;
    private String sjName;
    private String carNo;
    private List<data>data;

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

    public List<NeedOpenFenceDto.data> getData() {
        return data;
    }

    public void setData(List<NeedOpenFenceDto.data> data) {
        this.data = data;
    }

    public class  data{
        private String id;
        private String sjId;
        private int  toEnclosureType;
        private int toErrorRange;
        private int toRadius;
        private  String toLocation;
        private int stopAlarm;//stopAlarm是否开启停留预警（1-是 2-否）
        private int alarmTime;//alarmTime报警时间（分钟）
        private String wayBillId;
        private int deviationAlarm;
        private String []path;
        private double distance;

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public int getDeviationAlarm() {
            return deviationAlarm;
        }

        public void setDeviationAlarm(int deviationAlarm) {
            this.deviationAlarm = deviationAlarm;
        }

        public String[] getPath() {
            return path;
        }

        public void setPath(String[] path) {
            this.path = path;
        }

        public String getWayBillId() {
            return wayBillId;
        }

        public void setWayBillId(String wayBillId) {
            this.wayBillId = wayBillId;
        }

        public int getStopAlarm() {
            return stopAlarm;
        }

        public void setStopAlarm(int stopAlarm) {
            this.stopAlarm = stopAlarm;
        }

        public int getAlarmTime() {
            return alarmTime;
        }

        public void setAlarmTime(int alarmTime) {
            this.alarmTime = alarmTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSjId() {
            return sjId;
        }

        public void setSjId(String sjId) {
            this.sjId = sjId;
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

        public String getToLocation() {
            return toLocation;
        }

        public void setToLocation(String toLocation) {
            this.toLocation = toLocation;
        }
    }






}
