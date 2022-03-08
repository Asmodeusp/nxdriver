package com.saimawzc.freight.weight.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.saimawzc.freight.base.BaseActivity;
import com.wcz.fingerprintrecognitionmanager.FingerManager;
import com.wcz.fingerprintrecognitionmanager.callback.SimpleFingerCallback;
import com.wcz.fingerprintrecognitionmanager.dialog.CommonTipDialog;
import com.wcz.fingerprintrecognitionmanager.util.PhoneInfoCheck;

public class FingerprintUtils {


    /**
     * 验证指纹
     */
    public  void check(final BaseActivity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (FingerManager.checkSupport(context)) {
                case DEVICE_UNSUPPORTED:
                    context.showMessage("您的设备不支持指纹");
                    break;
                case SUPPORT_WITHOUT_KEYGUARD:
                    //设备支持但未处于安全保护中（你的设备必须是使用屏幕锁保护的，这个屏幕锁可以是password，PIN或者图案都行）
                    showOpenSettingDialog("您还未录屏幕锁保护，是否现在开启?",context);
                    break;
                case SUPPORT_WITHOUT_DATA:
                    showOpenSettingDialog("您还未录入指纹信息，是否现在录入?",context);
                    break;
                case SUPPORT:

                default:
            }
        }
    }

    /**
     * 打开提示去录入指纹的dialog
     */
    private  void showOpenSettingDialog(String msg, final BaseActivity baseActivity) {
        CommonTipDialog openFingerprintTipDialog = new CommonTipDialog(baseActivity);
        openFingerprintTipDialog.setSingleButton(false);
        openFingerprintTipDialog.setContentText("您还未录入指纹信息，是否现在录入?");
        openFingerprintTipDialog.setOnDialogButtonsClickListener(new CommonTipDialog.OnDialogButtonsClickListener() {
            @Override
            public void onCancelClick(View v) {

            }

            @Override
            public void onConfirmClick(View v) {
                startFingerprint(baseActivity);
            }
        });
        openFingerprintTipDialog.show();
    }

    /**
     * 引导指纹录入
     */
    public  void startFingerprint(BaseActivity activity) {
        final String BRAND = android.os.Build.BRAND;
        PhoneInfoCheck.getInstance(activity, BRAND).startFingerprint();
    }
}
