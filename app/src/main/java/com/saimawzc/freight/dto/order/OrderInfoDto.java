package com.saimawzc.freight.dto.order;

public class OrderInfoDto {
    private String imgUrl;
    private String goodsName;
    private String userName;
    private String goodsNum;
    private String biddtime;
    private int allownums;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getBiddtime() {
        return biddtime;
    }

    public void setBiddtime(String biddtime) {
        this.biddtime = biddtime;
    }

    public int getAllownums() {
        return allownums;
    }

    public void setAllownums(int allownums) {
        this.allownums = allownums;
    }
}
