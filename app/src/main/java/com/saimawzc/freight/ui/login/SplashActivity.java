package com.saimawzc.freight.ui.login;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.VersonDto;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.ui.DriverMainActivity;
import com.saimawzc.freight.ui.MainActivity;
import com.saimawzc.freight.ui.WebViewActivity;
import com.saimawzc.freight.weight.BottomDialogUtil;
import com.saimawzc.freight.weight.utils.DateUtils;
import com.saimawzc.freight.weight.utils.dialog.UpdateDialog;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import com.gyf.immersionbar.ImmersionBar;
import com.saimawzc.freight.weight.utils.update.InstallUtils;
import com.werb.permissionschecker.PermissionChecker;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
/**
 * 启动页
 */
public class SplashActivity extends BaseActivity {

    private UserInfoDto userInfoDto;
    String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    @Override
    protected int getViewId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        View decorView = getWindow().getDecorView();
        Hawk.put(PreferenceKey.IS_INTOFENCE,false);//把当前的围栏信息清空
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        initpermissionChecker();
        setDownApkUrl();
        if(!BaseActivity.isNetworkConnected(mContext)){
            turnToMain();
        }else {
            getVerson();
        }

    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void onGetBundle(Bundle bundle) {
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    private void turnToMain() {

        userInfoDto= Hawk.get(PreferenceKey.USER_INFO);
        if(!TextUtils.isEmpty(Hawk.get(PreferenceKey.ID,""))){//已经登录
            userInfoDto=Hawk.get(PreferenceKey.USER_INFO);
            if(userInfoDto!=null){
                if(userInfoDto.getRole()==2){
                    readyGo(MainActivity.class);
                } else if(userInfoDto.getRole()==3){
                    readyGo(DriverMainActivity.class);
                }
            }
        }else {
            Log.e("msg","跳转登陆");
            readyGo(LoginActivity.class);
        }
    }


    public void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).statusBarDarkFont(true).
                navigationBarColor(R.color.bg).
                init();
    }

    private UpdateDialog updateDialog;//版本更新Dialog
    /***
     * 获取版本更新接口
     * **/
    private VersonDto tempVersonDto;
    private void getVerson(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("appSource","1");//安卓
            jsonObject.put("appType",2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
       authApi.getVerson(body).enqueue(new CallBack<VersonDto>() {
            @Override
            public void success(final VersonDto response) {
                if(response==null){
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                                turnToMain();
                                return;
                            } catch (InterruptedException e) {
                            } finally {
                                turnToMain();
                                return;
                            }
                        }
                    }.start();
                }
                tempVersonDto=response;
//                    if(!response.getVersionNum().equals(BaseActivity.getVersionName(SplashActivity.this))){
                    if(checkNeedUpgrade(BaseActivity.getVersionName(SplashActivity.this),response.getVersionNum())){
                        if(permissionChecker.isLackPermissions(PERMISSIONS)){
                            final BottomDialogUtil bottomDialogUtil = new BottomDialogUtil.Builder()
                                    .setContext(context) //设置 context
                                    .setContentView(R.layout.dialog_savepermiss) //设置布局文件
                                    .setOutSideCancel(false) //设置点击外部取消
                                    .builder()
                                    .show();
                            TextView btnPrivacy= (TextView) bottomDialogUtil.getItemView(R.id.btnPrivacy);
                            final CheckBox checkBox= (CheckBox) bottomDialogUtil.getItemView(R.id.check);
                            TextView tvcancel=(TextView) bottomDialogUtil.getItemView(R.id.tvcancel);
                            TextView tvconfire=(TextView) bottomDialogUtil.getItemView(R.id.tvconfire);
                            btnPrivacy.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    WebViewActivity.loadUrl(context, "隐私声明","https://www.wzcwlw.com/privacyStatement.html");
                                }
                            });
                            tvcancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    turnToMain();
                                }
                            });
                            tvconfire.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(!checkBox.isChecked()){
                                        showMessage("请阅读并同意隐私权限");
                                        return;
                                    }else {
                                        permissionChecker.requestPermissions();
                                    }
                                }
                            });

                        }else {
                            if(TextUtils.isEmpty(apkURLQ)){
                                setDownApkUrl();
                            }
                            updateDialog = new UpdateDialog();
                            updateDialog.customVersionDialogTwo(SplashActivity.this,response);
                            if(response.getUpdateContent().contains("\\n")){
                                updateDialog.tvMsg.setText(response.getUpdateContent().replace(
                                        "\\n"
                                        ,
                                        "\n"
                                ));
                            }else {
                                updateDialog.tvMsg.setText(response.getUpdateContent());
                            }
                        }
                    }else {
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                } finally {
                                    turnToMain();
                                }
                            }
                        }.start();
                    }


            }
            @Override
            public void fail(String code, String message) {
                new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    } finally {
                        turnToMain();
                    }
                }
            }.start();

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionChecker.PERMISSION_REQUEST_CODE:
                if (permissionChecker.hasAllPermissionsGranted(grantResults)) {
                    if(tempVersonDto==null){
                        return;
                    }
                    if(!tempVersonDto.getVersionNum().equals(BaseActivity.getVersionName(SplashActivity.this))){
                        updateDialog = new UpdateDialog();
                        updateDialog.customVersionDialogTwo(SplashActivity.this,tempVersonDto);
                        if(tempVersonDto.getUpdateContent().contains("\\n")){
                            updateDialog.tvMsg.setText(tempVersonDto.getUpdateContent().replace(
                                    "\\n"
                                    ,
                                    "\n"
                            ));
                        }else {
                            updateDialog.tvMsg.setText(tempVersonDto.getUpdateContent());
                        }
                    }else {
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                } finally {
                                    turnToMain();
                                }
                            }
                        }.start();
                    }
                } else {
                    // 权限拒绝后的提示
                    turnToMain();
                }
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(updateDialog!=null){
            updateDialog.dissmiss();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        final String oldTime=Hawk.get(PreferenceKey.OLD_UPDATE_TIME);
        if(!TextUtils.isEmpty(oldTime)){
            File file=new File(oldTime);
            if(!file.exists()){
                Hawk.put(PreferenceKey.OLD_UPDATE_TIME,"");
                return;
            }
            try{
              int jiange=getGapMinutes(DateUtils.getInstance().getDate(oldTime),getCurrentTime("yyyy-MM-dd HH:mm"));
              if(jiange<=2){
                  InstallUtils.checkInstallPermission(this, new InstallUtils.InstallPermissionCallBack() {
                      @Override
                      public void onGranted() {
                          Hawk.put(PreferenceKey.OLD_UPDATE_TIME,"");
                          installApk(oldTime);
                      }
                      @Override
                      public void onDenied() {
                      }
                  });
              }
            }catch (Exception e){

            }
        }
    }
    public static boolean checkNeedUpgrade(String app_version, String latest_android_version) {// 检测是否需要更新。
        if (app_version == null || app_version.length() == 0 || latest_android_version == null || latest_android_version.length() == 0) {
            return false;
        }

        String[] oldAppVer = app_version.split("\\.");
        String[] latestAppVer = latest_android_version.split("\\.");
        int minSize = Math.min(oldAppVer.length, latestAppVer.length);
        for (int j = 0; j < minSize; j++) {
            if ((oldAppVer[j] != null && oldAppVer[j].length() > 0) && (latestAppVer[j] != null && latestAppVer[j].length() > 0)) {
                int oldVal;
                int latestVal;
                try {
                    oldVal = Integer.valueOf(oldAppVer[j]);
                    latestVal = Integer.valueOf(latestAppVer[j]);
                } catch (Exception e) {
                    oldVal = -1;
                    latestVal = -1;
                }
                if (latestVal > oldVal) {
                    return true;
                } else if (latestVal < oldVal) {
                    return false;
                }
            }
        }
        if (latestAppVer.length > minSize) {
            return true;
        }
        return false;
    }

}
