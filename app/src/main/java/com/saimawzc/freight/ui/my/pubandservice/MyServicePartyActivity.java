package com.saimawzc.freight.ui.my.pubandservice;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.my.MyServiceAdapter;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.face.FaceQueryDto;
import com.saimawzc.freight.dto.taxi.service.MyServiceListDto;
import com.saimawzc.freight.presenter.order.taxi.MyServiceListPresenter;
import com.saimawzc.freight.view.order.taxi.service.MySeriviceListView;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

public class MyServicePartyActivity extends BaseActivity implements MySeriviceListView {
    private MyServiceAdapter myServiceAdapter;
    private String idCardNum;
    private List<MyServiceListDto>mDatas=new ArrayList<>();
    private MyServiceListPresenter presenter;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rv) RecyclerView rv;
    @Override
    protected int getViewId() {
        return R.layout.activity_myserivice;
    }

    @Override
    protected void init() {
        setToolbar(toolbar,"我的服务方");
        setNeedOnBus(true);
        userInfoDto=getUserInfoDto(userInfoDto);
        idCardNum=getIntent().getStringExtra("idcardNum");
        myServiceAdapter=new MyServiceAdapter(mDatas,this);
        presenter=new MyServiceListPresenter(this,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(myServiceAdapter);
        presenter.getData(idCardNum,userInfoDto.getRoleId());
    }
    @Override
    protected void initListener() {
        myServiceAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if(userInfoDto==null||mDatas.size()<=position){
                    return;
                }
                presenter.getFaceData(mDatas.get(position).getCycph(),idCardNum,userInfoDto);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }
    @OnClick({R.id.tvadd})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvadd:
                readyGo(ServiceSubmitActivity.class);
                break;
        }

    }

    @Override
    public void getList(final List<MyServiceListDto> dtos) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(dtos!=null){
                    mDatas.clear();
                    myServiceAdapter.addMoreData(dtos);
                }
            }
        });

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void getFaceDto(FaceQueryDto dto) {
        if(dto!=null){
            Bundle bundle=new Bundle();
            bundle.putSerializable("data",dto);
            readyGo(ServiceSubmitActivity.class,bundle);
        }
    }

    @Override
    public void showLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showLoadingDialog();
            }
        });


    }

    @Override
    public void dissLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissLoadingDialog();
            }
        });


    }

    @Override
    public void Toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showMessage(str);
            }
        });


    }

    @Override
    public void oncomplete() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RepeatMessa(String str) {
       if(!TextUtils.isEmpty(str)){
           if(str.equals(Constants.reshService)){
               userInfoDto=getUserInfoDto(userInfoDto);
               presenter.getData(idCardNum,userInfoDto.getRoleId());
           }
       }
    }
}
