package com.saimawzc.freight.ui.sendcar.driver;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.LinearLayout;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.sendcar.route.LogistoicViewPageAdatper;
import com.saimawzc.freight.base.BaseApplication;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.sendcar.LogistoicDto;
import com.saimawzc.freight.presenter.sendcar.LogistocPresenter;
import com.saimawzc.freight.view.sendcar.LogistoicView;
import com.saimawzc.freight.weight.utils.trace.TraceUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
/***
 * 物流信息
 * **/
public class LogistacsInfoFragment extends BaseFragment implements LogistoicView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private List<LogistoicDto>mDatas=new ArrayList<>();
    LogistocPresenter presenter;
    private String id;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.ll_dot) LinearLayout llDot;
    private SparseBooleanArray isLarge;
    private int dotSize = 12;
    private int dotSpace = 12;
    private Animator animatorToLarge;
    private Animator animatorToSmall;
    private LogistoicViewPageAdatper adatper;
    private String type="";
    @Override
    public int initContentView() {
        return R.layout.fragment_logistacs;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        setNeedOnBus(true);
        context.setToolbar(toolbar,"物流信息");
        id=getArguments().getString("id");
        try {
            type=getArguments().getString("type");
        }catch (Exception e){
            type="";
        }
        presenter=new LogistocPresenter(this,mContext);
        presenter.getData(id,type);
        animatorToLarge = AnimatorInflater.loadAnimator(mContext, R.animator.scale_to_large);
        animatorToSmall = AnimatorInflater.loadAnimator(mContext, R.animator.scale_to_small);

    }

    @Override
    public void initData() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(final int position) {
                // 遍历一遍子View，设置相应的背景。

                for (int i = 0; i < llDot.getChildCount(); i++) {
                    if (i == position ) {// 被选中
                        llDot.getChildAt(i).setBackgroundResource(R.drawable.dot_selected);
                        if (!isLarge.get(i)) {
                            animatorToLarge.setTarget(llDot.getChildAt(i));
                            animatorToLarge.start();
                            isLarge.put(i, true);
                        }
                    } else {// 未被选中
                        llDot.getChildAt(i).setBackgroundResource(R.drawable.dot_unselected);
                        if (isLarge.get(i)) {
                            animatorToSmall.setTarget(llDot.getChildAt(i));
                            animatorToSmall.start();
                            isLarge.put(i, false);
                        }
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void getData(List<LogistoicDto> dto) {
        if(dto!=null){
            mDatas.clear();
            mDatas.addAll(dto);
            adatper = new LogistoicViewPageAdatper(mContext, mDatas);
            viewPager.setAdapter(adatper);
            setIndicator();
        }

    }
    private void setIndicator() {
        isLarge = new SparseBooleanArray();
        // 记得创建前先清空数据，否则会受遗留数据的影响。
        llDot.removeAllViews();
        for (int i = 0; i < mDatas.size(); i++) {
            View view = new View(mContext);
            view.setBackgroundResource(R.drawable.dot_unselected);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dotSize, dotSize);
            layoutParams.leftMargin = dotSpace / 2;
            layoutParams.rightMargin = dotSpace / 2;
            layoutParams.topMargin = dotSpace / 2;
            layoutParams.bottomMargin = dotSpace / 2;
            llDot.addView(view, layoutParams);
            isLarge.put(i, false);
        }
        llDot.getChildAt(0).setBackgroundResource(R.drawable.dot_selected);
        animatorToLarge.setTarget(llDot.getChildAt(0));
        animatorToLarge.start();
        isLarge.put(0, true);
    }
    @Override
    public void showLoading() {
        context.showLoadingDialog();
    }

    @Override
    public void dissLoading() {
        context.dismissLoadingDialog();
    }

    @Override
    public void Toast(String str) {
        context.showMessage(str);
    }

    @Override
    public void oncomplete() {
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshCarLeader(String str) {
        if(!TextUtils.isEmpty(str)){
            if(str.equals(Constants.reshReQS)){
                Log.e("msg","刷新"+str);
                presenter.getData(id,type);
               // context.finish();
            }
        }
    }
}
