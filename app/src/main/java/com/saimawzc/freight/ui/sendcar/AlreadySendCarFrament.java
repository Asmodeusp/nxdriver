package com.saimawzc.freight.ui.sendcar;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.weight.NoData;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.dialog.PopupWindowUtil;
import com.baidu.trace.api.entity.EntityListRequest;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.entity.SearchResponse;
import com.baidu.trace.api.track.HistoryTrackRequest;
import com.baidu.trace.model.TraceLocation;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.order.SendCarAdapter;
import com.saimawzc.freight.base.BaseApplication;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.order.SendCarDto;
import com.saimawzc.freight.presenter.order.SendCarLsitPresenter;
import com.saimawzc.freight.ui.baidu.TracingActivity;
import com.saimawzc.freight.ui.baidu.utils.BitmapUtil;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.ui.order.waybill.manage.OrderManageMapActivity;
import com.saimawzc.freight.view.order.SendCarListView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.utils.LoadMoreListener;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.saimawzc.freight.adapter.BaseAdapter.IS_RESH;

/***
 * 已派车
 * **/
public class AlreadySendCarFrament extends BaseFragment implements SendCarListView {

    @BindView(R.id.cy) RecyclerView rv;
    private SendCarAdapter adapter;
    private List<SendCarDto.SendCarData>mDatas=new ArrayList<>();
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private SendCarLsitPresenter presenter;
    private int page=1;
    private LoadMoreListener loadMoreListener;
    private String status="5";//已派车
    @BindView(R.id.tvpopuw) TextView tvPopuw;
    @BindView(R.id.llpopuw) LinearLayout llpopuw;
    private String searchType="";
    @BindView(R.id.edsearch) ClearTextEditText edSearch;
    @BindView(R.id.llSearch) LinearLayout llSearch;
    @BindView(R.id.tvSearch) TextView tvSearch;
    @BindView(R.id.nodata) NoData noData;

    @BindView(R.id.tvDelMore)TextView tvDelMore;
    @BindView(R.id.rlbottom) RelativeLayout rlBottom;
    @BindView(R.id.check) CheckBox checkBox;


