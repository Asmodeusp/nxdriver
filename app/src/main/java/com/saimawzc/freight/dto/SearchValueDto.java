package com.saimawzc.freight.dto;

import java.io.Serializable;

public class SearchValueDto implements Serializable {
    private String searchName;
    private String getSearchValue;

    public String getSearchName() {
        return searchName;
    }

    public SearchValueDto(String searchName, String getSearchValue) {
        this.searchName = searchName;
        this.getSearchValue = getSearchValue;
    }
    public SearchValueDto() {

    }
    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getGetSearchValue() {
        return getSearchValue;
    }

    public void setGetSearchValue(String getSearchValue) {
        this.getSearchValue = getSearchValue;
    }
}
