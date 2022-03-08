package com.saimawzc.freight.ui.order.rob;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.order.mainindex.RobOrderAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.order.mainindex.RobOrderDto;
import com.saimawzc.freight.presenter.order.RobOrderPresenter;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.ui.order.waybill.manage.OrderManageMapActivity;
import com.saimawzc.freight.view.order.RobOrderView;
import com.saimawzc.freight.weight.NoData;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.LoadMoreListener;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;


/***
 * 抢单
 * */
public class RobOrderFragment extends BaseFragment implements RobOrderView {

    @BindView(R.id.cv) RecyclerView rv;
    private RobOrderAdapter adapter;
    private List<RobOrderDto.robOrderData>mDatas=new ArrayList<>();
    private RobOrderPresenter presenter;
    private int page=1;
    private LoadMoreListener loadMoreListener;
    @BindView(R.id.SwipeRefreshLayout)SwipeRefreshLayout refreshLayout;
    @BindView(R.id.nodata)
    NoData noData;

    @Override
    public int initContentView() {
        return R.layout.fragment_orderwaybill;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        setNeedOnBus(true);
        adapter=new RobOrderAdapter(mDatas,mContext);
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                presenter.getData(page);
            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter=new RobOrderPresenter(this,mContext);
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                presenter.getData(page);
            }
        });

    }
    @Override
    public void initData() {

        adapter.setOnTabClickListener(new RobOrderAdapter.OnTabClickListener() {
            @Override
            public void onItemClick(String type, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle;
                int wayBillStatus=mDatas.get(position).getWaybillType();
                if(wayBillStatus==2){//预运单
                    if(type.equals("tab1")){//清单
                        bundle =new Bundle();
                        bundle.putString("type","rob");
                        bundle.putString("id",mDatas.get(position).getWaybillId());
                        bundle.putSerializable("data",mDatas.get(position));
                        bundle.putString("from","biddqingdan");
                        readyGo(OrderMainActivity.class,bundle);
                    }else if(type.equals("tab2")){//参与竞价
                        bundle =new Bundle();
                        bundle.putString("type",mDatas.get(position).getWaybillType()+"");
                        bundle.putString("id",mDatas.get(position).getId());
                        bundle.putString("endTime",mDatas.get(position).getEndTime());
                        bundle.putString("from","joinbidd");
                        readyGo(OrderMainActivity.class,bundle);

                    }
                }else {//参与竞价
                    bundle =new Bundle();
                    bundle.putString("type",mDatas.get(position).getWaybillType()+"");
                    bundle.putString("id",mDatas.get(position).getId());
                    bundle.putString("endTime",mDatas.get(position).getEndTime());
                    bundle.putString("from","joinbidd");
                    readyGo(OrderMainActivity.class,bundle);

                }
            }
        });



        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getData(page);
            }
        });


        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle;
                if(mDatas.get(position).getWaybillType()==3){//调度单
                    bundle=new Bundle();
                    bundle.putString("id",mDatas.get(position).getWaybillId());
                    readyGo(OrderManageMapActivity.class,bundle);
                }else {
                    bundle =new Bundle();
                    bundle.putString("from","orderdelation");
                    bundle.putString("id",mDatas.get(position).getWaybillId());
                    bundle.putString("type","delation");
                    bundle.putInt("waybillstatus",mDatas.get(position).getWaybillType());
                    readyGo(OrderMainActivity.class,bundle);
                }
            }
            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshSendCompete(String str) {
        if(!TextUtils.isEmpty(str)){
            if(str.equals(Constants.reshChangeCYS)){
                Log.e("msg","刷新"+str);
                page=1;
                presenter.getData(page);
            }
        }
    }
    @Override
    public void getPlanOrderList(List<RobOrderDto.robOrderData> dtos) {
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
            if(dtos==null||dtos.size()<=0){
                noData.setVisibility(View.VISIBLE);
            }else {
                noData.setVisibility(View.GONE);
            }
        }
        adapter.addMoreData(dtos);
    }

    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);

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
        if(TextUtils.isEmpty(PreferenceKey.CYS_IS_INDENFICATION)||!Hawk.get(PreferenceKey.CYS_IS_INDENFICATION,"").equals("1")){
            if(!str.contains("权限")){
                context.showMessage(str);
            }
        }else {
            context.showMessage(str);
            if(mDatas==null||mDatas.size()<=0){
                noData.setVisibility(View.VISIBLE);
            }else {
                noData.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void oncomplete() {

    }

}
