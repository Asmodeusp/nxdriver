package com.saimawzc.freight.ui.sendcar.driver;

import android.graphics.Color;
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

import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.weight.NoData;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.bigkoo.pickerview.OptionsPickerView;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.sendcar.ComeleteExecuteAdpater;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.base.TimeChooseListener;
import com.saimawzc.freight.dto.sendcar.CompleteExecuteDto;
import com.saimawzc.freight.presenter.sendcar.CompeleteExcecutePresenter;
import com.saimawzc.freight.ui.baidu.TracingActivity;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.view.sendcar.CompleteExecuteView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.utils.LoadMoreListener;
import com.saimawzc.freight.weight.utils.TimeChooseDialogUtil;
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
 * 已完成
 * **/

public class
SendCarCompleteFragment extends BaseFragment
        implements CompleteExecuteView {

    private ComeleteExecuteAdpater adpater;
    private List<CompleteExecuteDto.ComeletaExecuteData> mDatas=new ArrayList<>();
    @BindView(R.id.rv) RecyclerView rv;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private int page=1;
    private LoadMoreListener loadMoreListener;
    private CompeleteExcecutePresenter presenter;
    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.tvpopuw)TextView tvPopuw;
    @BindView(R.id.llpopuw)LinearLayout llpopuw;
    private String queryType="";

    @BindView(R.id.tvStartTime)TextView tvStartTime;
    @BindView(R.id.tvEndTime)TextView tvEndTime;
    @BindView(R.id.tvStatus)TextView tvStatus;
    int status;
    @BindView(R.id.nodata)
    NoData noData;

    @Override
    public int initContentView() {
        return R.layout.fragment_paiche_comelete;
    }
    @Override
    public void initView() {
        userInfoDto= Hawk.get(PreferenceKey.USER_INFO);
        adpater=new ComeleteExecuteAdpater(mDatas,mContext);
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adpater);
        setNeedOnBus(true);
        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(!IS_RESH){
                    page++;
                    presenter.getData(page,queryType,edSearch.getText().toString(),tvStartTime.getText().toString()
                            ,tvEndTime.getText().toString(),status);
                    IS_RESH=true;
                }

            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter=new CompeleteExcecutePresenter(this,mContext);
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                presenter.getData(page,queryType,edSearch.getText().toString(),tvStartTime.getText().toString()
                        ,tvEndTime.getText().toString(),status);
            }
        });
        edSearch.addTextChangedListener(textWatcher);
        edSearch.hiddenIco();
    }
    @Override
    public void initData() {
       // edSearch.addTextChangedListener(textWatcher);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getData(page,queryType,edSearch.getText().toString(),tvStartTime.getText().toString()
                        ,tvEndTime.getText().toString(),status);

            }
        });

        adpater.setOnTabClickListener(new BaseAdapter.OnTabClickListener() {
            @Override
            public void onItemClick(String type, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle;
                if(type.equals("tab1")){//物流信息
                    bundle=new Bundle();
                    bundle.putString("from","logistaic");
                    bundle.putString("id",mDatas.get(position).getId());
                    readyGo(OrderMainActivity.class,bundle);
                }else if(type.equals("tab2")){//地图轨迹
                    if(mDatas.get(position).getStartTime()==0){
                        context.showMessage("当前运单尚未开始");
                        return;
                    }
                    bundle=new Bundle();
                    bundle.putString("type","guiji");
                    bundle.putDouble("startTime",mDatas.get(position).getStartTime());
                    bundle.putDouble("endTime",mDatas.get(position).getEndTime());
                    if(userInfoDto!=null&&!TextUtils.isEmpty(userInfoDto.getRoleId())){
                        bundle.putString("id",userInfoDto.getRoleId());
                    }
                    bundle.putString("travelId","siji");
                    readyGo(TracingActivity.class,bundle);
                }else if(type.equals("tab3")){//客商信息
                    context.showMessage("客商信息正在开发中");

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
                bundle.putString("from","sendcardelation");
                bundle.putString("id",mDatas.get(position).getId());
                bundle.putString("type","complete");
                bundle.putInt("status",-1);
                bundle.putString("danhao",mDatas.get(position).getDispatchCarNo());
                bundle.putString("startadress", mDatas.get(position).getFromUserAddress());
                bundle.putString("endadress", mDatas.get(position).getToUserAddress());
                bundle.putSerializable("data",null);
                bundle.putInt("position",position);
                readyGo(OrderMainActivity.class,bundle);
            }
            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
    private OptionsPickerView optionsPickerView;//底部滚轮实现
    TimeChooseDialogUtil chooseDialogUtil;
    private List<String> statusList=new ArrayList<>();
    @OnClick({R.id.llSearch,R.id.llpopuw,R.id.llstatus,R.id.llstarttime,R.id.llendtime})
    public void click(View view){
        switch (view.getId()){
            case R.id.llstarttime:
                if(chooseDialogUtil==null){
                    chooseDialogUtil  =new TimeChooseDialogUtil(mContext);
                }

                chooseDialogUtil.showDialog(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        tvStartTime.setText(result);
                        page=1;
                        presenter.getData(page,queryType,edSearch.getText().toString(),tvStartTime.getText().toString()
                                ,tvEndTime.getText().toString(),status);
                    }

                    @Override
                    public void cancel() {
                        chooseDialogUtil.dissDialog();
                    }
                });

                break;
            case R.id.llendtime:
                if(chooseDialogUtil==null){
                    chooseDialogUtil  =new TimeChooseDialogUtil(mContext);
                }
                chooseDialogUtil.showDialog(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        tvEndTime.setText(result);
                        page=1;
                        presenter.getData(page,queryType,edSearch.getText().toString(),tvStartTime.getText().toString()
                                ,tvEndTime.getText().toString(),status);
                    }

                    @Override
                    public void cancel() {
                        chooseDialogUtil.dissDialog();
                    }
                });
                break;
            case R.id.llstatus:
                statusList.clear();
                statusList.add("已派车");
                statusList.add("已签收");
                statusList.add("未对账");
                statusList.add("已结算");
                statusList.add("未付款");
                statusList.add("已结束");
                optionsPickerView = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        String str = statusList.get(options1);
                        tvStatus.setText(str);
                        status=options1+1;
                        page=1;
                        presenter.getData(page,queryType,edSearch.getText().toString(),tvStartTime.getText().toString()
                                ,tvEndTime.getText().toString(),status);
                    }
                }).setCancelColor(Color.GRAY).
                        setSubmitColor(Color.RED).build();
                optionsPickerView.setNPicker(statusList,null,null);
                optionsPickerView.show();
                break;

            case R.id.llSearch:
                page=1;
                mDatas.clear();
                presenter.getData(page,queryType,edSearch.getText().toString(),tvStartTime.getText().toString()
                        ,tvEndTime.getText().toString(),status);
                break;
            case R.id.llpopuw:
                final PopupWindowUtil popupWindowUtil = new PopupWindowUtil.Builder()
                        .setContext(mContext.getApplicationContext()) //设置 context
                        .setContentView(R.layout.dialog_waitsendcar) //设置布局文件
                        .setOutSideCancel(true) //设置点击外部取消
                        .setwidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setFouse(true)
                        .builder()
                        .showAsLaction(llpopuw, Gravity.CENTER,-10,0);
                popupWindowUtil.setOnClickListener(new int[]{R.id.rlall, R.id.rlpaiche,R.id.rlCarNo,
                        R.id.rlcarrive,R.id.rlfahuodi,R.id.rlmudi,R.id.rlwuliao}, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            case R.id.rlall://全部
                                tvPopuw.setText("全部");
                                queryType="1";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlCarNo:
                                tvPopuw.setText("车牌号");
                                queryType="6";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlpaiche://
                                tvPopuw.setText("派车单");
                                queryType="2";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlcarrive://
                                tvPopuw.setText("承运商");
                                queryType="3";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlfahuodi://
                                tvPopuw.setText("发货地");
                                queryType="4";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlmudi:
                                tvPopuw.setText("目的地");
                                queryType="5";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlwuliao:
                                tvPopuw.setText("物料名称");
                                queryType="7";
                                popupWindowUtil.dismiss();
                                break;
                        }
                    }
                });
                break;
        }
    }
    @Override
    public void isLastPage(boolean islastpage) {
        if(islastpage){
            loadMoreListener.isLoading = true;
            adpater.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }else {
            loadMoreListener.isLoading = false;
            adpater.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }
    }
    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);
    }
    @Override
    public void getSendCarList(List<CompleteExecuteDto.ComeletaExecuteData> dtos){
        if(dtos!=null){
            if(page==1){
                mDatas.clear();
                adpater.notifyDataSetChanged();
                if(dtos==null||dtos.size()<=0){
                    noData.setVisibility(View.VISIBLE);
                }else {
                    noData.setVisibility(View.GONE);
                }
            }
            llSearch.setVisibility(View.GONE);
            adpater.addMoreData(dtos);
            IS_RESH=false;
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
        if(TextUtils.isEmpty(PreferenceKey.DRIVER_IS_INDENFICATION)||!Hawk.get(PreferenceKey.DRIVER_IS_INDENFICATION,"").equals("1")){
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
    public void reshSendCompete(String str) {
        if(!TextUtils.isEmpty(str)){
            if(str.equals(Constants.reshChange)||str.equals(Constants.reshReQS)){
                Log.e("msg","刷新"+str);
                page=1;
                presenter.getData(page,queryType,edSearch.getText().toString(),tvStartTime.getText().toString()
                        ,tvEndTime.getText().toString(),status);
            }
        }
    }
}
