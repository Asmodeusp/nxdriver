package com.saimawzc.freight.ui.tab;

import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseImmersionFragment;
import com.saimawzc.freight.dto.my.PersonCenterDto;
import com.saimawzc.freight.dto.my.carrier.MyCarrierDto;
import com.saimawzc.freight.dto.my.lessess.MyLessessDto;
import com.saimawzc.freight.dto.wallet.SonAccountDto;
import com.saimawzc.freight.presenter.mine.MinePersonter;
import com.saimawzc.freight.ui.login.LoginActivity;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.ui.my.carmanage.CarLearderListActivity;
import com.saimawzc.freight.ui.my.person.ChangeRoleActivity;
import com.saimawzc.freight.ui.my.pubandservice.PublisherActivity;
import com.saimawzc.freight.ui.wallet.WalletActivity;
import com.saimawzc.freight.view.mine.MineView;
import com.saimawzc.freight.weight.BottomDialogUtil;
import com.saimawzc.freight.weight.CircleImageView;
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
/**Created by Administrator on 2020/7/31.
 * 我的
 */
public class MineFragment  extends BaseImmersionFragment
        implements MineView {
    private NormalDialog dialog;
    @BindView(R.id.tvName)TextView tvName;
    @BindView(R.id.avatar_min)CircleImageView headImage;
    @BindView(R.id.tvcompamy)TextView tvComapny;
    @BindView(R.id.tvidentificstatus)TextView tvIdentificationStatus;
    @BindView(R.id.tvRole)TextView tvRole;
    private MinePersonter personter;
    @BindView(R.id.tvunPassLessess)TextView tvunPassLessess;
    @BindView(R.id.tvverson)TextView tvVerson;
    @Override
    public int initContentView() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        personter=new MinePersonter(this,mContext);
        tvVerson.setText("当前版本号："+ BaseActivity.getVersionName(mContext));
    }
    @Override
    public void initData() {
    }
    @OnClick({R.id.rl_LogOut,R.id.rl_mycar,R.id.rl_useridentification,R.id.id_mydriver,R.id.rlwallet
    ,R.id.mylessess,R.id.avatar_min,R.id.changerole,R.id.mysettlement, R.id.rl_myship,R.id.imgKefu
    ,R.id.rlset,R.id.rlpublish,R.id.rlcarmanage})
    public void click(View view){
        Bundle bundle=null;
        if(context.isLogin()){
            switch (view.getId()){
                case R.id.rlwallet://钱包
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
                case R.id.mysettlement://我的结算
                    bundle=new Bundle();
                    bundle.putString("from","mysettlement");
                    //readyGo(PersonCenterActivity.class,bundle);
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
                case R.id.mylessess:
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

                case R.id.id_mydriver:
                    if(personCenterDto==null){
                        context.showMessage("数据获取有误");
                        return;
                    }
                    if(personCenterDto.getAuthState()!=1){
                        context.showMessage("您当前尚未认证");
                        return;
                    }
                    if(personCenterDto.getRoleType()==2){//承运商
                        bundle=new Bundle();
                        bundle.putString("from","mydriver");
                        readyGo(PersonCenterActivity.class,bundle);
                    }else {
                        bundle=new Bundle();
                        bundle.putString("from","mycarrive");
                        readyGo(PersonCenterActivity.class,bundle);
                    }
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
                    bundle=new Bundle();
                    bundle.putString("from","mycar");
                    readyGo(PersonCenterActivity.class,bundle);
                    break;
                case R.id.rl_myship://我的船舶
                    if(personCenterDto==null){
                        context.showMessage("数据获取有误");
                        return;
                    }
                    if(personCenterDto.getAuthState()!=1){
                        context.showMessage("您当前尚未认证");
                        return;
                    }
                    bundle=new Bundle();
                    bundle.putString("from","myship");
                    readyGo(PersonCenterActivity.class,bundle);
                    break;
                case R.id.rl_useridentification://用户认证
                    if(personCenterDto==null){
                        personter.getPerson();
                        context.showMessage("正在获取加载信息数据...");
                        return;
                    }
                    if(personCenterDto.getRoleType()==3
                            ||Hawk.get(PreferenceKey.LOGIN_TYPE,0)==3){//司机
                        bundle=new Bundle();
                        bundle.putString("from","driveridentification");
                        bundle.putString("type","");
                    }else if(personCenterDto.getRoleType()==2
                            ||Hawk.get(PreferenceKey.LOGIN_TYPE,0)==2){//承运商
                        if(personCenterDto.getCysType()==1){//承运商
                            bundle=new Bundle();
                            bundle.putString("from","personalcarrier");
                        }else if(personCenterDto.getCysType()==2){//一般纳税人
                            bundle=new Bundle();
                            bundle.putString("from","nomaltaxicarrier");
                        }else if(personCenterDto.getCysType()==3){//小型规模企业
                            bundle=new Bundle();
                            bundle.putString("from","samllcarrive");
                        }else if(personCenterDto.getCysType()==0){
                            bundle=new Bundle();
                            bundle.putString("from","useridentification");
                        }
                        bundle.putString("type","");
                    }
                    readyGo(PersonCenterActivity.class,bundle);
                    context.dismissLoadingDialog();
                    break;
                case R.id.rl_LogOut://注销
                    dialog = new NormalDialog(mContext).isTitleShow(false)
                            .content("确定退出登录吗?")
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
                                    Hawk.put(PreferenceKey.ID,"");
                                    Hawk.put(PreferenceKey.CYS_IS_INDENFICATION,"");
                                    Hawk.put(PreferenceKey.LOGIN_TYPE,"");
                                    Hawk.put(PreferenceKey.USER_INFO,null);
                                    Hawk.put(PreferenceKey.PERSON_CENTER,null);
                                    readyGo(LoginActivity.class);

                                }
                            });
                    dialog.show();
                    break;
                case R.id.imgKefu:
                    final BottomDialogUtil bottomDialogUtil = new BottomDialogUtil.Builder()
                            .setContext(context) //设置 context
                            .setContentView(R.layout.dialog_kefu) //设置布局文件
                            .setOutSideCancel(true) //设置点击外部取消
                            .builder()
                            .show();
                    TextView tvdayPhone= (TextView) bottomDialogUtil.getItemView(R.id.tvDayPhone);
                    TextView tvNightPhone= (TextView) bottomDialogUtil.getItemView(R.id.tvNightPhone);
                    TextView tvtousuPhone=(TextView) bottomDialogUtil.getItemView(R.id.tvtousuPhone);

                    String[] PERMISSIONS = new String[]{
                            Manifest.permission.CALL_PHONE
                    };
                    if(context.permissionChecker.isLackPermissions(PERMISSIONS)){
                        context.permissionChecker.requestPermissions();
                        context.showMessage("未获取到电话权限");
                        return;
                    }
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
                case R.id.rlpublish://发不方管理
                    readyGo(PublisherActivity.class);
                    break;
                case R.id.rlcarmanage:
                    readyGo(CarLearderListActivity.class);
                    break;
            }
        }else {
            readyGo(LoginActivity.class);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                personter.getPerson();
                personter.getLessess();
            }
        });

    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true).
                navigationBarColor(R.color.bg).
                init();
    }

    @Override
    public void getPersonDto(PersonCenterDto response) {
        Hawk.put(PreferenceKey.PERSON_CENTER,response);
        personCenterDto=response;

        tvName.setText(response.getName());
        Glide.with(mContext).load(personCenterDto.getPicture()).error(R.drawable.ico_head_defalut)
                .into(headImage);
        Hawk.put(PreferenceKey.CYS_IS_INDENFICATION,response.getAuthState()+"");
        if(response.getAuthState()==0){
            tvIdentificationStatus.setText("未认证");
        }else if(response.getAuthState()==1){
            tvIdentificationStatus.setText("已认证");
        }else if(response.getAuthState()==2){
            tvIdentificationStatus.setText("认证中");
        }else {
            tvIdentificationStatus.setText("认证失败");
        }
        tvComapny.setText("承运商");
    }

    @Override
    public void getMyCarrive(List<MyCarrierDto> carrierDtos) {

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
    public void getsonAccount(SonAccountDto sonAccountDto, String code) {
            Bundle bundle;
            if(!TextUtils.isEmpty(code)){
                if(code.contains("2100")){//未签约
                    bundle=new Bundle();
                    bundle.putString("from","wallet");
                    readyGo(PersonCenterActivity.class,bundle);
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
}
