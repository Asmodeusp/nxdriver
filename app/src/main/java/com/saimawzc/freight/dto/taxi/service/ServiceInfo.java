package com.saimawzc.freight.dto.taxi.service;

public class ServiceInfo {


    boolean success;
    String message;
    sfdata data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public sfdata getData() {
        return data;
    }

    public void setData(sfdata data) {
        this.data = data;
    }

    public class sfdata{
        private String frontIdCard;
        private String idCardNum;
        private String reverseIdCard;
        private String userName;

        public String getFrontIdCard() {
            return frontIdCard;
        }

        public void setFrontIdCard(String frontIdCard) {
            this.frontIdCard = frontIdCard;
        }

        public String getIdCardNum() {
            return idCardNum;
        }

        public void setIdCardNum(String idCardNum) {
            this.idCardNum = idCardNum;
        }

        public String getReverseIdCard() {
            return reverseIdCard;
        }

        public void setReverseIdCard(String reverseIdCard) {
            this.reverseIdCard = reverseIdCard;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }



}
