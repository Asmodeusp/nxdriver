package com.saimawzc.freight.dto.order;

import java.util.List;

public class CarDriverForPage {
    public boolean isLastPage;
    int total;
    public List<CarDriverDto>list;


    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CarDriverDto> getList() {
        return list;
    }

    public void setList(List<CarDriverDto> list) {
        this.list = list;
    }
}
