package com.saimawzc.freight.ui.order.driver;

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
import com.saimawzc.freight.adapter.order.DriverplanOrderAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.order.PlanOrderReshDto;
import com.saimawzc.freight.dto.order.bill.MyPlanOrderDto;
import com.saimawzc.freight.presenter.order.PlanOrderPresenter;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.view.order.PlanOrderView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.NoData;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.LoadMoreListener;
import com.saimawzc.freight.weight.utils.dialog.PopupWindowUtil;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.saimawzc.freight.adapter.BaseAdapter.IS_RESH;

/***
 *   我的计划订单
 * **/
public class DriverPlanOrderFragment extends BaseFragment
        implements PlanOrderView {

    @BindView(R.id.SwipeRefreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.cycle) RecyclerView rv;
    private List<MyPlanOrderDto.planOrderData> mDatas=new ArrayList<>();
    DriverplanOrderAdapter adapter;
    private PlanOrderPresenter presenter;
    private int page=1;
    private LoadMoreListener loadMoreListener;
    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.tvpopuw)TextView tvPopuw;
    @BindView(R.id.llpopuw)LinearLayout llpopuw;
    private String searchType="";

    @BindView(R.id.nodata)
    NoData noData;
    @OnClick({R.id.llSearch,R.id.llpopuw})
    public void click(View view){
        switch (view.getId()){
            case R.id.llSearch:
                page=1;
                presenter.getsjData(page,searchType,edSearch.getText().toString());
                break;
            case R.id.llpopuw:
                final PopupWindowUtil popupWindowUtil = new PopupWindowUtil.Builder()
                        .setContext(mContext) //设置 context
                        .setContentView(R.layout.dialog_yundan) //设置布局文件
                        .setOutSideCancel(true) //设置点击外部取消
                        .setwidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setFouse(true)
                        .builder()
                        .showAsLaction(llpopuw, Gravity.LEFT,0,0);
                popupWindowUtil.setOnClickListener(new int[]{R.id.rlall, R.id.receiveS,R.id.fahuos,R.id.wuliaoname}, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            case R.id.rlall://全部
                                tvPopuw.setText("全部");
                                searchType="";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.receiveS:
                                tvPopuw.setText("收货商");
                                searchType="toName";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.fahuos:
                                tvPopuw.setText("发货商");
                                searchType="fromName";
                                popupWindowUtil.dismiss();
                                break;

                            case R.id.wuliaoname://
                                tvPopuw.setText("物料名称");
                                searchType="materialsName";
                                popupWindowUtil.dismiss();
                                break;

                        }
                    }
                });
                break;
        }
    }
    @Override
    public int initContentView() {
        return R.layout.fragment_orderbidd;
    }

    @Override
    public void initView() {

        mContext=getActivity();
        adapter=new DriverplanOrderAdapter(mDatas,mContext);
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        setNeedOnBus(true);

        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(IS_RESH==false){
                    page++;
                    presenter.getsjData(page,searchType,edSearch.getText().toString());
                    IS_RESH=true;
                }
            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter=new PlanOrderPresenter(this,mContext);
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                presenter.getsjData(page,searchType,edSearch.getText().toString());
            }
        });
        edSearch.addTextChangedListener(textWatcher);
        edSearch.hiddenIco();

    }

    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getsjData(page,searchType,edSearch.getText().toString());

            }
        });

        adapter.setOnTabClickListener(new DriverplanOrderAdapter.OnTabClickListener() {
            Bundle bundle;
            @Override
            public void onItemClick(String type, int position) {

                if(mDatas.size()<=position){
                    return;
                }
                if(type.equals("tab1")){//派车 trantype 1派车 2派船
                    bundle=new Bundle();
                    bundle.putString("from","sendcar");
                    bundle.putString("id",mDatas.get(position).getId());
                    bundle.putString("type",1+"");
                    bundle.putString("sendcarnum",1+"");
                    bundle.putInt("tranttype",mDatas.get(position).getTranType());
                    readyGo(OrderMainActivity.class,bundle);
                }else if(type.equals("tab2")){//一车多派
                    bundle=new Bundle();
                    bundle.putString("from","sendcar");
                    bundle.putString("id",mDatas.get(position).getId());
                    bundle.putString("type",1+"");
                    bundle.putString("sendcarnum","morer");
                    bundle.putInt("tranttype",mDatas.get(position).getTranType());
                    readyGo(OrderMainActivity.class,bundle);

                }else if(type.equals("tab3")){//生成预运单
                    bundle=new Bundle();
                    bundle.putString("from","addwaybill");
                    bundle.putSerializable("data",mDatas.get(position));
                    readyGo(OrderMainActivity.class,bundle);
                }else if(type.equals("tab4")){
                    bundle=new Bundle();
                    bundle.putString("from","applypause");
                    bundle.putString("id",mDatas.get(position).getId());
                    bundle.putString("type",1+"");
                    readyGo(OrderMainActivity.class,bundle);
                }
            }
        });

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle =new Bundle();
                    bundle.putString("from","orderdelation");
                    bundle.putString("id",mDatas.get(position).getId());
                    bundle.putString("type","delation");
                    bundle.putInt("waybillstatus",1);
                    readyGo(OrderMainActivity.class,bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }

    @Override
    public void getPlanOrderList(List<MyPlanOrderDto.planOrderData> dtos) {
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
        IS_RESH=false;
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
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }else {
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }

    }

    @Override
    public void reshPlanData(PlanOrderReshDto dto, int position) {

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
        if(TextUtils.isEmpty(PreferenceKey.DRIVER_IS_INDENFICATION)||!Hawk.get(PreferenceKey.DRIVER_IS_INDENFICATION,"").equals("1")){
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

    /**
     * 监听输入框
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
            //控制登录按钮是否可点击状态
            if (!TextUtils.isEmpty(edSearch.getText().toString())) {
                llSearch.setVisibility(View.VISIBLE);
                tvSearch.setText(edSearch.getText().toString());
            } else {
                llSearch.setVisibility(View.GONE);
            }
        }
    };
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshPlanOrder(String str) {
        if(!TextUtils.isEmpty(str)){
           if(str.equals(Constants.reshYunDnsJ)||
                   str.equals(Constants.reshChange)){
               Log.e("msg","刷新"+str);
               page=1;
               presenter.getsjData(page,searchType,edSearch.getText().toString());
           }
        }
    }

}
