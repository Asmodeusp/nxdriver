package com.saimawzc.freight.ui.order.waybill;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.order.OrderBiddRankAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.order.waybill.RankPageDto;
import com.saimawzc.freight.presenter.order.bill.BiddRankPresenter;
import com.saimawzc.freight.view.order.BiddRandView;
import com.saimawzc.freight.weight.utils.LoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.saimawzc.freight.adapter.BaseAdapter.IS_RESH;

/***
 * 竞价排名
 * **/
public class BiddRankFragment extends BaseFragment implements BiddRandView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.cy) RecyclerView rv;
    private OrderBiddRankAdapter adapter;
    BiddRankPresenter presenter;
    private List<RankPageDto.rankDto>mDatas=new ArrayList<>();
    private LoadMoreListener loadMoreListener;
    private String id;
    private int page=1;
    @Override
    public int initContentView() {
        return R.layout.fragment_rank;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"竞价排行");
        id=getArguments().getString("id");
        adapter = new OrderBiddRankAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter=new BiddRankPresenter(this,mContext);
        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(!IS_RESH){
                    page++;
                    presenter.getRankList(page,id);
                    IS_RESH=true;
                }

            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter.getRankList(page,id);
    }

    @Override
    public void initData() {

    }

    @Override
    public void getRandLise(List<RankPageDto.rankDto> dtos) {
        adapter.addMoreData(dtos);
    }

    @Override
    public void isLastPage(boolean isLastPage) {
        if(isLastPage){
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }else {
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }

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
}
