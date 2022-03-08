package com.saimawzc.freight.dto.account;

import java.util.List;

public class WaitComfirmQueryPageDto {

   private boolean isLastPage;
   private List<WaitComfirmAccountDto>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<WaitComfirmAccountDto> getList() {
        return list;
    }

    public void setList(List<WaitComfirmAccountDto> list) {
        this.list = list;
    }
}
