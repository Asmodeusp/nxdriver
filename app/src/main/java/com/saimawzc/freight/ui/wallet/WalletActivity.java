package com.saimawzc.freight.ui.wallet;

import android.os.Bundle;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseImmersionFragment;
import com.saimawzc.freight.weight.utils.GalleryUtils;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by Administrator on 2020/7/31.
 */
public class WalletActivity extends BaseActivity {

    String comeFrom = "";
    BaseImmersionFragment mCurrentFragment;

    @Override
    protected int getViewId() {
        return R.layout.activity_personcenter;
    }
    @Override
    protected void init() {

        mCurrentFragment=new WalletCompeteSignFragment();

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
        if(bundle!=null){
            comeFrom=bundle.getString("from");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GalleryFinal.OnHanlderResultCallback call = GalleryFinal.getCallback();
        call=null;
        GalleryUtils.functionConfig=null;
        ThemeConfig thtme = GalleryFinal.getGalleryTheme();
        thtme=null;
    }
}
