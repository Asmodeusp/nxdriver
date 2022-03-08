package com.saimawzc.freight.dto;

public class LcInfoDto {
    private String lcbh;


    /**
     * 料仓名称
     */
    private String lcmc;


    /**
     * 料仓状态
     */
    private String lczt;


    /**
     * 物料名称
     */
    private String wlmc;


    /**
     * 物料规格
     */
    private String wlgg;


    /**
     * 料位信息
     */
    private String lwxx;


    /**
     * 可卸料量
     */
    private String kxll;

    public String getLczt() {
        return lczt;
    }

    public void setLczt(String lczt) {
        this.lczt = lczt;
    }
}
