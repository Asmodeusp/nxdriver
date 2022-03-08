package com.saimawzc.freight.dto.order.waybill;

import java.io.Serializable;

/****
 * 发货商 收货商
 * */

public class GoodsCompanyDto implements Serializable {

    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
