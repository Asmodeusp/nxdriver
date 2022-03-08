package com.saimawzc.freight.dto.sendcar;

public class TrantProcessDto {

    int code;
    private String name;
    boolean flag;
    String picture;
    boolean nextclockInFlag;
    boolean pictureFlag;

    boolean albumFlag;//是否允许拍照

    public boolean isAlbumFlag() {
        return albumFlag;
    }

    public void setAlbumFlag(boolean albumFlag) {
        this.albumFlag = albumFlag;
    }

    public boolean isPictureFlag() {
        return pictureFlag;
    }

    public void setPictureFlag(boolean pictureFlag) {
        this.pictureFlag = pictureFlag;
    }

    public boolean isNextclockInFlag() {
        return nextclockInFlag;
    }

    public void setNextclockInFlag(boolean nextclockInFlag) {
        this.nextclockInFlag = nextclockInFlag;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
