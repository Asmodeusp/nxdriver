package com.saimawzc.freight.ui.order;

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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.order.OrderManageListAdpater;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.order.ManageListDto;
import com.saimawzc.freight.presenter.order.ManageOrderPresenter;
import com.saimawzc.freight.ui.order.waybill.manage.OrderManageMapActivity;
import com.saimawzc.freight.view.order.ManageOrderView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.NoData;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.LoadMoreListener;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.PopupWindowUtil;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/****
 * ???????????????
 * **/

public class ManageOrderFragment extends BaseFragment implements ManageOrderView {
    @BindView(R.id.fresh) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv) RecyclerView rv;
    private List<ManageListDto.ManageOrderData> mDatas=new ArrayList<>();
    private OrderManageListAdpater adpater;
    private ManageOrderPresenter presenter;
    private int page=1;
    private LoadMoreListener loadMoreListener;
    @BindView(R.id.nodata)
    NoData noData;
    @Override
    public int initContentView() {
        return R.layout.fragment_managelist;
    }
    private NormalDialog dialog;

    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.tvpopuw)TextView tvPopuw;
    @BindView(R.id.llpopuw)LinearLayout llpopuw;
    private String searchType="";
    @OnClick({R.id.llSearch,R.id.llpopuw})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.llSearch:
                page = 1;
                presenter.getData(page, searchType, edSearch.getText().toString());
                break;
            case R.id.llpopuw:
                final PopupWindowUtil popupWindowUtil = new PopupWindowUtil.Builder()
                        .setContext(mContext.getApplicationContext()) //?????? context
                        .setContentView(R.layout.dialog_yundan) //??????????????????
                        .setOutSideCancel(true) //????????????????????????
                        .setwidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setFouse(true)
                        .builder()
                        .showAsLaction(llpopuw, Gravity.LEFT, 0, 0);
                popupWindowUtil.setOnClickListener(new int[]{R.id.rlall, R.id.receiveS, R.id.fahuos, R.id.wuliaoname}, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.rlall://??????
                                tvPopuw.setText("??????");
                                searchType = "";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.receiveS:
                                tvPopuw.setText("?????????");
                                searchType = "toName";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.fahuos:
                                tvPopuw.setText("?????????");
                                searchType = "fromName";
                                popupWindowUtil.dismiss();
                                break;

                            case R.id.wuliaoname://
                                tvPopuw.setText("????????????");
                                searchType = "materialsName";
                                popupWindowUtil.dismiss();
                                break;

                        }
                    }
                });
                break;
        }
    }

    @Override
    public void initView() {
        mContext=getActivity();
        adpater=new OrderManageListAdpater(mDatas,mContext);
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adpater);
         setNeedOnBus(true);
        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                presenter.getData(page,searchType,edSearch.getText().toString());
            }
        };
        rv.setOnScrollListener(loadMoreListener);
        edSearch.addTextChangedListener(textWatcher);
        edSearch.hiddenIco();
        presenter=new ManageOrderPresenter(this,mContext);
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                presenter.getData(page,searchType,edSearch.getText().toString());
            }
        });


    }

    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getData(page,searchType,edSearch.getText().toString());
            }
        });
        adpater.setOnTabClickListener(new OrderManageListAdpater.OnTabClickListener() {
            @Override
            public void onItemClick(String type, final int position) {

                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle=new Bundle();
                if(type.equals("tab1")){
                    if(mDatas.get(position).getSendType()==0){//?????????????????????
                        dialog = new NormalDialog(mContext).isTitleShow(false)
                                .content("??????????????????????")
                                .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                                .btnNum(2).btnText("??????", "??????");
                        dialog.setOnBtnClickL(
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        dialog.dismiss();
                                    }
                                },
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        presenter.delete(mDatas.get(position).getId());
                                        if(!context.isDestroy(context)){
                                            dialog.dismiss();
                                        }
                                    }
                                });
                        dialog.show();
                    }else {//????????????????????????
                        bundle=new Bundle();
                        bundle.putString("from","applypause");
                        bundle.putString("id",mDatas.get(position).getId());
                        bundle.putString("type",3+"");
                        readyGo(OrderMainActivity.class,bundle);
                    }
                }
                if(type.equals("tab2")){//????????????
                    if(mDatas.get(position).getSendType()==0){//???????????? ????????????
                        bundle=new Bundle();
                        bundle.putString("from","applypause");
                        bundle.putString("id",mDatas.get(position).getId());
                        bundle.putString("type",3+"");
                        readyGo(OrderMainActivity.class,bundle);
                    }else {//???????????? ??????
                        context.showMessage("???????????????????????????");
                    }
                }
                if(type.equals("tab3")){//??????
                    bundle=new Bundle();
                    bundle.putString("from","sendcar");
                    bundle.putString("id",mDatas.get(position).getId());
                    bundle.putString("type",3+"");
                    bundle.putInt("tranttype",mDatas.get(position).getTranType());
                    readyGo(OrderMainActivity.class,bundle);
                }
            }
        });

        adpater.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("id",mDatas.get(position).getId());
                readyGo(OrderManageMapActivity.class,bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }

    @Override
    public void getPlanOrderList(List<ManageListDto.ManageOrderData> dtos) {
        if(page==1){
            mDatas.clear();
            adpater.notifyDataSetChanged();
            if(dtos==null||dtos.size()<=0){
                noData.setVisibility(View.VISIBLE);
            }else {
                noData.setVisibility(View.GONE);
            }
        }
        adpater.addMoreData(dtos);
        llSearch.setVisibility(View.GONE);

    }

    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);

    }

    @Override
    public void isLastPage(boolean isLastPage) {
        if(isLastPage){
            loadMoreListener.isLoading = true;
            adpater.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }else {
            loadMoreListener.isLoading = false;
            adpater.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }
    }

    @Override
    public void showLoading() {
        context.showLoadingDialog();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshManageorese(String str) {
        if(!TextUtils.isEmpty(str)){
            if(str.equals(Constants.reshYunDn)||
                    str.equals(Constants.reshChangeCYS)){
                Log.e("msg","??????"+str);
                page=1;
                presenter.getData(page,searchType,edSearch.getText().toString());
            }
        }
    }
    @Override
    public void dissLoading() {
     context.dismissLoadingDialog();
    }

    @Override
    public void Toast(String str) {
        if(TextUtils.isEmpty(PreferenceKey.CYS_IS_INDENFICATION)||!Hawk.get(PreferenceKey.CYS_IS_INDENFICATION,"").equals("1")){
            if(!str.contains("??????")){
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
    /**
     * ???????????????
     */
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void afterTextChanged(Editable editable) {
            //???????????????????????????????????????
            if (!TextUtils.isEmpty(edSearch.getText().toString())) {
                llSearch.setVisibility(View.VISIBLE);
                tvSearch.setText(edSearch.getText().toString());
            } else {
                llSearch.setVisibility(View.GONE);
            }
        }
    };


}
