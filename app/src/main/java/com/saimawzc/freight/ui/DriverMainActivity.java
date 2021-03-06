package com.saimawzc.freight.ui;


import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.trace.api.fence.AddMonitoredPersonResponse;
import com.baidu.trace.api.fence.CircleFence;
import com.baidu.trace.api.fence.CreateFenceRequest;
import com.baidu.trace.api.fence.CreateFenceResponse;
import com.baidu.trace.api.fence.DeleteFenceRequest;
import com.baidu.trace.api.fence.DeleteFenceResponse;
import com.baidu.trace.api.fence.DeleteMonitoredPersonResponse;
import com.baidu.trace.api.fence.DistrictFence;
import com.baidu.trace.api.fence.FenceInfo;
import com.baidu.trace.api.fence.FenceListRequest;
import com.baidu.trace.api.fence.FenceListResponse;
import com.baidu.trace.api.fence.FenceShape;
import com.baidu.trace.api.fence.FenceType;
import com.baidu.trace.api.fence.HistoryAlarmResponse;
import com.baidu.trace.api.fence.ListMonitoredPersonResponse;
import com.baidu.trace.api.fence.MonitoredStatus;
import com.baidu.trace.api.fence.MonitoredStatusByLocationRequest;
import com.baidu.trace.api.fence.MonitoredStatusByLocationResponse;
import com.baidu.trace.api.fence.MonitoredStatusInfo;
import com.baidu.trace.api.fence.MonitoredStatusResponse;
import com.baidu.trace.api.fence.OnFenceListener;
import com.baidu.trace.api.fence.PolygonFence;
import com.baidu.trace.api.fence.UpdateFenceResponse;
import com.baidu.trace.model.CoordType;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseApplication;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.FrameDto;
import com.saimawzc.freight.dto.SearchValueDto;
import com.saimawzc.freight.dto.WaybillEncloDto;
import com.saimawzc.freight.dto.my.PersonCenterDto;
import com.saimawzc.freight.dto.my.carrier.MyCarrierDto;
import com.saimawzc.freight.dto.my.lessess.MyLessessDto;
import com.saimawzc.freight.dto.sendcar.DriverTransDto;
import com.saimawzc.freight.presenter.MainPersonter;
import com.saimawzc.freight.ui.login.LoginActivity;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.ui.tab.driver.DriverMainFragment;
import com.saimawzc.freight.ui.tab.driver.DriverMineFragment;
import com.saimawzc.freight.ui.tab.driver.DriverSendCarIndexFragment;
import com.saimawzc.freight.ui.tab.driver.DriverWaybillIndexFragment;
import com.saimawzc.freight.view.DriverMainView;
import com.saimawzc.freight.weight.BottomDialogUtil;
import com.saimawzc.freight.weight.OnTopCoutomPosCallback;
import com.saimawzc.freight.weight.overtimer.OverallTimer;
import com.saimawzc.freight.weight.utils.app.AppManager;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.QueueDialog;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.keeplive.jobhander.JobHandlerService;
import com.saimawzc.freight.weight.utils.keeplive.onepixe.ScreenService;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import com.saimawzc.freight.weight.utils.serves.SingASongService;
import com.saimawzc.freight.weight.utils.trace.TraceUtils;
import com.saimawzc.freight.weight.utils.update.InstallUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jpeng.jptabbar.BadgeDismissListener;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.saimawzc.freight.weight.waterpic.ImageUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import zhy.com.highlight.HighLight;
import zhy.com.highlight.interfaces.HighLightInterface;
import zhy.com.highlight.shape.RectLightShape;

import static com.baidu.navisdk.ui.util.TipTool.toast;
import static com.saimawzc.freight.ui.sendcar.driver.DriverTransportFragment.convertMap2Trace;

/*****
 *??????
 * **/
