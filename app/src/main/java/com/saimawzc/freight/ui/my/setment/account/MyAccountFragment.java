package com.saimawzc.freight.ui.my.setment.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.SearchValueDto;
import com.saimawzc.freight.dto.account.MySetmentPageQueryDto;
import com.saimawzc.freight.ui.my.setment.account.acountmanage.AlreadyOrderSetmentFragment;
import com.saimawzc.freight.ui.my.setment.account.acountmanage.WaitOrderSetmentFragment;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.weight.CaterpillarIndicator;
import com.saimawzc.freight.weight.utils.api.bms.BmsApi;
import com.saimawzc.freight.weight.utils.dialog.PopupWindowUtil;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.http.Http;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
/***
 * 我的结算单
 * **/
public class MyAccountFragment extends BaseFragment {

    @BindView(R.id.viewpage) ViewPager viewPager;
    @BindView(R.id.pager_title) CaterpillarIndicator pagerTitle;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rightImgBtn)
    ImageView tvRightBtn;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;

    private AlreadyOrderSetmentFragment confirmedFragment;
    private WaitOrderSetmentFragment unConfirmFragment;
    public final int seachCode=111;
    @Override
    public int initContentView() {
        return R.layout.fragment_mycount;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"我的结算单");
        confirmedFragment=new AlreadyOrderSetmentFragment();
        unConfirmFragment=new WaitOrderSetmentFragment();
        list = new ArrayList<Fragment>();
        list.add(unConfirmFragment);
        list.add(confirmedFragment);
        getLsit();


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
        tvRightBtn.setVisibility(View.VISIBLE);
        tvRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupWindowUtil popupWindowUtil = new PopupWindowUtil.Builder()
                        .setContext(mContext) //设置 context
                        .setContentView(R.layout.dialog_mysetmemt) //设置布局文件
                        .setOutSideCancel(true) //设置点击外部取消
                        .setwidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setFouse(true)
                        .builder()
                        .showAsLaction(tvRightBtn, Gravity.RIGHT,0,0);
                popupWindowUtil.setOnClickListener(new int[]{R.id.rladd,R.id.rlquery}, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle  bundle=null;
                        switch (v.getId()){
                            case R.id.rladd://添加结算单
                                bundle=new Bundle();
                                bundle.putString("from","waitaccount");
                                readyGo(OrderMainActivity.class,bundle);
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlquery://查询结算单
                                bundle=new Bundle();
                                bundle.putString("from","queryaccount");
                                readyGoForResult(OrderMainActivity.class,seachCode,bundle);
                                popupWindowUtil.dismiss();
                                break;

                        }
                    }
                });
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==seachCode&& resultCode == RESULT_OK){
            ArrayList<SearchValueDto>  searchValueDtos = (ArrayList<SearchValueDto>) data.getSerializableExtra("list");
            Intent intent = new Intent();
            Bundle bundle=new Bundle();
            bundle.putSerializable("list",searchValueDtos);
            intent.putExtras(bundle);
            if(viewPager.getCurrentItem()==1){//已确认
                intent.putExtra("type",Constants.reshAccount_confirm);
            }else if(viewPager.getCurrentItem()==0){//未确认
                intent.putExtra("type",Constants.reshAccount_unconfirm);
            }
            EventBus.getDefault().post(intent);

        }
    }



    public BmsApi bmsApi= Http.http.createApi(BmsApi.class);
    private void getLsit(){

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",1);
            jsonObject.put("pageSize",20);
            jsonObject.put("checkStatus",2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        bmsApi.getmysetmentlist(body).enqueue(new CallBack<MySetmentPageQueryDto>() {
            @Override
            public void success(MySetmentPageQueryDto response) {
                if(response==null||response.getList()==null||response.getList().size()<=0){
                    List<CaterpillarIndicator.TitleInfo> titleInfos = new ArrayList<>();
                    titleInfos.add(new CaterpillarIndicator.TitleInfo("未确认",0));
                    titleInfos.add(new CaterpillarIndicator.TitleInfo("已确认"));
                    pagerTitle.init(0, titleInfos, viewPager);
                }else {
                    int count=response.getList().get(0).getUnsettleNum();
                    List<CaterpillarIndicator.TitleInfo> titleInfos = new ArrayList<>();
                    titleInfos.add(new CaterpillarIndicator.TitleInfo("未确认",count));
                    titleInfos.add(new CaterpillarIndicator.TitleInfo("已确认"));
                    pagerTitle.init(0, titleInfos, viewPager);
                }

            }
            @Override
            public void fail(String code, String message) {
                List<CaterpillarIndicator.TitleInfo> titleInfos = new ArrayList<>();
                titleInfos.add(new CaterpillarIndicator.TitleInfo("未确认",0));
                titleInfos.add(new CaterpillarIndicator.TitleInfo("已确认"));
                pagerTitle.init(0, titleInfos, viewPager);
            }
        });
    }
}
