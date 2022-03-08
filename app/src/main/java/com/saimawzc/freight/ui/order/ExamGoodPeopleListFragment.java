package com.saimawzc.freight.ui.order;

import android.Manifest;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.order.GoodsHzListAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.order.ExamGoodDto;
import com.saimawzc.freight.presenter.order.ExamGoodPresenter;
import com.saimawzc.freight.view.order.ExamGoodView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/***
 * 验货人列表
 * ***/
public class ExamGoodPeopleListFragment extends BaseFragment implements ExamGoodView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.cy) RecyclerView rv;
    private GoodsHzListAdapter adapter;
    private List<ExamGoodDto>mDatas=new ArrayList<>();
    private ExamGoodPresenter presenter;


    @Override
    public int initContentView() {
        return R.layout.fragment_examgoods;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        initpermissionChecker();
        context.setToolbar(toolbar,"收货人列表");
        adapter=new GoodsHzListAdapter(mDatas,mContext);
        layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter=new ExamGoodPresenter(this,mContext);
        presenter.getExamGoodList(getArguments().getString("id"));
    }

    @Override
    public void initData() {
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String[] PERMISSIONS = new String[]{
                        Manifest.permission.CALL_PHONE
                };
                if(permissionChecker.isLackPermissions(PERMISSIONS)){
                    permissionChecker.requestPermissions();
                    context.showMessage("未获取到电话权限");
                    return;
                }
                context.callPhone(mDatas.get(position).getUserAccount());
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }

    @Override
    public void getExamList(List<ExamGoodDto> dto) {
        if(dto!=null){
            adapter.addMoreData(dto);
        }
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
}