    @OnClick({R.id.llSearch,R.id.llpopuw,R.id.tvDelMore,R.id.tvOffAll})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvDelMore:
                if(adapter.getShowCheck()){
                    adapter.setIsShowCheck(false);
                    rlBottom.setVisibility(View.GONE);
                }else {
                    adapter.setIsShowCheck(true);
                    rlBottom.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tvOffAll:
                if(adapter.getList()==null||adapter.getList().size()<=0){
                    context.showMessage("请选择批量关闭的单子");
                    return;
                }
                String ids="";
                for(int i=0;i<adapter.getList().size();i++){
                    if(i==adapter.getList().size()-1){
                        ids+=adapter.getList().get(i);
                    }else {
                        ids+=adapter.getList().get(i)+",";
                    }

                }
                Bundle bundle=new Bundle();
                bundle.putString("from","stoptrant");
                bundle.putString("id",ids);
                readyGoForResult(OrderMainActivity.class,999,bundle);
                break;
            case R.id.llSearch:
                page=1;
                presenter.getSendCarList(page,status,searchType,edSearch.getText().toString());
                break;
            case R.id.llpopuw:
                final PopupWindowUtil popupWindowUtil = new PopupWindowUtil.Builder()
                        .setContext(mContext.getApplicationContext()) //设置 context
                        .setContentView(R.layout.dialog_paiche) //设置布局文件
                        .setOutSideCancel(true) //设置点击外部取消
                        .setwidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setFouse(true)
                        .builder()
                        .showAsLaction(llpopuw, Gravity.LEFT,0,0);
                popupWindowUtil.setOnClickListener(new int[]{R.id.rlall,R.id.rlcarNo,R.id.rlsjName,
                        R.id.rldanhao,R.id.rlfahuoshang,R.id.rlshouhuo}, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            case R.id.rlall://全部
                                tvPopuw.setText("全部");
                                searchType="全部";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlcarNo:
                                tvPopuw.setText("车牌号");
                                searchType="carNo";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlsjName:
                                tvPopuw.setText("司机姓名");
                                searchType="sjName";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rldanhao://
                                tvPopuw.setText("单号");
                                searchType="dispatchCarNo";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlfahuoshang://
                                tvPopuw.setText("发货商");
                                searchType="fromName";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlshouhuo://
                                tvPopuw.setText("收货商");
                                searchType="toName";
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
        return R.layout.fragment_sendcar_mainindex;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        edSearch.hiddenIco();

        tvDelMore.setVisibility(View.VISIBLE);
        adapter=new SendCarAdapter(mDatas,mContext,"1");
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        setNeedOnBus(true);
        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(!IS_RESH){
                    IS_RESH=true;
                    page++;
                    presenter.getSendCarList(page,status,searchType,edSearch.getText().toString());
                }
            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter=new SendCarLsitPresenter(this,mContext);
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                presenter.getSendCarList(page,status,searchType,edSearch.getText().toString());
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
                presenter.getSendCarList(page,status,searchType,edSearch.getText().toString());
            }
        });
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle  bundle=new Bundle();
                bundle.putString("id",mDatas.get(position).getId());
                readyGo(OrderManageMapActivity.class,bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        adapter.setOnTabClickListener(new SendCarAdapter.OnTabClickListener() {
            @Override
            public void onItemClick(String type, int position) {
                Bundle bundle;
                if(type.equals("tab1")){//当前位置
                    if(mDatas.get(position).getStartTime()==0){
                        context.showMessage("当前运单尚未开始");
                        return;
                    }
                    BitmapUtil.init();
                    bundle=new Bundle();
                    bundle.putString("type","guiji");
                    bundle.putDouble("startTime",mDatas.get(position).getStartTime());
                    bundle.putDouble("endTime",mDatas.get(position).getEndTime());
                    bundle.putString("id",mDatas.get(position).getSjId());
                    bundle.putString("travelId",mDatas.get(position).getId());
                    readyGo(TracingActivity.class,bundle);
                }else if(type.equals("tab2")){//申请停运
                    bundle=new Bundle();
                    bundle.putString("from","stoptrant");
                    bundle.putString("id",mDatas.get(position).getId());
                    readyGo(OrderMainActivity.class,bundle);
                }
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    checkBox.setText("取消全选");
                    if(mDatas.size()>0){
                        adapter.getList().clear();
                        for(int i=0;i<mDatas.size();i++){
                            adapter.getMap().put(i,true);
                            adapter.getList().add(mDatas.get(i).getId());
                        }
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    checkBox.setText("全选");
                    if(mDatas.size()>0){
                        adapter.getList().clear();
                        for(int i=0;i<mDatas.size();i++){
                            adapter.getMap().put(i,false);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

            }
        });
        adapter.setOnItemCheckListener(new SendCarAdapter.OnItemCheckListener() {
            @Override
            public void onItemClick(View view, int position, boolean isselect) {
                if(mDatas.size()<=position){
                    return;
                }
                if(isselect==true){
                    adapter.getList().add(mDatas.get(position).getId());
                }else {
                    adapter.getList().remove(mDatas.get(position).getId());
                }
                Log.e("msg",adapter.getList().toString());
               adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void getSendCarList(List<SendCarDto.SendCarData> dtos) {
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
            if(dtos==null||dtos.size()<=0){
                noData.setVisibility(View.VISIBLE);
            }else {
                noData.setVisibility(View.GONE);
            }
            rv.removeAllViews();
        }
        llSearch.setVisibility(View.GONE);
        adapter.addMoreData(dtos);
        IS_RESH=false;
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


    OnEntityListener entityListener = new OnEntityListener() {
        // 查询失败回调接口
        @Override
        public void onSearchEntityCallback(SearchResponse arg0) {
            context.showMessage(arg0.toString());
        }

        // 查询entity回调接口，返回查询结果列表
        @Override
        public void onReceiveLocation(TraceLocation arg0) {
            System.out.println("entity回调接口消息 : " + arg0);
            context.showMessage(arg0.toString());
        }


    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshSendCompete(String str) {
        if(!TextUtils.isEmpty(str)){
            if(str.equals(Constants.reshChangeCYS)||str.equals(Constants.reshYunDn)){
                Log.e("msg","刷新"+str);
                page=1;
                presenter.getSendCarList(page,status,searchType,edSearch.getText().toString());
            }
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==999 && resultCode == RESULT_OK) {
           adapter.getList().clear();
            adapter.getMap().clear();
           adapter.notifyDataSetChanged();
            page=1;
            presenter.getSendCarList(page,status,searchType,edSearch.getText().toString());
        }
    }
}
