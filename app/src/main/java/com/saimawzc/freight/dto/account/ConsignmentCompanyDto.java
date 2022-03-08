package com.saimawzc.freight.dto.account;

import java.io.Serializable;

/***
 * 托运公司
 * **/

public class ConsignmentCompanyDto implements Serializable {

    private String id;
    private String companyName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
