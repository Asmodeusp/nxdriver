package com.saimawzc.freight.ui.my.set;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mob.MobSDK;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.VersonDto;
import com.saimawzc.freight.ui.WebViewActivity;
import com.saimawzc.freight.ui.login.ForgetPassActivity;
import com.saimawzc.freight.ui.login.LoginActivity;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.weight.OnBottomCoustomPosCallback;
import com.saimawzc.freight.weight.OnRightCountomPosCallback;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.dialog.UpdateDialog;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.keeplive.KeepLiveUtils;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import com.werb.permissionschecker.PermissionChecker;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import zhy.com.highlight.HighLight;
import zhy.com.highlight.interfaces.HighLightInterface;
import zhy.com.highlight.shape.RectLightShape;

import static com.baidu.navisdk.util.jar.JarUtils.getPackageName;

public class MySetFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvtips)
    TextView tvTips;
    @BindView(R.id.rlrunbackgroud)
    RelativeLayout rlrunbackgroud;
    public  final String[] PERMISSIONSq = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    private HighLight mHightLight;
    @Override
    public int initContentView() {
        return R.layout.fragment_set;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        initpermissionChecker();
        context.setToolbar(toolbar,"系统设置");
        if(TextUtils.isEmpty(Hawk.get(PreferenceKey.TIpmSet,""))){
            showNextTipViewOnCreated();
        }

    }
    KeepLiveUtils utils;
    private NormalDialog dialog;
    @OnClick({R.id.rlforgetword,R.id.rlsuggest,
            R.id.rlrunbackgroud,R.id.rlupdate,R.id.rlzc,R.id.rlshare
            ,R.id.rlyinsi,R.id.rlaboutus,R.id.rlxieyi})
    public void click(View view){
        Bundle bundle;
        switch (view.getId()){
            case R.id.rlforgetword:
                readyGo(ForgetPassActivity.class);
                break;
            case R.id.rlshare:
                try {
                    MobSDK.submitPolicyGrantResult(true, null);
                }catch (Exception e){
                }
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showShare();
                    }
                });
                break;
            case R.id.rlyinsi:
                WebViewActivity.loadUrl(context, "隐私声明","https://www.wzcwlw.com/privacyStatement.html");
                break;
            case R.id.rlxieyi:
                WebViewActivity.loadUrl(context, "用户协议","https://www.wzcwlw.com/userAgreement.html");
                break;
            case R.id.rlaboutus:
                WebViewActivity.loadUrl(context, "关于我们","https://www.wzcwlw.com/about.html");
                break;
            case R.id.rlsuggest:
                bundle=new Bundle();
                bundle.putString("from","addsuggest");
                readyGo(PersonCenterActivity.class,bundle);
                break;
            case R.id.rlrunbackgroud:
                bundle=new Bundle();
                bundle.putString("from","keepset");
                readyGo(PersonCenterActivity.class,bundle);
                break;
            case R.id.rlupdate:
                if(permissionChecker.isLackPermissions(PERMISSIONSq)){
                    permissionChecker.requestPermissions();
                    context.showMessage("未获取到存储权限");
                }else {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getVerson();
                        }
                    });
                }
                break;
            case R.id.rlzc:
                dialog = new NormalDialog(mContext).isTitleShow(false)
                        .content("确定注销账号吗?")
                        .contentGravity(Gravity.CENTER)
                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                        .btnNum(2).btnText("取消", "确定");
                dialog.setOnBtnClickL(
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if(!context.isDestroy(context)){
                                    dialog.dismiss();
                                }
                            }
                        },
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if(!context.isDestroy(context)){
                                    dialog.dismiss();
                                }
                                unRisister();
                            }
                        });
                dialog.show();
                break;
        }
    }
    @Override
    public void initData() {
    }
    private UpdateDialog updateDialog;//版本更新Dialog
    /***
     * 获取版本更新接口
     * **/
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
        context.authApi.getVerson(body).enqueue(new CallBack<VersonDto>() {
            @Override
            public void success(final VersonDto response) {
                if(response==null|| TextUtils.isEmpty(response.getVersionNum())){
                    return;
                }
                if(checkNeedUpgrade(BaseActivity.getVersionName(context),response.getVersionNum())){
                    updateDialog = new UpdateDialog();
                    updateDialog.customVersionDialogTwo(context,response);
                    if(response.getUpdateContent().contains("\\n")){
                        updateDialog.tvMsg.setText(response.getUpdateContent().replace(
                                "\\n"
                                ,
                                "\n"
                        ));
                    }else {
                        updateDialog.tvMsg.setText(response.getUpdateContent());
                    }
                }else {//和服务器版本相同
                    context.showMessage("您当前已经是最新版本了");
                    return;
//                    updateDialog = new UpdateDialog();
//                    response.setIsSHowNo(1);
//                    updateDialog.customVersionDialogTwo(context,response);
//                    updateDialog.tvMsg.setText("当前版本和服务器版本一致，是否替换");
                }
            }
            @Override
            public void fail(String code, String message) {

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case PermissionChecker.PERMISSION_REQUEST_CODE:
                if (permissionChecker.hasAllPermissionsGranted(grantResults)) {
                    // 执行你的相关操作
                    getVerson();
                } else {
                    // 权限拒绝后的提示
                    permissionChecker.showDialog();
                    //getVerson();
                }
                break;
        }
    }

    private void unRisister(){

        context.authApi.unResister().enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(final EmptyDto response) {
                Hawk.put(PreferenceKey.ID,"");
                Hawk.put(PreferenceKey.CYS_IS_INDENFICATION,"");
                Hawk.put(PreferenceKey.LOGIN_TYPE,"");
                Hawk.put(PreferenceKey.USER_INFO,null);
                Hawk.put(PreferenceKey.PERSON_CENTER,null);
                readyGo(LoginActivity.class);
            }
            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
            }
        });
    }
    public  void showNextTipViewOnCreated(){
        mHightLight = new HighLight(mContext)//
                .autoRemove(false)
                .enableNext()
                .setOnLayoutCallback(new HighLightInterface.OnLayoutCallback() {
                    @Override
                    public void onLayouted() {
                        if(mHightLight!=null){
                            //界面布局完成添加tipview
                            mHightLight.addHighLight(rlrunbackgroud,R.layout.info_gravity_bottom_right,new OnRightCountomPosCallback(20),new RectLightShape());
                            //然后显示高亮布局
                            try {
                                mHightLight.show();
                            }catch (Exception e){
                            }
                            Hawk.put(PreferenceKey.TIpmSet,"SHOW");
                        }
                    }
                })
                .setClickCallback(new HighLight.OnClickCallback() {
                    @Override
                    public void onClick() {
                        if(mHightLight!=null){
                            try {
                                mHightLight.next();
                            }catch (Exception e){

                            }
                        }
                    }
                });
    }

    private void showShare() {

        OnekeyShare oks = new OnekeyShare(); //关闭sso授权 oks.disableSSOWhenAuthorize();
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("分享");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本");
       // 启动分享GUI
        oks.show(mContext);

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
