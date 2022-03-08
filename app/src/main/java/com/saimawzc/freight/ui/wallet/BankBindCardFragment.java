package com.saimawzc.freight.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.wallet.BindBankDto;
import com.saimawzc.freight.dto.wallet.MsBankDto;
import com.saimawzc.freight.presenter.wallet.BKPresenter;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.view.wallet.BKView;
import com.saimawzc.freight.weight.utils.FileUtil;
import com.saimawzc.freight.weight.utils.ocr.RecognizeService;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public class BankBindCardFragment extends BaseFragment implements TextWatcher, BKView {
    private String bankName;
    private MsBankDto bigBankDto;
    @BindView(value= R.id.edardnum) EditText edCardNum;
    @BindView(value=R.id.edphone) EditText edPhone;
    private MsBankDto msBankDto;
    private BKPresenter presenter;
    @BindView(value=R.id.toolbar)
    Toolbar toolbar;
    @BindView(value=R.id.tvopenbank) TextView tvOpenBank;

    public BankBindCardFragment() {
        super();
    }

    public void Toast(String arg2) {
        this.context.showMessage(arg2);
    }

    static BKPresenter Access$000(BankBindCardFragment arg0) {
        return arg0.presenter;
    }

    public void afterTextChanged(Editable arg2) {
        if(!TextUtils.isEmpty(this.edCardNum.getText().toString()) && this.edCardNum.getText().toString().length() >= 16) {
            this.presenter.cardBin(this.edCardNum.getText().toString());
        }
    }

    public void beforeTextChanged(CharSequence arg1, int arg2, int arg3, int arg4) {
    }

    @OnClick(value={R.id.imgchoosePerson, R.id.tvopenbank, R.id.tvSubmit}) public void click(View arg3) {
        Bundle v3_1;
        String v0;
        int v3 = arg3.getId();
        if(v3 != R.id.imgchoosePerson) {
            String v1 = "银行卡号不能为空";
            if(v3 == R.id.tvSubmit) {
                v0 = "请选择开户银行";
                if(this.msBankDto == null) {
                    this.context.showMessage(v0);
                    return;
                }
                else if(this.bigBankDto == null) {
                    this.context.showMessage(v0);
                    return;
                }
                else if(this.context.isEmptyStr(this.edCardNum)) {
                    this.context.showMessage(v1);
                    return;
                }
                else if(this.context.isEmptyStr(this.tvOpenBank)) {
                    this.context.showMessage("开户行不能为空");
                    return;
                }
                else if(this.context.isEmptyStr(this.edPhone)) {
                    this.context.showMessage("开户预留手机号不能为空");
                    return;
                }
                else {
                    BindBankDto v3_2 = new BindBankDto();
                    v3_2.setBankNo(this.msBankDto.getBankNo());
                    v3_2.setBankAcc(this.edCardNum.getText().toString());
                    v3_2.setOpenBranch(this.bigBankDto.getOpenBranch());
                    v3_2.setMobile(this.edPhone.getText().toString());
                    this.presenter.bind(v3_2);
                }
            }
            else if(v3 != R.id.tvopenbank) {
            }
            else if(this.context.isEmptyStr(this.edCardNum)) {
                this.context.showMessage(v1);
                return;
            }
            else {
                v0 = "from";
                if(!TextUtils.isEmpty(this.bankName)) {
                    v3_1 = new Bundle();
                    v3_1.putString(v0, "choosebigbank");
                    v3_1.putString("bankname", this.bankName);
                    this.readyGoForResult(PersonCenterActivity.class, 2000, v3_1);
                }
                else {
                    v3_1 = new Bundle();
                    v3_1.putString(v0, "choosebank");
                    this.readyGoForResult(PersonCenterActivity.class, 2001, v3_1);
                }
            }
        }
        else {
            this.initCamera("bankCard");
        }
    }

    public void dissLoading() {
        this.context.dismissLoadingDialog();
    }

    public void getCarBin(MsBankDto arg1) {
        if(arg1 != null) {
            this.msBankDto = arg1;
            this.bankName = arg1.getBankName();
        }
    }

    public int initContentView() {
        return R.layout.fragment_bindbankcard;
    }

    public void initData() {
        this.edCardNum.addTextChangedListener(((TextWatcher)this));
    }

    public void initView() {
        this.mContext = this.getActivity();
        this.context.setToolbar(this.toolbar, "绑卡");
        this.presenter = new BKPresenter(((BKView)this), this.mContext);
    }

    public void onActivityResult(int arg4, int arg5, Intent arg6) {
        super.onActivityResult(arg4, arg5, arg6);
        int v0 = -1;
        if(arg4 != 1003 || arg5 != v0) {
            String v2 = "data";
            if(arg4 == 2000 && arg5 == v0) {
                this.bigBankDto = (MsBankDto) arg6.getSerializableExtra(v2);
                if(arg6 != null) {
                    this.tvOpenBank.setText(this.bigBankDto.getBankName());
                }
                else {
                }

                return;
            }

            if(arg4 != 2001) {
                return;
            }

            if(arg5 != v0) {
                return;
            }

            this.bigBankDto = (MsBankDto) arg6.getSerializableExtra(v2);
            this.msBankDto = (MsBankDto) arg6.getSerializableExtra("msbank");
            if(arg6 == null) {
                return;
            }

            this.tvOpenBank.setText(this.bigBankDto.getBankName());
        }
        else {
            String v4 = FileUtil.getSaveFile(this.mContext, this.tempImage).getAbsolutePath();
            if(!TextUtils.isEmpty(((CharSequence)v4))) {
                new File(v4);
                RecognizeService.recBankCard(this.mContext.getApplicationContext(), v4, new RecognizeService.ServiceListener() {
                    public void onResult(String arg4) {
                        String v0 = "bank_card_number";
                        Log.e("msg", arg4);
                        try {
                            JSONObject v4_1 = new JSONObject(new JSONObject(arg4).getString("result"));
                            BankBindCardFragment.this.edCardNum.setText(v4_1.getString(v0));
                            BankBindCardFragment.this.presenter.cardBin(v4_1.getString(v0));
                        }
                        catch(JSONException v4) {
                            v4.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public void onTextChanged(CharSequence arg1, int arg2, int arg3, int arg4) {
    }

    public void oncomplete() {
        this.context.finish();
    }

    public void showLoading() {
        this.context.showLoadingDialog();
    }
}

