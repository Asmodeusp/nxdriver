package com.saimawzc.freight.ui.my.pubandservice;

import android.content.Intent;
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
import com.saimawzc.freight.adapter.my.carleader.ChooseTaxiCarAdapter;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.my.car.MyCarDto;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.presenter.mine.car.MyCarPresenter;
import com.saimawzc.freight.view.mine.car.MyCarView;
import com.saimawzc.freight.weight.utils.LoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChooseTaxiCarActivity extends BaseActivity implements MyCarView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rvList;
    private List<SearchCarDto> mDatas=new ArrayList<>();
    private MyCarPresenter presenter;
    private ChooseTaxiCarAdapter adapter;
    private int page=1;
    private LoadMoreListener loadMoreListener;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private int status=1;
    @Override
    protected int getViewId() {
        return R.layout.activity_choosecar;
    }

    @Override
    protected void init() {
        setToolbar(toolbar,"选择车辆");
        presenter=new MyCarPresenter(this,mContext);
        adapter = new ChooseTaxiCarAdapter(mDatas, mContext);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvList.setLayoutManager(layoutManager);
        rvList.setAdapter(adapter);

        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                isLoading = false;
                presenter.getcarList(status,page,"");//0未确定 1已拒绝 2已同意

            }
        };
        rvList.setOnScrollListener(loadMoreListener);
        presenter.getcarList(status,page,"");
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getcarList(status,page,"");
            }
        });
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Intent intent=new Intent();
                intent.putExtra("carNo",mDatas.get(position).getCarNo());
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    protected void onGetBundle(Bundle bundle) {
    }

    @Override
    public void compelete(MyCarDto dto) {
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
        }
        if(dto.isLastPage()==false){
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }else {
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }
        adapter.addMoreData(dto.getList());
    }
    @Override
    public void stopRefresh() {
        stopSwipeRefreshLayout(refreshLayout);
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void dissLoading() {
        dismissLoadingDialog();
    }

    @Override
    public void Toast(String str) {
        showMessage(str);
    }

    @Override
    public void oncomplete() {
    }
}
