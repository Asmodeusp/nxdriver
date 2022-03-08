package com.saimawzc.freight.ui.my.set.suggest;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.sendcar.ShowImageAdpater;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.my.set.SuggestDto;
import com.saimawzc.freight.presenter.mine.set.SuggestDelationPresenter;
import com.saimawzc.freight.view.mine.set.MySuggestDelationView;
import com.saimawzc.freight.weight.utils.preview.PlusImageActivity;

import java.util.ArrayList;

import butterknife.BindView;

/****
 * 反馈详情
 * ***/

public class SuggestDealDelationFragment extends BaseFragment
        implements MySuggestDelationView {

    private SuggestDto suggestDto;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tvquestionContect) TextView tvQuesTion;
    @BindView(R.id.tvanstionContect)TextView tvAnstion;
    @BindView(R.id.cy_question) RecyclerView cyQuestion;
    @BindView(R.id.cy_anstion)RecyclerView rvAns;
    private ShowImageAdpater imgAdapterquestion;
    private ShowImageAdpater imgAdapterAns;
    private SuggestDelationPresenter presenter;

    @Override
    public int initContentView() {
        return R.layout.fragment_suggestdelation;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"反馈详情");

        suggestDto= (SuggestDto) getArguments().getSerializable("data");
        if(suggestDto!=null){
            presenter=new SuggestDelationPresenter(this,mContext);
            presenter.getSuggestDelation(suggestDto.getId());
            final ArrayList<String> datas=new ArrayList<>();
            tvQuesTion.setText(suggestDto.getContent());
            if(!TextUtils.isEmpty(suggestDto.getPicture())){
                if(!suggestDto.getPicture().contains(",")){
                    datas.add(suggestDto.getPicture());
                }else {
                    String[] node = suggestDto.getPicture().split(",");
                    for(int i=0;i<node.length;i++){
                        datas.add(node[i]);
                    }
                }
            }
            imgAdapterquestion=new ShowImageAdpater(datas,mContext);
            GridLayoutManager layoutManager= new GridLayoutManager(mContext, 3, RecyclerView.VERTICAL, false);
            cyQuestion.setLayoutManager(layoutManager);
            cyQuestion.setAdapter(imgAdapterquestion);

            imgAdapterquestion.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(mContext, PlusImageActivity.class);
                    intent.putStringArrayListExtra("imglist", datas);
                    intent.putExtra("currentpos", position);
                    intent.putExtra("from", "delation");
                    mContext.startActivity(intent);
                }
                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
        }

    }

    @Override
    public void initData() {

    }

    @Override
    public void getSuggestDelation(SuggestDto dto) {
        if(dto!=null){
            final ArrayList<String> datas=new ArrayList<>();
            tvAnstion.setText(dto.getReplyContent());
            if(!TextUtils.isEmpty(dto.getPicture())){
                if(!dto.getPicture().contains(",")){
                    datas.add(dto.getPicture());
                }else {
                    String[] node = dto.getPicture().split(",");
                    for(int i=0;i<node.length;i++){
                        datas.add(node[i]);
                    }
                }
            }
            imgAdapterAns=new ShowImageAdpater(datas,mContext);
            GridLayoutManager layoutManager= new GridLayoutManager(mContext, 3, RecyclerView.VERTICAL, false);
            rvAns.setLayoutManager(layoutManager);
            rvAns.setAdapter(imgAdapterAns);

            imgAdapterAns.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(mContext, PlusImageActivity.class);
                    intent.putStringArrayListExtra("imglist", datas);
                    intent.putExtra("currentpos", position);
                    intent.putExtra("from", "delation");
                    mContext.startActivity(intent);
                }
                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
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
