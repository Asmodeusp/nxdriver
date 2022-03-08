package com.saimawzc.freight.dto.account;

import java.util.List;

/****
 * 分页查询我的结算单我的结算
 * **/
public class MySetmentPageQueryDto {
    private boolean isLastPage;
    private List<MySetmentDto>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<MySetmentDto> getList() {
        return list;
    }

    public void setList(List<MySetmentDto> list) {
        this.list = list;
    }
}
