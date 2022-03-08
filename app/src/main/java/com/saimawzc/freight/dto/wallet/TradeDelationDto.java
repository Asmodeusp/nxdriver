package com.saimawzc.freight.dto.wallet;

import java.util.List;

public class TradeDelationDto {

    private boolean isLastPage;
    private List<data>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<data> getList() {
        return list;
    }

    public void setList(List<data> list) {
        this.list = list;
    }

    public  class  data{
        private String thirdNo;
        private String payFundAccName;
        private String colFundAccName;
        private String money;//交易金额
        private String outputTime;
        private String state;
        private String remark;
        private String balance;//交易后余额
        private String cashBala;//交易后可取余额
        private int transType;
        private String payFundAcc;

        public String getPayFundAcc() {
            return payFundAcc;
        }

        public void setPayFundAcc(String payFundAcc) {
            this.payFundAcc = payFundAcc;
        }

        public String getThirdNo() {
            return thirdNo;
        }

        public void setThirdNo(String thirdNo) {
            this.thirdNo = thirdNo;
        }

        public String getPayFundAccName() {
            return payFundAccName;
        }

        public void setPayFundAccName(String payFundAccName) {
            this.payFundAccName = payFundAccName;
        }

        public String getColFundAccName() {
            return colFundAccName;
        }

        public void setColFundAccName(String colFundAccName) {
            this.colFundAccName = colFundAccName;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getOutputTime() {
            return outputTime;
        }

        public void setOutputTime(String outputTime) {
            this.outputTime = outputTime;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getCashBala() {
            return cashBala;
        }

        public void setCashBala(String cashBala) {
            this.cashBala = cashBala;
        }

        public int getTransType() {
            return transType;
        }

        public void setTransType(int transType) {
            this.transType = transType;
        }
    }


}
