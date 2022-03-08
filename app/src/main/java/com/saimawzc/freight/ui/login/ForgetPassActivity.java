package com.saimawzc.freight.ui.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.presenter.login.ForgetPassresenter;
import com.saimawzc.freight.view.login.ForgetPassView;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/7/30.
 * 忘记密码
 */


public class ForgetPassActivity  extends BaseActivity
        implements ForgetPassView{

    @BindView(R.id.edit_account)EditText edPhone;
    @BindView(R.id.edCode)EditText edCode;
    @BindView(R.id.btn_Code)TextView btnCode;
    @BindView(R.id.ed_pass)EditText edPass;
    @BindView(R.id.ed_orderpass)EditText edOrderPass;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.btn_acc_clear) ImageView mAccClear;
    @BindView(R.id.btn_Login_mask) TextView mLoginMask;
    private ForgetPassresenter passresenter;

    @Override
    protected int getViewId() {
        return R.layout.activity_forgetpass;
    }

    @Override
    protected void init() {
        setToolbar(toolbar,"忘记密码");
        passresenter=new ForgetPassresenter(this,this);
    }

    @Override
    protected void initListener() {
        edPhone.addTextChangedListener(textWatcher);
        edCode.addTextChangedListener(textWatcher);
        edPass.addTextChangedListener(textWatcher);
        edOrderPass.addTextChangedListener(textWatcher);
    }
    @Override
    protected void onGetBundle(Bundle bundle) {

    }



    @OnClick({R.id.btn_acc_clear,R.id.btn_Code,R.id.btn_Login})
    public void click(View view ){
        switch (view.getId()){
            case R.id.btn_acc_clear://清除用户账号
                edPhone.setText("");
                break;
            case R.id.btn_Code:
                if(isEmptyStr(edPhone.getText().toString())){
                    showMessage("手号码不能为空");
                    return;
                }
                if(edPhone.getText().toString().length()!=11){
                    showMessage("手号码有误");
                    return;
                }
                passresenter.getCode();
                break;
            case R.id.btn_Login:
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                if(isEmptyStr(edPhone.getText().toString())){
                    showMessage("手号码不能为空");
                    return;
                }
                if(isEmptyStr(edCode.getText().toString())){
                    showMessage("验证码不能为空");
                    return;
                }
                if(isEmptyStr(edPass.getText().toString())){
                    showMessage("密码不能为空");
                    return;
                }
                if(!edPass.getText().toString().matches(Constants.PW_PATTERN)){
                    showMessage("至少八个字符，至少一个大写字母，一个小写字母，一个数字和一个特殊字符");
                    return;
                }
                if(!edPass.getText().toString().equals(edOrderPass.getText().toString())){
                    showMessage("两次输入密不一致码");
                    return;
                }


                passresenter.forgetPass();
                break;

        }
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
            if (!TextUtils.isEmpty(edOrderPass.getText().toString()) &&!TextUtils.isEmpty(edPass.getText().toString()) &&!TextUtils.isEmpty(edPhone.getText().toString()) && !TextUtils.isEmpty(edCode.getText().toString())) {
                mLoginMask.setVisibility(View.GONE);
            } else {
                mLoginMask.setVisibility(View.VISIBLE);
            }
            //控制清除按钮是否显示
            if (TextUtils.isEmpty(edPhone.getText().toString())) {
                mAccClear.setVisibility(View.INVISIBLE);
            } else {
                mAccClear.setVisibility(View.VISIBLE);
            }

        }
    };

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void dissLoading() {
        dismissLoadingDialog();

    }

    @Override
    public void Toast(String str) {
        showMessage(str);

    }

    @Override
    public void oncomplete() {
        showMessage("修改成功");
        finish();

    }

    @Override
    public String getPhone() {
        return edPhone.getText().toString();
    }

    @Override
    public String getCode() {
        return edCode.getText().toString();
    }
    @Override
    public String getPass() {
        return edPass.getText().toString();
    }
    @Override
    public void changeStatus() {
        mCountDownTimer.start();
    }
    private CountDownTimer mCountDownTimer = new CountDownTimer(60 * 1000, 1000) {//一分钟，间隔一秒
        @Override
        public void onTick(long millisUntilFinished) {
            btnCode.setEnabled(false);
            btnCode.setText(millisUntilFinished / 1000 + "s");
        }
        @Override
        public void onFinish() {
            btnCode.setText("获取验证码");
            btnCode.setEnabled(true);
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCountDownTimer!=null){
            mCountDownTimer.cancel();
        }
    }
}
