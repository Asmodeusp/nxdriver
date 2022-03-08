package com.saimawzc.freight.ui.my.carrier;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.weight.CaterpillarIndicator;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/8/8.
 * 我的承运商
 */

public class MyCarrierFragment extends BaseFragment {

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.pager_title)CaterpillarIndicator pagerTitle;
    @BindView(R.id.viewpage)ViewPager viewPager;
    private PassCarrierFragment passCarrierFragment;
    private UnPassCarrierFragment unPassCarrierFragment;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;
    @BindView(R.id.tvaddcar)RelativeLayout rladdcar;


    @Override
    public int initContentView() {
        return R.layout.fragment_mydriver;
    }

    @OnClick({R.id.tvaddcar})
    public void click(View view){
        Bundle bundle;
        switch (view.getId()){
            case R.id.tvaddcar:
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                bundle=new Bundle();
                bundle.putString("from","adddriver");
                readyGo(PersonCenterActivity.class,bundle);
                break;
        }

    }

    @Override
    public void initView() {
        context.setToolbar(toolbar,"我的承运商");
        rladdcar.setVisibility(View.GONE);
        passCarrierFragment=new PassCarrierFragment();
        unPassCarrierFragment=new UnPassCarrierFragment();
        list = new ArrayList<Fragment>();
        list.add(passCarrierFragment);
        list.add(unPassCarrierFragment);
        List<CaterpillarIndicator.TitleInfo> titleInfos = new ArrayList<>();
        titleInfos.add(new CaterpillarIndicator.TitleInfo("已添加"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("待确认"));
        pagerTitle.init(0, titleInfos, viewPager);

        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }
            @Override
            public int getCount() {
                return list.size();
            }
        };
        viewPager.setAdapter(mAdapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
