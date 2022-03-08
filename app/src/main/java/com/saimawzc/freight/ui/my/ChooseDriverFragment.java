package com.saimawzc.freight.ui.my;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/8/1.
 * 选择司机认证
 */
public class ChooseDriverFragment extends BaseFragment {
    @BindView(R.id.toolbar)Toolbar toolbar;
    private String type;


    @Override
    public int initContentView() {
        return R.layout.fragment_choosedrivercarrier;
    }

    @Override
    public void initView() {
        context.setToolbar(toolbar,"选择认证类型");
        type=getArguments().getString("type");

    }

    @OnClick({R.id.rl_siji,R.id.ri_chuanyuan})
    public void click(View view){
        Bundle bundle=null;

        switch (view.getId()){
            case R.id.rl_siji://司机认证
                bundle=new Bundle();
                bundle.putString("from","driveridentification");
                bundle.putString("carrivetype","1");//1司机
                readyGo(PersonCenterActivity.class,bundle);
                break;
            case R.id.ri_chuanyuan://船员认证
                bundle=new Bundle();
                bundle.putString("from","driveridentification");
                bundle.putString("carrivetype","2");//2船员
                readyGo(PersonCenterActivity.class,bundle);
                break;
        }
    }



    @Override
    public void initData() {

    }
}
