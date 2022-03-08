package com.saimawzc.freight.dto.wallet;

import java.io.Serializable;
import java.util.List;

public class SonAccountDto implements Serializable {
    private String fundAcc;
    private String fundAccName;
    private double balance;
    private double useBala;
    private double cashBala;
    private List<bankList>bankList;

    public String getFundAcc() {
        return fundAcc;
    }

    public void setFundAcc(String fundAcc) {
        this.fundAcc = fundAcc;
    }

    public String getFundAccName() {
        return fundAccName;
    }

    public void setFundAccName(String fundAccName) {
        this.fundAccName = fundAccName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getUseBala() {
        return useBala;
    }

    public void setUseBala(double useBala) {
        this.useBala = useBala;
    }

    public double getCashBala() {
        return cashBala;
    }

    public void setCashBala(double cashBala) {
        this.cashBala = cashBala;
    }

    public List<SonAccountDto.bankList> getBankList() {
        return bankList;
    }

    public void setBankList(List<SonAccountDto.bankList> bankList) {
        this.bankList = bankList;
    }

    public  class  bankList implements Serializable{
        private String bankNo;
        private String bankName;
        private String cardNo;
        private int  defaultFlag;
        private String mobile;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getBankNo() {
            return bankNo;
        }

        public void setBankNo(String bankNo) {
            this.bankNo = bankNo;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public int getDefaultFlag() {
            return defaultFlag;
        }

        public void setDefaultFlag(int defaultFlag) {
            this.defaultFlag = defaultFlag;
        }
    }

}
