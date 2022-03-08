package com.saimawzc.freight.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.presenter.login.LoginPresenter;
import com.saimawzc.freight.ui.MainActivity;
import com.saimawzc.freight.ui.DriverMainActivity;
import com.saimawzc.freight.ui.WebViewActivity;
import com.saimawzc.freight.view.login.LoginView;
import com.saimawzc.freight.weight.BottomDialogUtil;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import com.saimawzc.freight.weight.utils.app.AppManager;
import com.saimawzc.freight.weight.utils.dialog.BottomDialog;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import com.wcz.fingerprintrecognitionmanager.FingerManager;
import com.wcz.fingerprintrecognitionmanager.callback.SimpleFingerCallback;
import com.wcz.fingerprintrecognitionmanager.dialog.CommonTipDialog;
import com.wcz.fingerprintrecognitionmanager.util.PhoneInfoCheck;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**Created by Administrator on 2020/7/30.
 * 登录
 */
public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.edit_account)EditText editAccount;
    @BindView(R.id.edit_password)EditText editPassword;
    @BindView(R.id.btn_Login_mask) TextView mLoginMask;
    @BindView(R.id.btn_acc_clear) ImageView mAccClear;
    @BindView(R.id.btn_passclear) ImageView mPassClear;
    @BindView(R.id.useAgreement)TextView useAgreement;
    @BindView(R.id.btnPrivacy)TextView btnPrivacy;
    private LoginPresenter presenter;
    @BindView(R.id.checkbox) CheckBox checkBox;
    @BindView(R.id.checkPrivaty)CheckBox checkPrivaty;

    @Override
    protected int getViewId() {
        return R.layout.activity_login;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void init() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        useAgreement.setText("<<用户协议>>");
        btnPrivacy.setText("<<隐私声明>>");
        presenter=new LoginPresenter(this,this);
        if(!TextUtils.isEmpty(Hawk.get(PreferenceKey.USER_ACCOUNT,""))){
            editAccount.setText(Hawk.get(PreferenceKey.USER_ACCOUNT,""));
            mLoginMask.setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(Hawk.get(PreferenceKey.PASS_WORD,""))){
            editPassword.setText(Hawk.get(PreferenceKey.PASS_WORD,""));
        }
        if(Hawk.get(PreferenceKey.ISREMEMBER_PASS,"").equals("1")){
            checkBox.setChecked(true);
        }
        if(TextUtils.isEmpty(Hawk.get(PreferenceKey.READ_PRIVACY,""))){
            showPrivacyDialog();
        }else {
            checkPrivaty.setChecked(true);
        }
        if(TextUtils.isEmpty(Hawk.get(PreferenceKey.Fingerprint_Account,""))
                ||TextUtils.isEmpty(Hawk.get(PreferenceKey.Fingerprint_Pass,""))||
                TextUtils.isEmpty(Hawk.get(PreferenceKey.Fingerprint_Type,""))){
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try{
                        if(!isDestroy(LoginActivity.this)){
                            check();
                        }
                    }catch (Exception e){

                    }
                }
            },2000);

        }

    }
    @Override
    protected void initListener() {
        editAccount.addTextChangedListener(textWatcher);
        editPassword.addTextChangedListener(textWatcher);
    }
    @Override
    protected void onGetBundle(Bundle bundle) {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.btn_acc_clear,R.id.btn_passclear,R.id.btnCode,R.id.btn_ForgetPsw,
            R.id.btn_Login,R.id.btn_Resister,R.id.useAgreement,R.id.btnPrivacy,R.id.imgfingerprint})
    public void click(View view ){
        switch (view.getId()){

            case R.id.imgfingerprint:
                if(!isDestroy(LoginActivity.this)){
                    check();
                }
                break;
            case R.id.btn_acc_clear://清除用户账号
                editAccount.setText("");
                break;
            case R.id.btn_passclear://清除密码
                editPassword.setText("");
                break;
            case R.id.btnCode://验证码登录
                readyGo(VerificationCodeLoginActivity.class);
                break;
            case R.id.btn_ForgetPsw://忘记密码
                readyGo(ForgetPassActivity.class);
                break;
            case R.id.btn_Resister://注册
                chooseIdentity(1);
                break;
            case R.id.useAgreement://用户协议
                WebViewActivity.loadUrl(context, "用户协议","https://www.wzcwlw.com/userAgreement.html");
                break;
            case R.id.btnPrivacy://隐私声明
                WebViewActivity.loadUrl(context, "隐私声明","https://www.wzcwlw.com/privacyStatement.html");
                break;
            case R.id.btn_Login:
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                if(!checkPrivaty.isChecked()){
                    showMessage("请先勾选同意后再进行登录");
                    return;
                }
                if(TextUtils.isEmpty(editAccount.getText().toString())){
                    showMessage("请输入账号");
                    return;
                }
                if(TextUtils.isEmpty(editPassword.getText().toString())){
                    showMessage("请输入密码");
                    return;
                }
                if(!editPassword.getText().toString().matches(Constants.PW_PATTERN)){
                    showMessage("至少八个字符，至少一个大写字母，一个小写字母，一个数字和一个特殊字符");
                    return;
                }
                chooseIdentity(2);
                break;
        }
    }

    /***
     * 选择身份
     * **/
    private BottomDialog bottomDialog;
    private void chooseIdentity(final int type){//1注册  2登录
        if(bottomDialog==null){
            bottomDialog=new BottomDialog(LoginActivity.this, R.style.BaseDialog,R.layout.dialog_resister);
        }
        LinearLayout lineDrierv=bottomDialog.findViewById(R.id.lineDriver);
        LinearLayout lineCarrier=bottomDialog.findViewById(R.id.lineCarrier);
        TextView tvTip=bottomDialog.findViewById(R.id.tvTime);
        if(type==1){
            tvTip.setText("请选择你要以什么样的身份进行注册");
        }else {
            tvTip.setText("请选择你要以什么样的身份进行登录");
        }
        bottomDialog.show();
        lineDrierv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type==1){
                    Bundle bundle=new Bundle();
                    bundle.putString("type","司机");
                    readyGo(RegisterActivity.class,bundle);
                }else if(type==2){
                    presenter.login(3,checkBox.isChecked());
                }

            }
        });
        lineCarrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type==1){
                    Bundle bundle=new Bundle();
                    bundle.putString("type","承运商");
                    readyGo(RegisterActivity.class,bundle);
                }else if(type==2){
                    presenter.login(2,checkBox.isChecked());
                }
            }
        });
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
            if (!TextUtils.isEmpty(editAccount.getText().toString()) && !TextUtils.isEmpty(editPassword.getText().toString())) {
                mLoginMask.setVisibility(View.GONE);
            } else {
                mLoginMask.setVisibility(View.VISIBLE);
            }
            //控制清除按钮是否显示
            if (TextUtils.isEmpty(editAccount.getText().toString())) {
                mAccClear.setVisibility(View.INVISIBLE);
            } else {
                mAccClear.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(editPassword.getText().toString())) {
                mPassClear.setVisibility(View.INVISIBLE);
            } else {
                mPassClear.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        try{
            if(bottomDialog!=null){
                bottomDialog.dismiss();
            }
        }catch (Exception e){
        }
    }

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
    }
    @Override
    public String getPhone() {
        return editAccount.getText().toString();
    }
    @Override
    public String getPass() {
        return editPassword.getText().toString();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void oncomplete(int role) {
        Hawk.put(PreferenceKey.isChange_or_login,"true");
        if(role==2){//承运商
            Log.e("msg","登录承运商");
            readyGo(MainActivity.class);
        }else if(role==3){//司机
            Log.e("msg","登录司机");
            readyGo(DriverMainActivity.class);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bottomDialog != null ) {
            bottomDialog.dismiss();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private NormalDialog dialog;
    private void showPrivacyDialog(){
        final BottomDialogUtil bottomDialogUtil = new BottomDialogUtil.Builder()
                .setContext(context) //设置 context
                .setContentView(R.layout.dialog_privacy) //设置布局文件
                .setOutSideCancel(false) //设置点击外部取消
                .builder()
                .show();
        TextView tvtips= (TextView) bottomDialogUtil.getItemView(R.id.tvtips);
        String tips="欢迎使用我找车司机，我们将通过《用户协议》和《隐私政策》帮助您了解我们为你提供的服务，以及收集处理个人信息的方式。";
        CustomClickableSpan clickableSpan = new CustomClickableSpan(LoginActivity.this,15,21,"用户协议");
        SpannableStringBuilder builder = new SpannableStringBuilder(tips);
        builder.setSpan(clickableSpan, 15, 21, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#4098FD")), 15, 21, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        CustomClickableSpan clickableSpan2 = new CustomClickableSpan(LoginActivity.this,22,28,"隐私政策");
        builder.setSpan(clickableSpan2, 22, 28, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#4098FD")), 22, 28, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tvtips.setMovementMethod(LinkMovementMethod.getInstance());
        tvtips.setText(builder);

        TextView tvcancel=(TextView) bottomDialogUtil.getItemView(R.id.tvcancel);
        TextView tvconfire=(TextView) bottomDialogUtil.getItemView(R.id.tvconfire);

        tvcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new NormalDialog(mContext).isTitleShow(false)
                        .content("需同意《个人信息保护指引》我们才能够继续为您提供服务")
                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                        .btnNum(2).btnText("放弃使用", "同意并继续");
                dialog.setOnBtnClickL(
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if(!context.isDestroy(context)){
                                    dialog.dismiss();
                                }
                                AppManager.get().finishAllActivity();
                            }
                        },
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if(!context.isDestroy(context)){
                                    dialog.dismiss();
                                }
                                Hawk.put(PreferenceKey.READ_PRIVACY,"true");
                                checkPrivaty.setChecked(true);

                            }
                        });
                dialog.show();
                if(bottomDialogUtil!=null){
                    bottomDialogUtil.dismiss();
                }
            }
        });
        tvconfire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Hawk.put(PreferenceKey.READ_PRIVACY,"true");
               checkPrivaty.setChecked(true);
               if(bottomDialogUtil!=null){
                   bottomDialogUtil.dismiss();
               }
            }
        });
    }


     class  CustomClickableSpan extends ClickableSpan {
        private Context mContext;
        private String randTag;
        public CustomClickableSpan(Context context, int start,int end,String tag) {
            mContext = context;
            this.randTag=tag;
        }
        @Override
        public void onClick(View widget) {
            if (widget instanceof TextView) {
                if(randTag.equals("用户协议")){
                    WebViewActivity.loadUrl(context, "用户协议","https://www.wzcwlw.com/userAgreement.html");
                }else if(randTag.equals("隐私政策")){
                    WebViewActivity.loadUrl(context, "隐私声明","https://www.wzcwlw.com/privacyStatement.html");
                }
            }
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.clearShadowLayer();
        }
    }
    /**
     * 验证指纹
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void check() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (FingerManager.checkSupport(LoginActivity.this)) {
                case DEVICE_UNSUPPORTED:
                    showMessage("您的设备不支持指纹");
                    break;
                case SUPPORT_WITHOUT_KEYGUARD:
                    //设备支持但未处于安全保护中（你的设备必须是使用屏幕锁保护的，这个屏幕锁可以是password，PIN或者图案都行）

                    showOpenSettingDialog("您还未录屏幕锁保护，是否现在开启?");

                    break;
                case SUPPORT_WITHOUT_DATA:
                    showOpenSettingDialog("您还未录入指纹信息，是否现在录入?");
                    break;
                case SUPPORT:
                    FingerManager.build().setApplication(getApplication())
                            .setTitle("指纹验证")
                            .setDes("请按下指纹")
                            .setNegativeText("取消")
                            .setFingerCallback(new SimpleFingerCallback() {
                                @Override
                                public void onSucceed() {
                                    showMessage("验证成功");
                                    Log.e("msg","验证成功");
                                    if(TextUtils.isEmpty(Hawk.get(PreferenceKey.Fingerprint_Account,""))
                                    ||TextUtils.isEmpty(Hawk.get(PreferenceKey.Fingerprint_Pass,""))||
                                            TextUtils.isEmpty(Hawk.get(PreferenceKey.Fingerprint_Type,""))){
                                        showMessage("请先进行密码登录");
                                        return;
                                    }
                                    Log.e("msg","开始登录");
                                    presenter.login(Hawk.get(PreferenceKey.Fingerprint_Type,""),Hawk.get(PreferenceKey.Fingerprint_Account,""),Hawk.get(PreferenceKey.Fingerprint_Pass,""));
                                    return;
                                }
                                @Override
                                public void onFailed() {
                                    showMessage("指纹无法识别");
                                }
                                @Override
                                public void onChange() {
                                    FingerManager.updateFingerData(LoginActivity.this);
                                    check();
                                }

                                @Override
                                public void onCancel() {
                                    super.onCancel();
                                    //FingerManager.getInstance()
                                }
                            })
                            .create().startListener(this);
                           ;
                    break;
                default:
            }
        }
    }
    /**
     * 引导指纹录入
     */
    public void startFingerprint() {
        final String BRAND = android.os.Build.BRAND;
        PhoneInfoCheck.getInstance(this, BRAND).startFingerprint();
    }
    /**
     * 打开提示去录入指纹的dialog
     */
    private void showOpenSettingDialog(String msg) {
        CommonTipDialog openFingerprintTipDialog = new CommonTipDialog(this);
        openFingerprintTipDialog.setSingleButton(false);
        openFingerprintTipDialog.setContentText("您还未录入指纹信息，是否现在录入?");
        openFingerprintTipDialog.setOnDialogButtonsClickListener(new CommonTipDialog.OnDialogButtonsClickListener() {
            @Override
            public void onCancelClick(View v) {

            }

            @Override
            public void onConfirmClick(View v) {
                startFingerprint();
            }
        });
        openFingerprintTipDialog.show();
    }

}
