package com.saimawzc.freight.ui.tab.driver;

import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.entity.LocRequest;
import com.bumptech.glide.Glide;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseApplication;
import com.saimawzc.freight.base.BaseImmersionFragment;
import com.saimawzc.freight.dto.my.PersonCenterDto;
import com.saimawzc.freight.dto.my.carrier.MyCarrierDto;
import com.saimawzc.freight.dto.my.lessess.MyLessessDto;
import com.saimawzc.freight.dto.wallet.SonAccountDto;
import com.saimawzc.freight.presenter.mine.MinePersonter;
import com.saimawzc.freight.ui.CommonActivity;
import com.saimawzc.freight.ui.login.LoginActivity;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.ui.my.carqueue.MyCarQueneActivity;
import com.saimawzc.freight.ui.my.person.ChangeRoleActivity;
import com.saimawzc.freight.ui.my.pubandservice.MyServicePartyActivity;
import com.saimawzc.freight.ui.wallet.WalletActivity;
import com.saimawzc.freight.view.mine.MineView;
import com.saimawzc.freight.weight.BottomDialogUtil;
import com.saimawzc.freight.weight.CircleImageView;
import com.saimawzc.freight.weight.OnBottomCoustomPosCallback;
import com.saimawzc.freight.weight.OnRightCountomPosCallback;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zhy.com.highlight.HighLight;
import zhy.com.highlight.interfaces.HighLightInterface;
import zhy.com.highlight.shape.RectLightShape;
/**Created by Administrator on 2020/7/31.
 * 我的
 */
