package com.saimawzc.freight.ui.order;


import android.content.Intent;
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
import com.saimawzc.freight.adapter.order.mainindex.WaitOrderAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.order.mainindex.WaitOrderDto;
import com.saimawzc.freight.presenter.order.WaitOrderPresenter;
import com.saimawzc.freight.ui.order.waybill.manage.OrderManageMapActivity;
import com.saimawzc.freight.view.order.WaitOrderView;
import com.saimawzc.freight.weight.NoData;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.LoadMoreListener;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
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
 * 首页待确认
 * **/
public class WaitOrderFragment extends BaseFragment implements WaitOrderView {
    private NormalDialog dialog;
    @BindView(R.id.cv) RecyclerView rv;
    private WaitOrderAdapter adapter;
    private List<WaitOrderDto.waitOrderData>mDatas=new ArrayList<>();
    private WaitOrderPresenter presenter;

    private int page=1;
    private LoadMoreListener loadMoreListener;
    @BindView(R.id.SwipeRefreshLayout)SwipeRefreshLayout refreshLayout;
    @BindView(R.id.nodata)
    NoData noData;
    @Override
    public int initContentView() {
        return R.layout.fragment_waitorder;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        adapter=new WaitOrderAdapter(mDatas,mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        setNeedOnBus(true);
        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                presenter.getData(page);
            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter=new WaitOrderPresenter(this,mContext);
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
        adapter.setOnTabClickListener(new WaitOrderAdapter.OnTabClickListener() {
            @Override
            public void onItemClick(String type, final int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle;
                int billTpye=mDatas.get(position).getWaybillType();
                if(billTpye==2){//预运单
                    if(type.equals("tab1")){//清单
                        bundle =new Bundle();
                        bundle.putString("type","wait");
                        bundle.putString("id",mDatas.get(position).getWaybillId());
                        bundle.putString("from","biddqingdan");
                        bundle.putSerializable("data",mDatas.get(position));
                        readyGo(OrderMainActivity.class,bundle);
                    }else if(type.equals("tab2")){//确认指派
                        bundle =new Bundle();
                        bundle.putString("from","orderdelation");
                        bundle.putString("id",mDatas.get(position).getWaybillId());
                        bundle.putInt("waybillstatus",mDatas.get(position).getWaybillType());
                        readyGo(OrderMainActivity.class,bundle);
                    }
                }else {//确认指派
                    if(mDatas.get(position).getWaybillType()!=3){
                        bundle =new Bundle();
                        bundle.putString("from","orderdelation");
                        bundle.putString("id",mDatas.get(position).getWaybillId());
                        bundle.putInt("waybillstatus",mDatas.get(position).getWaybillType());
                        readyGo(OrderMainActivity.class,bundle);
                    }else {
                        dialog = new NormalDialog(mContext).isTitleShow(false)
                                .content("确定指派?")
                                .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                                .btnNum(2).btnText("拒绝", "同意");
                        dialog.setOnBtnClickL(
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        assign(mDatas.get(position).getWaybillId(),"3","2");
                                        if(!context.isDestroy(context)){
                                            dialog.dismiss();
                                        }
                                    }
                                },
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        assign(mDatas.get(position).getWaybillId(),"3","1");
                                        if(!context.isDestroy(context)){
                                            dialog.dismiss();
                                        }
                                    }
                                });
                        dialog.show();
                    }

                }

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
            if(mDatas==null||mDatas.size()<=0){
                noData.setVisibility(View.VISIBLE);
            }else {
                noData.setVisibility(View.GONE);
            }
            context.showMessage(str);
        }
    }

    @Override
    public void oncomplete() {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshWaitOrder(String str) {
        if(!TextUtils.isEmpty(str)){
            if(str.equals(Constants.reshYunDn)||
                    str.equals(Constants.reshChangeCYS)){
                Log.e("msg","刷新"+str);
                page=1;
                presenter.getData(page);
            }
        }
    }
}
