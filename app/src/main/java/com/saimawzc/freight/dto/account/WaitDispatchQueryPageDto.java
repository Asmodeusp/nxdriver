package com.saimawzc.freight.dto.account;

import java.util.List;

/**
 * 待结算派车单
 * **/
public class WaitDispatchQueryPageDto {
   private boolean isLastPage;
   private List<WaitDispatchDto>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<WaitDispatchDto> getList() {
        return list;
    }

    public void setList(List<WaitDispatchDto> list) {
        this.list = list;
    }
}
