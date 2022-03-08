package com.saimawzc.freight.ui.my.car;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.my.SearchCarAdapter;
import com.saimawzc.freight.adapter.my.lessess.ChangeCarAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.my.car.MyCarDto;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.presenter.mine.car.MyCarPresenter;
import com.saimawzc.freight.presenter.mine.car.MyChangeCarPresenter;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.view.mine.car.MyCarView;
import com.saimawzc.freight.weight.utils.LoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2020/8/10.
 * 车辆变更
 */

public class MyCarChangeFragment extends BaseFragment implements MyCarView {

    @BindView(R.id.rv)RecyclerView rv;
    private ChangeCarAdapter adapter;
    private List<SearchCarDto> mDatas=new ArrayList<>();
    private MyChangeCarPresenter presenter;
    private int page=1;
    private LoadMoreListener loadMoreListener;
    @BindView(R.id.SwipeRefreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.toolbar)Toolbar toolbar;

    @Override
    public int initContentView() {
        return R.layout.fragment_carchange;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"车辆变更");
        presenter=new MyChangeCarPresenter(this,mContext);
        adapter = new ChangeCarAdapter(mDatas, mContext,1);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                isLoading = false;
                presenter.getcarList(2,page);//0未确定 1已拒绝 2已同意

            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter.getcarList(2,page);
    }

    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getcarList(2,page);
            }
        });

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
//                Bundle bundle=new Bundle();
//                bundle.putString("from","resistercar");
//                bundle.putString("id",mDatas.get(position).getId());
//                bundle.putString("type","carinfo");
//                if(mDatas.get(position).getIfRegister()==1){
//                    bundle.putBoolean("modify",true);
//                }
//                readyGo(PersonCenterActivity.class,bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

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

    }

    @Override
    public void compelete(MyCarDto carDtoList) {
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
        }
        if(carDtoList.isLastPage()==false){
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }else {
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }
        adapter.addMoreData(carDtoList.getList());
    }



    @Override
    public void stopRefresh() {
        stopSwipeRefreshLayout(refreshLayout);
    }
}
