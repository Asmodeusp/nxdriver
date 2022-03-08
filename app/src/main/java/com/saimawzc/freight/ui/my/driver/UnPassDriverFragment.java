package com.saimawzc.freight.ui.my.driver;

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
import com.saimawzc.freight.adapter.my.driver.MyDriverAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.my.driver.DriverPageDto;
import com.saimawzc.freight.dto.my.driver.MyDriverDto;
import com.saimawzc.freight.presenter.mine.driver.MyDriverPresenter;
import com.saimawzc.freight.view.mine.driver.MyDriverView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.utils.LoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;



/**
 * Created by Administrator on 2020/8/1.
 */

public class UnPassDriverFragment extends BaseFragment implements MyDriverView, TextWatcher {

    @BindView(R.id.rv)RecyclerView rv;
    private MyDriverAdapter adapter;
    private List<MyDriverDto> mDatas=new ArrayList<>();
    private MyDriverPresenter presenter;
    private LoadMoreListener loadMoreListener;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    int page=1;
    private int status=2;//（1.以添加 2.待确认）

    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @Override
    public int initContentView() {
        return R.layout.fragment_unpass_car;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        edSearch.setHint("请输入手机号或者（司机/船员）名称");
        presenter=new MyDriverPresenter(this,mContext);
        adapter = new MyDriverAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        setNeedOnBus(true);
        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                isLoading = false;
                presenter.getcarList(status,page,edSearch.getText().toString());

            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter.getcarList(status,page,edSearch.getText().toString());
    }

    @Override
    public void initData() {
        edSearch.addTextChangedListener(this);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getcarList(2,page,edSearch.getText().toString());


            }
        });
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page=1;
                presenter.getcarList(2,page,edSearch.getText().toString());
            }
        });
    }

    @Override
    public void getMyDriverList(DriverPageDto driverDtos) {
        llSearch.setVisibility(View.GONE);
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
        }
        if(driverDtos.isLastPage()==false){
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }else {
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }
        adapter.addMoreData(driverDtos.getList());
    }

    @Override
    public void stopRefresh() {
        stopSwipeRefreshLayout(refreshLayout);
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
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!TextUtils.isEmpty(edSearch.getText().toString())) {
            llSearch.setVisibility(View.VISIBLE);
            tvSearch.setText(edSearch.getText().toString());
        } else {
            llSearch.setVisibility(View.GONE);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshManageorder(String str) {
        if(!TextUtils.isEmpty(str)){
            if(str.equals(Constants.reshMyDriver)){
                Log.e("msg","刷新"+str);
                page=1;
                presenter.getcarList(status,page,edSearch.getText().toString());
            }
        }
    }
}
