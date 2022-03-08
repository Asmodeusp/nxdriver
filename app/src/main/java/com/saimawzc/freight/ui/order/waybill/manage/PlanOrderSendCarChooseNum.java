package com.saimawzc.freight.ui.order.waybill.manage;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.widget.EditText;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.order.CarDriverDto;
import com.saimawzc.freight.dto.order.CarInfolDto;
import com.saimawzc.freight.presenter.order.CarDriverPresenter;
import com.saimawzc.freight.view.order.SendDriverView;
import com.saimawzc.freight.weight.LengthFilter;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PlanOrderSendCarChooseNum extends BaseFragment implements SendDriverView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edNum)
    EditText edNum;
    private CarDriverPresenter presenter;
    CarInfolDto.carInfoData data;
    private String type;
    private String id;
    CarDriverDto driverDto;
    @BindView(R.id.edCarNum)EditText edCarNum;
    String sendCarNum;
    @Override
    public int initContentView() {
        return R.layout.fragment_plansendcarnum;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"预提量");
        presenter=new CarDriverPresenter(this,mContext);
       edNum.setFilters(new InputFilter[] {new LengthFilter(3)});

        try {
            data= (CarInfolDto.carInfoData) getArguments().getSerializable("data");
            type=getArguments().getString("type");
            id=getArguments().getString("id");
            driverDto= (CarDriverDto) getArguments().getSerializable("driver");
            sendCarNum=getArguments().getString("sendcarnum");
            if(sendCarNum.equals("1")){
                edCarNum.setText("1");
                edCarNum.setEnabled(false);
            }

        }catch (Exception e){

        }
    }

    @OnClick(R.id.tvOrder)
    public void click(){
        if(!RepeatClickUtil.isFastClick()){
            context.showMessage("您操作太频繁，请稍后再试");
            return;
        }
        if(context.isEmptyStr(edNum)){
            context.showMessage("请输入预提量");
            return;
        }
        if(!sendCarNum.equals("1")){
            if(context.isEmptyStr(edCarNum)){
                context.showMessage("请输入车次");
                return;
            }
        }
        data.setYutiNum(edNum.getText().toString());
        if(Hawk.get(PreferenceKey.LOGIN_TYPE,2)==2){
            presenter.sendCar(data,driverDto,type,id,edCarNum.getText().toString());
        }else {
            presenter.sendsjCar(data,null,type,id,edCarNum.getText().toString());
        }


    }

    @Override
    public void initData() {

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

    @Override
    public void getDriverList(List<CarDriverDto> dtos) {

    }

    @Override
    public void stopResh() {

    }

    @Override
    public void isLastPage(boolean isLastPage) {

    }

    @Override
    public BaseActivity getContect() {
        return context;
    }
}
