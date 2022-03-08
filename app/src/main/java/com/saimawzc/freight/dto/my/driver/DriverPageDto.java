package com.saimawzc.freight.dto.my.driver;

import java.util.List;

/**
 * Created by Administrator on 2020/8/10.
 */

public class DriverPageDto {
    private int pageNum;
    int total;
    private boolean isLastPage;
    private List<MyDriverDto>list;

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

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<MyDriverDto> getList() {
        return list;
    }

    public void setList(List<MyDriverDto> list) {
        this.list = list;
    }
}
