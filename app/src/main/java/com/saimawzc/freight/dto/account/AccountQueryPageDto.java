package com.saimawzc.freight.dto.account;

import java.util.List;

/****
 * 对账管理
 * **/
public class AccountQueryPageDto {

    private boolean isLastPage;

    private List<ReconclitionDto>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<ReconclitionDto> getList() {
        return list;
    }

    public void setList(List<ReconclitionDto> list) {
        this.list = list;
    }
}
