package com.saimawzc.freight.ui.my.car.ship;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.my.lessess.ChangeShipAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.my.car.ship.MyShipDto;
import com.saimawzc.freight.dto.my.car.ship.SearchShipDto;
import com.saimawzc.freight.presenter.mine.car.MyChangeShipPresenter;
import com.saimawzc.freight.view.mine.car.ship.MyShipView;
import com.saimawzc.freight.weight.utils.LoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2020/8/10.
 * 船舶变更
 */

public class MyShipChangeFragment extends BaseFragment implements MyShipView {

    @BindView(R.id.rv)RecyclerView rv;
    private ChangeShipAdapter adapter;
    private List<SearchShipDto> mDatas=new ArrayList<>();
    private MyChangeShipPresenter presenter;
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
        context.setToolbar(toolbar,"船舶变更");
        presenter=new MyChangeShipPresenter(this,mContext);
        adapter = new ChangeShipAdapter(mDatas, mContext,1);
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


//    @Override
//    public void compelete(List<SearchShipDto> shipDtos) {
//
//    }

    @Override
    public void compelete(MyShipDto shipDtos) {
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
        }
        if(shipDtos.isLastPage()==false){
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }else {
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }
        adapter.addMoreData(shipDtos.getList());
    }

    @Override
    public void stopRefresh() {
        stopSwipeRefreshLayout(refreshLayout);
    }
}
