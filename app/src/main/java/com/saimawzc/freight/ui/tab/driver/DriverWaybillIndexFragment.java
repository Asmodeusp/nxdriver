package com.saimawzc.freight.ui.tab.driver;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.base.BaseImmersionFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.ui.order.ManageOrderFragment;
import com.saimawzc.freight.ui.order.PlanOrderFragment;
import com.saimawzc.freight.ui.order.WayBillFragment;
import com.saimawzc.freight.ui.order.driver.DriverManageOrderFragment;
import com.saimawzc.freight.ui.order.driver.DriverPlanOrderFragment;
import com.saimawzc.freight.ui.order.driver.DriverWayBillFragment;
import com.saimawzc.freight.weight.CaterpillarIndicator;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2020/7/31.
 * 运单
 */
public class DriverWaybillIndexFragment extends BaseImmersionFragment {

    @BindView(R.id.viewpage) ViewPager viewPager;
    @BindView(R.id.pager_title)
    CaterpillarIndicator pagerTitle;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;
    private DriverPlanOrderFragment planOrderFragment;
    private DriverWayBillFragment wayBillFragment;
    private DriverManageOrderFragment manageFragment;

    @Override
    public int initContentView() {
        return R.layout.fragment_yundan;
    }

    @Override
    public void initView() {
        planOrderFragment=new DriverPlanOrderFragment();
        wayBillFragment=new DriverWayBillFragment();
        manageFragment=new DriverManageOrderFragment();
        list = new ArrayList<Fragment>();
        list.add(planOrderFragment);
        list.add(wayBillFragment);
        list.add(manageFragment);
        List<CaterpillarIndicator.TitleInfo> titleInfos = new ArrayList<>();
        titleInfos.add(new CaterpillarIndicator.TitleInfo("大单"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("小单"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("合单"));

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
