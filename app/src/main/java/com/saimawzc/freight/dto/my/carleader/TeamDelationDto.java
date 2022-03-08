package com.saimawzc.freight.dto.my.carleader;

import android.support.v4.view.PagerAdapter;

import com.saimawzc.freight.weight.utils.preference.Preference;

import java.util.List;

public class TeamDelationDto {
    private String bankName;
    private String cardId;
    private String clientName;
    private String idCardNum;
    private List<TeamList>list;

    public class TeamList{
        private String carNo;
        private String sjName;
        private String sjPhone;
        private int status;
        private String picture;
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getCarNo() {
            return carNo;
        }

        public void setCarNo(String carNo) {
            this.carNo = carNo;
        }

        public String getSjName() {
            return sjName;
        }

        public void setSjName(String sjName) {
            this.sjName = sjName;
        }

        public String getSjPhone() {
            return sjPhone;
        }

        public void setSjPhone(String sjPhone) {
            this.sjPhone = sjPhone;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public List<TeamList> getList() {
        return list;
    }

    public void setList(List<TeamList> list) {
        this.list = list;
    }
}
