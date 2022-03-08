package com.saimawzc.freight.modle.mine.carleader.imple;

import com.saimawzc.freight.dto.my.queue.CarQueueDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.mine.carleader.CarQueueModel;
import com.saimawzc.freight.view.mine.queue.MyQueueView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;
/**
 * Created by Administrator on 2020/8/10.
 *
 */

public class CarQueueModelImple extends BasEModeImple implements CarQueueModel {


    @Override
    public void getData(final MyQueueView view, int page, int status) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            jsonObject.put("status",status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getQueue(body).enqueue(new CallBack<CarQueueDto>() {
            @Override
            public void success(CarQueueDto response) {
                view.dissLoading();
                view.getQueue(response);
                view.stopRefresh();

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
                view.stopRefresh();
            }
        });
    }
}
