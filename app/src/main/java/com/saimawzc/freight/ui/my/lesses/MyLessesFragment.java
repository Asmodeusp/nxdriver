package com.saimawzc.freight.ui.my.lesses;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.weight.CaterpillarIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2020/8/8.
 * 我的承租人
 */

public class MyLessesFragment extends BaseFragment{

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.pager_title)CaterpillarIndicator pagerTitle;
    private PassLessessFragment passLessessFragment;
    private UnPassLessessFragment unPassLessessFragment;
    @BindView(R.id.viewpage)ViewPager viewPager;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;

    @Override
    public int initContentView() {
        return R.layout.fragment_mylesses;
    }

    @Override
    public void initView() {
        context.setToolbar(toolbar,"我的承租人");
        passLessessFragment=new PassLessessFragment();
        unPassLessessFragment=new UnPassLessessFragment();
        list = new ArrayList<Fragment>();
        list.add(passLessessFragment);
        list.add(unPassLessessFragment);
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
}
