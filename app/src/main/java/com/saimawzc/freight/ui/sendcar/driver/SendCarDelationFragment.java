package com.saimawzc.freight.ui.sendcar.driver;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.sendcar.SendCarMaterAdpater;
import com.saimawzc.freight.adapter.sendcar.SendCarNavAdpater;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.sendcar.CompleteExecuteDto;
import com.saimawzc.freight.dto.sendcar.SendCarDelatiodto;
import com.saimawzc.freight.dto.sendcar.WaitExecuteDto;
import com.saimawzc.freight.presenter.sendcar.SendCarDelationPresenter;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.view.sendcar.SendCarDelationView;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.DragTV;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/****
 * 派车详情
 * **/
public class SendCarDelationFragment extends BaseFragment
        implements SendCarDelationView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cv_natival) RecyclerView cvNatival;
    private SendCarNavAdpater navAdpater;
    private SendCarMaterAdpater materAdpater;

    public LinearLayoutManager navLineManage;
    private String id;

    private int type=1;
    SendCarDelationPresenter presenter;
    String []WayBillIdList;

    @BindView(R.id.tvSendId) TextView tvSendId;
    @BindView(R.id.tvStratAdress)TextView tvStratAdress;
    @BindView(R.id.tvEndAdress)TextView tvEndAdress;
    @BindView(R.id.tvCarrive)TextView tvCarrive;
    @BindView(R.id.tvCarNo)TextView tvCarNo;
    @BindView(R.id.rvSmallorder)RecyclerView rvSmallOrder;
    @BindView(R.id.tvKeshang)TextView tvKeshang;

    @BindView(R.id.tvKeBm)TextView tvKsSendId;//客商派车编码
    @BindView(R.id.tvSendPass)TextView tvSendPass;
    @BindView(R.id.tvQkTime)TextView tvQkTime;//取卡时间
    @BindView(R.id.tvpaicheEndTime)TextView tvpaicheEndTime;
    @BindView(R.id.imgapplyfp) ImageView imgapplyfp;
    @BindView(R.id.imgapplyzh) ImageView imgapplyzh;
    @BindView(R.id.imgapplyxh) ImageView imgapplyxh;
    @BindView(R.id.tvStartTask) DragTV tvStartTask;
    @BindView(R.id.tvLCId) TextView tvLCId;
    @BindView(R.id.tvLWText) TextView tvLWText;
    @BindView(R.id.resTxt2Relative)
    RelativeLayout resTxt2Relative;
    @BindView(R.id.resTxt2Text) TextView resTxt2Text;
    private  WaitExecuteDto.WaitExecuteData data;



    @Override
    public int initContentView() {
        return R.layout.fragment_sendcardelation;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        id=getArguments().getString("id");
        if(TextUtils.isEmpty(id)){
            context.showMessage("未获取到派车订单");
            return;
        }
        try {
            if(getArguments().getString("type").equals("complete")){
                tvStartTask.setVisibility(View.GONE);
            }else {
                tvStartTask.setVisibility(View.VISIBLE);
            }
            data= (WaitExecuteDto.WaitExecuteData) getArguments().getSerializable("data");
        }catch (Exception e){

        }
        context.setToolbar(toolbar,"派车详情");
        presenter=new SendCarDelationPresenter(this,mContext);
        presenter.getData(id,type);
    }
    private NormalDialog dialog;
    @Override
    public void initData() {
        try {
            if(getArguments().getInt("status")==2){
                tvStartTask.setText("查看任务");
            }else {
                tvStartTask.setText("开启任务");
            }
        }catch (Exception e){
        }

        tvStartTask.setOnTabClickListener(new DragTV.listen() {
            @Override
            public void onItemClick(View view) {
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                if(data==null){
                    return;
                }
                if(getArguments().getInt("status")==2){//查看任务
                    Bundle bundle=new Bundle();
                    bundle.putString("id",data.getId());
                    bundle.putString("tranttype",data.getTranType()+"");
                    bundle.putString("from","trant");
                    readyGo(OrderMainActivity.class,bundle);
                }else {//开启任务
                    dialog = new NormalDialog(mContext).isTitleShow(false)
                            .content("确定开启任务?")
                            .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                            .btnNum(2).btnText("取消", "确定");
                    dialog.setOnBtnClickL(
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    if (!context.isDestroy(context)) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("id", data.getId());
                                        bundle.putString("tranttype", data.getTranType() + "");
                                        bundle.putString("from", "trant");
                                        readyGo(OrderMainActivity.class, bundle);
                                        dialog.dismiss();
                                    }
                                }
                            },
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    presenter.startTask(data.getId());
                                    dialog.dismiss();
                                }
                            });
                    dialog.show();
                }
            }
        });
    }

    @Override
    public void getDelation(SendCarDelatiodto dtos,int type) {
        if(dtos!=null){
            tvSendId.setText(getArguments().getString("danhao"));
            tvStratAdress.setText(getArguments().getString("startadress"));
            tvEndAdress.setText(getArguments().getString("endadress"));
            tvCarrive.setText(dtos.getCysName());
            tvCarNo.setText(dtos.getCarNo());
            tvKeshang.setText(dtos.getThirdOrderNo());
            tvKsSendId.setText(dtos.getThirdDispatchNo());
            tvSendPass.setText(dtos.getThirdDispatchPwd());
            tvQkTime.setText(dtos.getStartTime());
            tvpaicheEndTime.setText(dtos.getEndTime());
            tvLCId.setText(dtos.getLcbh());
            tvLWText.setText(dtos.getLwxx());
            if (dtos.getResTxt2()!=null&&!TextUtils.isEmpty(dtos.getResTxt2())) {
                resTxt2Relative.setVisibility(View.VISIBLE);
                resTxt2Text.setText(dtos.getResTxt2());
            }else {
                resTxt2Relative.setVisibility(View.GONE);
            }

            if(type==1){//小单
                navLineManage=new LinearLayoutManager(mContext);
                navLineManage.setOrientation(LinearLayoutManager.HORIZONTAL);
                navAdpater=new SendCarNavAdpater(dtos.getWaybillIdList(),mContext);
                WayBillIdList=dtos.getWaybillIdList();
                cvNatival.setLayoutManager(navLineManage);
                cvNatival.setAdapter(navAdpater);
            }
            if(navAdpater!=null){
                navAdpater.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if(WayBillIdList.length<=position){
                            return;
                        }
                        navAdpater.setmPosition(position);
                        presenter.getData(WayBillIdList[position],2);
                    }
                    @Override
                    public void onItemLongClick(View view, int position) {
                    }
                });
            }
            //物料
            layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            materAdpater=new SendCarMaterAdpater(dtos.getMaterialsList(),mContext);
            rvSmallOrder.setLayoutManager(layoutManager);
            rvSmallOrder.setAdapter(materAdpater);
            if(dtos.getProvideInvoice()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgapplyfp);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgapplyfp);
            }
            if(dtos.getProvideLoad()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgapplyzh);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgapplyzh);
            }
            if(dtos.getProvideUnload()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgapplyxh);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgapplyxh);
            }
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
        Bundle bundle=new Bundle();
        bundle.putString("id",data.getId());
        bundle.putString("tranttype",data.getTranType()+"");
        bundle.putString("from","trant");
        readyGo(OrderMainActivity.class,bundle);
    }
}
