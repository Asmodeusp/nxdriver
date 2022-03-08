package com.saimawzc.freight.dto.order.waybill;

import java.util.List;

public class RankPageDto {
    private boolean isLastPage;
    private List<rankDto>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<rankDto> getList() {
        return list;
    }

    public void setList(List<rankDto> list) {
        this.list = list;
    }

    public class rankDto{
        private String biddPrice;

        public String getBiddPrice() {
            return biddPrice;
        }

        public void setBiddPrice(String biddPrice) {
            this.biddPrice = biddPrice;
        }
    }
}
