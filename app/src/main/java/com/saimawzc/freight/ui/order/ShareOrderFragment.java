package com.saimawzc.freight.ui.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.saimawzc.freight.adapter.order.mainindex.ShareOrderAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.order.mainindex.WaitOrderDto;
import com.saimawzc.freight.presenter.order.ShareOrderPresenter;
import com.saimawzc.freight.ui.order.waybill.manage.OrderManageMapActivity;
import com.saimawzc.freight.view.order.WaitOrderView;
import com.saimawzc.freight.weight.NoData;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.LoadMoreListener;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/****
 * 首页分享
 * **/
public class ShareOrderFragment extends BaseFragment implements WaitOrderView {
    private NormalDialog dialog;
    @BindView(R.id.cv) RecyclerView rv;
    private ShareOrderAdapter adapter;
    private List<WaitOrderDto.waitOrderData>mDatas=new ArrayList<>();
    private ShareOrderPresenter presenter;

    private int page=1;
    private LoadMoreListener loadMoreListener;
    @BindView(R.id.nodata)
    NoData noData;
    @BindView(R.id.SwipeRefreshLayout)SwipeRefreshLayout refreshLayout;
    @Override
    public int initContentView() {
        return R.layout.fragment_waitorder;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        setNeedOnBus(true);
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        adapter=new ShareOrderAdapter(mDatas,mContext);
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

        presenter=new ShareOrderPresenter(this,mContext);
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                presenter.getData(page);
            }
        });

    }

    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getData(page);
            }
        });
        //详情
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
    @Override
    public void getPlanOrderList(List<WaitOrderDto.waitOrderData> dtos) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshShareOrder(String str) {
        if(!TextUtils.isEmpty(str)){
            if(str.equals(Constants.reshYunDn)||
                    str.equals(Constants.reshChangeCYS)){
                Log.e("msg","刷新"+str);
                page=1;
                presenter.getData(page);
            }
        }
    }


    private void assign(String id,String type,String sattus){
        context.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("type",type);
            jsonObject.put("status",sattus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        context.orderApi.roderAssign(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                context.dismissLoadingDialog();
                page=1;
                presenter.getData(page);
                EventBus.getDefault().post(Constants.reshYunDn);
            }
            @Override
            public void fail(String code, String message) {
                context.dismissLoadingDialog();
                context.showMessage(message);
            }
        });
    }




}
