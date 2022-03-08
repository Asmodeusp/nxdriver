package com.saimawzc.freight.ui.my.carqueue;

import android.os.Bundle;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseFragment;
public class MyCarQueneActivity extends BaseActivity {
    @Override
    protected int getViewId() {
        return R.layout.activity_personcenter;
    }

    @Override
    protected void init() {
       BaseFragment mCurrentFragment=new MyCarQueueFragment();
        if(mCurrentFragment!=null){
            mCurrentFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, mCurrentFragment)
                    .commit();
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }
}
