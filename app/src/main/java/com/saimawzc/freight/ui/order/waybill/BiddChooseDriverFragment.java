package com.saimawzc.freight.ui.order.waybill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.order.CarDriverAdpater;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.order.CarDriverDto;
import com.saimawzc.freight.dto.order.CarInfolDto;
import com.saimawzc.freight.presenter.order.CarDriverPresenter;
import com.saimawzc.freight.view.order.SendDriverView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/****
 *
 * */
public class BiddChooseDriverFragment extends
        BaseFragment implements SendDriverView {

    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch) LinearLayout llSearch;
    @BindView(R.id.tvSearch) TextView tvSearch;
    private List<CarDriverDto>mDatas=new ArrayList<>();
    @BindView(R.id.SwipeRefreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv)RecyclerView rv;
    private CarDriverAdpater adapter;
    private CarDriverPresenter presenter;
    private int currentTag=0;
    CarInfolDto.carInfoData data;
    private String type;
    private String id;
    private int trantType=1;//1司机 2船员
    private int CHOOSE_DRIVER=1001;
    @OnClick({R.id.tvCannel,R.id.llSearch})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvCannel:
                context.finish();
                break;
            case R.id.llSearch:
                presenter.getData(edSearch.getText().toString(),trantType,"","");
                break;
        }
    }
    @Override
    public int initContentView() {
        return R.layout.fragment_bidd_choosedriver;
    }

    @Override
    public void initView() {

        mContext=getActivity();
        adapter=new CarDriverAdpater(mDatas,mContext);
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter=new CarDriverPresenter(this,mContext);
        presenter.getData(edSearch.getText().toString(),trantType,"","");
        edSearch.addTextChangedListener(textWatcher);
    }

    @Override
    public void initData() {


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               mDatas.clear();
                presenter.getData(edSearch.getText().toString(),trantType,"","");
            }
        });

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Intent intent=new Intent();
                intent.putExtra("data",mDatas.get(position));
                context.setResult(RESULT_OK, intent);
                context. finish();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });


    }


    @Override
    public void getDriverList(List<CarDriverDto> dtos) {
        if(!TextUtils.isEmpty(edSearch.getText().toString())){
            llSearch.setVisibility(View.GONE);
        }
        if(mDatas!=null){
            mDatas.clear();
        }
        adapter.addMoreData(dtos);
    }
    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);

    }

    @Override
    public void isLastPage(boolean isLastPage) {

    }

    @Override
    public void showLoading() {
        context.showLoadingDialog();
    }

    @Override
    public void dissLoading() {
        context.dismissLoadingDialog();
    }

    @Override
    public void Toast(String str) {
        context.showMessage(str);
    }

    @Override
    public void oncomplete() {

    }

    @Override
    public BaseActivity getContect() {
        return context;
    }

    /**
     * 监听输入框
     */
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void afterTextChanged(Editable editable) {
            //控制登录按钮是否可点击状态
            if (!TextUtils.isEmpty(edSearch.getText().toString())) {
                llSearch.setVisibility(View.VISIBLE);
                tvSearch.setText(edSearch.getText().toString());
            } else {
                llSearch.setVisibility(View.GONE);
            }
        }
    };


}
