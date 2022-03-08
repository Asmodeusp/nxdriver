package com.saimawzc.freight.dto.my.car;

import java.util.List;

/**
 * Created by Administrator on 2020/8/8.
 */

public class MyCarDto {
    private int pageNum;
    int total;
    private boolean isLastPage;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    private List<SearchCarDto>list;

    public List<SearchCarDto> getList() {
        return list;
    }

    public void setList(List<SearchCarDto> list) {
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
