package com.saimawzc.freight.ui.order.waybill;

import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.presenter.order.StopTrantPresenter;
import com.saimawzc.freight.presenter.order.waybill.ApplyPauseTrantPresenter;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import butterknife.BindView;
import butterknife.OnClick;

/***
 * 申请停运
 * **/
public class ApplyPauseTrantFragment extends BaseFragment implements BaseView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String id;
    private String type;
    @BindView(R.id.edsearch) EditText edSearch;
    private ApplyPauseTrantPresenter presenter;

    @Override
    public int initContentView() {
        return R.layout.fragment_stoptant;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"申请停运");
        id=getArguments().getString("id");
        type=getArguments().getString("type");
        presenter=new ApplyPauseTrantPresenter(this,mContext);

    }

    @OnClick(R.id.tvOrder)
    public void click(){
        if(context.isEmptyStr(edSearch)){
            context.showMessage("请输入停运理由理由");
            return;
        }
        if(Hawk.get(PreferenceKey.LOGIN_TYPE,0)==2){
            presenter.stopTrant(id,type,edSearch.getText().toString());
        }else {
            presenter.stopsjTrant(id,type,edSearch.getText().toString());
        }


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
        context.finish();

    }
}
