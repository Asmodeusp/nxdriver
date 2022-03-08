package com.saimawzc.freight.dto.sendcar;

import java.util.List;

public class TrantDto {
    private String id;
    private String fromUserAddress;
    private String fromLocation;
    private String toUserAddress;
    private String toLocation;
    private String carNo;
    private float carLength;
    private float carWigth;
    private  float carHeigth;
    private int carColor;
    private float carLoad;
    private List<DriverTransDto>waybillList;
    private List<TrantProcessDto>transportStatusList;

    private int tranType;



    public int getTranType() {
        return tranType;
    }

    public void setTranType(int tranType) {
        this.tranType = tranType;
    }

    private int fromEnclosureType;//发货围栏类型（1.原型围栏 2.行政区划围栏 3.多边形围栏）
    private int fromErrorRange;//发货围栏误差范围 默认50
    private int fromRadius;//发货围栏半径（圆型围栏必填 默认3000）
    private int fromAddressType;//发货地址类型（1-地址 2-围栏）

    private int toEnclosureType;
    private int toErrorRange;
    private int toRadius;
    private  int  toAddressType;




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

    public int getFromAddressType() {
        return fromAddressType;
    }

    public void setFromAddressType(int fromAddressType) {
        this.fromAddressType = fromAddressType;
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

    public int getToAddressType() {
        return toAddressType;
    }

    public void setToAddressType(int toAddressType) {
        this.toAddressType = toAddressType;
    }

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

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public float getCarLength() {
        return carLength;
    }

    public void setCarLength(float carLength) {
        this.carLength = carLength;
    }

    public float getCarWigth() {
        return carWigth;
    }

    public void setCarWigth(float carWigth) {
        this.carWigth = carWigth;
    }

    public float getCarHeigth() {
        return carHeigth;
    }

    public void setCarHeigth(float carHeigth) {
        this.carHeigth = carHeigth;
    }

    public void setCarLoad(float carLoad) {
        this.carLoad = carLoad;
    }

    public int getCarColor() {
        return carColor;
    }

    public void setCarColor(int carColor) {
        this.carColor = carColor;
    }

    public float getCarLoad() {
        return carLoad;
    }


    public List<DriverTransDto> getWaybillList() {
        return waybillList;
    }

    public void setWaybillList(List<DriverTransDto> waybillList) {
        this.waybillList = waybillList;
    }

    public List<TrantProcessDto> getTransportStatusList() {
        return transportStatusList;
    }

    public void setTransportStatusList(List<TrantProcessDto> transportStatusList) {
        this.transportStatusList = transportStatusList;
    }
}
