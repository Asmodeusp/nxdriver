package com.saimawzc.freight.ui.wallet;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.wallet.BankListAdpater;
import com.saimawzc.freight.base.BaseImmersionFragment;
import com.saimawzc.freight.dto.wallet.SonAccountDto;
import com.saimawzc.freight.presenter.wallet.SonAccountPresenter;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.view.wallet.SignedWalletView;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WalletCompeteSignFragment extends BaseImmersionFragment
        implements SignedWalletView {

    @BindView(R.id.cv) RecyclerView rv;
    private BankListAdpater adpater;
    private List<SonAccountDto.bankList>mDatas=new ArrayList<>();
    private SonAccountPresenter presenter;
    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.tvcompamy)TextView tvcompamy;
    @BindView(R.id.tvallmoney)TextView tvAllMoney;
    @BindView(R.id.tvusermoney)TextView tvUseMoney;
    public SonAccountDto sonAccountDto;

    @Override
    public int initContentView() {
        return R.layout.fragment_compete_sign;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        adpater=new BankListAdpater(mDatas,mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adpater);
        presenter=new SonAccountPresenter(this,mContext);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(presenter!=null){
            presenter.getSonAccount();
        }
    }
    NormalDialog dialog;
    @Override
    public void initData() {
        adpater.setOnTabClickListener(new BaseAdapter.OnTabClickListener() {
            public void onItemClick(String arg7, final int arg8) {
                if(WalletCompeteSignFragment.this.mDatas.size() <= arg8) {
                    return;
                }
                if(WalletCompeteSignFragment.this.mDatas.get(arg8).getDefaultFlag() != 1) {
                    WalletCompeteSignFragment.this.dialog = new NormalDialog(
                            WalletCompeteSignFragment.this.mContext).isTitleShow(false).content("设置该卡片为默认卡吗?").
                            showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit()).btnNum(2).btnText(new String[]{"取消", "确定"});
                    WalletCompeteSignFragment.this.dialog.setOnBtnClickL(new OnBtnClickL[]{new OnBtnClickL() {
                        public void onBtnClick() {
                            dialog.dismiss();
                        }
                    }, new OnBtnClickL() {
                        public void onBtnClick() {
                           dialog.dismiss();
                           presenter.setDefaultcard(mDatas.get(arg8).getCardNo());
                        }
                    }});
                    WalletCompeteSignFragment.this.dialog.show();
                }
            }
        });
    }
    @OnClick({R.id.rldelation,R.id.imgback,R.id.tvwithdraw,R.id.addcar})
    public void click(View view){
        Bundle bundle;
        switch (view.getId()){
            case R.id.rldelation:
                if(sonAccountDto==null){
                    return;
                }
                bundle=new Bundle();
                bundle.putString("from","walletdelation");
                bundle.putString("fundAcc",sonAccountDto.getFundAcc());
                readyGo(PersonCenterActivity.class,bundle);
                break;
            case R.id.imgback:
                context.finish();
                break;
            case R.id.tvwithdraw://提现
                if(sonAccountDto!=null){
                    if(sonAccountDto.getUseBala()<=0){
                        context.showMessage("您当前没有可提现的可用余额");
                        return;
                    }
                    if(sonAccountDto.getBankList()==null||sonAccountDto.getBankList().size()<=0){
                        context.showMessage("您当前没有可用银行卡");
                        return;
                    }
                    bundle=new Bundle();
                    bundle.putSerializable("data",sonAccountDto);
                    bundle.putString("from","withdraw");
                    readyGo(PersonCenterActivity.class,bundle);
                }

                break;
            case R.id.addcar:
                bundle = new Bundle();
                bundle.putString("from", "addbankcard");
                this.readyGo(PersonCenterActivity.class, bundle);
                break;

        }

    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true).
                statusBarColor(R.color.blue).
                navigationBarColor(R.color.bg).
                init();
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
        if(presenter != null) {
            presenter.getSonAccount();
        }
    }

    @Override
    public void getSonAccoucnt(SonAccountDto dto) {
        if(dto!=null){
            sonAccountDto=dto;
            tvName.setText(dto.getFundAccName());
            tvcompamy.setText(dto.getFundAcc());
            tvAllMoney.setText(dto.getBalance()+"");
            tvUseMoney.setText(dto.getUseBala()+"");
            mDatas.clear();
            adpater.addMoreData(dto.getBankList());
        }

    }
}
