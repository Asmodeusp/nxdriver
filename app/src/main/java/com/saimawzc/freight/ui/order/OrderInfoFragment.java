package com.saimawzc.freight.ui.order;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.ui.order.Information.InfoManageFragment;
import com.saimawzc.freight.ui.order.Information.InfoPlanFragment;
import com.saimawzc.freight.ui.order.Information.InfoWayBillFragment;
import com.saimawzc.freight.weight.CaterpillarIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
/*信息宝*/

public class OrderInfoFragment extends BaseFragment {

    @BindView(R.id.viewpage) ViewPager viewPager;
    @BindView(R.id.pager_title)
    CaterpillarIndicator pagerTitle;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;
    private InfoPlanFragment infoPlanFragment;
    private InfoWayBillFragment wayBillFragment;
    private InfoManageFragment manageFragment;

    @Override
    public int initContentView() {
        return R.layout.fragment_orderinfo;
    }

    @Override
    public void initView() {
        infoPlanFragment=new InfoPlanFragment();
        wayBillFragment=new InfoWayBillFragment();
        manageFragment=new InfoManageFragment();

        list = new ArrayList<Fragment>();
        list.add(infoPlanFragment);
        list.add(wayBillFragment);
        list.add(manageFragment);
        List<CaterpillarIndicator.TitleInfo> titleInfos = new ArrayList<>();
        titleInfos.add(new CaterpillarIndicator.TitleInfo("计划单"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("预运单"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("调度单"));
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
