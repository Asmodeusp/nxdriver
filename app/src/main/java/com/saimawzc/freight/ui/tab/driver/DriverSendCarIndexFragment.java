package com.saimawzc.freight.ui.tab.driver;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseImmersionFragment;
import com.saimawzc.freight.ui.sendcar.driver.SendCarCompleteFragment;
import com.saimawzc.freight.ui.sendcar.driver.SendCarWaitExecuteFragment;
import com.saimawzc.freight.weight.CaterpillarIndicator;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2020/7/31.
 * 派车
 */
public class DriverSendCarIndexFragment extends BaseImmersionFragment {

    @BindView(R.id.viewpage)
    ViewPager viewPager;
    @BindView(R.id.pager_title)
    CaterpillarIndicator pagerTitle;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;
    private SendCarWaitExecuteFragment waitExecuteFragment;
    private SendCarCompleteFragment carCompleteFragment;


    @Override
    public int initContentView() {
        return R.layout.fragment_sendcarindex;
    }
    @Override
    public void initView() {
        list = new ArrayList<Fragment>();
        waitExecuteFragment=new SendCarWaitExecuteFragment();
        carCompleteFragment=new SendCarCompleteFragment();
        list.add(waitExecuteFragment);
        list.add(carCompleteFragment);

        List<CaterpillarIndicator.TitleInfo> titleInfos = new ArrayList<>();
        titleInfos.add(new CaterpillarIndicator.TitleInfo("待执行"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("已完成"));
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
    public void initImmersionBar() {
        ImmersionBar.with(this).titleBar(pagerTitle).
                navigationBarColor(R.color.bg).
                statusBarDarkFont(true).init();
    }
}
