package com.saimawzc.freight.dto.order.waybill;


/**
 * 新增订单货物
 * */
public class AddWayBillGoodsDto {

    private GoodsCompanyDto goodsCompanyDto;
    private double goodNum;
    private  double goodPrice;
    private double goodprice_two;

    public double getGoodprice_two() {
        return goodprice_two;
    }

    public void setGoodprice_two(double goodprice_two) {
        this.goodprice_two = goodprice_two;
    }

    private int util;
    private String unitName;
    private int bussType;

    public int getBussType() {
        return bussType;
    }

    public void setBussType(int bussType) {
        this.bussType = bussType;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public GoodsCompanyDto getGoodsCompanyDto() {
        return goodsCompanyDto;
    }

    public void setGoodsCompanyDto(GoodsCompanyDto goodsCompanyDto) {
        this.goodsCompanyDto = goodsCompanyDto;
    }

    public double getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(double goodNum) {
        this.goodNum = goodNum;
    }

    public double getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(double goodPrice) {
        this.goodPrice = goodPrice;
    }

    public int getUtil() {
        return util;
    }

    public void setUtil(int util) {
        this.util = util;
    }
}
