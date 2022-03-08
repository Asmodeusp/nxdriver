package com.saimawzc.freight.modle.mine.carleader.imple;

import com.saimawzc.freight.dto.face.FaceQueryDto;
import com.saimawzc.freight.dto.my.carleader.TeamDelationDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.modle.mine.carleader.TeamDelationModel;
import com.saimawzc.freight.view.mine.carleader.TeamDelationView;
import com.saimawzc.freight.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/10.
 *
 */

public class TeamDelationModelImple extends BasEModeImple implements TeamDelationModel {




    @Override
    public void getData(final TeamDelationView view, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getTeamDelation(body).enqueue(new CallBack<TeamDelationDto>() {
            @Override
            public void success(TeamDelationDto response) {
                view.dissLoading();
                view.getDelation(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);

            }
        });
    }

    @Override
    public void getPerInfo(final TeamDelationView view, final String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getFaceINfo(body).enqueue(new CallBack<FaceQueryDto.Facedata>() {
            @Override
            public void success(FaceQueryDto.Facedata response) {
                view.dissLoading();
                view.getPersonifo(response,id);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);

            }
        });
    }
}
