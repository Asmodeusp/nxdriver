package com.saimawzc.freight.ui.my.carrier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.my.carrier.MyCarrierAdapter;
import com.saimawzc.freight.adapter.my.driver.MyDriverAdapter;
import com.saimawzc.freight.base.BaseApplication;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.my.carrier.CarrierPageDto;
import com.saimawzc.freight.dto.my.carrier.MyCarrierDto;
import com.saimawzc.freight.dto.my.driver.MyDriverDto;
import com.saimawzc.freight.presenter.mine.car.MyCarPresenter;
import com.saimawzc.freight.presenter.mine.carrier.MyCarrierPresenter;
import com.saimawzc.freight.view.mine.carrier.MyCarrierView;
import com.saimawzc.freight.view.mine.driver.MyDriverView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.utils.LoadMoreListener;
import com.saimawzc.freight.weight.utils.trace.TraceUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2020/8/1.
 */

public class PassCarrierFragment extends BaseFragment implements MyCarrierView , TextWatcher {

    @BindView(R.id.rv)RecyclerView rv;
    private MyCarrierAdapter adapter;
    private List<MyCarrierDto> mDatas=new ArrayList<>();
    private MyCarrierPresenter presenter;
    private int page=1;
    private LoadMoreListener loadMoreListener;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private int status=1;//1已经添加 2 待确定

    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;

    @Override
    public int initContentView() {
        return R.layout.fragment_pass_car;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        edSearch.setHint("请输入手机号或者承运商名称");
        edSearch.addTextChangedListener(this);
        adapter = new MyCarrierAdapter(mDatas, mContext,1);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        mContext=getActivity();
        setNeedOnBus(true);
        presenter=new MyCarrierPresenter(this,mContext);

        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                isLoading = false;
                presenter.getcarList(status,page,edSearch.getText().toString());//0未确定 1已拒绝 2已同意

            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter.getcarList(status,page,edSearch.getText().toString());
    }

    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getcarList(status,page,edSearch.getText().toString());

            }
        });
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page=1;
                presenter.getcarList(status,page,edSearch.getText().toString());
            }
        });
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
       stopSwipeRefreshLayout(refreshLayout);
    }



    @Override
    public void getMyCarrierList(CarrierPageDto carrierDtos) {
        llSearch.setVisibility(View.GONE);
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
        }
        if(carrierDtos.isLastPage()==false){
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }else {
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }
        adapter.addMoreData(carrierDtos.getList());
    }

    @Override
    public void stopRefresh() {
        stopSwipeRefreshLayout(refreshLayout);

    }

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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshCarLeader(String str) {
        if(!TextUtils.isEmpty(str)){
            if(str.equals(Constants.reshCarrive)){
                Log.e("msg","刷新"+str);
                page=1;
                presenter.getcarList(status,page,edSearch.getText().toString());
                return;
            }

        }
    }
}
