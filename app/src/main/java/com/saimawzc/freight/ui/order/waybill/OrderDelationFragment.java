package com.saimawzc.freight.ui.order.waybill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.order.waybill.WayBillGoodAdpater;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.order.OrderDelationDto;
import com.saimawzc.freight.dto.order.mainindex.WaitOrderDto;
import com.saimawzc.freight.dto.order.waybill.AddWayBillGoodsDto;
import com.saimawzc.freight.dto.order.waybill.GoodsCompanyDto;
import com.saimawzc.freight.presenter.order.waybill.WaybillApprovalPresenter;
import com.saimawzc.freight.ui.order.ShowArtActivity;
import com.saimawzc.freight.view.order.WaybillApprovalView;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/***
 * 订单详情
 * **/
public class OrderDelationFragment extends BaseFragment
        implements WaybillApprovalView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String id;
    @BindView(R.id.tvAuthority)
    TextView tvAuthority;//组织机构
    @BindView(R.id.tvconsigncompany) TextView tvConsignCompany;
    @BindView(R.id.tvbilltype) TextView tvBillType;
    @BindView(R.id.tvsendcompany) TextView tvSendCompany;
    @BindView(R.id.tvsendadress) TextView tvSendAdress;
    @BindView(R.id.sendBussinesstime) TextView tvSendBussiTime;
    @BindView(R.id.tvreceivecompany) TextView tvReceiveCompany;
    @BindView(R.id.tvreceiveadress) TextView tvReceiveAdress;
    @BindView(R.id.tvreceivebuniesstime) TextView tvReceiveBuniessTime;
    @BindView(R.id.tvorderpeople) TextView tvOrderPeople;
    @BindView(R.id.tvreceivestrage) TextView tvReceiveStrage;
    @BindView(R.id.tvsign) TextView tvSignStrage;
    @BindView(R.id.tvauthsign) TextView tvIsAutoSign;
    @BindView(R.id.tvreceivetime) TextView tvReceiveTime;
    @BindView(R.id.tvkeshangnum) TextView tvKsNum;
    @BindView(R.id.tvmaketime) TextView tvMakeTime;
    @BindView(R.id.tvmakepeople) TextView tvMakePeople;
    @BindView(R.id.tvpayxieyi) TextView tvPayXieyi;
    @BindView(R.id.tvmark) TextView tvMark;
    @BindView(R.id.imgapplyyh) ImageView imgApplyyh;
    @BindView(R.id.imgapplyzh) ImageView imgApplyzh;
    @BindView(R.id.imgapplyxh) ImageView imgApplyxh;
    @BindView(R.id.imgapplyfp) ImageView imgApplyfp;
    @BindView(R.id.imgapplyzhpz) ImageView imgApplyzhpz;
    @BindView(R.id.imgapplyxhpz) ImageView imgApplyxhpz;
    @BindView(R.id.tvTrantWay)TextView tvTrantWay;
    @BindView(R.id.tvbussestype)TextView tvBussType;
    @BindView(R.id.cy) RecyclerView rv;
    private WaybillApprovalPresenter presenter;
    private String type="";
    @BindView(R.id.llBtn) LinearLayout llBtn;
    private WayBillGoodAdpater adpater;
    private List<AddWayBillGoodsDto> mDatas=new ArrayList<>();
    private int waybillstatus;
    @BindView(R.id.tvBigNo)TextView tvBigNo;
    @BindView(R.id.llBigNo)LinearLayout llBigNo;

    @BindView(R.id.tvroule)TextView tvRoute;
    @BindView(R.id.tvreceiveobj)TextView tvReceiveObj;
    @BindView(R.id.tvhZname)TextView tvhZname;
    @BindView(R.id.tvstayTime)TextView tvstayTime;

    @BindView(R.id.imgplyj)ImageView imgPyyj;
    @BindView(R.id.imgstayyz)ImageView imgstayyz;
    @BindView(R.id.imglock)ImageView imglock;
    @BindView(R.id.imgguobang)ImageView imgguobang;
    @BindView(R.id.imgoffline)ImageView imgoffline;

    @BindView(R.id.tvcarmodel)TextView tvcarmodel;
    @BindView(R.id.tvdriverage)TextView tvDriverAge;
    @BindView(R.id.tvcarage)TextView tvCarAge;
    @BindView(R.id.tvaqgz)TextView tvAqgz;
    @BindView(R.id.imgcarmodel)ImageView imgCarModel;
    @BindView(R.id.tvrelacom)TextView tvRelaCom;
    @BindView(R.id.imgwl)ImageView imgsignWl;

    @BindView(R.id.imgtrantorder)ImageView imgTrantOrder;
    @BindView(R.id.imgintosign)ImageView imgIntosign;
    @BindView(R.id.imgopenArrival)ImageView imgopenArrival;
    @BindView(R.id.imgautotrant)ImageView imgautotrant;
    @BindView(R.id.tvroadless)TextView tvLoadLess;
    @BindView(R.id.imgbangdan)ImageView imgBangdan;
    @BindView(R.id.tvfence)TextView tvFence;
    @BindView(R.id.tvbeidoustatus)TextView tvbeidoustatus;
    @BindView(R.id.imgautoarrive)ImageView imgArrive;
    @BindView(R.id.tvofftime)TextView tvOffTime;
    @BindView(R.id.tvspaceTime)TextView tvSpaceTime;
    @BindView(R.id.resTxt2)TextView resTxt2;
    @BindView(R.id.resTxt2Linear)LinearLayout resTxt2Linear;
    private Double pointPrice;

    @Override
    public int initContentView() {
        return R.layout.fragment_orderdelation;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"订单审核");
        id=getArguments().getString("id");
        try{
            waybillstatus=getArguments().getInt("waybillstatus");
            type=getArguments().getString("type");
            if(type.equals("delation")){
                llBtn.setVisibility(View.GONE);
                context.setToolbar(toolbar,"订单详情");
            }
        }catch (Exception e){

        }
        presenter=new WaybillApprovalPresenter(this,mContext);
        if(waybillstatus==1){
            llBigNo.setVisibility(View.VISIBLE);
        }else {
            llBigNo.setVisibility(View.GONE);
        }
        if(Hawk.get(PreferenceKey.LOGIN_TYPE,0)==2){//承运商
            if(waybillstatus==1){//计划订单
                presenter.getpOrderDelation(id,1+"");
            }else if(waybillstatus==2){//预备运单
                presenter.getpOrderDelation(id,2+"");
            }else {
            }
        }else if(Hawk.get(PreferenceKey.LOGIN_TYPE,0)==3){
            if(waybillstatus==1){//计划订单
                presenter.getSjOrderDelation(id,1+"");
            }else if(waybillstatus==2){//预备运单
                presenter.getSjOrderDelation(id,2+"");
            }else {
            }
        }
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        adpater=new WayBillGoodAdpater(mDatas,mContext,1);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adpater);

    }

    @OnClick({R.id.tvAgreen,R.id.tvRefuse})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvAgreen:
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                presenter.approval(id,1,waybillstatus+"");
                break;
            case R.id.tvRefuse:
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                presenter.approval(id,2,waybillstatus+"");
                break;
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void getOrderDelation(final OrderDelationDto dto) {
        if(dto!=null){
            tvAuthority.setText(dto.getCompanyName());
            tvConsignCompany.setText(dto.getHandComName());
            if(dto.getWayBillType().equals("2")){
                tvBillType.setText("采购运单");
            }else if(dto.getWayBillType().equals("1")){
                tvBillType.setText("销售运单");
            }else if(dto.getWayBillType().equals("3")){
                tvBillType.setText("调拨运单");
            }else {
                tvBillType.setText(dto.getWayBillType());
            }
            tvBigNo.setText(dto.getPlanWayBillNo());
            if(dto.getTranType()==1){
                tvTrantWay.setText("汽运");

            }else if(dto.getTranType()==2){
                tvTrantWay.setText("船运");
            }

            tvSendCompany.setText(dto.getFromName());
            tvSendAdress.setText(dto.getFromUserAddress());
            tvSendBussiTime.setText(dto.getFromOperateTime());
            tvReceiveCompany.setText(dto.getToName());
            tvReceiveAdress.setText(dto.getToUserAddress());
            tvReceiveBuniessTime.setText(dto.getToOperateTime());
            tvOrderPeople.setText(dto.getConfirmorName());
            tvReceiveStrage.setText(dto.getConfirmorStacticsName()+"");
            tvSignStrage.setText(dto.getSingStacticsName());
            tvRelaCom.setText(dto.getChoose().getRelationComName());
            if(dto.getBusinessType()==1){
                tvBussType.setText("总包");
            }else if(dto.getBusinessType()==2){
                tvBussType.setText("自营");
            }else if(dto.getBusinessType()==3){
                tvBussType.setText("平台业务");
            }
            //运价
            Double pointPrice = dto.getPointPrice();
            for(int i=0;i<dto.getList().size();i++){
                AddWayBillGoodsDto tempDto=new AddWayBillGoodsDto();
                GoodsCompanyDto goodNameDto=new GoodsCompanyDto();
                goodNameDto.setName(dto.getList().get(i).getMaterialsName());
                goodNameDto.setId(dto.getList().get(i).getMaterialsId());
                tempDto.setGoodsCompanyDto(goodNameDto);
                tempDto.setUtil(dto.getList().get(i).getUnit());
                tempDto.setGoodPrice(pointPrice);
                tempDto.setGoodNum(dto.getList().get(i).getWeight());
                tempDto.setUnitName(dto.getList().get(i).getUnitName());
                tempDto.setBussType(dto.getBusinessType());
                tempDto.setGoodprice_two(dto.getList().get(i).getGoodsPrice());
                mDatas.add(tempDto);
            }
            adpater.notifyDataSetChanged();

            if(dto.getAutoSign()==1){
                tvIsAutoSign.setText("是");
            }else {
                tvIsAutoSign.setText("否");
            }
            tvReceiveTime.setText(dto.getArrivalStartTime()+"~"+dto.getArrivalEndTime());
            tvLoadLess.setText(dto.getChoose().getRoadLoss()+"%");
            tvKsNum.setText(dto.getChoose().getThirdPartyNo());
            tvMakeTime.setText(dto.getChoose().getMakerTime());
            tvMakePeople.setText(dto.getChoose().getMakerName());
            tvPayXieyi.setText(dto.getChoose().getPayProtocolName());
            tvMark.setText(dto.getChoose().getRemark());
            if (dto.getChoose().getResTxt2()!=null&&!TextUtils.isEmpty(dto.getChoose().getResTxt2())) {
                resTxt2.setText(dto.getChoose().getResTxt2());
            }else {
                resTxt2Linear.setVisibility(View.GONE);
            }
            tvReceiveObj.setText(dto.getChoose().getPushAlarmRoleName());
            tvhZname.setText(dto.getChoose().getAlarmHzName());
            tvRoute.setText(dto.getRouteName());
            if(dto.getChoose().getAlarmTime()>0){
                tvstayTime.setText(dto.getChoose().getAlarmTime()+"分钟");
            }
            tvcarmodel.setText(dto.getChoose().getCarTypeName());
            tvDriverAge.setText(dto.getChoose().getDrivingYears()+"年");
            tvCarAge.setText(dto.getChoose().getTravelYears()+"年");
            tvOffTime.setText(dto.getChoose().getBeiDouOffTime()+"小时认为北斗离线");
            tvSpaceTime.setText(dto.choose.getSpaceTime()+"分钟");
            if(TextUtils.isEmpty(dto.choose.getContext())){
                tvAqgz.setText("暂无告知");
                tvAqgz.setTextColor(mContext.getResources().getColor(R.color.color_black));
            }else {
                tvAqgz.setText("点击查看");
                tvAqgz.setTextColor(mContext.getResources().getColor(R.color.red));
                tvAqgz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle=new Bundle();
                        bundle.putString("data",dto.choose.getContext());
                        readyGo(ShowArtActivity.class,bundle);
                    }
                });
            }
            tvFence.setText(dto.choose.getHighEnclosureName());
            if(dto.getChoose().getBeiDouStatus()==1){
                tvbeidoustatus.setText("强制");
            }else if(dto.getChoose().getBeiDouStatus()==2){
                tvbeidoustatus.setText("提醒");
            }else {
                tvbeidoustatus.setText("无");
            }

            //提供发票
            if(dto.getChoose().getProvideInvoice()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgApplyfp);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgApplyfp);
            }

            //提供卸货
            if(dto.getChoose().getProvideUnload()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgApplyxh);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgApplyxh);
            }
            //提供发货
            if(dto.getChoose().getProvideLoad()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgApplyzh);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgApplyzh);
            }
            //验货
            if(dto.getChoose().getCheck()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgApplyyh);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgApplyyh);
            }
            //装货拍照
            if(dto.getChoose().getLoadPhotos()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgApplyzhpz);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgApplyzhpz);
            }

            //卸货拍照
            if(dto.getChoose().getUnloadPhotos()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgApplyxhpz);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgApplyxhpz);
            }
            //
            if(dto.getChoose().getDeviationAlarm()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgPyyj);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgPyyj);
            }
            if(dto.getChoose().getStopAlarm()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgstayyz);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgstayyz);
            }
            if(dto.getChoose().getBindSmartLock()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imglock);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imglock);
            }
            if(dto.getChoose().getOutFactoryPhotos()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgguobang);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgguobang);
            }
            if(dto.getChoose().getOffLineAlarm()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgoffline);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgoffline);
            }
            if(dto.getChoose().getOpenCarType()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgCarModel);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgCarModel);
            }

            if(dto.getChoose().getFenceClock()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgsignWl);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgsignWl);
            }
            if(dto.getChoose().getOpenTransport()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgTrantOrder);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgTrantOrder);
            }
            if(dto.getChoose().getOpenFactorySignIn()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgIntosign);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgIntosign);
            }
            if(dto.getChoose().getOpenArrival()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgopenArrival);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgopenArrival);
            }
            if(dto.getChoose().getAutoTransport()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgautotrant);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgautotrant);
            }
            if(dto.getChoose().getPoundAlarm()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgBangdan);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgBangdan);
            }
            if(dto.getChoose().getSjSignIn()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgArrive);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgArrive);
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
        EventBus.getDefault().post(Constants.reshYunDn);
        context.finish();
    }


}
