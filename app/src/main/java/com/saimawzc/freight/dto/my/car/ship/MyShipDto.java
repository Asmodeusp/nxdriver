package com.saimawzc.freight.dto.my.car.ship;

import java.util.List;

/**
 * Created by Administrator on 2020/8/8.
 */

public class MyShipDto {
    private int pageNum;
    int total;
    private boolean isLastPage;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    private List<SearchShipDto>list;

    public List<SearchShipDto> getList() {
        return list;
    }

    public void setList(List<SearchShipDto> list) {
        this.list = list;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
