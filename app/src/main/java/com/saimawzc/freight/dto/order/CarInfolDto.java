package com.saimawzc.freight.dto.order;
import java.io.Serializable;
import java.util.List;

/***
 * 车辆详情
 * **/
public class CarInfolDto {

    private  boolean isLastPage;


    private List<carInfoData>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<carInfoData> getList() {
        return list;
    }

    public void setList(List<carInfoData> list) {
        this.list = list;
    }

    public class  carInfoData implements Serializable {
        private String id;
        private String carNo;
        private String carTypeId;
        private String carTypeName;
        private String yutiNum;
        private String shipName;//如果是船的话新增船舶名称
        private Integer isBlackList;
        private Integer ifDisable;
        private String hzUserName;

        public Integer getIsBlackList() {
            if (isBlackList!=null) {
                return isBlackList;
            }else {
                return 0;
            }

        }

        public void setIsBlackList(Integer isBlackList) {
            this.isBlackList = isBlackList;
        }

        public Integer getIfDisable() {
            if (ifDisable!=null) {
                return ifDisable;
            }else {
                return 1;
            }
        }

        public void setIfDisable(Integer ifDisable) {
            this.ifDisable = ifDisable;
        }

        public String getHzUserName() {
            if (ifDisable!=null) {
                return hzUserName;
            }else {
                return "";
            }
        }

        public void setHzUserName(String hzUserName) {
            this.hzUserName = hzUserName;
        }
        public String getShipName() {
            return shipName;
        }

        public void setShipName(String shipName) {
            this.shipName = shipName;
        }

        public String getYutiNum() {
            return yutiNum;
        }

        public void setYutiNum(String yutiNum) {
            this.yutiNum = yutiNum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCarNo() {
            return carNo;
        }

        public void setCarNo(String carNo) {
            this.carNo = carNo;
        }

        public String getCarTypeId() {
            return carTypeId;
        }

        public void setCarTypeId(String carTypeId) {
            this.carTypeId = carTypeId;
        }

        public String getCarTypeName() {
            return carTypeName;
        }

        public void setCarTypeName(String carTypeName) {
            this.carTypeName = carTypeName;
        }
    }



}
