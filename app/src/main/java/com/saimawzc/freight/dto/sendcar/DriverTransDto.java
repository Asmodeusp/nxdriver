package com.saimawzc.freight.dto.sendcar;

import java.io.Serializable;
import java.util.Arrays;

public class DriverTransDto implements Serializable {
    private String id;
    private String wayBillNo;
    private String wayBillId;
    private String materialsNames;
    private String fromUserAddress;
    private String fromLocation;
    private String toUserAddress;
    private String toLocation;
    private String cardId;
    private String takeCardPwd;
    private int limitCar;
    private int nowPosition;
    private boolean isLineUp;

    private int fromEnclosureType;//发货围栏类型（1.原型围栏 2.行政区划围栏 3.多边形围栏）
    private int fromErrorRange;//发货围栏误差范围 默认50
    private int fromRadius;//发货围栏半径（圆型围栏必填 默认3000）
    private int fromAddressType;//发货地址类型（1-地址 2-围栏）
    private String fromRegion;
    private String context;
    private int fenceClock;//是否围栏内签收


    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
    }

    private String thirdOrderNo;//第三方单号

    private int cancelOrder;// 0-未撤单 1-撤单中 2-撤单成功 3-撤单失败

    private String poundAlarm;//是否开启磅单预警
    private int  highEnclosureAlarm;//是否开启高危围栏

    public int getHighEnclosureAlarm() {
        return highEnclosureAlarm;
    }

    public void setHighEnclosureAlarm(int highEnclosureAlarm) {
        this.highEnclosureAlarm = highEnclosureAlarm;
    }

    public String getPoundAlarm() {
        return poundAlarm;
    }

    public void setPoundAlarm(String poundAlarm) {
        this.poundAlarm = poundAlarm;
    }

    public int getCancelOrder() {
        return cancelOrder;
    }

    public void setCancelOrder(int cancelOrder) {
        this.cancelOrder = cancelOrder;
    }

    public int getFenceClock() {
        return fenceClock;
    }

    public void setFenceClock(int fenceClock) {
        this.fenceClock = fenceClock;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFromRegion() {
        return fromRegion;
    }

    public void setFromRegion(String fromRegion) {
        this.fromRegion = fromRegion;
    }

    private int toEnclosureType;
    private int toErrorRange;
    private int toRadius;
    private  int  toAddressType;
    private int enclosureStatus;//1需要开启围栏  2不需要
    private int bindSmartLock;//否绑定智能物流锁(1-是 2-否)
    private int stopAlarm;
    private  int alarmTime;
    private int deviationAlarm;//是否开启偏离预警
    private String[]path;//偏离轨迹路线
    private double distance;
    private String toRegion;
    private int check;

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public String getToRegion() {
        return toRegion;
    }

    public void setToRegion(String toRegion) {
        this.toRegion = toRegion;
    }

    public int getBindSmartLock() {
        return bindSmartLock;
    }

    public void setBindSmartLock(int bindSmartLock) {
        this.bindSmartLock = bindSmartLock;
    }

    public String getWayBillId() {
        return wayBillId;
    }

    public void setWayBillId(String wayBillId) {
        this.wayBillId = wayBillId;
    }

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

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getTakeCardPwd() {
        return takeCardPwd;
    }

    public void setTakeCardPwd(String takeCardPwd) {
        this.takeCardPwd = takeCardPwd;
    }

    public int getEnclosureStatus() {
        return enclosureStatus;
    }

    public void setEnclosureStatus(int enclosureStatus) {
        this.enclosureStatus = enclosureStatus;
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

    public String getWayBillNo() {
        return wayBillNo;
    }

    public void setWayBillNo(String wayBillNo) {
        this.wayBillNo = wayBillNo;
    }

    public String getMaterialsNames() {
        return materialsNames;
    }

    public void setMaterialsNames(String materialsNames) {
        this.materialsNames = materialsNames;
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

    public int getLimitCar() {
        return limitCar;
    }

    public void setLimitCar(int limitCar) {
        this.limitCar = limitCar;
    }

    public int getNowPosition() {
        return nowPosition;
    }

    public void setNowPosition(int nowPosition) {
        this.nowPosition = nowPosition;
    }

    public boolean isLineUp() {
        return isLineUp;
    }

    public void setLineUp(boolean lineUp) {
        isLineUp = lineUp;
    }

    @Override
    public String toString() {
        return "DriverTransDto{" +
                "id='" + id + '\'' +
                ", wayBillNo='" + wayBillNo + '\'' +
                ", wayBillId='" + wayBillId + '\'' +
                ", materialsNames='" + materialsNames + '\'' +
                ", fromUserAddress='" + fromUserAddress + '\'' +
                ", fromLocation='" + fromLocation + '\'' +
                ", toUserAddress='" + toUserAddress + '\'' +
                ", toLocation='" + toLocation + '\'' +
                ", cardId='" + cardId + '\'' +
                ", takeCardPwd='" + takeCardPwd + '\'' +
                ", fromEnclosureType=" + fromEnclosureType +
                ", fromErrorRange=" + fromErrorRange +
                ", fromRadius=" + fromRadius +
                ", fromAddressType=" + fromAddressType +
                ", fromRegion='" + fromRegion + '\'' +
                ", toEnclosureType=" + toEnclosureType +
                ", toErrorRange=" + toErrorRange +
                ", toRadius=" + toRadius +
                ", toAddressType=" + toAddressType +
                ", enclosureStatus=" + enclosureStatus +
                ", bindSmartLock=" + bindSmartLock +
                ", stopAlarm=" + stopAlarm +
                ", alarmTime=" + alarmTime +
                ", deviationAlarm=" + deviationAlarm +
                ", path=" + Arrays.toString(path) +
                ", distance=" + distance +
                ", toRegion='" + toRegion + '\'' +
                ", check=" + check +
                '}';
    }
}
