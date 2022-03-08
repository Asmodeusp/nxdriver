package com.saimawzc.freight.ui.my.driver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.weight.CaterpillarIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/8/8.
 * 我的司机
 */

public class MyDriverFragment extends BaseFragment {

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.pager_title)CaterpillarIndicator pagerTitle;
    @BindView(R.id.viewpage)ViewPager viewPager;
    private PassDriverFragment passDriverFragment;
    private UnPassDriverFragment unPassDriverFragment;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;


    @Override
    public int initContentView() {
        return R.layout.fragment_mydriver;
    }

    @OnClick({R.id.tvaddcar})
    public void click(View view){
        Bundle bundle;
        switch (view.getId()){
            case R.id.tvaddcar:
                bundle=new Bundle();
                bundle.putString("from","adddriver");
                readyGo(PersonCenterActivity.class,bundle);
                break;
        }

    }

    @Override
    public void initView() {
        context.setToolbar(toolbar,"我的司机");
        passDriverFragment=new PassDriverFragment();
        unPassDriverFragment=new UnPassDriverFragment();
        list = new ArrayList<Fragment>();
        list.add(passDriverFragment);
        list.add(unPassDriverFragment);
        List<CaterpillarIndicator.TitleInfo> titleInfos = new ArrayList<>();
        titleInfos.add(new CaterpillarIndicator.TitleInfo("已通过"));
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