public class DriverMainActivity extends BaseActivity
        implements OnTabSelectListener, BadgeDismissListener,
        DriverMainView {

    private BaseApplication trackApp = null;
    @Titles
    private static final int[] mTitles = {R.string.tab1, R.string.tab2, R.string.tab3, R.string.tab4};
    @SeleIcons
    private static final int[] mSeleIcons = {R.drawable.ico_mainindex_blue, R.drawable.ico_yundan_blue, R.drawable.ico_paicheblue, R.drawable.ico_main_blue};
    @NorIcons
    private static final int[] mNormalIcons = {R.drawable.ico_mainindex_gray, R.drawable.ico_yundan_gray, R.drawable.ico_paiche_gray, R.drawable.ico_mine_gray};
    @BindView(R.id.tabbar)
    JPTabBar mTabbar;
    private Fragment[] fragments;
    private DriverMainFragment mainIndexFragment;
    private DriverWaybillIndexFragment waybillFragment;
    private DriverSendCarIndexFragment findFragment;
    private DriverMineFragment mineFragment;
    private MainPersonter personter;
    private HighLight mHightLight;
    private LocationClient mClient;
    private MyLocationListener myLocationListener;
    private BDLocation currenrtLocation;
    private NormalDialog normalDialog;
    //@BindView(R.id.tvtwxt)TextView tvWlInfo;
    // @BindView(R.id.tvwlin)TextView tvWliN;

    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void init() {
        mContext = this;
        checkLocation();
        trackApp = (BaseApplication) mContext.getApplicationContext();
        userInfoDto = getUserInfoDto(userInfoDto);
        if (!isLogin()) {
            readyGo(LoginActivity.class);
        }
        personter = new MainPersonter(this, mContext);
        initpermissionChecker();
        mainIndexFragment = new DriverMainFragment();
        waybillFragment = new DriverWaybillIndexFragment();
        findFragment = new DriverSendCarIndexFragment();
        mineFragment = new DriverMineFragment();
        mTabbar.setTabListener(this);
        fragments = new Fragment[]{mainIndexFragment, waybillFragment, findFragment, mineFragment};
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, mainIndexFragment)
                .show(mainIndexFragment)
                .commit();
        mTabbar.setTabListener(this);
        //??????Badge???????????????
        mTabbar.setDismissListener(this);
        initAccessToken();//?????????b????????????????????????
        initLocation(0);
        setNeedOnBus(true);
        try {
            getPersonterData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Hawk.get(PreferenceKey.CITY_INFO) == null) {
            cacheArae();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                initWithApiKey();
            }
        }).start();
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File file = new File(ImageUtil.getSystemPhotoPath(DriverMainActivity.this));
                    personter.deleteFile(file);
                }
            }).start();

        } catch (Exception e) {
        }
        if (TextUtils.isEmpty(Hawk.get(PreferenceKey.TIpMain, ""))) {
            showNextTipViewOnCreated();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                personter.getYdInfo();
                personter.getFram();
            }
        });
        saveLife();
    }

    @Override
    protected void initListener() {

    }

    private void checkLocation() {
        if (XXPermissions.isGranted(context, PERMISSIONSS_LOCATION)) {
            Log.e("MainActivity", "??????");
        } else {
            Log.d("MainActivity", "?????????");
        }
        XXPermissions.with(this)
                // ??????????????????
                .permission(PERMISSIONSS_LOCATION)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        toast("??????????????????");
                        if (normalDialog!=null) {
                            normalDialog.dismiss();
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        toast("????????????????????????????????????????????????");
                        // ??????????????????????????????????????????????????????????????????
                        initDiaLog();
                    }
                });

    }

    private boolean isBreak = false;

    @Override
    public void onBackPressed() {
        if (isBreak) {
            AppManager.get().finishAllActivity();
        } else {
            isBreak = true;
            showMessage("????????????????????????!");
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
        if (requestCode == 100) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (Settings.canDrawOverlays(this)) {
                    showMessage("?????????");
                } else {
                    showMessage("??????????????????");
                }
            }
        }
        if (requestCode == XXPermissions.REQUEST_CODE) {
            if (XXPermissions.isGranted(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
                    XXPermissions.isGranted(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                toast("????????????????????????????????????????????????");
                if (normalDialog!=null) {
                    normalDialog.dismiss();
                }
            } else {
                initDiaLog();


            }
        }
    }

    private void initDiaLog() {
        normalDialog = new NormalDialog(mContext).isTitleShow(false)

                .content("??????????????????????????????????????????????????????")
                .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                .btnNum(1).btnText("??????");
        normalDialog.setCanceledOnTouchOutside(false);
        normalDialog.setOnBtnClickL(
                new OnBtnClickL() {//??????
                    @Override
                    public void onBtnClick() {
                        XXPermissions.startPermissionActivity(context, PERMISSIONSS_LOCATION);
                    }
                });
        normalDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindForApp();
        if (dialog != null) {
            if (!isDestroy(this)) {
                dialog.dismiss();
            }
        }
    }

    int currentTabIndex = 0;

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
    public void onDismiss(int position) {
    }

    /**
     * ?????????????????????????????????
     ***/
    int ishowBigg = 0;
    private NormalDialog dialog;

    @Override
    protected void onResume() {
        super.onResume();
        ishowBigg = 0;
        if (downloadCallBack != null) {
            if (InstallUtils.isDownloading()) {
                InstallUtils.setDownloadCallBack(downloadCallBack);
            }
        }
        if (personter != null) {
            personter.getCarriveList();
            personter.getLessess();
        }
        if (!TextUtils.isEmpty(Hawk.get(PreferenceKey.isChange_or_login, "")) &&
                (Hawk.get(PreferenceKey.isChange_or_login, "").equals("true"))) {
            EventBus.getDefault().post(Constants.reshChange);
            Hawk.put(PreferenceKey.isChange_or_login, "false");
        }
        try {
            String tz = getIntent().getStringExtra("tztype");
            if (!TextUtils.isEmpty(tz)) {
                currentTabIndex = 2;
                mTabbar.setSelectTab(2);
            }
        } catch (Exception r) {

        }
        if (trackApp != null) {
            if (trackApp.mClient == null) {
                initTrack(trackApp);
            }
        }
    }

    private void getPersonterData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mineApi.getPersoneCener().enqueue(new CallBack<PersonCenterDto>() {
                    @Override
                    public void success(final PersonCenterDto response) {
                        Hawk.put(PreferenceKey.PERSON_CENTER, response);
                        if (response.getAuthState() == 0) {
                            if (dialog == null) {//?????????
                                dialog = new NormalDialog(mContext).isTitleShow(false)
                                        .content("???????????????????????????????????????")
                                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                                        .btnNum(2).btnText("??????", "??????");
                            }
                            dialog.setOnBtnClickL(
                                    new OnBtnClickL() {//??????
                                        @Override
                                        public void onBtnClick() {
                                            Bundle bundle = null;
                                            if (response.getRoleType() == 2) {//???????????????
                                                bundle = new Bundle();
                                                bundle.putString("from", "useridentification");
                                            } else if (response.getRoleType() == 3) {//????????????
                                                bundle = new Bundle();
                                                bundle.putString("from", "sijicarriver");
                                                readyGo(PersonCenterActivity.class, bundle);
                                            }
                                            readyGo(PersonCenterActivity.class, bundle);
                                            if (!isDestroy(DriverMainActivity.this)) {
                                                dialog.dismiss();
                                            }
                                        }
                                    },
                                    new OnBtnClickL() {//??????
                                        @Override
                                        public void onBtnClick() {
                                            if (!isDestroy(DriverMainActivity.this)) {
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                            dialog.show();
                        } else {
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
        if (carrierDtos == null || carrierDtos.size() <= 0) {
            ishowBigg++;
            if (ishowBigg >= 2) {
                mTabbar.HideBadge(3);
            }
        } else {
            mTabbar.ShowBadge(3, "");
        }
    }

    @Override
    public void getmylessee(List<MyLessessDto> lessessDtos) {
        if (lessessDtos == null || lessessDtos.size() <= 0) {
            ishowBigg++;
            if (ishowBigg >= 2) {
                mTabbar.HideBadge(3);
            }
        } else {
            mTabbar.ShowBadge(3, "");
        }
    }

    @Override
    public void getDialog(FrameDto dto) {
        if (dto != null) {
            if (!TextUtils.isEmpty(dto.getContent())) {
                showDialog(dto.getContent());
            }
        }
    }

    private WaybillEncloDto fenceDto;

    /**
     * ??????????????????
     **/
    @Override
    public void getYdInfo(WaybillEncloDto dto) {
        stopClent();
        if (dto == null) {
            return;
        }
        if (getUserInfoDto(userInfoDto) == null) {
            return;
        }
        if (trackApp == null) {
            return;
        }
        if (trackApp.mClient == null) {
            return;
        }
        Hawk.put(PreferenceKey.AUTO_SIGN_DTO, null);
        fenceDto = dto;
        Hawk.put(PreferenceKey.AUTO_SIGN_DTO, dto);
        String monitoredPerson = getUserInfoDto(userInfoDto).getRoleId();//??????ID
        FenceListRequest fenceListRequest = FenceListRequest.buildServerRequest(trackApp.getTag(), trackApp.serviceId,
                monitoredPerson, null, CoordType.bd09ll, 0, 0);
        trackApp.mClient.queryFenceList(fenceListRequest, mFenceListener);
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
        if (TextUtils.isEmpty(PreferenceKey.DRIVER_IS_INDENFICATION) || !Hawk.get(PreferenceKey.DRIVER_IS_INDENFICATION, "").equals("1")) {
            if (!str.contains("??????")) {
                context.showMessage(str);
            }
        } else {
            context.showMessage(str);
        }
    }

    @Override
    public void oncomplete() {

    }

    public void showNextTipViewOnCreated() {
        mHightLight = new HighLight(DriverMainActivity.this)//
                .autoRemove(false)
                .enableNext()
                .setOnLayoutCallback(new HighLightInterface.OnLayoutCallback() {
                    @Override
                    public void onLayouted() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mHightLight != null) {
                                    //????????????????????????tipview
                                    mHightLight.addHighLight(mTabbar.getItemAtIndex(3), R.layout.info_gravity_left_down, new OnTopCoutomPosCallback(280), new RectLightShape());
                                    //????????????????????????
                                    try {
                                        mHightLight.show();
                                    } catch (Exception e) {
                                    }
                                    Hawk.put(PreferenceKey.TIpMain, "show");
                                }
                            }
                        }, 2000);
                    }
                })
                .setClickCallback(new HighLight.OnClickCallback() {
                    @Override
                    public void onClick() {
                        if (mHightLight != null) {
                            try {
                                mHightLight.next();
                            } catch (Exception e) {
                            }
                        }
                    }
                });
    }

    private void showDialog(String contect) {
        final BottomDialogUtil bottomDialogUtil = new BottomDialogUtil.Builder()
                .setContext(context) //?????? context
                .setContentView(R.layout.dialog_sijitip) //??????????????????
                .setOutSideCancel(false) //????????????????????????
                .builder()
                .show();
        TextView tvOrder = (TextView) bottomDialogUtil.getItemView(R.id.tvOrder);
        TextView tvcontect = (TextView) bottomDialogUtil.getItemView(R.id.tvcontect);
        tvcontect.setText(contect);
        tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialogUtil.dismiss();
            }
        });
    }


    // ????????????????????????
    OnFenceListener mFenceListener = new OnFenceListener() {
        // ??????????????????
        @Override
        public void onCreateFenceCallback(CreateFenceResponse response) {
            if (response == null) {
                return;
            }
            Log.e("msg", response.toString());
            startClent();
        }

        // ??????????????????
        @Override
        public void onUpdateFenceCallback(UpdateFenceResponse response) {
        }

        // ??????????????????
        @Override
        public void onDeleteFenceCallback(DeleteFenceResponse response) {
            if (response == null) {
                startFence();
                return;
            }
            startFence();
        }

        // ??????????????????
        @Override
        public void onFenceListCallback(FenceListResponse response) {
            if (response == null || response.getFenceInfos() == null) {
                startFence();
                return;
            }
            if (trackApp == null) {
                startFence();
                return;
            }
            if (trackApp.mClient == null) {
                startFence();
                return;
            }
            if (response.getSize() >= 0) {
                if (FenceType.server == response.getFenceType()) {
                    List<Long> deleteFenceIds = new ArrayList<>();
                    if (response.getFenceInfos().size() <= 0) {
                        startFence();
                        return;
                    }
                    List<FenceInfo> infos = response.getFenceInfos();
                    for (int i = 0; i < infos.size(); i++) {
                        if (infos.get(i).getFenceShape() == FenceShape.circle) {
                            CircleFence circleFence = infos.get(i).getCircleFence();
                            if (circleFence.getFenceName().contains("yd")
                                    || circleFence.getFenceName().contains("gw")) {
                                deleteFenceIds.add(circleFence.getFenceId());
                            }
                        } else if (infos.get(i).getFenceShape() == FenceShape.polygon) {
                            PolygonFence polygonFence = infos.get(i).getPolygonFence();
                            if (polygonFence.getFenceName().contains("yd")
                                    || polygonFence.getFenceName().contains("gw")) {
                                deleteFenceIds.add(polygonFence.getFenceId());
                            }
                        } else if (infos.get(i).getFenceShape() == FenceShape.district) {
                            DistrictFence districtFence = infos.get(i).getDistrictFence();
                            if (districtFence.getFenceName().contains("yd")
                                    || districtFence.getFenceName().contains("gw")) {
                                deleteFenceIds.add(districtFence.getFenceId());
                            }
                        }
                    }

                    DeleteFenceRequest deleteRequest = DeleteFenceRequest.buildServerRequest
                            (trackApp.getTag(), trackApp.serviceId, trackApp.entityName, deleteFenceIds);
                    //????????????????????????
                    trackApp.mClient.deleteFence(deleteRequest, mFenceListener);
                } else {
                    startFence();
                }
            } else {
                startFence();

            }
        }

        // ??????????????????
        @Override
        public void onMonitoredStatusCallback(MonitoredStatusResponse response) {
        }

        // ??????????????????????????????
        @Override
        public void onMonitoredStatusByLocationCallback(MonitoredStatusByLocationResponse
                                                                response) {
        }

        // ??????????????????
        @Override
        public void onHistoryAlarmCallback(HistoryAlarmResponse response) {
        }

        @Override
        public void onAddMonitoredPersonCallback(AddMonitoredPersonResponse addMonitoredPersonResponse) {
        }

        @Override
        public void onDeleteMonitoredPersonCallback(DeleteMonitoredPersonResponse deleteMonitoredPersonResponse) {

            if (deleteMonitoredPersonResponse == null) {
                return;
            }
        }

        @Override
        public void onListMonitoredPersonCallback(ListMonitoredPersonResponse listMonitoredPersonResponse) {
        }
    };

    private List<String> alreadyCreatFence = new ArrayList<>();

    private void startFence() {
        if (fenceDto == null) {
            return;
        }
        if (getUserInfoDto(userInfoDto) == null) {
            return;
        }
        if (trackApp == null) {
            return;
        }
        if (trackApp.mClient == null) {
            return;
        }
        int craetType;
        int tag = 0;
        // ????????????ID
        long serviceId = trackApp.serviceId;
        // ????????????
        CoordType coordType = CoordType.bd09ll;
        // ????????????????????????????????????
        CreateFenceRequest request = null;
        String fenceName;// ????????????
        double radius = 0;
        int denoise = 0;
        int enclosureType = 0;
        String monitoredPerson = getUserInfoDto(userInfoDto).getRoleId();
        List<com.baidu.trace.model.LatLng> traceVertexes = new ArrayList<>();
        List<WaybillEncloDto.yundanData> yundanData;
        List<WaybillEncloDto.highEnclosureRes> highEnclosureResList = fenceDto.getHighEnclosureRes();
        if (fenceDto.getStartData() == null || fenceDto.getStartData().size() == 0) {//??????????????????????????????
            yundanData = fenceDto.getUnStartData();
            craetType = 1;
        } else {//???????????????????????????
            if (fenceDto.getStartData() == null || fenceDto.getStartData().size() == 0) {
                return;
            }
            yundanData = fenceDto.getStartData();
            craetType = 2;
        }
        if (highEnclosureResList != null && highEnclosureResList.size() > 0) {//??????????????????
            startClent();
            for (int i = 0; i < highEnclosureResList.size(); i++) {
                tag = 10;
                fenceName = highEnclosureResList.get(i).getId() + "gw" + highEnclosureResList.get(i).getWaybillId();
                String locol = highEnclosureResList.get(i).getLocation();
                if (!TextUtils.isEmpty(locol)) {
                    String[] node = locol.split(";");
                    for (int j = 0; j < node.length; j++) {
                        String[] lang = node[j].split(",");
                        LatLng center = new LatLng(Double.parseDouble(lang[1]), Double.parseDouble(lang[0]));
                        if (convertMap2Trace(center) != null) {
                            traceVertexes.add(convertMap2Trace(center));
                        }
                    }
                    request = CreateFenceRequest.buildServerPolygonRequest(tag, trackApp.serviceId, fenceName
                            , monitoredPerson, traceVertexes, denoise, coordType);
                    trackApp.mClient.createFence(request, mFenceListener);
                }
            }
        }
        if (yundanData == null || yundanData.size() <= 0) {
            return;
        }
        alreadyCreatFence.clear();
        int fenceNum = 0;
        Hawk.put(PreferenceKey.AUTO_SIGN_WL_NUM, fenceNum);
        startClent();
        for (int i = 0; i < yundanData.size(); i++) {
            if (yundanData.get(i) != null) {
                List<WaybillEncloDto.wayBillId> tempDto = yundanData.get(i).getWayBillId();
                if (tempDto != null && tempDto.size() > 0) {
                    // ????????????????????? : ??????
                    if (craetType == 1) {//?????????
                        radius = tempDto.get(0).getFromRadius();
                        // ????????????
                        denoise = tempDto.get(0).getFromErrorRange();
                        enclosureType = tempDto.get(0).getFromEnclosureType();
                    } else if (craetType == 2) {//?????????
                        radius = tempDto.get(0).getToRadius();
                        // ????????????
                        denoise = tempDto.get(0).getToErrorRange();
                        enclosureType = tempDto.get(0).getToEnclosureType();
                    }
                    fenceName = tempDto.get(0).getId() + "yd" + yundanData.get(i).getId();
                    if (enclosureType == 1) {//??????
                        //????????????
                        tag = 7;
                        String locol = "";
                        String adress = "";
                        if (craetType == 1) {
                            locol = tempDto.get(0).getFromLocation();
                            adress = tempDto.get(0).getFromUserAddress();
                        } else {
                            locol = tempDto.get(0).getToLocation();
                            adress = tempDto.get(0).getToUserAddress();
                        }
                        String[] node = locol.split(",");
                        LatLng center;
                        if (Double.parseDouble(node[1]) < Double.parseDouble(node[0])) {
                            center = new LatLng(Double.parseDouble(node[1]), Double.parseDouble(node[0]));
                        } else {
                            center = new LatLng(Double.parseDouble(node[0]), Double.parseDouble(node[1]));
                        }
                        if (convertMap2Trace(center) != null) {
                            request = CreateFenceRequest.buildServerCircleRequest(tag, trackApp.serviceId, fenceName,
                                    monitoredPerson, convertMap2Trace(center), radius, denoise,
                                    coordType);
                            // ????????????????????????
                            if (!alreadyCreatFence.contains(adress)) {
                                trackApp.mClient.createFence(request, mFenceListener);
                                alreadyCreatFence.add(adress);
                                fenceNum++;
                            }
                        }
                        // ???????????????????????????
                    } else if (enclosureType == 2) {//????????????
                        tag = 8;
                        String locol = "";
                        String adress = "";
                        if (craetType == 1) {
                            locol = tempDto.get(0).getFromRegion();
                            adress = tempDto.get(0).getFromUserAddress();
                        } else {
                            locol = tempDto.get(0).getToRegion();
                            adress = tempDto.get(0).getToUserAddress();
                        }
                        request = CreateFenceRequest.buildServerDistrictRequest(tag,
                                serviceId, fenceName, monitoredPerson, locol, denoise);
                        if (!alreadyCreatFence.contains(adress)) {
                            trackApp.mClient.createFence(request, mFenceListener);
                            alreadyCreatFence.add(adress);
                            fenceNum++;
                        }
                    } else if (enclosureType == 3) {//3?????????
                        tag = 9;
                        String locol = "";
                        String adress = "";
                        if (craetType == 1) {
                            locol = tempDto.get(0).getFromRegion();
                            adress = tempDto.get(0).getFromUserAddress();
                        } else {
                            locol = tempDto.get(0).getToRegion();
                            adress = tempDto.get(0).getToUserAddress();
                        }
                        String[] node = locol.split(";");
                        for (int j = 0; j < node.length; j++) {
                            String[] lang = node[j].split(",");
                            LatLng center = new LatLng(Double.parseDouble(lang[1]), Double.parseDouble(lang[0]));
                            if (convertMap2Trace(center) != null) {
                                traceVertexes.add(convertMap2Trace(center));
                            }
                        }
                        request = CreateFenceRequest.buildServerPolygonRequest(tag, trackApp.serviceId, fenceName
                                , monitoredPerson, traceVertexes, denoise, coordType);
                        if (!alreadyCreatFence.contains(adress)) {
                            trackApp.mClient.createFence(request, mFenceListener);
                            alreadyCreatFence.add(adress);
                            fenceNum++;
                        }
                    }
                    if (i >= yundanData.size() - 1) {
                        Hawk.put(PreferenceKey.AUTO_SIGN_WL_NUM, fenceNum);
                    }
                }
            }
        }
    }

    /**
     * ?????????????????????
     */
    private void initLocation(int type) {
        // ?????????????????????
        mClient = new LocationClient(getApplicationContext());
        myLocationListener = new MyLocationListener();
        // ??????????????????
        mClient.registerLocationListener(myLocationListener);
        LocationClientOption mOption = new LocationClientOption();
        mOption.setIsNeedAddress(true);
        //?????????????????????????????????????????????????????????????????????false
        //?????????????????????????????????????????????????????????????????????true
        mOption.setNeedNewVersionRgc(true);
        // ???????????????0?????????????????????????????????????????????????????????????????????????????????1000ms???????????????
        mOption.setScanSpan(1000 * 60 * 3);
        // ???????????????gcj02??????????????????????????????????????????????????????????????????????????????????????????bd09ll;
        mOption.setCoorType("bd09ll");
        // ???????????????false?????????????????????Gps??????
        mOption.setOpenGps(true);
        // ??????????????????
        mClient.setLocOption(mOption);
        if (type == 1) {
            mClient.start();
        }
    }

    List<SearchValueDto> searchValueDtos = new ArrayList<>();

    /**
     * ??????????????????
     */
    class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null || bdLocation.getLocType() == 62) {
                return;
            }
            currenrtLocation = bdLocation;
            if (getUserInfoDto(userInfoDto) == null) {
                return;
            }
            if (trackApp == null) {
                return;
            }
            if (trackApp.mClient == null) {
                return;
            }
            Log.e("msg", "??????????????????????????????");
            FenceListRequest fenceListRequest =
                    FenceListRequest.buildServerRequest(trackApp.getTag(), trackApp.serviceId,
                            getUserInfoDto(userInfoDto).getRoleId(), null, CoordType.bd09ll, 0, 0);
            trackApp.mClient.queryFenceList(fenceListRequest, mFenceListener1);
        }
    }

    // ????????????????????????
    OnFenceListener mFenceListener1 = new OnFenceListener() {
        // ??????????????????
        @Override
        public void onCreateFenceCallback(CreateFenceResponse response) {
        }

        // ??????????????????
        @Override
        public void onUpdateFenceCallback(UpdateFenceResponse response) {
        }

        // ??????????????????
        @Override
        public void onDeleteFenceCallback(DeleteFenceResponse response) {
        }

        // ??????????????????
        @Override
        public void onFenceListCallback(FenceListResponse response) {
            if (response == null || response.getFenceInfos() == null) {
                return;
            }
            if (trackApp == null) {
                return;
            }
            if (trackApp.mClient == null) {
                return;
            }
            if (response.getSize() > 0) {
                if (FenceType.server == response.getFenceType()) {
                    List<Long> deleteFenceIds = new ArrayList<>();
                    if (response.getFenceInfos().size() <= 0) {
                        return;
                    }
                    List<FenceInfo> wlinfos = response.getFenceInfos();
                    if (wlinfos == null || wlinfos.size() <= 0) {
                        return;
                    }
                    if (searchValueDtos != null) {
                        searchValueDtos.clear();
                    }
                    //String wlStr="";
                    for (int i = 0; i < wlinfos.size(); i++) {
                        if (wlinfos.get(i).getFenceShape() == FenceShape.circle) {
                            CircleFence circleFence = wlinfos.get(i).getCircleFence();
                            if (circleFence.getFenceName().contains("yd") || circleFence.getFenceName().contains("gw")) {
                                deleteFenceIds.add(circleFence.getFenceId());
                                searchValueDtos.add(new SearchValueDto(circleFence.getFenceId() + "", circleFence.getFenceName()));
                                //   wlStr+="????????????"+"??????ID???"+circleFence.getFenceId()+"??????fenceName"+circleFence.getFenceName();
                            }
                        } else if (wlinfos.get(i).getFenceShape() == FenceShape.polygon) {
                            PolygonFence polygonFence = wlinfos.get(i).getPolygonFence();
                            if (polygonFence.getFenceName().contains("yd")
                                    || polygonFence.getFenceName().contains("gw")) {
                                deleteFenceIds.add(polygonFence.getFenceId());
                                searchValueDtos.add(new SearchValueDto(polygonFence.getFenceId() + "", polygonFence.getFenceName()));
                                //wlStr+="???????????????"+"??????ID???"+polygonFence.getFenceId()+"??????fenceName"+polygonFence.getFenceName();
                            }
                        } else if (wlinfos.get(i).getFenceShape() == FenceShape.district) {
                            DistrictFence districtFence = wlinfos.get(i).getDistrictFence();
                            if (districtFence.getFenceName().contains("yd") || districtFence.getFenceName().contains("gw")) {
                                deleteFenceIds.add(districtFence.getFenceId());
                                searchValueDtos.add(new SearchValueDto(districtFence.getFenceId() + "", districtFence.getFenceName()));
                                //wlStr+="????????????"+"??????ID???"+districtFence.getFenceId()+"??????fenceName"+districtFence.getFenceName();
                            }
                        }
                    }
                    //tvWlInfo.setText(wlStr);
                    if (deleteFenceIds.size() > 0) {
                        if (currenrtLocation == null) {
                            return;
                        }
                        if (getUserInfoDto(userInfoDto) == null) {
                            return;
                        }
                        com.baidu.trace.model.LatLng latLng = new com.baidu.trace.model.LatLng(currenrtLocation.getLatitude(), currenrtLocation.getLongitude());
                        MonitoredStatusByLocationRequest request = MonitoredStatusByLocationRequest.buildServerRequest
                                (trackApp.getTag(), trackApp.serviceId, getUserInfoDto(userInfoDto).getRoleId(), deleteFenceIds, latLng, CoordType.bd09ll);
                        trackApp.mClient.queryMonitoredStatusByLocation(request, mFenceListener1);
                    }
                }
            }
        }

        // ??????????????????
        @Override
        public void onMonitoredStatusCallback(MonitoredStatusResponse
                                                      response) {
        }

        // ??????????????????????????????
        @Override
        public void onMonitoredStatusByLocationCallback(MonitoredStatusByLocationResponse
                                                                response) {
            if (response == null || response.getMonitoredStatusInfos() == null) {
                return;
            }
            if (getUserInfoDto(userInfoDto) == null) {
                return;
            }
            if (getUserInfoDto(userInfoDto).getRole() != 3) {
                return;
            }
            if (response.getMonitoredStatusInfos().size() <= 0) {
                return;
            }
            //????????????????????????????????????
            List<MonitoredStatusInfo> monitoredStatusInfos = response.getMonitoredStatusInfos();
            if (monitoredStatusInfos == null || monitoredStatusInfos.size() <= 0) {
                return;
            }
            int temp = 0;
            String id = "";
            int bianlinum = 0;
            //String wlStr="";
            for (MonitoredStatusInfo monitoredStatusInfo : monitoredStatusInfos) {
                MonitoredStatus status = monitoredStatusInfo.getMonitoredStatus();//????????????
//                showMessage("????????????????????????"+status);
                bianlinum++;
                Log.e("status", status + "");
                Log.e("status", MonitoredStatus.in + "");
                // wlStr+=monitoredStatusInfo.getFenceId()+"?????????"+status+",";
                if (status == MonitoredStatus.in) {
                    if (searchValueDtos.size() > 0) {
                        for (int i = 0; i < searchValueDtos.size(); i++) {
                            if (searchValueDtos.get(i).getSearchName().
                                    equals(monitoredStatusInfo.getFenceId() + "")) {
                                if (searchValueDtos.get(i).getGetSearchValue().contains("gw")) {
                                    dealGw(searchValueDtos.get(i).getGetSearchValue());
                                } else {
                                    if (temp == 0) {//?????????????????????
                                        id = searchValueDtos.get(i).getGetSearchValue();
                                    }
                                    temp++;
                                }
                            }
                        }
                    }
                }
                if (bianlinum >= monitoredStatusInfos.size()) {
                    if (temp > 0) {
                        deal(temp, id);
                        temp = 0;
                    }
                    //tvWliN.setText(wlStr);
                }
            }
        }

        // ??????????????????
        @Override
        public void onHistoryAlarmCallback(HistoryAlarmResponse response) {
        }

        @Override
        public void onAddMonitoredPersonCallback(AddMonitoredPersonResponse addMonitoredPersonResponse) {
        }

        @Override
        public void onDeleteMonitoredPersonCallback(DeleteMonitoredPersonResponse deleteMonitoredPersonResponse) {
        }

        @Override
        public void onListMonitoredPersonCallback(ListMonitoredPersonResponse listMonitoredPersonResponse) {
        }
    };
    TraceUtils utils;
    private List<String> alreadFence = new ArrayList<>();

    private void deal(final int count, final String id) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (alreadFence != null) {
                    alreadFence.clear();
                }
                WaybillEncloDto autoSignDto = Hawk.get(PreferenceKey.AUTO_SIGN_DTO);
                if (autoSignDto != null) {
                    if (autoSignDto.getStartData() != null
                            && autoSignDto.getStartData().size() > 0) {//???????????????????????????
                        WaybillEncloDto.yundanData tempYdDto = autoSignDto.getStartData().get(0);
                        if (tempYdDto == null || tempYdDto.getWayBillId().size() == 0) {
                            return;
                        }

                        WaybillEncloDto.wayBillId wayBillDto = tempYdDto.getWayBillId().get(0);
                        if (wayBillDto == null) {
                            return;
                        }
                        if (wayBillDto.getSjSignIn() == 1) {
                            if (TextUtils.isEmpty(wayBillDto.getFromLocation())
                                    || !wayBillDto.getFromLocation().contains(",")) {
                                return;
                            }
                            String[] langstart = wayBillDto.getFromLocation().split(",");
                            LatLng lastr = new LatLng(Double.parseDouble(langstart[1]), Double.parseDouble(langstart[0]));
                            LatLng curr = new LatLng(currenrtLocation.getLatitude(), currenrtLocation.getLongitude());
                            double dis1 = DistanceUtil.getDistance(lastr, curr);
                            if (dis1 < 100) {
                                return;
                            }
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("id", wayBillDto.getId());//??????ID
                                if (currenrtLocation != null) {
                                    jsonObject.put("location", currenrtLocation.getLongitude() + "," + currenrtLocation.getLatitude());
                                    jsonObject.put("address", currenrtLocation.getAddrStr());
                                    if (!TextUtils.isEmpty(wayBillDto.getToLocation())) {
                                        String[] lang = wayBillDto.getToLocation().split(",");
                                        if (lang.length > 1) {
                                            LatLng toLang = new LatLng(Double.parseDouble(lang[1]), Double.parseDouble(lang[0]));
                                            LatLng locationLang = new LatLng(currenrtLocation.getLatitude(), currenrtLocation.getLongitude());
                                            double distance = DistanceUtil.getDistance(toLang, locationLang);
                                            jsonObject.put("distance", distance + "");
                                        } else {
                                            jsonObject.put("distance", "");
                                        }
                                    } else {
                                        jsonObject.put("distance", "");
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                            tmsApi.autoArriver(body).enqueue(new CallBack<EmptyDto>() {
                                @Override
                                public void success(EmptyDto response) {
                                    personter.getYdInfo();
                                    if (utils == null) {
                                        utils = new TraceUtils((BaseApplication) getApplicationContext(), context);
                                    }
                                    if (utils != null) {
                                        utils.startSercive();
                                        utils.startRealTimeLoc(30);
                                        OverallTimer.getInstance().startTimer();
                                    }
                                    startClent();
                                }

                                @Override
                                public void fail(String errorCode, String message) {
                                    startClent();
                                }
                            });
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putString("id", wayBillDto.getId());
                            bundle.putString("waybillid", wayBillDto.getWayBillId());
                            bundle.putString("code", 7 + "");
                            if (currenrtLocation != null) {
                                bundle.putString("location", currenrtLocation.getLongitude() + "," + currenrtLocation.getLatitude());
                                bundle.putString("toadress", wayBillDto.getToLocation());//?????????????????????
                            }
                            bundle.putString("city", currenrtLocation.getCity());
                            bundle.putString("country", currenrtLocation.getDistrict());
                            bundle.putString("adress", currenrtLocation.getAddrStr());
                            DriverTransDto driverTransDto = new DriverTransDto();
                            driverTransDto.setToAddressType(2);//????????????
                            driverTransDto.setToLocation(wayBillDto.getToLocation());
                            driverTransDto.setToEnclosureType(wayBillDto.getToEnclosureType());
                            driverTransDto.setToRadius(wayBillDto.getToRadius());
                            driverTransDto.setToRegion(wayBillDto.getToRegion());
                            bundle.putSerializable("data", driverTransDto);
                            bundle.putString("tranttype", 1 + "");
                            bundle.putString("from", "arriveorder");
                            readyGo(OrderMainActivity.class, bundle);
                        }
                        stopClent();
                    } else if (autoSignDto.getUnStartData() != null
                            && autoSignDto.getUnStartData().size() > 0) {//???????????????????????????
                        if (count > 1) {
                            showMessage("?????????????????????????????????????????????");
                            Bundle bundle = new Bundle();
                            bundle.putString("tztype", "waitzx");
                            readyGo(DriverMainActivity.class, bundle);
                        } else {//??????
                            if (TextUtils.isEmpty(id)) {
                                return;
                            }
                            if (!id.contains("yd")) {
                                return;
                            }
                            String res = id.replaceAll("yd", ",");
                            if (TextUtils.isEmpty(res)) {
                                return;
                            }
                            final String[] node = res.split(",");
                            if (node == null || node.length < 2) {
                                return;
                            }
                            String adress = "";
                            if (autoSignDto.getUnStartData() != null) {
                                for (int i = 0; i < autoSignDto.getUnStartData().size(); i++) {
                                    List<WaybillEncloDto.wayBillId> tempDto =
                                            autoSignDto.getUnStartData().get(i).getWayBillId();
                                    if (autoSignDto.getUnStartData().get(i).getId().equals(node[1])) {
                                        if (tempDto != null) {
                                            adress = tempDto.get(0).getFromUserAddress();
                                        }
                                    }
                                }
                            }
                            if (!TextUtils.isEmpty(adress)) {
                                if (autoSignDto.getUnStartData() != null) {
                                    for (int i = 0; i < autoSignDto.getUnStartData().size(); i++) {
                                        List<WaybillEncloDto.wayBillId> tempDto =
                                                autoSignDto.getUnStartData().get(i).getWayBillId();
                                        if (tempDto != null) {
                                            if (tempDto.get(0).getFromUserAddress().equals(adress)) {
                                                alreadFence.add(tempDto.get(0).getFromUserAddress());
                                            }
                                        }
                                    }
                                }
                            }
                            if (alreadFence.size() > 1) {
                                showMessage("?????????????????????????????????????????????");
                                Log.e("msg", "????????????????????????????????????????????????");
                                Bundle bundle = new Bundle();
                                bundle.putString("tztype", "waitzx");
                                readyGo(DriverMainActivity.class, bundle);
                                return;
                            }
                            JSONObject jsonObject = new JSONObject();
                            try {
                                if (node != null && !TextUtils.isEmpty(node[0])) {
                                    jsonObject.put("id", node[0]);//??????ID
                                }
                                jsonObject.put("operationType", 1);
                                jsonObject.put("type", 4);
                                if (currenrtLocation != null) {
                                    jsonObject.put("location", currenrtLocation.getLongitude() + "," + currenrtLocation.getLatitude());
                                    jsonObject.put("address", currenrtLocation.getAddrStr());
                                }
                                jsonObject.put("picture", "");
                                jsonObject.put("eqType", "1");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                            tmsApi.trantDaka(body).enqueue(new CallBack<EmptyDto>() {
                                @Override
                                public void success(EmptyDto response) {
                                    Bundle bundle = new Bundle();
                                    if (node != null && !TextUtils.isEmpty(node[1])) {
                                        bundle.putString("id", node[1]);
                                    }
                                    bundle.putString("tranttype", 1 + "");
                                    bundle.putString("from", "trant");
                                    readyGo(OrderMainActivity.class, bundle);
                                    if (utils == null) {
                                        utils = new TraceUtils((BaseApplication) getApplicationContext(), context);
                                    }
                                    if (utils != null) {
                                        utils.startSercive();
                                        utils.startRealTimeLoc(30);
                                        OverallTimer.getInstance().startTimer();
                                    }
                                    Hawk.put(PreferenceKey.AUTO_SIGN_DTO, null);
                                    startClent();
                                    personter.getYdInfo();
                                }

                                @Override
                                public void fail(String code, String message) {
                                    startClent();
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    /****??????????????????****/
    private void dealGw(final String id) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("msg", "??????????????????");
                if (TextUtils.isEmpty(id)) {
                    return;
                }
                if (!id.contains("gw")) {
                    return;
                }
                String res = id.replaceAll("gw", ",");
                if (TextUtils.isEmpty(res)) {
                    return;
                }
                final String[] node = res.split(",");
                if (node == null || node.length < 2) {
                    return;
                }
                List<String> alreadyList = Hawk.get(PreferenceKey.AlreadyUploadWl);
                String location = "";
                if (currenrtLocation != null) {
                    location = currenrtLocation.getLongitude() + "," + currenrtLocation.getLatitude();
                }
                if (alreadyList == null) {
                    alreadyList = new ArrayList<>();
                    alreadyList.add(node[0]);
                    personter.gWFence(node[1], node[0], 9 + "", location, alreadyList);
                } else {
                    if (alreadyList.size() <= 0) {
                        alreadyList.add(node[0]);
                        personter.gWFence(node[1], node[0], 9 + "", location, alreadyList);
                    } else {
                        if (alreadyList.contains(node[0])) {
                        } else {
                            alreadyList.add(node[0]);
                            personter.gWFence(node[1], node[0], 9 + "", location, alreadyList);
                        }
                    }

                }
            }
        });

    }

    private void stopClent() {
        if (mClient != null) {
            if (mClient.isStarted()) {
                mClient.stop();
            }
        }
    }

    private void startClent() {
        if (mClient != null) {
            if (!mClient.isStarted()) {
                mClient.start();
            }
        } else {
            initLocation(1);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshCarLeader(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (str.equals(Constants.reshAuroAdto)
                    || str.equals(Constants.reshChange)) {
                Log.e("msg", "??????" + str);
                if (personter != null) {
                    personter.getYdInfo();
                }
                return;
            }
            if (str.equals(Constants.openTruck)) {
                if (utils == null) {
                    utils = new TraceUtils((BaseApplication) getApplicationContext(), context);
                }
                if (utils != null) {
                    utils.startSercive();
                    utils.startRealTimeLoc(30);
                }
                return;
            }
        }
    }


}
