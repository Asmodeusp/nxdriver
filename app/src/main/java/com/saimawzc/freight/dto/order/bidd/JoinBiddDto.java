package com.saimawzc.freight.dto.order.bidd;

import android.graphics.Paint;

public class JoinBiddDto {
    private int biddNum;//总共的可以竞价次数
    private int overBiddNum;//剩余竞价次数
    private int useBiddNum;//已经竞价次数
    private double lastBiddPrice;
    private int rank;
    private String pointWeight;
    private String extent;//降价幅度
    private double floorPrice;//最低价格
    private double highWeight;

    private String needBeiDou;//是否需要北斗
    private String moreDispatch;//是否需要多次竞价
    private String carTypeId;//车辆类型ID
    private String carTypeName;//车辆名
    private String showRanking;//是否展示排名
    private String roleType;//竞价类型

    public double getHighWeight() {
        return highWeight;
    }

    public void setHighWeight(double highWeight) {
        this.highWeight = highWeight;
    }

    public double getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(double floorPrice) {
        this.floorPrice = floorPrice;
    }

    public String getPointWeight() {
        return pointWeight;
    }

    public String getExtent() {
        return extent;
    }

    public void setExtent(String extent) {
        this.extent = extent;
    }

    public void setPointWeight(String pointWeight) {
        this.pointWeight = pointWeight;
    }


    public int getBiddNum() {
        return biddNum;
    }

    public void setBiddNum(int biddNum) {
        this.biddNum = biddNum;
    }

    public int getOverBiddNum() {
        return overBiddNum;
    }

    public void setOverBiddNum(int overBiddNum) {
        this.overBiddNum = overBiddNum;
    }

    public int getUseBiddNum() {
        return useBiddNum;
    }

    public void setUseBiddNum(int useBiddNum) {
        this.useBiddNum = useBiddNum;
    }

    public double getLastBiddPrice() {
        return lastBiddPrice;
    }

    public void setLastBiddPrice(double lastBiddPrice) {
        this.lastBiddPrice = lastBiddPrice;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getNeedBeiDou() {
        return needBeiDou;
    }

    public void setNeedBeiDou(String needBeiDou) {
        this.needBeiDou = needBeiDou;
    }

    public String getMoreDispatch() {
        return moreDispatch;
    }

    public void setMoreDispatch(String moreDispatch) {
        this.moreDispatch = moreDispatch;
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

    public String getShowRanking() {
        return showRanking;
    }

    public void setShowRanking(String showRanking) {
        this.showRanking = showRanking;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
