package com.saimawzc.freight.ui.sendcar.driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.presenter.sendcar.ChangeCarPresenter;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.view.BaseView;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/*****
 * 换车
 * */
public class ChangeCarFragment extends BaseFragment
        implements BaseView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvCarNo)
    TextView tvCarNo;
    @BindView(R.id.edreason)
    EditText edreason;
    String companyId = "";
    private String dispatchCarId;

    private int CHANGE_CAR = 10001;
    private SearchCarDto searchCarDto;
    private ChangeCarPresenter presenter;


    @OnClick({R.id.rl_mycar})
    public void click(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.rl_mycar:
                bundle = new Bundle();
                bundle.putString("from", "changecarsearch");
                //拿到companyId
                companyId = ((OrderMainActivity) getActivity()).getCompanyId();
                //传值到下一个页面
                bundle.putString("companyId", companyId);
                readyGoForResult(PersonCenterActivity.class, CHANGE_CAR, bundle);
                break;
        }

    }

    @Override
    public int initContentView() {
        return R.layout.fagment_changecar;
    }

    @OnClick(R.id.tvOrder)
    public void click() {
        if (context.isEmptyStr(edreason)) {
            context.showMessage("请输入换车理由");
            return;
        }
        if (searchCarDto == null) {
            context.showMessage("请选择车辆");
            return;
        }
        presenter.getData(dispatchCarId, searchCarDto, edreason.getText().toString());

    }

    @Override
    public void initView() {
        mContext = getActivity();
        context.setToolbar(toolbar, "异常换车");
        dispatchCarId = getArguments().getString("dispatchCarId");
        if (TextUtils.isEmpty(dispatchCarId)) {
            context.showMessage("未获取到派车单信息");
            return;
        }
        presenter = new ChangeCarPresenter(this, mContext);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHANGE_CAR && resultCode == RESULT_OK) {
            searchCarDto = (SearchCarDto) data.getSerializableExtra("data");
            if (searchCarDto != null) {
                tvCarNo.setText(searchCarDto.getCarNo());
            }
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
