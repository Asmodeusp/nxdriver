package com.saimawzc.freight.modle.order.modelImple.taxi;



import com.saimawzc.freight.dto.login.TaxiAreaDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.order.modle.taxi.TaxiAdressModel;
import com.saimawzc.freight.view.order.taxi.TaxiAdressView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TaxiAdressModelImple extends BasEModeImple implements TaxiAdressModel {


    @Override
    public void getAdressList(final TaxiAdressView view, String pid) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pid",pid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getAreaTaxi(body).enqueue(new CallBack<List<TaxiAreaDto>>() {
            @Override
            public void success(List<TaxiAreaDto> response) {
                view.dissLoading();
                view.getadressList(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
