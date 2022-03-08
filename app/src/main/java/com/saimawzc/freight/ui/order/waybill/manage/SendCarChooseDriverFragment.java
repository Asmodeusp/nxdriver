package com.saimawzc.freight.ui.order.waybill.manage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.order.CarDriverAdpater;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.order.CarDriverDto;
import com.saimawzc.freight.dto.order.CarInfolDto;
import com.saimawzc.freight.presenter.order.CarDriverPresenter;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.view.order.SendDriverView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.LoadMoreListener;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.saimawzc.freight.adapter.BaseAdapter.IS_RESH;

/****
 * 派车选司机
 * */
public class SendCarChooseDriverFragment extends
        BaseFragment implements SendDriverView {

    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.aaaa)
    LinearLayout aaaa;
    @BindView(R.id.llSearch) LinearLayout llSearch;
    @BindView(R.id.tvSearch) TextView tvSearch;
    private List<CarDriverDto>mDatas=new ArrayList<>();
    @BindView(R.id.SwipeRefreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv)RecyclerView rv;
    @BindView(R.id.tvOrder)TextView tvOrder;
    private CarDriverAdpater adapter;
    private CarDriverPresenter presenter;
    private int currentTag=0;
    CarInfolDto.carInfoData data;
    private String type;
    private String id;
    private int trantType=1;//1司机 2船员
    private int CHOOSE_DRIVER=1001;
    private int page=1;
    private String companyId;
    private LoadMoreListener loadMoreListener;
    private PopupWindow popupWindow;

    @OnClick({R.id.tvCannel,R.id.llSearch,R.id.tvOrder})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvOrder:

                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                if(data==null){
                    context.showMessage("车辆信息为空");
                    return;
                }
                if(TextUtils.isEmpty(type)){
                    context.showMessage("单号类型为空");
                    return;
                }
                if(TextUtils.isEmpty(id)){
                    context.showMessage("单号ID为空");
                    return;
                }
                if(mDatas.size()<=0){
                    context.showMessage("请选择司机");
                    return;
                }
                presenter.sendCar(data,mDatas.get(currentTag),type,id,"");
                break;
            case R.id.tvCannel:
                context.finish();
                break;
            case R.id.llSearch:
                page=1;
                if(data==null){
                    presenter.getDatabyPage(edSearch.getText().toString(),trantType,"",page,companyId);
                }else {
                    presenter.getDatabyPage(edSearch.getText().toString(),trantType,data.getId(),page,companyId);
                }

                break;
        }
    }
    @Override
    public int initContentView() {
        return R.layout.fragment_driver;
    }

    @Override
    public void initView() {

        mContext=getActivity();
        try {
            data= (CarInfolDto.carInfoData) getArguments().getSerializable("data");
            type=getArguments().getString("type");
            id=getArguments().getString("id");
            trantType=getArguments().getInt("tranttype");
            companyId=getArguments().getString("companyId");
            if(type.equals("1")){
                tvOrder.setVisibility(View.GONE);
            }
        }catch (Exception e){

        }

        adapter=new CarDriverAdpater(mDatas,mContext);
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter=new CarDriverPresenter(this,mContext);
        if(data==null){
            presenter.getDatabyPage(edSearch.getText().toString(),trantType,"",page,companyId);
        }else {
            presenter.getDatabyPage(edSearch.getText().toString(),trantType,data.getId(),page,companyId);
        }
        edSearch.addTextChangedListener(textWatcher);

        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(IS_RESH==false){
                    page++;
                    if(data==null){
                        presenter.getDatabyPage(edSearch.getText().toString(),trantType,"",page,companyId);
                    }else {
                        presenter.getDatabyPage(edSearch.getText().toString(),trantType,data.getId(),page,companyId);
                    }
                    IS_RESH=true;
                }
            }
        };
        rv.setOnScrollListener(loadMoreListener);
    }

    @Override
    public void initData() {


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
              // mDatas.clear();
                page=1;
                if(data==null){
                    presenter.getDatabyPage(edSearch.getText().toString(),trantType,"",page,companyId);
                }else {
                    presenter.getDatabyPage(edSearch.getText().toString(),trantType,data.getId(),page,companyId);
                }
            }
        });

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                CarDriverDto carDriverDto = mDatas.get(position);
                if (carDriverDto.getStatus()!=null&&carDriverDto.getIfDisable()!=null) {
                    if (carDriverDto.getStatus()==1) {
//                        ToastUtil.ShowMsg(getActivity(),"该司机已被" + carInfoData.getHzUserName() + "货主加入黑名单，如要继续派车，" +
//                                "请联系" + carInfoData.getHzUserName() + "货主",3);
                        View inflate = LayoutInflater.from(mContext).inflate(R.layout.message_send, null, false);
                        TextView messageText = inflate.findViewById(R.id.messageText);
                        messageText.setText("该司机已被" + carDriverDto.getHzUserName() + "货主加入黑名单，如要继续派车，" +
                                "请联系" + carDriverDto.getHzUserName() + "货主");
                        // PopUpWindow 传入 ContentView
                        popupWindow = new PopupWindow(inflate, 500, ViewGroup.LayoutParams.WRAP_CONTENT);
                        // 设置背景
                        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.scan_code_gray999));
                        // 外部点击事件
                        popupWindow.setOutsideTouchable(true);
                        popupWindow.showAtLocation(aaaa, Gravity.CENTER, 0, 0);
                        new Handler(new Handler.Callback() {
                            @Override
                            public boolean handleMessage(@NonNull Message msg) {
                                popupWindow.dismiss();
                                return false;
                            }
                        }).sendEmptyMessageDelayed(0,3000);

                        return;
                    }else {
                        if (carDriverDto.getIfDisable()==2) {
//                            ToastUtil.ShowMsg(getActivity(),"该司机已被我找车平台加入黑名单，如要继续派车，请联系平台客服.",3);
                            View inflate = LayoutInflater.from(mContext).inflate(R.layout.message_send, null, false);
                            TextView messageText = inflate.findViewById(R.id.messageText);
                            messageText.setText("该司机已被我找车平台加入黑名单，如要继续派车，请联系平台客服.");
                            // PopUpWindow 传入 ContentView
                            popupWindow = new PopupWindow(inflate, 500, ViewGroup.LayoutParams.WRAP_CONTENT);
                            // 设置背景
                            popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.scan_code_gray999));
                            // 外部点击事件
                            popupWindow.setOutsideTouchable(true);
                            popupWindow.showAtLocation(aaaa, Gravity.CENTER, 0, 0);
                            new Handler(new Handler.Callback() {
                                @Override
                                public boolean handleMessage(@NonNull Message msg) {
                                    popupWindow.dismiss();
                                    return false;
                                }
                            }).sendEmptyMessageDelayed(0,3000);
                            return;
                        }
                    }
                }else {
                    if (carDriverDto.getIfDisable()!=null) {
                        if (carDriverDto.getIfDisable()==2) {
//                            ToastUtil.ShowMsg(getActivity(),"该司机已被我找车平台加入黑名单，如要继续派车，请联系平台客服.",3);
                            View inflate = LayoutInflater.from(mContext).inflate(R.layout.message_send, null, false);
                            TextView messageText = inflate.findViewById(R.id.messageText);
                            messageText.setText("该司机已被我找车平台加入黑名单，如要继续派车，请联系平台客服.");
                            // PopUpWindow 传入 ContentView
                            popupWindow = new PopupWindow(inflate, 500, ViewGroup.LayoutParams.WRAP_CONTENT);
                            // 设置背景
                            popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.scan_code_gray999));
                            // 外部点击事件
                            popupWindow.setOutsideTouchable(true);
                            popupWindow.showAtLocation(aaaa, Gravity.CENTER, 0, 0);
                            new Handler(new Handler.Callback() {
                                @Override
                                public boolean handleMessage(@NonNull Message msg) {
                                    popupWindow.dismiss();
                                    return false;
                                }
                            }).sendEmptyMessageDelayed(0,3000);
                            return;
                        }
                    }

                }
                if(type.equals("1")){//计划订单
                    Bundle bundle=new Bundle();
                    bundle.putString("from","chooseplanordernum");
                    bundle.putSerializable("data",data);
                    bundle.putString("type",getArguments().getString("type"));
                    bundle.putString("id",getArguments().getString("id"));
                    bundle.putString("sendcarnum",getArguments().getString("sendcarnum"));
                    bundle.putSerializable("driver",mDatas.get(position));
                    readyGoForResult(OrderMainActivity.class,CHOOSE_DRIVER,bundle);

                }else {
                    currentTag=position;
                    adapter.setmPosition(position);
                }



            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });


    }


    @Override
    public void getDriverList(List<CarDriverDto> dtos) {
        IS_RESH=false;
        if(!TextUtils.isEmpty(edSearch.getText().toString())){
            llSearch.setVisibility(View.GONE);
        }
        if(page==1){
            if(mDatas!=null){
                mDatas.clear();
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
    public BaseActivity getContect() {
        return context;
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
        Intent intent=new Intent();
        context.setResult(Activity.RESULT_OK,intent);
        context.finish();
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
        if(requestCode==CHOOSE_DRIVER && resultCode == RESULT_OK){
            Intent intent=new Intent();
            context.setResult(Activity.RESULT_OK,intent);
            context.finish();
        }
    }
}
