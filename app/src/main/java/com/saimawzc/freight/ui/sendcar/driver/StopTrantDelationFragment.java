package com.saimawzc.freight.ui.sendcar.driver;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.order.StopTrantDelationDto;
import com.saimawzc.freight.presenter.order.StopTrantDelationPresenter;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.view.order.StopTrantDelationView;

import butterknife.BindView;

/****
 * 停运详情
 * **/
public class StopTrantDelationFragment extends BaseFragment implements StopTrantDelationView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvdanhao) TextView tvdanhao;
    @BindView(R.id.tvapplyPeople) TextView tvapplyPeople;
    @BindView(R.id.tvtime) TextView tvtime;
    @BindView(R.id.tvreason) TextView tvreason;
    private StopTrantDelationPresenter presenter;
    private String id;

    @Override
    public int initContentView() {
        return R.layout.fragment_stop_trantdelation;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        id=getArguments().getString("id");
        context.setToolbar(toolbar,"停运详情");
        presenter=new StopTrantDelationPresenter(this,mContext);
        presenter.getDelation(id);

    }

    @Override
    public void initData() {

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

    @Override
    public void getDtlation(StopTrantDelationDto dto) {
        if(dto!=null){
            tvdanhao.setText(dto.getDispatchNo());
            tvapplyPeople.setText(dto.getOffRoleName());
            tvtime.setText(dto.getOffTime());
            tvreason.setText(dto.getOffOpinion());
        }

    }
}
