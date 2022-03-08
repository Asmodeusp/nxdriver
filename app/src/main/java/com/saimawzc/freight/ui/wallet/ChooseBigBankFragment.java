package com.saimawzc.freight.ui.wallet;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.wallet.MsBankAdpater;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.wallet.MsBankDto;
import com.saimawzc.freight.presenter.wallet.MsBankPresenter;
import com.saimawzc.freight.view.wallet.MsBankView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/****
 * 选择大额行号
 * **/
public class ChooseBigBankFragment extends BaseFragment
        implements MsBankView {
    @BindView(R.id.edsearch) ClearTextEditText edSearch;
    @BindView(R.id.llSearch) LinearLayout llSearch;
    @BindView(R.id.tvSearch) TextView tvSearch;
    @BindView(R.id.rvCar) RecyclerView rvCar;
    @BindView(R.id.tvbankname)TextView tvBankName;//银行名
    public LinearLayoutManager carlistlayoutManager;
    private List<MsBankDto>mDatas=new ArrayList<>();
    private MsBankAdpater adpater;
    private MsBankPresenter presenter;
    private MsBankDto msBankDto;

    @OnClick({R.id.tvCannel,R.id.llSearch})
    public void click(View view){
        if(!RepeatClickUtil.isFastClick()){
            context.showMessage("您操作太频繁，请稍后再试");
            return;
        }
        switch (view.getId()){
            case R.id.tvCannel:
                context.finish();
                break;
            case R.id.llSearch:
                if(context.isEmptyStr(edSearch)){
                    context.showMessage("请输入银行所属区域");
                    return;
                }
                String str=tvBankName.getText().toString()+edSearch.getText().toString();
                if(str.length()<6){
                    context.showMessage("搜索关键字少于6位");
                    return;
                }
                mDatas.clear();
                presenter.getBigBank(str);
                break;

        }
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_chooseobigpenbank;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        presenter=new MsBankPresenter(this,mContext);
        adpater=new MsBankAdpater(mDatas,mContext);
        carlistlayoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        adpater=new MsBankAdpater(mDatas,mContext);
        rvCar.setLayoutManager(carlistlayoutManager);
        rvCar.setAdapter(adpater);
        try{
            tvBankName.setText(getArguments().getString("bankname"));
        }catch (Exception e){
        }
        try{
            msBankDto= (MsBankDto) getArguments().getSerializable("data");
        }catch (Exception e){

        }
        edSearch.addTextChangedListener(textWatcher);
    }

    @Override
    public void initData() {
        adpater.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                adpater.setmPosition(position);
                Intent intent=new Intent();
                intent.putExtra("data",mDatas.get(position));
                if(msBankDto!=null){
                    intent.putExtra("openbank",msBankDto);
                }
                context.setResult(RESULT_OK, intent);
                context. finish();


            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }

    /***
     * 机构选择银行
     * **/
    @Override
    public void getBinkList(List<MsBankDto> dtos) {
    }

    /***
     * 大额行号
     * **/
    @Override
    public void getBigBank(List<MsBankDto> dtos) {
        llSearch.setVisibility(View.GONE);
        adpater.addMoreData(dtos);
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
    /**
     * 监听输入框
     */
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void afterTextChanged(Editable editable) {
            //控制登录按钮是否可点击状态
            if (!TextUtils.isEmpty(edSearch.getText().toString())) {
                llSearch.setVisibility(View.VISIBLE);
                tvSearch.setText(edSearch.getText().toString());
            } else {
                llSearch.setVisibility(View.GONE);
            }
        }
    };
}
