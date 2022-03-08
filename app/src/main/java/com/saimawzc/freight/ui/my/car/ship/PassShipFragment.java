package com.saimawzc.freight.ui.my.car.ship;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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
import com.saimawzc.freight.adapter.my.SearchShipAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.my.car.ship.MyShipDto;
import com.saimawzc.freight.dto.my.car.ship.SearchShipDto;
import com.saimawzc.freight.presenter.mine.car.MyShipPresenter;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.view.mine.car.ship.MyShipView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.utils.LoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;



/**
 * Created by Administrator on 2020/8/1.
 * 通过的船舶
 */
public class PassShipFragment extends BaseFragment implements MyShipView , TextWatcher {

    @BindView(R.id.rv)RecyclerView rv;
    private SearchShipAdapter adapter;
    private List<SearchShipDto> mDatas=new ArrayList<>();
    private MyShipPresenter presenter;
    private int page=1;
    private LoadMoreListener loadMoreListener;
    @BindView(R.id.SwipeRefreshLayout) SwipeRefreshLayout refreshLayout;

    private int status=1; //checkStatus*	integer($int32)审核状态(1.已审核 3.审核中)
    @BindView(R.id.edsearch) ClearTextEditText edSearch;
    @BindView(R.id.llSearch) LinearLayout llSearch;
    @BindView(R.id.tvSearch) TextView tvSearch;
    @Override
    public int initContentView() {
        return R.layout.fragment_pass_car;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        setNeedOnBus(true);
        edSearch.setHint("请输入船舶名称或船讯网ID");
        presenter=new MyShipPresenter(this,mContext);
        adapter = new SearchShipAdapter(mDatas, mContext,1);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

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
        edSearch.addTextChangedListener(this);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getcarList(status,page,edSearch.getText().toString());


            }
        });
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("from","resistership");
                bundle.putString("id",mDatas.get(position).getId());
                bundle.putString("type","shipinfo");
                if(mDatas.get(position).getIfRegister()==1){
                    bundle.putBoolean("ismodify",true);
                }
                readyGo(PersonCenterActivity.class,bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

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

    }



    @Override
    public void compelete(MyShipDto shipDtos) {
        llSearch.setVisibility(View.GONE);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshShipPass(String str) {
        if(!TextUtils.isEmpty(str)){
            if(str.equals(Constants.reshShip)){
                Log.e("msg","刷新"+str);
                page=1;
                presenter.getcarList(status,page,edSearch.getText().toString());
            }
        }
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
        if (!TextUtils.isEmpty(edSearch.getText().toString())) {
            llSearch.setVisibility(View.VISIBLE);
            tvSearch.setText(edSearch.getText().toString());
        } else {
            llSearch.setVisibility(View.GONE);
        }
    }
}
