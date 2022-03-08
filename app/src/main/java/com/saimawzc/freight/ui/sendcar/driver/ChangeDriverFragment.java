package com.saimawzc.freight.ui.sendcar.driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.my.driver.MyDriverDto;
import com.saimawzc.freight.dto.sendcar.ScSearchDriverDto;
import com.saimawzc.freight.presenter.sendcar.ChangeDriverPresenter;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.view.BaseView;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class ChangeDriverFragment extends BaseFragment implements BaseView {

    @BindView(R.id.toolbar) Toolbar toolbar;

    private String id;
    @BindView(R.id.tvdriverNo)
    TextView tvdriverNo;
    @BindView(R.id.edreason)
    EditText edreason;
    private ScSearchDriverDto myDriverDto;
    private ChangeDriverPresenter presenter;
    private  String companyId;


    @OnClick({R.id.rl_mydriver,R.id.tvOrder})
    public void click(View view){
        switch (view.getId()){
            case R.id.rl_mydriver:
                Bundle bundle=new Bundle();
                bundle.putString("from","searchdriver");
                bundle.putString("id",getArguments().getString("id"));
                //拿到companyId
                companyId = ((OrderMainActivity) getActivity()).getCompanyId();
                //传值到下一个页面
                bundle.putString("companyId", companyId);
                Log.e("company",companyId);
                readyGoForResult(OrderMainActivity.class,1000,bundle);
                break;
            case R.id.tvOrder:
                if(myDriverDto==null){
                    context.showMessage("请选择司机");
                    return;
                }
                if(context.isEmptyStr(edreason)){
                    context.showMessage("请输入更换理由");
                    return;
                }
                presenter.getData(id,myDriverDto,edreason.getText().toString());
                break;

        }


    }



    @Override
    public int initContentView() {
        return R.layout.fagment_changedriver;
    }

    @Override
    public void initView() {
        //拿到companyId
        companyId = ((OrderMainActivity) getActivity()).getCompanyId();
        mContext=getActivity();
        context.setToolbar(toolbar,"变更司机");
        id=getArguments().getString("dispatchCarId");
        presenter=new ChangeDriverPresenter(this,mContext);
    }

    @Override
    public void initData() {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000 && resultCode == RESULT_OK){
            myDriverDto= (ScSearchDriverDto) data.getSerializableExtra("data");
            if(myDriverDto!=null){
                tvdriverNo.setText(myDriverDto.getSjName());
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

}
