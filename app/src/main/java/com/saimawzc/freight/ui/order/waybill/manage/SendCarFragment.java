package com.saimawzc.freight.ui.order.waybill.manage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.order.CarListAdpater;
import com.saimawzc.freight.adapter.order.CarModelAdpater;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.order.CarDriverDto;
import com.saimawzc.freight.dto.order.CarInfolDto;
import com.saimawzc.freight.dto.order.CarModelDto;
import com.saimawzc.freight.presenter.order.SendCarPresenter;
import com.saimawzc.freight.ui.login.LoginActivity;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.view.order.SendCarView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.LoadMoreListener;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import com.saimawzc.freight.weight.utils.ToastUtil;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
/****
 * 派车  派船
 * */
public class SendCarFragment extends BaseFragment implements SendCarView {

    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch) LinearLayout llSearch;
    @BindView(R.id.tvSearch) TextView tvSearch;
    private CarModelAdpater carModelAdpater;
    private List<CarModelDto>carModelDtos=new ArrayList<>();
    private List<CarInfolDto.carInfoData>carInfoData=new ArrayList<>();
    @BindView(R.id.rvCarmodle) RecyclerView rvCarModel;
    @BindView(R.id.rvCar)RecyclerView rvCar;
    public LinearLayoutManager carlistlayoutManager;
    private CarListAdpater carListadapter;
    private int trantType=1;
    private String companyId;

    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private SendCarPresenter presenter;
    @BindView(R.id.tvOrder)TextView tvOrder;
    @BindView(R.id.aaaa)
    RelativeLayout aaaa;
    PopupWindow popupWindow;

    private int page=1;
    private LoadMoreListener loadMoreListener;
    private String currentCarTypeId;

    private int CHOOSE_DRIVER=1001;
    private int clickPosition=0;
    private String beidouStatus=3+"";



    @OnClick({R.id.tvCannel,R.id.llSearch,R.id.tvOrder})
    public void click(View view){
        if(!RepeatClickUtil.isFastClick()){
            context.showMessage("您操作太频繁，请稍后再试");
            return;
        }
        switch (view.getId()){
            case R.id.tvCannel:
                context.finish();
                break;
            case R.id.llSearch:
                if(context.isEmptyStr(edSearch)){
                    context.showMessage("请输入货主名");
                    return;
                }
                page=1;
                carInfoData.clear();
                if(Hawk.get(PreferenceKey.LOGIN_TYPE,2)==2){
                    presenter.getCarInfo(page,currentCarTypeId,edSearch.getText().toString(),trantType,companyId);
                }else {
                    presenter.getsjCarInfo(page,currentCarTypeId,edSearch.getText().toString(),trantType,companyId);
                }
                break;
            case R.id.tvOrder:

                if(carInfoData==null||carInfoData.size()<=0){
                    context.showMessage("车辆信息为空");
                    return;
                }
                if(carInfoData.get(clickPosition)==null){
                    context.showMessage("车辆信息为空");
                    return;
                }
                if(TextUtils.isEmpty(getArguments().getString("type"))){
                    context.showMessage("单号类型为空");
                    return;
                }
                if(TextUtils.isEmpty(getArguments().getString("id"))){
                    context.showMessage("单号ID为空");
                    return;
                }
                presenter.sendCar(carInfoData.get(clickPosition),getArguments().getString("type"),getArguments().getString("id"),"");
                break;
        }
    }
    @Override
    public int initContentView() {
        return R.layout.fragment_sendcar;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        try {
            trantType=getArguments().getInt("tranttype");
        }catch (Exception e){

        }
        try {
            beidouStatus=getArguments().getString("beidoustatus");
        }catch (Exception e){
            beidouStatus=3+"";
        }
        try {
            companyId=getArguments().getString("companyId");
        }catch (Exception e){

        }
        presenter=new SendCarPresenter(this,mContext);
        presenter.getCarmodel(trantType);

        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        carModelAdpater=new CarModelAdpater(carModelDtos,mContext);
        rvCarModel.setLayoutManager(layoutManager);
        rvCarModel.setAdapter(carModelAdpater);

        carlistlayoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        carListadapter=new CarListAdpater(carInfoData,mContext);
        rvCar.setLayoutManager(carlistlayoutManager);
        rvCar.setAdapter(carListadapter);
        if(Hawk.get(PreferenceKey.LOGIN_TYPE,2)==2){
            tvOrder.setVisibility(View.GONE);
        }else {
            if(getArguments().getString("type").equals("1")){//计划单
                tvOrder.setVisibility(View.GONE);
            }else {
                tvOrder.setVisibility(View.VISIBLE);
            }

        }

        loadMoreListener=new LoadMoreListener(carlistlayoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                if(Hawk.get(PreferenceKey.LOGIN_TYPE,2)==2){
                    presenter.getCarInfo(page,currentCarTypeId,edSearch.getText().toString(),trantType,companyId);
                }else {
                    presenter.getsjCarInfo(page,currentCarTypeId,edSearch.getText().toString(),trantType,companyId);
                }
            }
        };
        rvCar.setOnScrollListener(loadMoreListener);
        edSearch.addTextChangedListener(textWatcher);
    }

    @Override
    public void initData() {
        carModelAdpater.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(carModelDtos.size()<=position){
                    return;

                }
                carModelAdpater.setmPosition(position);
                carModelAdpater.notifyDataSetChanged();
                page=1;
                currentCarTypeId=carModelDtos.get(position).getId();
                if(Hawk.get(PreferenceKey.LOGIN_TYPE,2)==2){
                    presenter.getCarInfo(position,carModelDtos.get(position).getId(),edSearch.getText().toString(),trantType,companyId);
                }else {
                    presenter.getsjCarInfo(position,carModelDtos.get(position).getId(),edSearch.getText().toString(),trantType,companyId);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                if(Hawk.get(PreferenceKey.LOGIN_TYPE,2)==2){
                    presenter.getCarInfo(page,currentCarTypeId,edSearch.getText().toString(),trantType,companyId);
                }else {
                    presenter.getsjCarInfo(page,currentCarTypeId,edSearch.getText().toString(),trantType,companyId);
                }
            }
        });

        carListadapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(carInfoData.size()<=position){
                    return;
                }
                CarInfolDto.carInfoData carInfoData = SendCarFragment.this.carInfoData.get(position);
                if (carInfoData.getIsBlackList()!=null&&carInfoData.getIfDisable()!=null) {
                    if (carInfoData.getIsBlackList()==1) {
//                        ToastUtil.ShowMsg(getActivity(),"该司机已被" + carInfoData.getHzUserName() + "货主加入黑名单，如要继续派车，" +
//                                "请联系" + carInfoData.getHzUserName() + "货主",3);
                        View inflate = LayoutInflater.from(mContext).inflate(R.layout.message_send, null, false);
                        TextView messageText = inflate.findViewById(R.id.messageText);
                        messageText.setText("该司机已被" + carInfoData.getHzUserName() + "货主加入黑名单，如要继续派车，" +
                                "请联系" + carInfoData.getHzUserName() + "货主");
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
                        if (carInfoData.getIfDisable()==2) {
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
                    if (carInfoData.getIfDisable()!=null) {
                        if (carInfoData.getIfDisable()==2) {
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
                clickPosition=position;
                carListadapter.setmPosition(position);
                if(!TextUtils.isEmpty(beidouStatus)){
                    if(beidouStatus.equals("1")||beidouStatus.equals("2")){
                        presenter.isHavaBeiDou(getArguments().getString("type"),
                                getArguments().getString("id"), SendCarFragment.this.carInfoData.get(position).getId(),
                                SendCarFragment.this.carInfoData.get(position).getCarNo());
                    }else {
                        if(Hawk.get(PreferenceKey.LOGIN_TYPE,2)==2){//承运商
                            Bundle bundle=new Bundle();
                            bundle.putString("from","chooseSj");
                            bundle.putSerializable("data", SendCarFragment.this.carInfoData.get(position));
                            bundle.putString("type",getArguments().getString("type"));
                            bundle.putString("id",getArguments().getString("id"));
                            bundle.putString("sendcarnum",getArguments().getString("sendcarnum"));
                            bundle.putInt("tranttype",trantType);
                            bundle.putString("companyId",companyId);
                            readyGoForResult(OrderMainActivity.class,CHOOSE_DRIVER,bundle);
                        }else {
                            if(getArguments().getString("type").equals("1")){//大单
                                Bundle bundle=new Bundle();
                                bundle.putString("from","chooseplanordernum");
                                bundle.putSerializable("data", SendCarFragment.this.carInfoData.get(position));
                                bundle.putString("type",getArguments().getString("type"));
                                bundle.putString("id",getArguments().getString("id"));
                                bundle.putString("sendcarnum",getArguments().getString("sendcarnum"));
                                bundle.putSerializable("driver",null);
                                readyGoForResult(OrderMainActivity.class,CHOOSE_DRIVER,bundle);
                            }
                        }
                    }
                }else {
                    if(Hawk.get(PreferenceKey.LOGIN_TYPE,2)==2){//
                        Bundle bundle=new Bundle();
                        bundle.putString("from","chooseSj");
                        bundle.putString("companyId",companyId);
                        bundle.putSerializable("data", SendCarFragment.this.carInfoData.get(position));
                        bundle.putString("type",getArguments().getString("type"));
                        bundle.putString("id",getArguments().getString("id"));
                        bundle.putString("sendcarnum",getArguments().getString("sendcarnum"));
                        bundle.putInt("tranttype",trantType);
                        readyGoForResult(OrderMainActivity.class,CHOOSE_DRIVER,bundle);
                    }else {
                        if(getArguments().getString("type").equals("1")){//大单
                            Bundle bundle=new Bundle();
                            bundle.putString("from","chooseplanordernum");
                            bundle.putSerializable("data", SendCarFragment.this.carInfoData.get(position));
                            bundle.putString("type",getArguments().getString("type"));
                            bundle.putString("id",getArguments().getString("id"));
                            bundle.putString("sendcarnum",getArguments().getString("sendcarnum"));
                            bundle.putSerializable("driver",null);
                            readyGoForResult(OrderMainActivity.class,CHOOSE_DRIVER,bundle);
                        }
                    }
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }


    @Override
    public void getCarModelList(List<CarModelDto> dtos) {
        carModelDtos.clear();
        CarModelDto carModelDto=new CarModelDto();
        carModelDto.setCarTypeName("全部");
        carModelDto.setId("");
        carModelDtos.add(carModelDto);
        if(dtos.size()>0){
            currentCarTypeId="";
            if(Hawk.get(PreferenceKey.LOGIN_TYPE,2)==2){
                presenter.getCarInfo(page,currentCarTypeId,edSearch.getText().toString(),trantType,companyId);
            }else {
                presenter.getsjCarInfo(page,currentCarTypeId,edSearch.getText().toString(),trantType,companyId);
            }
        }
        carModelAdpater.addMoreData(dtos);

    }

    @Override
    public void getCarInfolList(List<CarInfolDto.carInfoData> dtos) {
         if(page==1){

             carInfoData.clear();
             carListadapter.notifyDataSetChanged();
             if(!TextUtils.isEmpty(edSearch.getText().toString())){
                 carListadapter.setmPosition(10000);
                 llSearch.setVisibility(View.GONE);
             }
         }
        carListadapter.addMoreData(dtos);
    }

    @Override
    public void isLastPage(boolean islastpage) {
        if(islastpage){
            loadMoreListener.isLoading = true;
            carListadapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }else {
            loadMoreListener.isLoading = false;
            carListadapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }
    }

    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);

    }
    private NormalDialog dialog;
    @Override
    public void ishaveBeiDou(boolean stasue) {
        if(stasue){//有北斗
            if(Hawk.get(PreferenceKey.LOGIN_TYPE,2)==2){//
                Bundle bundle=new Bundle();
                bundle.putString("from","chooseSj");
                bundle.putSerializable("data",carInfoData.get(clickPosition));
                bundle.putString("type",getArguments().getString("type"));
                bundle.putString("id",getArguments().getString("id"));
                bundle.putString("sendcarnum",getArguments().getString("sendcarnum"));
                bundle.putInt("tranttype",trantType);
                bundle.putString("companyId",companyId);
                readyGoForResult(OrderMainActivity.class,CHOOSE_DRIVER,bundle);
            }else {
                if(getArguments().getString("type").equals("1")){//大单
                    Bundle bundle=new Bundle();
                    bundle.putString("from","chooseplanordernum");
                    bundle.putSerializable("data",carInfoData.get(clickPosition));
                    bundle.putString("type",getArguments().getString("type"));
                    bundle.putString("id",getArguments().getString("id"));
                    bundle.putString("sendcarnum",getArguments().getString("sendcarnum"));
                    bundle.putSerializable("driver",null);
                    readyGoForResult(OrderMainActivity.class,CHOOSE_DRIVER,bundle);
                }
            }
        }else {
            if(beidouStatus.equals("1")){
                context.showMessage("当前车辆无北斗，禁止派车！");
            }else {
                dialog = new NormalDialog(mContext).isTitleShow(false)
                        .content("当前车辆无北斗，是否继续派车?")
                        .contentGravity(Gravity.CENTER)
                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                        .btnNum(2).btnText("取消", "确定");
                dialog.setOnBtnClickL(
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if(!context.isDestroy(context)){
                                    dialog.dismiss();
                                }
                            }
                        },
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if(!context.isDestroy(context)){
                                    dialog.dismiss();
                                }

                                if(Hawk.get(PreferenceKey.LOGIN_TYPE,2)==2){//
                                    Bundle bundle=new Bundle();
                                    bundle.putString("from","chooseSj");
                                    bundle.putSerializable("data",carInfoData.get(clickPosition));
                                    bundle.putString("type",getArguments().getString("type"));
                                    bundle.putString("id",getArguments().getString("id"));
                                    bundle.putString("sendcarnum",getArguments().getString("sendcarnum"));
                                    bundle.putInt("tranttype",trantType);
                                    bundle.putString("companyId",companyId);
                                    readyGoForResult(OrderMainActivity.class,CHOOSE_DRIVER,bundle);
                                }else {
                                    if (getArguments().getString("type").equals("1")) {//大单
                                        Bundle bundle = new Bundle();
                                        bundle.putString("from", "chooseplanordernum");
                                        bundle.putSerializable("data", carInfoData.get(clickPosition));
                                        bundle.putString("type", getArguments().getString("type"));
                                        bundle.putString("id", getArguments().getString("id"));
                                        bundle.putString("sendcarnum", getArguments().getString("sendcarnum"));
                                        bundle.putSerializable("driver", null);
                                        readyGoForResult(OrderMainActivity.class, CHOOSE_DRIVER, bundle);
                                    }
                                }
                            }
                        });
                dialog.show();
            }

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
            if(Hawk.get(PreferenceKey.LOGIN_TYPE,2)==2){//承运商
                EventBus.getDefault().post(Constants.reshYunDn);
            }else {
                EventBus.getDefault().post(Constants.reshYunDnsJ);
            }
            context.finish();

        }
    }
}
