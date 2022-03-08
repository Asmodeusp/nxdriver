package com.saimawzc.freight.ui.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseImmersionFragment;
import com.saimawzc.freight.ui.order.ManageOrderFragment;
import com.saimawzc.freight.ui.order.PlanOrderFragment;
import com.saimawzc.freight.ui.order.WayBillFragment;
import com.saimawzc.freight.weight.CaterpillarIndicator;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.gyf.immersionbar.ImmersionBar;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
/**
 * Created by Administrator on 2020/7/31.
 * 运单
 */
public class WaybillIndexFragment extends BaseImmersionFragment {

    @BindView(R.id.viewpage) ViewPager viewPager;
    @BindView(R.id.pager_title)
    CaterpillarIndicator pagerTitle;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;
    private PlanOrderFragment planOrderFragment;
    private WayBillFragment wayBillFragment;
    private ManageOrderFragment manageFragment;

    @Override
    public int initContentView() {
        return R.layout.fragment_yundan;
    }

    @Override
    public void initView() {
        planOrderFragment=new PlanOrderFragment();
        wayBillFragment=new WayBillFragment();
        manageFragment=new ManageOrderFragment();
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
