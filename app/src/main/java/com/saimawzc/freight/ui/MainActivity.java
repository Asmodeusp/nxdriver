package com.saimawzc.freight.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.FrameDto;
import com.saimawzc.freight.dto.WaybillEncloDto;
import com.saimawzc.freight.dto.my.PersonCenterDto;
import com.saimawzc.freight.dto.my.carrier.MyCarrierDto;
import com.saimawzc.freight.dto.my.lessess.MyLessessDto;
import com.saimawzc.freight.presenter.MainPersonter;
import com.saimawzc.freight.ui.login.LoginActivity;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.ui.tab.SendCarIndexFragment;
import com.saimawzc.freight.ui.tab.MainIndexFragment;
import com.saimawzc.freight.ui.tab.MineFragment;
import com.saimawzc.freight.ui.tab.WaybillIndexFragment;
import com.saimawzc.freight.view.DriverMainView;
import com.saimawzc.freight.weight.BottomDialogUtil;
import com.saimawzc.freight.weight.utils.app.AppManager;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import com.gyf.immersionbar.ImmersionBar;
import com.jpeng.jptabbar.BadgeDismissListener;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.saimawzc.freight.weight.waterpic.ImageUtil;
import com.werb.permissionschecker.PermissionChecker;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;




public class MainActivity extends BaseActivity
        implements OnTabSelectListener,BadgeDismissListener, DriverMainView {
    @Titles
    private static final int[] mTitles = {R.string.tab1,R.string.tab2,R.string.tab3,R.string.tab4};
    @SeleIcons
    private static final int[] mSeleIcons = {R.drawable.ico_mainindex_blue,R.drawable.ico_yundan_blue,R.drawable.ico_paicheblue,R.drawable.ico_main_blue};
    @NorIcons
    private static final int[] mNormalIcons = {R.drawable.ico_mainindex_gray, R.drawable.ico_yundan_gray, R.drawable.ico_paiche_gray, R.drawable.ico_mine_gray};
    @BindView(R.id.tabbar)JPTabBar mTabbar;
    private Fragment[] fragments;
    private MainIndexFragment mainIndexFragment;
    private WaybillIndexFragment waybillFragment;
    private SendCarIndexFragment findFragment;
    private MineFragment mineFragment;
    private MainPersonter personter;
    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }
    @Override
    protected void init() {
        mContext=this;
        if(!isLogin()){
            readyGo(LoginActivity.class);
        }
        personter=new MainPersonter(this,mContext);
        personter.getFram();
        userInfoDto=Hawk.get(PreferenceKey.USER_INFO);
        initpermissionChecker();
        mainIndexFragment = new MainIndexFragment();
        waybillFragment = new WaybillIndexFragment();
        findFragment = new SendCarIndexFragment();
        mineFragment = new MineFragment();
        mTabbar.setTabListener(this);
        fragments = new Fragment[]{mainIndexFragment, waybillFragment, findFragment, mineFragment};
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, mainIndexFragment)
               .show(mainIndexFragment)
                .commit();
        mTabbar.setTabListener(this);
        //设置Badge消失的代理
        mTabbar.setDismissListener(this);
        initAccessToken();//初始化b百度云读取身份证
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getPersonterData();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(Hawk.get(PreferenceKey.CITY_INFO)==null){
            cacheArae();
        }

        try{
            File file=new File(ImageUtil.getSystemPhotoPath(MainActivity.this));
            if(personter!=null){
                personter.deleteFile(file);
            }

        }catch (Exception e){
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                initWithApiKey();
            }
        }).start();

    }

    @Override
    protected void initListener() {
    }

    private boolean isBreak = false;
    @Override
    public void onBackPressed() {
        if (isBreak) {
            AppManager.get().finishAllActivity();
        } else {
            isBreak = true;
            showMessage("再次点击退出应用!");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isBreak = false;
                }
            }, 3000);
        }
    }

    @Override
    protected void onGetBundle(Bundle bundle) {
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (Settings.canDrawOverlays(this)) {
                    showMessage("有权限");
                } else {
                    showMessage("未获取到权限");
                }
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionChecker.PERMISSION_REQUEST_CODE:
                break;
            case 1000:
                //有注册权限且用户允许安装
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //install(apkPath);
                } else {
                    //将用户引导至安装未知应用界面。
                    //Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                   // startActivityForResult(intent, 1001);
                }
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindForApp();
        if(dialog!=null){
            if(!context.isDestroy(context)){
                dialog.dismiss();
            }
        }
    }
    int  currentTabIndex=0;
    @Override
    public void onTabSelect(int index) {
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragmentContainer, fragments[index]);
            }
            trx.show(fragments[index]).commitAllowingStateLoss();
        }
        currentTabIndex = index;
    }
    @Override
    public void onClickMiddle(View middleBtn) {
    }
    @Override
    public void onDismiss(int position) {//红点消失
    }
    /**
     * 判断该用户是否已经认证
     * ***/
    private NormalDialog dialog;
    @Override
    protected void onResume() {
        super.onResume();
        if(personter!=null){
            personter.getLessess();
        }
        if(!TextUtils.isEmpty(Hawk.get(PreferenceKey.isChange_or_login, "")) &&
                (Hawk.get(PreferenceKey.isChange_or_login, "").equals("true"))) {

            Log.e("msg", "发送重新登录广播");
            EventBus.getDefault().post(Constants.reshChangeCYS);
            Hawk.put(PreferenceKey.isChange_or_login, "false");
        }
    }
    private void getPersonterData(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mineApi.getPersoneCener().enqueue(new CallBack<PersonCenterDto>() {
                    @Override
                    public void success(final PersonCenterDto response) {
                        Hawk.put(PreferenceKey.PERSON_CENTER,response);
                        if(response.getAuthState()==0){
                            if(dialog==null){//未认证
                                dialog = new NormalDialog(mContext).isTitleShow(false)
                                        .content("您当前未认证，请前往认证？")
                                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                                        .btnNum(2).btnText("确认", "取消");
                            }
                            dialog.setOnBtnClickL(
                                    new OnBtnClickL() {//搜索
                                        @Override
                                        public void onBtnClick() {
                                            Bundle bundle=null;
                                            if(response.getRoleType()==2){//认证承运商
                                                bundle=new Bundle();
                                                bundle.putString("from","useridentification");
                                            }else if(response.getRoleType()==3){//认证司机
                                                bundle =new Bundle();
                                                bundle.putString("from","sijicarriver");
                                                readyGo(PersonCenterActivity.class,bundle);
                                            }
                                            readyGo(PersonCenterActivity.class,bundle);
                                            if(!isDestroy(MainActivity.this)){
                                                dialog.dismiss();
                                            }
                                        }
                                    },
                                    new OnBtnClickL() {//注册
                                        @Override
                                        public void onBtnClick() {
                                            dialog.dismiss();
                                        }
                                    });
                            dialog.show();
                        }else {

                        }
                    }
                    @Override
                    public void fail(String code, String message) {
                        context.showMessage(message);
                    }
                });
            }
        });
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).statusBarDarkFont(true).
                navigationBarColor(R.color.bg).
                init();
    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void getMyCarrive(List<MyCarrierDto> carrierDtos) {
        if(carrierDtos==null||carrierDtos.size()<=0){
            mTabbar.HideBadge(3);
        }else {
            mTabbar.ShowBadge(3,"");
        }
    }

    @Override
    public void getmylessee(List<MyLessessDto> lessessDtos) {
        if(lessessDtos==null||lessessDtos.size()<=0){
            mTabbar.HideBadge(3);
        }else {
            mTabbar.ShowBadge(3,"");
        }
    }

    @Override
    public void getDialog(FrameDto dto) {
        if(dto!=null){
            if(!TextUtils.isEmpty(dto.getContent())){
                showDialog(dto.getContent());
            }
        }
    }

    @Override
    public void getYdInfo(WaybillEncloDto dto) {

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
        if(TextUtils.isEmpty(PreferenceKey.CYS_IS_INDENFICATION)||!Hawk.get(PreferenceKey.CYS_IS_INDENFICATION,"").equals("1")){
            if(!str.contains("权限")){
                context.showMessage(str);
            }
        }else {
            context.showMessage(str);
        }
    }

    @Override
    public void oncomplete() {

    }
    private void showDialog(String contect){
        final BottomDialogUtil bottomDialogUtil = new BottomDialogUtil.Builder()
                .setContext(context) //设置 context
                .setContentView(R.layout.dialog_sijitip) //设置布局文件
                .setOutSideCancel(false) //设置点击外部取消
                .builder()
                .show();
        TextView tvOrder= (TextView) bottomDialogUtil.getItemView(R.id.tvOrder);
        TextView tvcontect=(TextView) bottomDialogUtil.getItemView(R.id.tvcontect);
        tvcontect.setText(contect);
        tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialogUtil.dismiss();
            }
        });
    }
}
