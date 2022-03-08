package com.saimawzc.freight.ui.order.waybill;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.face.FaceQueryDto;
import com.saimawzc.freight.dto.order.CarDriverDto;
import com.saimawzc.freight.dto.order.CarInfolDto;
import com.saimawzc.freight.dto.order.bidd.JoinBiddDto;
import com.saimawzc.freight.presenter.order.bill.JoinBiddPresenter;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.ui.order.face.FaceLivenessExpActivity;
import com.saimawzc.freight.view.order.JoinBiddView;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/***
 * 参与竞价
 * **/

public class JoinBiddFragment extends BaseFragment implements JoinBiddView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private JoinBiddPresenter presenter;
    @BindView(R.id.tvendtime) TextView  tvEndTime;
    @BindView(R.id.edRobNum) EditText edRobNum;
    @BindView(R.id.edprice)EditText edPrice;
    @BindView(R.id.tvbidddelation)TextView tvbidddelation;
    @BindView(R.id.rlrob) RelativeLayout rlRob;
    private String id;
    private String endTime;
    private String type;
    @BindView(R.id.util)TextView tvutil;
    @BindView(R.id.rlfloor)RelativeLayout rlFloor;
    @BindView(R.id.tv_loor_price)TextView tvFloorPrice;
    @BindView(R.id.rlHightNum)RelativeLayout rlHightNum;
    @BindView(R.id.tvHightNum)TextView editHight;
    @BindView(R.id.llsendByCar) LinearLayout llCar;
    @BindView(R.id.checkBeidou) CheckBox checkBeidou;
    @BindView(R.id.checkmoresend) CheckBox checkmoresend;
    @BindView(R.id.tvCarType)TextView tvCarType;
    @BindView(R.id.rlBiddRank)LinearLayout llBindRank;
    @BindView(R.id.tvCarNum)EditText edCarNum;
    @BindView(R.id.tvCarNo)TextView tvCarNo;
    @BindView(R.id.tvDriverName)TextView tvDriverName;
    @BindView(R.id.rlchooseDriver)RelativeLayout rlChooseDriver;
    private String carTypeId;
    private  CarInfolDto.carInfoData carDto;
    private CarDriverDto driverDto;
    @BindView(R.id.llcheck)LinearLayout llCheck;

    private String taxeId;//税务Id;
    private FaceQueryDto faceQueryDto;

    @Override
    public int initContentView() {
        return R.layout.fragment_joinbidd;
    }
    @Override
    public void initView() {
        userInfoDto=Hawk.get(PreferenceKey.USER_INFO);
        mContext=getActivity();
        context.setToolbar(toolbar,"参与竞价");
        id=getArguments().getString("id");
        endTime=  getArguments().getString("endTime");
        try{
            type=getArguments().getString("type");
        }catch (Exception e){
        }
        if(!type.equals("1")){
            rlRob.setVisibility(View.GONE);
        }else {
            if(TextUtils.isEmpty(type)){
                tvutil.setText("元/车");
            }else {
                if(type.equals("1")){
                    tvutil.setText("元/吨");
                }else {
                    tvutil.setText("元/车");
                }
            }
        }
        presenter=new JoinBiddPresenter(this,mContext);
        if(Hawk.get(PreferenceKey.LOGIN_TYPE,0)==2){
            presenter.getBiddDelation(id);
        }else if(Hawk.get(PreferenceKey.LOGIN_TYPE,0)==3){
            presenter.getsjBiddDelation(id);
        }
        tvEndTime.setText(endTime);
    }
    @OnClick({R.id.tvOrder,R.id.rlBiddRank,R.id.rlchooseCar,R.id.rlchooseDriver})
    public void click(View view){
        Bundle  bundle=null;
        switch (view.getId()){
            case R.id.tvOrder:
                if(type.equals("1")){
                    if(context.isEmptyStr(edRobNum)){
                        context.showMessage("请输入抢单量");
                        return;
                    }
                }
                if(context.isEmptyStr(edPrice)){
                    context.showMessage("请输入价格");
                    return;
                }
                if(!TextUtils.isEmpty(roleType)){
                    if(roleType.equals("5")){
                        if(Hawk.get(PreferenceKey.LOGIN_TYPE,0)==2){
                            if(carDto==null){
                                context.showMessage("请选择车辆");
                                return;
                            }
                            if(driverDto==null){
                                context.showMessage("请选择司机");
                                return;
                            }
                        }else if(Hawk.get(PreferenceKey.LOGIN_TYPE,0)==3){
                            if(carDto==null){
                                context.showMessage("请选择车辆");
                                return;
                            }
                        }
                        ///////补录税务系统
//                        if(userInfoDto!=null){
//                            if(TextUtils.isEmpty(taxeId)) {
//                                bundle = new Bundle();
//                                bundle.putString("from","swbulu");
//                                if(carDto != null) {
//                                    bundle.putString("carId", carDto.getId());
//                                }else {
//                                    bundle.putString("carId", "");
//                                }
//                                bundle.putSerializable("data",faceQueryDto);
//                                readyGoForResult(OrderMainActivity.class, 1006, bundle);
//                            }else {
//                                if(taxeId.equals("0")){
//                                    bundle=new Bundle();
//                                    bundle.putString("from","swbulu");
//                                    bundle.putString("carId", "");
//                                    bundle.putSerializable("data",faceQueryDto);
//                                    readyGoForResult(OrderMainActivity.class,1006,bundle);
//                                }else {
//                                    bundle=new Bundle();
//                                    bundle.putString("id",id);
//                                    readyGoForResult(FaceLivenessExpActivity.class,1005,bundle);
//                                }
//                            }
//
//                            return;
//                        }
                    }
                }

                if(Hawk.get(PreferenceKey.LOGIN_TYPE,0)==2){
                    if(roleType.equals("5")){
                        presenter.addBidd(id,edPrice.getText().toString(),edRobNum.getText().toString(),type,
                                edCarNum.getText().toString(),driverDto.getId(),carDto.getId());
                    }else {
                        presenter.addBidd(id,edPrice.getText().toString(),edRobNum.getText().toString(),type,
                                "","","");
                    }

                }else if(Hawk.get(PreferenceKey.LOGIN_TYPE,0)==3){
                    if(roleType.equals("5")){
                        presenter.addsjBidd(id,edPrice.getText().toString(),edRobNum.getText().toString(),type
                                ,edCarNum.getText().toString(),carDto.getId());
                    }else {
                        presenter.addsjBidd(id,edPrice.getText().toString(),edRobNum.getText().toString(),type
                                ,"","");
                    }

                }
                break;
            case R.id.rlBiddRank:
                bundle =new Bundle();
                bundle.putString("id",id);
                bundle.putString("from","rankbidd");
                readyGo(OrderMainActivity.class,bundle);
                break;
            case R.id.rlchooseCar://选择车辆
                bundle=new Bundle();
                bundle.putString("cartypeId",carTypeId);
                bundle.putString("from","choosecar");
                readyGoForResult(OrderMainActivity.class,123,bundle);
                break;
            case R.id.rlchooseDriver://选择司机
                bundle=new Bundle();
                bundle.putString("from","biddchoosedriver");
                readyGoForResult(OrderMainActivity.class,124,bundle);
                break;
        }
    }
    @Override
    public void initData() {
    }
    private String roleType="";
    @Override
    public void getBiddDelation(JoinBiddDto dto) {
        if(dto!=null){
            tvbidddelation.setText("可竞价"+dto.getOverBiddNum()+"/"+dto.getBiddNum()+","
                    +"您最后的报价"+dto.getLastBiddPrice()+","+"当前排名"+dto.getRank()+"，竞价重量："+dto.getPointWeight()
                    +"，竞价降价幅度："+dto.getExtent()+"元");

            if(dto.getFloorPrice()<=0){
                rlFloor.setVisibility(View.GONE);
            }else {
                rlFloor.setVisibility(View.VISIBLE);
                tvFloorPrice.setText(dto.getFloorPrice()+"元");
            }
            if(dto.getHighWeight()<=0){
                rlHightNum.setVisibility(View.GONE);
            }else {
                rlHightNum.setVisibility(View.VISIBLE);
                editHight.setText(dto.getHighWeight()+"");
            }
            if(!TextUtils.isEmpty(dto.getShowRanking())){
                if(dto.getShowRanking().equals("1")){
                    llBindRank.setVisibility(View.VISIBLE);
                }
            }
            roleType=dto.getRoleType();
            if(!TextUtils.isEmpty(dto.getRoleType())){
                if(dto.getRoleType().equals("5")){
                    llCar.setVisibility(View.VISIBLE);
                    tvCarType.setText(dto.getCarTypeName());
                    carTypeId=dto.getCarTypeId();
                    if(Hawk.get(PreferenceKey.LOGIN_TYPE,2)==3){
                        rlChooseDriver.setVisibility(View.GONE);
                    }else {
                        rlChooseDriver.setVisibility(View.VISIBLE);
                    }
                    int isSHowCheck=0;
                    if(!TextUtils.isEmpty(dto.getNeedBeiDou())){
                        if(dto.getNeedBeiDou().equals("1")){
                            checkBeidou.setChecked(true);
                        }else {
                            checkBeidou.setChecked(false);
                            checkBeidou.setVisibility(View.GONE);
                            isSHowCheck++;
                        }
                    }
                    if(!TextUtils.isEmpty(dto.getMoreDispatch())){
                        if(dto.getMoreDispatch().equals("1")){
                            checkmoresend.setChecked(true);
                        }else {
                            checkmoresend.setChecked(false);
                            checkmoresend.setVisibility(View.GONE);
                            isSHowCheck++;
                            edCarNum.setText("1");
                            edCarNum.setEnabled(false);
                        }
                    }
                    if(isSHowCheck>=2){
                        llCheck.setVisibility(View.GONE);
                    }
                }
            }
        }

    }

    @Override
    public void getFaceDto(FaceQueryDto dto) {
        if(dto!=null){
            if(dto.getData()!=null){
                taxeId=dto.getData().getId();
                faceQueryDto=dto;
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
        context.finish();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123&& resultCode == RESULT_OK){
            carDto = (CarInfolDto.carInfoData) data.getSerializableExtra("data");
            if(carDto!=null){
                tvCarNo.setText(carDto.getCarNo());
                faceQueryDto=null;
                if(userInfoDto!=null){
                    presenter.getFaceQueryDto(carDto.getId(),userInfoDto);
                }
            }
        }else if(requestCode==124&& resultCode == RESULT_OK){
            driverDto = (CarDriverDto) data.getSerializableExtra("data");
            if(driverDto!=null){
                tvDriverName.setText(driverDto.getSjName());
            }
        }else if(requestCode==1005&& resultCode == RESULT_OK){
            double source=data.getDoubleExtra("score",0);
            if(source<70){
                context.showMessage("人脸校验未通过");
            }else {
                if(Hawk.get(PreferenceKey.LOGIN_TYPE,0)==2){
                    if(roleType.equals("5")){
                        presenter.addBidd(id,edPrice.getText().toString(),edRobNum.getText().toString(),type,
                                edCarNum.getText().toString(),driverDto.getId(),carDto.getId());
                    }else {
                        presenter.addBidd(id,edPrice.getText().toString(),edRobNum.getText().toString(),type,
                                "","","");
                    }

                }else if(Hawk.get(PreferenceKey.LOGIN_TYPE,0)==3) {
                    if (roleType.equals("5")) {
                        presenter.addsjBidd(id, edPrice.getText().toString(), edRobNum.getText().toString(), type
                                , edCarNum.getText().toString(), carDto.getId());
                    } else {
                        presenter.addsjBidd(id, edPrice.getText().toString(), edRobNum.getText().toString(), type
                                , "", "");
                    }
                }
            }
        }else if(requestCode==1006&& resultCode == RESULT_OK){
           Bundle bundle=new Bundle();
            bundle.putString("id",id);
            readyGoForResult(FaceLivenessExpActivity.class,1005,bundle);

        }

    }
}
