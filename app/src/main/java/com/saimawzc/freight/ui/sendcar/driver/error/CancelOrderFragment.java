package com.saimawzc.freight.ui.sendcar.driver.error;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.presenter.order.CancelOrderPresenter;
import com.saimawzc.freight.view.order.error.CancelOrderView;
import butterknife.BindView;
import butterknife.OnClick;

/***撤单****/
public class CancelOrderFragment extends BaseFragment implements CancelOrderView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.edreason) EditText edReason;
    private String id;
    private CancelOrderPresenter presenter;

    @Override
    public int initContentView() {
        return R.layout.fragment_cancel_order;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"申请撤单");
        id=getArguments().getString("dispatchCarId");
        if(!TextUtils.isEmpty(id)){
            presenter=new CancelOrderPresenter(this,mContext);
        }
    }
    @Override
    public void initData() {
    }
    @OnClick({R.id.tvOrder})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvOrder:
                if(context.isEmptyStr(edReason)){
                    context.showMessage("请输入撤销原因");
                    return;
                }
                presenter.applySubmit(id,edReason.getText().toString());
                break;
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
        context.finish();
    }
}
