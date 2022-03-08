package com.saimawzc.freight.ui.my.identification;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/8/1.
 * 选择承运商
 */
public class ChooseCarrierFragment extends BaseFragment {
    @BindView(R.id.toolbar)Toolbar toolbar;
    private String type;

    @Override
    public int initContentView() {
        return R.layout.fragment_choosecarrier;
    }

    @Override
    public void initView() {
        context.setToolbar(toolbar,"选择承运商类型");
        type=getArguments().getString("type");

    }

    @OnClick({R.id.rl_person,R.id.nomal_Person,R.id.small_company})
    public void click(View view){
        Bundle bundle=null;

        switch (view.getId()){
            case R.id.rl_person://个人承运商
                bundle=new Bundle();
                bundle.putString("from","personalcarrier");
                bundle.putString("type",type);
                readyGo(PersonCenterActivity.class,bundle);
                break;
            case R.id.nomal_Person://一般纳税人
                //readyGo(UserIdentificationActivity.class);
                bundle=new Bundle();
                bundle.putString("from","nomaltaxicarrier");
                bundle.putString("type",type);
                readyGo(PersonCenterActivity.class,bundle);
                break;

            case R.id.small_company://小规模企业
                bundle=new Bundle();
                bundle.putString("from","samllcarrive");
                bundle.putString("type",type);
                readyGo(PersonCenterActivity.class,bundle);
                break;
        }
    }

    @Override
    public void initData() {

    }
}
