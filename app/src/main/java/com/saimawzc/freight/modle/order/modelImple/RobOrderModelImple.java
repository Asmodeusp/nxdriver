package com.saimawzc.freight.modle.order.modelImple;
import com.saimawzc.freight.dto.order.mainindex.RobOrderDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.RobOrderModel;
import com.saimawzc.freight.view.order.RobOrderView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.order.RobOrderListener;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RobOrderModelImple extends BasEModeImple implements RobOrderModel {






    @Override
    public void getRobLsit(final RobOrderView view, final RobOrderListener listener, int page) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getRobData(body).enqueue(new CallBack<RobOrderDto>() {
            @Override
            public void success(RobOrderDto response) {
                view.dissLoading();
                view.isLastPage(response.isLastPage());
                listener.getManageOrderList(response.getList());
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
