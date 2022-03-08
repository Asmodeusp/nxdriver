package com.saimawzc.freight.dto.sendcar;

import java.util.List;

public class WarnInfoDto {


    private String wayBillNo;
    private String sjName;
    private String carNo;
    private String sjPhone;

    public String getSjPhone() {
        return sjPhone;
    }

    public void setSjPhone(String sjPhone) {
        this.sjPhone = sjPhone;
    }

    private List<worninfotLog>warnInfoRes;

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

    public List<worninfotLog> getTransportLogList() {
        return warnInfoRes;
    }

    public void setTransportLogList(List<worninfotLog> transportLogList) {
        this.warnInfoRes = transportLogList;
    }

    public class worninfotLog{
        String id;
        private String waybillId;
        private String content;
        String createTime;
        int warnType;

        public int getWarnType() {
            return warnType;
        }

        public void setWarnType(int warnType) {
            this.warnType = warnType;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }



}
