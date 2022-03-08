package com.saimawzc.freight.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.my.PersonCenterDto;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import butterknife.BindView;

public class CommonActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    String title="";
    @Override
    protected int getViewId() {
        return R.layout.activity_common_activity;
    }
    @Override
    protected void init() {
        if(getIntent()!=null){
            try{
                title=getIntent().getStringExtra("title");
            }catch (Exception e){
            }

        }
        setToolbar(toolbar,title);
        showLoadingDialog();
        getPersonterData();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }
    private void getPersonterData(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mineApi.getPersoneCener().enqueue(new CallBack<PersonCenterDto>() {
                    @Override
                    public void success(final PersonCenterDto response) {
                        dismissLoadingDialog();
                        Hawk.put(PreferenceKey.PERSON_CENTER,response);

                    }
                    @Override
                    public void fail(String code, String message) {
                        dismissLoadingDialog();
                        context.showMessage(message);
                    }
                });
            }
        });
    }
}
