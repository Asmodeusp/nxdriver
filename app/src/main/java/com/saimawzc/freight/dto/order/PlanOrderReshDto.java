package com.saimawzc.freight.dto.order;

public class PlanOrderReshDto {
    private double overWeight;
    private String takeCardWeight;
    private String underWay;
    private String consult;

    public double getOverWeight() {
        return overWeight;
    }

    public void setOverWeight(double overWeight) {
        this.overWeight = overWeight;
    }

    public String getTakeCardWeight() {
        return takeCardWeight;
    }

    public void setTakeCardWeight(String takeCardWeight) {
        this.takeCardWeight = takeCardWeight;
    }

    public String getUnderWay() {
        return underWay;
    }

    public void setUnderWay(String underWay) {
        this.underWay = underWay;
    }

    public String getConsult() {
        return consult;
    }

    public void setConsult(String consult) {
        this.consult = consult;
    }
}
