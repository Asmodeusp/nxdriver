package com.saimawzc.freight.ui.order.waybill;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.presenter.order.StopTrantPresenter;
import com.saimawzc.freight.presenter.order.waybill.ApplyPauseTrantPresenter;
import com.saimawzc.freight.view.BaseView;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
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
    @BindView(R.id.edsearch)
    EditText edSearch;
    private ApplyPauseTrantPresenter presenter;

    @Override
    public int initContentView() {
        return R.layout.fragment_stoptant;
    }

    @Override
    public void initView() {
        mContext = getActivity();
        context.setToolbar(toolbar, "申请停运");
        id = getArguments().getString("id");
        type = getArguments().getString("type");
        presenter = new ApplyPauseTrantPresenter(this, mContext);

    }

    @OnClick(R.id.tvOrder)
    public void click() {
        if (context.isEmptyStr(edSearch)) {
            context.showMessage("请输入停运理由理由");
            return;
        }
        final NormalDialog normalDialog = new NormalDialog(mContext).isTitleShow(true)
                .title("警告")
                .content("如果您确认该车货物无法送达目的地，可在此终止当前运单，但可能会产生违约责任，提交终止后生效。")
                .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                .btnNum(2).btnText("取消", "确认");
        normalDialog.setCanceledOnTouchOutside(false);
        normalDialog.setOnBtnClickL(
                new OnBtnClickL() {//取消
                    @Override
                    public void onBtnClick() {
                        normalDialog.dismiss();
                    }
                },
                new OnBtnClickL() {//确认
                    @Override
                    public void onBtnClick() {
                        if (Hawk.get(PreferenceKey.LOGIN_TYPE, 0) == 2) {
                            presenter.stopTrant(id, type, edSearch.getText().toString());
                        } else {
                            presenter.stopsjTrant(id, type, edSearch.getText().toString());
                        }
                    }
                });
        normalDialog.show();


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
