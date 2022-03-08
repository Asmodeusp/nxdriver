package com.saimawzc.freight.ui.my.person;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.presenter.mine.person.ChangeRolePresenter;
import com.saimawzc.freight.ui.MainActivity;
import com.saimawzc.freight.ui.DriverMainActivity;
import com.saimawzc.freight.view.mine.person.ChangeRoleView;
import com.saimawzc.freight.weight.CircleImageView;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/8/13.
 * 切换角色
 */

public class ChangeRoleActivity extends BaseActivity implements ChangeRoleView{
    private int currentRole;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.btnChangRole)TextView btnChangRole;
    @BindView(R.id.btnChangAPP)TextView btnChangAPP;
    @BindView(R.id.imgchange)
    CircleImageView imgChange;
    private ChangeRolePresenter presenter;

    @OnClick({R.id.llchangeRole,R.id.llchangeApp})
    public void click(View view){
        switch (view.getId()){
            case R.id.llchangeRole:
                if(TextUtils.isEmpty(currentRole+"")){
                  showMessage("为获取到当前角色信息");
                    return;
                }
                if(currentRole==3){
                    presenter.changeRole(2);
                }else if(currentRole==2){
                    presenter.changeRole(3);
                }
                break;
            case R.id.llchangeApp:
                if(context.checkPackInfo("com.saimawzc.shipper")){
                    ComponentName componentName = new ComponentName
                            ("com.saimawzc.shipper", "com.saimawzc.shipper.ui.login.SplashActivity");//这里是 包名  以及 页面类的全称
                    Intent intent = new Intent();
                    intent.setComponent(componentName);
                    intent.putExtra("type", "110");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    context.showMessage("尚未安装该APP，请前往安装");
                }
                break;
        }
    }
    @Override
    protected int getViewId() {
        return R.layout.fragment_changerole;
    }

    @Override
    protected void init() {
        context.setToolbar(toolbar,"切换角色");
        presenter=new ChangeRolePresenter(this,this);
        if(currentRole==3){//司机
            btnChangRole.setText("承运商");
            imgChange.setBackgroundResource(R.drawable.ico_change_cys);
        }else if(currentRole==2){//承运商
            btnChangRole.setText("司机");
            imgChange.setBackgroundResource(R.drawable.ico_changesj);
        }

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onGetBundle(Bundle bundle) {
        try{
            currentRole=bundle.getInt("currentrole");
            Log.e("msg",currentRole+"当前角色");
        }catch (Exception e){
        }
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void dissLoading() {
      dismissLoadingDialog();
    }

    @Override
    public void Toast(String str) {
         showMessage(str);
    }

    @Override
    public void oncomplete() {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void oncomplete(int role) {
        context.showLoadingDialog();
        Hawk.put(PreferenceKey.isChange_or_login,"true");
        Hawk.put(PreferenceKey.changeAPPtime,System.currentTimeMillis());
        if(role==2){//承运商
            Hawk.put(PreferenceKey.DRIVER_IS_INDENFICATION,"");
            readyGo(MainActivity.class);
        }else if(role==3) {//司机
            Hawk.put(PreferenceKey.CYS_IS_INDENFICATION,"");
            readyGo(DriverMainActivity.class);
        }
    }
}
