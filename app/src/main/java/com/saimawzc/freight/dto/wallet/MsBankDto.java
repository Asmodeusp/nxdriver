package com.saimawzc.freight.dto.wallet;

import java.io.Serializable;

public class MsBankDto implements Serializable {
    private String bankNo;//银行编号
    private String bankName;//银行名称
    private String openBranch;//大额行号

    public String getOpenBranch() {
        return openBranch;
    }

    public void setOpenBranch(String openBranch) {
        this.openBranch = openBranch;
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
}
