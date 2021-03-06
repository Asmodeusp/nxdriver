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
 * ??????
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
        useAgreement.setText("<<????????????>>");
        btnPrivacy.setText("<<????????????>>");
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
            case R.id.btn_acc_clear://??????????????????
                editAccount.setText("");
                break;
            case R.id.btn_passclear://????????????
                editPassword.setText("");
                break;
            case R.id.btnCode://???????????????
                readyGo(VerificationCodeLoginActivity.class);
                break;
            case R.id.btn_ForgetPsw://????????????
                readyGo(ForgetPassActivity.class);
                break;
            case R.id.btn_Resister://??????
                chooseIdentity(1);
                break;
            case R.id.useAgreement://????????????
                WebViewActivity.loadUrl(context, "????????????","https://www.wzcwlw.com/userAgreement.html");
                break;
            case R.id.btnPrivacy://????????????
                WebViewActivity.loadUrl(context, "????????????","https://www.wzcwlw.com/privacyStatement.html");
                break;
            case R.id.btn_Login:
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("????????????????????????????????????");
                    return;
                }
                if(!checkPrivaty.isChecked()){
                    showMessage("????????????????????????????????????");
                    return;
                }
                if(TextUtils.isEmpty(editAccount.getText().toString())){
                    showMessage("???????????????");
                    return;
                }
                if(TextUtils.isEmpty(editPassword.getText().toString())){
                    showMessage("???????????????");
                    return;
                }
                if(!editPassword.getText().toString().matches(Constants.PW_PATTERN)){
                    showMessage("??????????????????????????????????????????????????????????????????????????????????????????????????????");
                    return;
                }
                chooseIdentity(2);
                break;
        }
    }

    /***
     * ????????????
     * **/
    private BottomDialog bottomDialog;
    private void chooseIdentity(final int type){//1??????  2??????
        if(bottomDialog==null){
            bottomDialog=new BottomDialog(LoginActivity.this, R.style.BaseDialog,R.layout.dialog_resister);
        }
        LinearLayout lineDrierv=bottomDialog.findViewById(R.id.lineDriver);
        LinearLayout lineCarrier=bottomDialog.findViewById(R.id.lineCarrier);
        TextView tvTip=bottomDialog.findViewById(R.id.tvTime);
        if(type==1){
            tvTip.setText("????????????????????????????????????????????????");
        }else {
            tvTip.setText("????????????????????????????????????????????????");
        }
        bottomDialog.show();
        lineDrierv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type==1){
                    Bundle bundle=new Bundle();
                    bundle.putString("type","??????");
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
                    bundle.putString("type","?????????");
                    readyGo(RegisterActivity.class,bundle);
                }else if(type==2){
                    presenter.login(2,checkBox.isChecked());
                }
            }
        });
    }

    /**
     * ???????????????
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
            //???????????????????????????????????????
            if (!TextUtils.isEmpty(editAccount.getText().toString()) && !TextUtils.isEmpty(editPassword.getText().toString())) {
                mLoginMask.setVisibility(View.GONE);
            } else {
                mLoginMask.setVisibility(View.VISIBLE);
            }
            //??????????????????????????????
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
        if(role==2){//?????????
            Log.e("msg","???????????????");
            readyGo(MainActivity.class);
        }else if(role==3){//??????
            Log.e("msg","????????????");
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
                .setContext(context) //?????? context
                .setContentView(R.layout.dialog_privacy) //??????????????????
                .setOutSideCancel(false) //????????????????????????
                .builder()
                .show();
        TextView tvtips= (TextView) bottomDialogUtil.getItemView(R.id.tvtips);
        String tips="???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????";
        CustomClickableSpan clickableSpan = new CustomClickableSpan(LoginActivity.this,15,21,"????????????");
        SpannableStringBuilder builder = new SpannableStringBuilder(tips);
        builder.setSpan(clickableSpan, 15, 21, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#4098FD")), 15, 21, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        CustomClickableSpan clickableSpan2 = new CustomClickableSpan(LoginActivity.this,22,28,"????????????");
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
                        .content("??????????????????????????????????????????????????????????????????????????????")
                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                        .btnNum(2).btnText("????????????", "???????????????");
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
                if(randTag.equals("????????????")){
                    WebViewActivity.loadUrl(context, "????????????","https://www.wzcwlw.com/userAgreement.html");
                }else if(randTag.equals("????????????")){
                    WebViewActivity.loadUrl(context, "????????????","https://www.wzcwlw.com/privacyStatement.html");
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
     * ????????????
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void check() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (FingerManager.checkSupport(LoginActivity.this)) {
                case DEVICE_UNSUPPORTED:
                    showMessage("???????????????????????????");
                    break;
                case SUPPORT_WITHOUT_KEYGUARD:
                    //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????password???PIN?????????????????????

                    showOpenSettingDialog("?????????????????????????????????????????????????");

                    break;
                case SUPPORT_WITHOUT_DATA:
                    showOpenSettingDialog("?????????????????????????????????????????????????");
                    break;
                case SUPPORT:
                    FingerManager.build().setApplication(getApplication())
                            .setTitle("????????????")
                            .setDes("???????????????")
                            .setNegativeText("??????")
                            .setFingerCallback(new SimpleFingerCallback() {
                                @Override
                                public void onSucceed() {
                                    showMessage("????????????");
                                    Log.e("msg","????????????");
                                    if(TextUtils.isEmpty(Hawk.get(PreferenceKey.Fingerprint_Account,""))
                                    ||TextUtils.isEmpty(Hawk.get(PreferenceKey.Fingerprint_Pass,""))||
                                            TextUtils.isEmpty(Hawk.get(PreferenceKey.Fingerprint_Type,""))){
                                        showMessage("????????????????????????");
                                        return;
                                    }
                                    Log.e("msg","????????????");
                                    presenter.login(Hawk.get(PreferenceKey.Fingerprint_Type,""),Hawk.get(PreferenceKey.Fingerprint_Account,""),Hawk.get(PreferenceKey.Fingerprint_Pass,""));
                                    return;
                                }
                                @Override
                                public void onFailed() {
                                    showMessage("??????????????????");
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
     * ??????????????????
     */
    public void startFingerprint() {
        final String BRAND = android.os.Build.BRAND;
        PhoneInfoCheck.getInstance(this, BRAND).startFingerprint();
    }
    /**
     * ??????????????????????????????dialog
     */
    private void showOpenSettingDialog(String msg) {
        CommonTipDialog openFingerprintTipDialog = new CommonTipDialog(this);
        openFingerprintTipDialog.setSingleButton(false);
        openFingerprintTipDialog.setContentText("?????????????????????????????????????????????????");
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
