package com.saimawzc.freight.ui.my.car;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.weight.CaterpillarIndicator;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/8/1.
 * 我的车辆
 */

public class MyCarFragment extends BaseFragment{
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.pager_title)CaterpillarIndicator pagerTitle;
    @BindView(R.id.viewpage)ViewPager viewPager;
    private PassCarFragment passCarFragment;
    private UnPassCarFragment unPassCarFragment;
    private WaitApproveFragment waitApproveFragment;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;
    private NormalDialog dialog;
    @BindView(R.id.right_btn)TextView rightBtn;


    @OnClick({R.id.tvaddcar})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvaddcar://添加车辆

                dialog = new NormalDialog(mContext).isTitleShow(true)
                        .content("搜索添加车辆需要车主同意，注册车辆需要相关资料进行审核")
                        .title("提示")
                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                        .btnNum(2).btnText("搜索", "注册");
                       // .btnTextColor(R.color.text_gray,R.color.blue)
                dialog.setOnBtnClickL(
                        new OnBtnClickL() {//搜索
                            @Override
                            public void onBtnClick() {
                                Bundle bundle=new Bundle();
                                bundle.putString("from","searchCar");
                                readyGo(PersonCenterActivity.class,bundle);
                                if(!context.isDestroy(context)){
                                    dialog.dismiss();
                                }
                            }
                        },
                        new OnBtnClickL() {//注册
                            @Override
                            public void onBtnClick() {
                                Bundle bundle=new Bundle();
                                bundle.putString("from","resistercar");
                                bundle.putString("type","resister");
                                readyGo(PersonCenterActivity.class,bundle);
                                if(!context.isDestroy(context)){
                                    dialog.dismiss();
                                }
                            }
                        });
                dialog.show();
                break;
        }
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_my_car;
    }
    @Override
    public void initView() {
        context.setToolbar(toolbar,"我的车辆");
        passCarFragment=new PassCarFragment();
        unPassCarFragment=new UnPassCarFragment();
        waitApproveFragment=new WaitApproveFragment();
        list = new ArrayList<Fragment>();
        list.add(passCarFragment);
        list.add(waitApproveFragment);
        list.add(unPassCarFragment);
        List<CaterpillarIndicator.TitleInfo> titleInfos = new ArrayList<>();
        titleInfos.add(new CaterpillarIndicator.TitleInfo("已通过"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("待审核"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("未通过"));
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

        rightBtn.setText("车辆变更");
        rightBtn.setVisibility(View.VISIBLE);

    }
    @Override
    public void initData() {

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("from","carchange");
                readyGo(PersonCenterActivity.class,bundle);

            }
        });


    }
    @Override
    public void onResume() {
        super.onResume();
        if(dialog!=null){
            if(!context.isDestroy(context)){
                dialog.dismiss();
            }
        }
    }
}
