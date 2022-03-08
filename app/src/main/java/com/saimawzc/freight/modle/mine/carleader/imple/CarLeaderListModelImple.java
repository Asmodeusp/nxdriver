package com.saimawzc.freight.modle.mine.carleader.imple;

import com.saimawzc.freight.dto.my.carleader.CarLeaderListDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.mine.carleader.CarLeaderModel;
import com.saimawzc.freight.view.mine.carleader.CarLeaderListView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;
/**
 * Created by Administrator on 2020/8/10.
 *
 */

public class CarLeaderListModelImple extends BasEModeImple implements CarLeaderModel {


    @Override
    public void getData(final CarLeaderListView view, int page) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            jsonObject.put("pageSize",20);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getCarLeaderList(body).enqueue(new CallBack<CarLeaderListDto>() {
            @Override
            public void success(CarLeaderListDto response) {
                view.dissLoading();
                view.getList(response);

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);

            }
        });
    }
}
