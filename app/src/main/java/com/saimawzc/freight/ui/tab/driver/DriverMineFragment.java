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
 * ??????
 */
public class DriverMineFragment extends BaseImmersionFragment
        implements MineView {
    private NormalDialog dialog;
    @BindView(R.id.tvName)TextView tvName;
    @BindView(R.id.avatar_min)CircleImageView headImage;
    @BindView(R.id.tvcompamy)TextView tvComapny;
    @BindView(R.id.tvidentificstatus)TextView tvIdentificationStatus;
    @BindView(R.id.tvrole)TextView tvRole;//???????????????  ??????
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
        tvVerson.setText("??????????????????"+ BaseActivity.getVersionName(mContext));
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
                case R.id.mywalley://????????????
                    if(personCenterDto==null){
                        context.showMessage("??????????????????");
                        return;
                    }
                    if(personCenterDto.getAuthState()!=1){
                        context.showMessage("?????????????????????");
                        return;
                    }
                    personter.getSonAccount();
                    break;
                case R.id.myFind://??????
                    bundle=new Bundle();
                    bundle.putString("title","??????");
                    readyGo(CommonActivity.class,bundle);
                    break;
                case R.id.mysettlement://????????????
                    bundle=new Bundle();
                    bundle.putString("from","mysettlement");
                    readyGo(PersonCenterActivity.class,bundle);
                    break;
                case R.id.changerole://????????????
                    if(personCenterDto==null){
                        context.showMessage("??????????????????");
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
                case R.id.mylessess://???????????????
                    if(personCenterDto==null){
                        context.showMessage("??????????????????");
                        return;
                    }
                    if(personCenterDto.getAuthState()!=1){
                        context.showMessage("?????????????????????");
                        return;
                    }
                    bundle=new Bundle();
                    bundle.putString("from","mylessess");
                    readyGo(PersonCenterActivity.class,bundle);
                    break;
                case R.id.mycys:
                    if(personCenterDto==null){
                        context.showMessage("??????????????????");
                        return;
                    }
                    if(personCenterDto.getAuthState()!=1){
                        context.showMessage("?????????????????????");
                        return;
                    }
                    bundle=new Bundle();
                    bundle.putString("from","mycarrive");
                    readyGo(PersonCenterActivity.class,bundle);
                    break;
                case R.id.myservice://???????????????
                    if(personCenterDto==null){
                        context.showMessage("??????????????????");
                        return;
                    }
                    if(personCenterDto.getAuthState()!=1){
                        context.showMessage("?????????????????????");
                        return;
                    }
                    bundle=new Bundle();
                    bundle.putString("idcardNum",personCenterDto.getIdCardNum());
                    readyGo(MyServicePartyActivity.class,bundle);
                    break;
                case R.id.rl_mycar://????????????
                    if(personCenterDto==null){
                        context.showMessage("??????????????????");
                        return;
                    }
                    if(personCenterDto.getAuthState()!=1){
                        context.showMessage("?????????????????????");
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
                case R.id.myzdz://???????????????
                    if(personCenterDto==null){
                        context.showMessage("??????????????????");
                        return;
                    }
                    if(personCenterDto.getAuthState()!=1){
                        context.showMessage("?????????????????????");
                        return;
                    }
                    readyGo(MyCarQueneActivity.class);
                    break;
                case R.id.rl_useridentification://????????????
                    if(personCenterDto==null){
                        personter.getPerson();
                        context.showMessage("??????????????????????????????...");
                        return;
                    }
                    if(tvIdentificationStatus.getText().toString().equals("?????????")){
                        bundle =new Bundle();
                        bundle.putString("from","sijicarriver");
                        readyGo(PersonCenterActivity.class,bundle);
                    }else {
                        bundle=new Bundle();
                        bundle.putString("from","driveridentification");
                        bundle.putString("carrivetype",personCenterDto.getDriverType()+"");//1?????? 2??????
                        readyGo(PersonCenterActivity.class,bundle);
                    }
                    break;
                case R.id.rl_LogOut://??????
                    dialog = new NormalDialog(mContext).isTitleShow(false)
                            .content("??????????????????????")
                            .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                            .btnNum(2).btnText("??????", "??????");
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
                        context.showMessage("????????????????????????");
                        return;
                    }

                    final BottomDialogUtil bottomDialogUtil = new BottomDialogUtil.Builder()
                            .setContext(context) //?????? context
                            .setContentView(R.layout.dialog_kefu) //??????????????????
                            .setOutSideCancel(true) //????????????????????????
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
            context.showMessage("????????????????????????");
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
            tvIdentificationStatus.setText("?????????");
        }else if(response.getAuthState()==1){
            tvIdentificationStatus.setText("?????????");
            if(!TextUtils.isEmpty(response.getRoleId())){
                if(trackApp!=null){
                    if(trackApp.mClient==null){
                        trackApp.mClient = new LBSTraceClient(trackApp);
                        trackApp.mTrace = new Trace(trackApp.serviceId, response.getRoleId());
                        trackApp.locRequest = new LocRequest(trackApp.serviceId);
                        // ????????????(??????:???)
                        int gatherInterval = 10;
                        // ??????????????????(??????:???)
                        int packInterval = 60;
                        // ???????????????????????????
                        trackApp.mClient.setInterval(gatherInterval, packInterval);
                    }
                }
            }
        }else if(response.getAuthState()==2){
            tvIdentificationStatus.setText("?????????");
        }else {
            tvIdentificationStatus.setText("????????????");
        }
        if(response.getDriverType()==1){
            tvRole.setText("????????????");
            tvComapny.setText("??????");
        }else if(response.getDriverType()==2) {
            tvRole.setText("????????????");
            tvComapny.setText("??????");
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
                if(code.contains("2100")){//?????????
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
            if(!str.contains("??????")){
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
                            //????????????????????????tipview
                            mHightLight.addHighLight(rlUseIdentifica,R.layout.info_gravity_bottom_down,new OnBottomCoustomPosCallback(20),new RectLightShape())
                                    .addHighLight(llMyCar,R.layout.info_gravity_bottom_right,new OnRightCountomPosCallback(20),new RectLightShape())
                                  .addHighLight(rlSet,R.layout.info_gravity_bottom_right,new OnRightCountomPosCallback(20),new RectLightShape());
                            //????????????????????????
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
