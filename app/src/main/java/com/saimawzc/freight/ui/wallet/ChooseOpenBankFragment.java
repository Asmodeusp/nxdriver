package com.saimawzc.freight.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.ocr.ui.camera.CameraActivity;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.wallet.MsBankAdpater;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.wallet.MsBankDto;
import com.saimawzc.freight.presenter.wallet.MsBankPresenter;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.view.wallet.MsBankView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.FileUtil;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import com.saimawzc.freight.weight.utils.ocr.RecognizeService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.saimawzc.freight.ui.my.identification.DriverCarrierFragment.REQUEST_CODE_PIC;

/****
 * 选择开户行
 * **/
public class ChooseOpenBankFragment extends BaseFragment implements MsBankView {
    @BindView(R.id.edsearch) ClearTextEditText edSearch;
    @BindView(R.id.llSearch) LinearLayout llSearch;
    @BindView(R.id.tvSearch) TextView tvSearch;
    @BindView(R.id.rvCar) RecyclerView rvCar;
    public LinearLayoutManager carlistlayoutManager;
    private List<MsBankDto>mDatas=new ArrayList<>();
    private MsBankAdpater adpater;
    private MsBankPresenter presenter;

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
                    context.showMessage("请输入银行名");
                    return;
                }
                mDatas.clear();
                presenter.getBinkList(edSearch.getText().toString());
                break;

        }
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_chooseopenbank;
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
        edSearch.addTextChangedListener(textWatcher);
        presenter.getBinkList(edSearch.getText().toString());

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
                Bundle bundle=new Bundle();
                bundle.putString("from","choosebigbank");
                bundle.putString("bankname",mDatas.get(position).getBankName());
                bundle.putSerializable("data",mDatas.get(position));
                readyGoForResult(PersonCenterActivity.class,2001,bundle);

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    public void getBinkList(List<MsBankDto> dtos) {
        adpater.addMoreData(dtos);
    }

    /***
     * 获取大额行号
     * **/
    @Override
    public void getBigBank(List<MsBankDto> dtos) {

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if(requestCode==2001 && resultCode == RESULT_OK){
               MsBankDto  bigBankDto= (MsBankDto) data.getSerializableExtra("data");
               MsBankDto msBankDto=(MsBankDto) data.getSerializableExtra("openbank");
                Intent intent=new Intent();
               intent.putExtra("data",bigBankDto);
               intent.putExtra("msbank",msBankDto);
                context.setResult(RESULT_OK, intent);
                context. finish();
        }
    }
}
