package com.saimawzc.freight.ui.order.waybill.manage;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.presenter.order.StopTrantPresenter;
import com.saimawzc.freight.view.BaseView;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/***
 * 关闭派车单
 * **/
public class StopTrantFragment extends BaseFragment implements BaseView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String id;
    @BindView(R.id.edsearch) EditText edSearch;
    private StopTrantPresenter presenter;

    @Override
    public int initContentView() {
        return R.layout.fragment_stoptant;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"关闭派车单");
        id=getArguments().getString("id");
        presenter=new StopTrantPresenter(this,mContext);

    }

    @OnClick(R.id.tvOrder)
    public void click(){
        if(context.isEmptyStr(edSearch)){
            context.showMessage("请输入关闭理由");
            return;
        }
        presenter.stopTrant(id,edSearch.getText().toString());

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
        Intent intent=new Intent();
        intent.putExtra("offsuccess","true");
        context.setResult(RESULT_OK, intent);
        context.finish();

    }
}
