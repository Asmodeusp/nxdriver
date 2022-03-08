package com.saimawzc.freight.dto.wallet;

import android.text.TextUtils;

public class TradeChooseDto {
    private int page;
    private String startTime;
    private String endTime;
    private String minMoney;
    private String maxMoney;
    private String transType;
    private String sortType;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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

    public String getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(String minMoney) {
        this.minMoney = minMoney;
    }

    public String getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(String maxMoney) {
        this.maxMoney = maxMoney;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    @Override
    public String toString() {
        return "TradeChooseDto{" +
                "page=" + page +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", minMoney='" + minMoney + '\'' +
                ", maxMoney='" + maxMoney + '\'' +
                ", transType='" + transType + '\'' +
                ", sortType='" + sortType + '\'' +
                '}';
    }
    public boolean isEmpty(){
        if(TextUtils.isEmpty(startTime)&&TextUtils.isEmpty(endTime)&&TextUtils.isEmpty(minMoney)
        &&TextUtils.isEmpty(maxMoney)&&TextUtils.isEmpty(transType)&&TextUtils.isEmpty(sortType)){
            return true;

        }else {
            return false;
        }

    }
}