public class DriverMineFragment extends BaseImmersionFragment
        implements MineView {
    private NormalDialog dialog;
    @BindView(R.id.tvName)TextView tvName;
    @BindView(R.id.avatar_min)CircleImageView headImage;
    @BindView(R.id.tvcompamy)TextView tvComapny;
    @BindView(R.id.tvidentificstatus)TextView tvIdentificationStatus;
    @BindView(R.id.tvrole)TextView tvRole;//角色，船员  司机
    private MinePersonter personter;
    @BindView(R.id.tvunPassLessess)TextView tvunPassLessess;
    @BindView(R.id.tvunPassCarrive)TextView tvUnPassCarrive;
    @BindView(R.id.tvverson)TextView tvVerson;
    private HighLight mHightLight;
    @BindView(R.id.rl_useridentification)RelativeLayout rlUseIdentifica;
    @BindView(R.id.rl_mycar) LinearLayout llMyCar;
    @BindView(R.id.rlset)RelativeLayout rlSet;
    @Override
    public int initContentView() {
        return R.layout.fragment_driver_mine;
    }
    private BaseApplication trackApp = null;
    @Override
    public void initView() {
        mContext=getActivity();
        trackApp= (BaseApplication) mContext.getApplicationContext();
        personter=new MinePersonter(this,mContext);
        tvVerson.setText("当前版本号："+ BaseActivity.getVersionName(mContext));
        if(TextUtils.isEmpty(Hawk.get(PreferenceKey.TIpmINE,""))){
            showNextTipViewOnCreated();
        }
    }
    @Override
    public void initData() {
    }
    @OnClick({R.id.rl_LogOut,R.id.rl_mycar,R.id.rl_useridentification,R.id.mywalley,R.id.myFind
    ,R.id.mylessess,R.id.mycys,R.id.avatar_min,R.id.changerole,R.id.mysettlement,R.id.imgKefu
    ,R.id.rlset,R.id.myservice,R.id.myzdz})
    public void click(View view){
        Bundle bundle=null;
        if(context.isLogin()){
            switch (view.getId()){
                case R.id.mywalley://我的钱包
                    if(personCenterDto==null){
                        context.showMessage("数据获取有误");
                        return;
                    }
                    if(personCenterDto.getAuthState()!=1){
                        context.showMessage("您当前尚未认证");
                        return;
                    }
                    personter.getSonAccount();
                    break;
                case R.id.myFind://发现
                    bundle=new Bundle();
                    bundle.putString("title","发现");
                    readyGo(CommonActivity.class,bundle);
                    break;
                case R.id.mysettlement://我的结算
                    bundle=new Bundle();
                    bundle.putString("from","mysettlement");
                    readyGo(PersonCenterActivity.class,bundle);
                    break;
                case R.id.changerole://切换角色
                    if(personCenterDto==null){
                        context.showMessage("数据获取有误");
                        return;
                    }
                    bundle=new Bundle();
                    bundle.putInt("currentrole",personCenterDto.getRoleType());
                    readyGo(ChangeRoleActivity.class,bundle);
                    break;

                case R.id.avatar_min:
                    bundle=new Bundle();
                    bundle.putString("from","personcenter");
                    readyGo(PersonCenterActivity.class,bundle);
                    break;
                case R.id.mylessess://我的承租人
                    if(personCenterDto==null){
                        context.showMessage("数据获取有误");
                        return;
                    }
                    if(personCenterDto.getAuthState()!=1){
                        context.showMessage("您当前尚未认证");
                        return;
                    }
                    bundle=new Bundle();
                    bundle.putString("from","mylessess");
                    readyGo(PersonCenterActivity.class,bundle);
                    break;
                case R.id.mycys:
                    if(personCenterDto==null){
                        context.showMessage("数据获取有误");
                        return;
                    }
                    if(personCenterDto.getAuthState()!=1){
                        context.showMessage("您当前尚未认证");
                        return;
                    }
                    bundle=new Bundle();
                    bundle.putString("from","mycarrive");
                    readyGo(PersonCenterActivity.class,bundle);
                    break;
                case R.id.myservice://我的服务方
                    if(personCenterDto==null){
                        context.showMessage("数据获取有误");
                        return;
                    }
                    if(personCenterDto.getAuthState()!=1){
                        context.showMessage("您当前尚未认证");
                        return;
                    }
                    bundle=new Bundle();
                    bundle.putString("idcardNum",personCenterDto.getIdCardNum());
                    readyGo(MyServicePartyActivity.class,bundle);
                    break;
                case R.id.rl_mycar://我的车辆
                    if(personCenterDto==null){
                        context.showMessage("数据获取有误");
                        return;
                    }
                    if(personCenterDto.getAuthState()!=1){
                        context.showMessage("您当前尚未认证");
                        return;
                    }
                    if(personCenterDto.getDriverType()==1){
                        bundle=new Bundle();
                        bundle.putString("from","mycar");
                        readyGo(PersonCenterActivity.class,bundle);
                    }else if(personCenterDto.getDriverType()==2){
                        bundle=new Bundle();
                        bundle.putString("from","myship");
                        readyGo(PersonCenterActivity.class,bundle);
                    }
                    break;
                case R.id.myzdz://我的车队长
                    if(personCenterDto==null){
                        context.showMessage("数据获取有误");
                        return;
                    }
                    if(personCenterDto.getAuthState()!=1){
                        context.showMessage("您当前尚未认证");
                        return;
                    }
                    readyGo(MyCarQueneActivity.class);
                    break;
                case R.id.rl_useridentification://用户认证
                    if(personCenterDto==null){
                        personter.getPerson();
                        context.showMessage("正在获取加载信息数据...");
                        return;
                    }
                    if(tvIdentificationStatus.getText().toString().equals("未认证")){
                        bundle =new Bundle();
                        bundle.putString("from","sijicarriver");
                        readyGo(PersonCenterActivity.class,bundle);
                    }else {
                        bundle=new Bundle();
                        bundle.putString("from","driveridentification");
                        bundle.putString("carrivetype",personCenterDto.getDriverType()+"");//1司机 2船员
                        readyGo(PersonCenterActivity.class,bundle);
                    }
                    break;
                case R.id.rl_LogOut://注销
                    dialog = new NormalDialog(mContext).isTitleShow(false)
                            .content("确定退出登录吗?")
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
                                    Hawk.put(PreferenceKey.ID,"");
                                    Hawk.put(PreferenceKey.DRIVER_IS_INDENFICATION,"");
                                    Hawk.put(PreferenceKey.LOGIN_TYPE,"");
                                    Hawk.put(PreferenceKey.USER_INFO,null);
                                    Hawk.put(PreferenceKey.PERSON_CENTER,null);
                                    readyGo(LoginActivity.class);
                                    if(!context.isDestroy(context)){
                                        dialog.dismiss();
                                    }
                                }
                            });
                    dialog.show();
                    break;
                case R.id.imgKefu:
                    if(context.isDestroy(context)){
                        return;
                    }
                    String[] PERMISSIONS = new String[]{
                            Manifest.permission.CALL_PHONE
                    };
                    if(context.permissionChecker.isLackPermissions(PERMISSIONS)){
                        context.permissionChecker.requestPermissions();
                        context.showMessage("未获取到电话权限");
                        return;
                    }

                    final BottomDialogUtil bottomDialogUtil = new BottomDialogUtil.Builder()
                            .setContext(context) //设置 context
                            .setContentView(R.layout.dialog_kefu) //设置布局文件
                            .setOutSideCancel(true) //设置点击外部取消
                            .builder()
                            .show();
                    TextView tvdayPhone= (TextView) bottomDialogUtil.getItemView(R.id.tvDayPhone);
                    TextView tvNightPhone= (TextView) bottomDialogUtil.getItemView(R.id.tvNightPhone);
                    TextView tvtousuPhone=(TextView) bottomDialogUtil.getItemView(R.id.tvtousuPhone);
                    tvdayPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.callPhone("4008874005");
                            bottomDialogUtil.dismiss();
                        }
                    });
                    tvNightPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.callPhone("17398448233");
                            bottomDialogUtil.dismiss();
                        }
                    });
                    tvtousuPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            context.callPhone("13895189901");
                            bottomDialogUtil.dismiss();
                        }
                    });
                    break;
                case R.id.rlset:
                    bundle =new Bundle();
                    bundle.putString("from","set");
                    readyGo(PersonCenterActivity.class,bundle);
                    break;
            }
        }else {
            readyGo(LoginActivity.class);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if(personter!=null){
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    personter.getPerson();
                    personter.getCarriveList();
                    personter.getLessess();
                }
            });
        }
    }
    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true).
                navigationBarColor(R.color.bg).
                init();
    }
    @Override
    public void getPersonDto(PersonCenterDto response) {
        if(response==null){
            context.showMessage("获取个人信息失败");
            personter.getPerson();
            return;
        }
        Hawk.put(PreferenceKey.PERSON_CENTER,response);
        personCenterDto=response;
        tvName.setText(response.getName());
        Glide.with(mContext.getApplicationContext()).load(personCenterDto.getPicture()).error(R.drawable.ico_head_defalut)
                .into(headImage);
        Hawk.put(PreferenceKey.DRIVER_IS_INDENFICATION,response.getAuthState()+"");
        if(response.getAuthState()==0){
            tvIdentificationStatus.setText("未认证");
        }else if(response.getAuthState()==1){
            tvIdentificationStatus.setText("已认证");
            if(!TextUtils.isEmpty(response.getRoleId())){
                if(trackApp!=null){
                    if(trackApp.mClient==null){
                        trackApp.mClient = new LBSTraceClient(trackApp);
                        trackApp.mTrace = new Trace(trackApp.serviceId, response.getRoleId());
                        trackApp.locRequest = new LocRequest(trackApp.serviceId);
                        // 定位周期(单位:秒)
                        int gatherInterval = 10;
                        // 打包回传周期(单位:秒)
                        int packInterval = 60;
                        // 设置定位和打包周期
                        trackApp.mClient.setInterval(gatherInterval, packInterval);
                    }
                }
            }
        }else if(response.getAuthState()==2){
            tvIdentificationStatus.setText("认证中");
        }else {
            tvIdentificationStatus.setText("认证失败");
        }
        if(response.getDriverType()==1){
            tvRole.setText("我的车辆");
            tvComapny.setText("司机");
        }else if(response.getDriverType()==2) {
            tvRole.setText("我的船舶");
            tvComapny.setText("船员");
        }
    }
    @Override
    public void getMyCarrive(List<MyCarrierDto> carrierDtos) {
        if(carrierDtos==null||carrierDtos.size()<=0){
            tvUnPassCarrive.setVisibility(View.GONE);
        }else {
            tvUnPassCarrive.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public void getmylessee(List<MyLessessDto> lessessDtos) {
        if(lessessDtos==null||lessessDtos.size()<=0){
            tvunPassLessess.setVisibility(View.GONE);
        }else {
            tvunPassLessess.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void getsonAccount(SonAccountDto sonAccountDto,String code) {

            if(!TextUtils.isEmpty(code)){
                if(code.contains("2100")){//未签约
                    Bundle  bundle=new Bundle();
                    bundle.putString("from","wallet");
                    readyGo(PersonCenterActivity.class,bundle);
                    //context.finish();
                }else {
                    readyGo(WalletActivity.class);
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
        if(TextUtils.isEmpty(PreferenceKey.DRIVER_IS_INDENFICATION)||!Hawk.get(PreferenceKey.DRIVER_IS_INDENFICATION,"").equals("1")){
            if(!str.contains("权限")){
                Log.e("msg","ttt"+str);
                context.showMessage(str);
            }
        }else {
            context.showMessage(str);
        }
    }
    @Override
    public void oncomplete() {
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
                            mHightLight.addHighLight(rlUseIdentifica,R.layout.info_gravity_bottom_down,new OnBottomCoustomPosCallback(20),new RectLightShape())
                                    .addHighLight(llMyCar,R.layout.info_gravity_bottom_right,new OnRightCountomPosCallback(20),new RectLightShape())
                                  .addHighLight(rlSet,R.layout.info_gravity_bottom_right,new OnRightCountomPosCallback(20),new RectLightShape());
                            //然后显示高亮布局
                            try {
                                mHightLight.show();
                            }catch (Exception e){
                            }
                            Hawk.put(PreferenceKey.TIpmINE,"SHOW");
                        }
                    }
                })
                .setClickCallback(new HighLight.OnClickCallback() {
                    @Override
                    public void onClick() {
                        if(mHightLight!=null){
                            try {
                                mHightLight.next();
                            }catch (Exception E){
                            }
                    }
                    }
                });
    }
}
