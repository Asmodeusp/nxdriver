package com.saimawzc.freight.dto.sendcar;

import java.util.List;

public class ArriverOrderDto {

    private List<materialsDto>materialsList;

    public List<materialsDto> getMaterialsList() {
        return materialsList;
    }

    public void setMaterialsList(List<materialsDto> materialsList) {
        this.materialsList = materialsList;
    }

    public class materialsDto{
        private String id;
        private String materialsId;
        private String materialsName;
        private double weight;
        private int unit;
        private String unitValue;
        private String signWeight;
        private double weighing;

        public double getWeighing() {
            return weighing;
        }

        public void setWeighing(double weighing) {
            this.weighing = weighing;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMaterialsId() {
            return materialsId;
        }

        public void setMaterialsId(String materialsId) {
            this.materialsId = materialsId;
        }

        public String getMaterialsName() {
            return materialsName;
        }

        public void setMaterialsName(String materialsName) {
            this.materialsName = materialsName;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public int getUnit() {
            return unit;
        }

        public void setUnit(int unit) {
            this.unit = unit;
        }

        public String getUnitValue() {
            return unitValue;
        }

        public void setUnitValue(String unitValue) {
            this.unitValue = unitValue;
        }

        public String getSignWeight() {
            return signWeight;
        }

        public void setSignWeight(String signWeight) {
            this.signWeight = signWeight;
        }
    }
}
