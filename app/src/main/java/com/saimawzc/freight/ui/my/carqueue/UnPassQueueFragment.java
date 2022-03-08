package com.saimawzc.freight.ui.my.carqueue;

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
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.my.carleader.MyQueueAdapter;
import com.saimawzc.freight.adapter.my.lessess.MyLessessAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.my.lessess.LessessPageDto;
import com.saimawzc.freight.dto.my.lessess.MyLessessDto;
import com.saimawzc.freight.dto.my.queue.CarQueueDto;
import com.saimawzc.freight.presenter.mine.carleader.CarQueuePresenter;
import com.saimawzc.freight.presenter.mine.lessess.MyLessessPresenter;
import com.saimawzc.freight.view.mine.lessess.MyLessessView;
import com.saimawzc.freight.view.mine.queue.MyQueueView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.utils.LoadMoreListener;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2020/8/1.
 */

public class UnPassQueueFragment extends BaseFragment implements MyQueueView {
    @BindView(R.id.rv)RecyclerView rv;
    private MyQueueAdapter adapter;
    private List<CarQueueDto.data> mDatas=new ArrayList<>();
    private int page=1;
    private LoadMoreListener loadMoreListener;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private int status=2;//	integer($int32)1车主未确认 2待确认 3拒绝
    private CarQueuePresenter presenter;


    @Override
    public int initContentView() {
        return R.layout.fragment_pass_queue;
    }

    @Override
    public void initView() {

        mContext=getActivity();
        adapter = new MyQueueAdapter(mDatas, mContext,2);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        mContext=getActivity();

        presenter=new CarQueuePresenter(this,mContext);
        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                isLoading = false;
                presenter.getData(page,status);

            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter.getData(page,status);
        setNeedOnBus(true);
    }

    @Override
    public void initData() {

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getData(page,status);
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
    public void getQueue(CarQueueDto queueDto) {

        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
        }
        if(queueDto.isLastPage()==false){
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }else {
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }
        adapter.addMoreData(queueDto.getList());
    }

    @Override
    public void stopRefresh() {
        stopSwipeRefreshLayout(refreshLayout);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshCarLeader1(String str) {
        Log.e("msg","dd"+str);
        if(!TextUtils.isEmpty(str)){
            if(str.equals(Constants.reshMyQueue)){
                Log.e("msg","刷新车队长详情");
                page=1;
                presenter.getData(page,status);
            }
        }
    }
}
