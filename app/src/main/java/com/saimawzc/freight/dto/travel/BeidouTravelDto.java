package com.saimawzc.freight.dto.travel;

import android.graphics.Paint;

import java.util.List;

public class BeidouTravelDto {
    private String id;
    private String carNo;
    private String fromUserAddress;
    private String fromLocation;
    private String toUserAddress;
    private String toLocation;
    private List<trackInfo>trackInfo;

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

    public List<BeidouTravelDto.trackInfo> getTrackInfo() {
        return trackInfo;
    }

    public void setTrackInfo(List<BeidouTravelDto.trackInfo> trackInfo) {
        this.trackInfo = trackInfo;
    }

    public class  trackInfo{
        double lat;
        double lon;
        private String gtm;
        private String spd;
        private String mlg;
        private String hgt;
        private String agl;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public String getGtm() {
            return gtm;
        }

        public void setGtm(String gtm) {
            this.gtm = gtm;
        }

        public String getSpd() {
            return spd;
        }

        public void setSpd(String spd) {
            this.spd = spd;
        }

        public String getMlg() {
            return mlg;
        }

        public void setMlg(String mlg) {
            this.mlg = mlg;
        }

        public String getHgt() {
            return hgt;
        }

        public void setHgt(String hgt) {
            this.hgt = hgt;
        }

        public String getAgl() {
            return agl;
        }

        public void setAgl(String agl) {
            this.agl = agl;
        }
    }
}
