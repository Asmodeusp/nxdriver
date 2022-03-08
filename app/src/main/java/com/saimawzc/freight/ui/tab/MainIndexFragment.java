package com.saimawzc.freight.ui.tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.EditText;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseImmersionFragment;
import com.saimawzc.freight.ui.order.ShareOrderFragment;
import com.saimawzc.freight.ui.order.WaitOrderFragment;
import com.saimawzc.freight.ui.order.rob.RobOrderFragment;
import com.saimawzc.freight.weight.CaterpillarIndicator;
import com.gyf.immersionbar.ImmersionBar;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/7/31.
 * 首页
 */
public class MainIndexFragment extends BaseImmersionFragment {
    @BindView(R.id.viewpage) ViewPager viewPager;
    @BindView(R.id.pager_title) CaterpillarIndicator pagerTitle;

    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;
    private WaitOrderFragment  waitOrderFragment;
    private RobOrderFragment robOrderFragment;
    private ShareOrderFragment shareOrderFragment;

    @Override
    public int initContentView() {
        return R.layout.fragment_mainindex;
    }

    @Override
    public void initView() {
//        String s = Hawk.get(PreferenceKey.ID, "");
//        ed_Text.setText(s);
        robOrderFragment=new RobOrderFragment();
        waitOrderFragment=new WaitOrderFragment();
        shareOrderFragment=new ShareOrderFragment();
        list = new ArrayList<Fragment>();
        list.add(waitOrderFragment);
        list.add(robOrderFragment);
        list.add(shareOrderFragment);
        List<CaterpillarIndicator.TitleInfo> titleInfos = new ArrayList<>();
        titleInfos.add(new CaterpillarIndicator.TitleInfo("待确认"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("抢单"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("推送"));
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
