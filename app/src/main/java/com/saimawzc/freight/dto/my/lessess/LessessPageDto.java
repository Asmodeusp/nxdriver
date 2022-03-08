package com.saimawzc.freight.dto.my.lessess;

import com.saimawzc.freight.dto.my.driver.MyDriverDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/10.
 */

public class LessessPageDto {
    private int pageNum;
    int total;
    private boolean isLastPage;
    private List<MyLessessDto>list;

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

    public List<MyLessessDto> getList() {
        return list;
    }

    public void setList(List<MyLessessDto> list) {
        this.list = list;
    }
}
