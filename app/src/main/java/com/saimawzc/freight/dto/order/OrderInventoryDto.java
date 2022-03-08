package com.saimawzc.freight.dto.order;

import java.util.List;

/***
 *
 * **/

public class OrderInventoryDto {
    private String toProName;
    private String toCityName;

    private String fromProName;
    private String fromCityName;
    private List<qdData> list;

    public String getToProName() {
        return toProName;
    }

    public void setToProName(String toProName) {
        this.toProName = toProName;
    }

    public String getToCityName() {
        return toCityName;
    }

    public void setToCityName(String toCityName) {
        this.toCityName = toCityName;
    }

    public String getFromProName() {
        return fromProName;
    }

    public void setFromProName(String fromProName) {
        this.fromProName = fromProName;
    }

    public String getFromCityName() {
        return fromCityName;
    }

    public void setFromCityName(String fromCityName) {
        this.fromCityName = fromCityName;
    }

    public List<qdData> getList() {
        return list;
    }

    public void setList(List<qdData> list) {
        this.list = list;
    }

    public class qdData{
        private String materialsName;
        private String weight;
        private String id;
        private String materialsId;
        private int unit;
        private String price;

        public String getMaterialsName() {
            return materialsName;
        }

        public void setMaterialsName(String materialsName) {
            this.materialsName = materialsName;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
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

        public int getUnit() {
            return unit;
        }

        public void setUnit(int unit) {
            this.unit = unit;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
