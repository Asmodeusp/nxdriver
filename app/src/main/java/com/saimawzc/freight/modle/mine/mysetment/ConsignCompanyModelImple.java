package com.saimawzc.freight.modle.mine.mysetment;



import com.saimawzc.freight.dto.account.ConsignmentCompanyDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.view.mine.setment.ConsignCompanyView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.order.ConsignCompanyListener;

import java.util.List;

public class ConsignCompanyModelImple extends BasEModeImple implements ConsignSignCompanyModel {

    @Override
    public void getCompanyList(final ConsignCompanyView view, final ConsignCompanyListener listener) {
        view.showLoading();
        orderApi.getConsignmentCompanyList().enqueue(new CallBack<List<ConsignmentCompanyDto>>() {
            @Override
            public void success(List<ConsignmentCompanyDto> response) {
                view.dissLoading();
                listener.back(response);
                view.stopResh();
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
                view.stopResh();

            }
        });
    }


}
