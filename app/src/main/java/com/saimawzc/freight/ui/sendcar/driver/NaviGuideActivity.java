/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.saimawzc.freight.ui.sendcar.driver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.baidu.navisdk.adapter.BNaviCommonParams;
import com.baidu.navisdk.adapter.BaiduNaviManagerFactory;
import com.baidu.navisdk.adapter.IBNRouteGuideManager;
import com.baidu.navisdk.adapter.IBNTTSManager;
import com.baidu.navisdk.adapter.IBNaviListener;
import com.baidu.navisdk.adapter.IBNaviViewListener;
import com.baidu.navisdk.adapter.struct.BNGuideConfig;
import com.baidu.navisdk.adapter.struct.BNHighwayInfo;
import com.baidu.navisdk.adapter.struct.BNRoadCondition;
import com.baidu.navisdk.adapter.struct.BNaviInfo;
import com.baidu.navisdk.adapter.struct.BNaviLocation;
import com.baidu.navisdk.adapter.struct.BNaviResultInfo;
import com.baidu.navisdk.ui.routeguide.model.RGLineItem;
import java.util.List;
/**
 * 诱导界面
 */
public class NaviGuideActivity extends AppCompatActivity {

    private static final String TAG = NaviGuideActivity.class.getName();
    private IBNRouteGuideManager mRouteGuideManager;
    private IBNaviListener.DayNightMode mMode = IBNaviListener.DayNightMode.DAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean fullScreen = supportFullScreen();
        Bundle params = new Bundle();
        params.putBoolean(BNaviCommonParams.ProGuideKey.IS_SUPPORT_FULL_SCREEN, fullScreen);
        mRouteGuideManager = BaiduNaviManagerFactory.getRouteGuideManager();

        BNGuideConfig config = new BNGuideConfig.Builder()
                .params(params)
                .build();
        View view = mRouteGuideManager.onCreate(NaviGuideActivity.this, config);
        if (view != null) {
            setContentView(view);
        }
        initTTSListener();
        routeGuideEvent();
    }

    // 导航过程事件监听
    private void routeGuideEvent() {
        BaiduNaviManagerFactory.getRouteGuideManager().setNaviListener(new IBNaviListener() {
            @Override
            public void onRoadNameUpdate(String name) {

            }


            @Override
            public void onRemainInfoUpdate(int remainDistance, int remainTime) {

            }

            @Override
            public void onYawingArriveViaPoint(int i) {

            }

            @Override
            public void onGuideInfoUpdate(BNaviInfo naviInfo) {

            }

            @Override
            public void onHighWayInfoUpdate(Action action, BNHighwayInfo info) {

            }

            @Override
            public void onFastExitWayInfoUpdate(Action action, String name, int dist, String id) {

            }

            @Override
            public void onEnlargeMapUpdate(Action action, View enlargeMap, String remainDistance,
                                           int progress, String roadName, Bitmap turnIcon) {

            }

            @Override
            public void onDayNightChanged(DayNightMode style) {

            }

            @Override
            public void onRoadConditionInfoUpdate(double progress, List<BNRoadCondition> items) {

            }

            @Override
            public void onMainSideBridgeUpdate(int type) {

            }

            @Override
            public void onViaListRemainInfoUpdate(Message message) {

            }

            @Override
            public void onLaneInfoUpdate(Action action, List<RGLineItem> laneItems) {

            }

            @Override
            public void onSpeedUpdate(int i, int i1) {

            }

            @Override
            public void onOverSpeed(int i, int i1) {

            }

            @Override
            public void onArriveDestination() {
                BNaviResultInfo info =
                        BaiduNaviManagerFactory.getRouteGuideManager().getNaviResultInfo();
                Toast.makeText(NaviGuideActivity.this, "导航结算数据: " + info.toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onArrivedWayPoint(int index) {

            }
            @Override
            public void onLocationChange(BNaviLocation naviLocation) {

            }

            @Override
            public void onMapStateChange(MapStateMode mapStateMode) {
                if (mapStateMode == MapStateMode.BROWSE) {
                   // Toast.makeText(NaviGuideActivity.this, "操作态", Toast.LENGTH_SHORT).show();
                } else if (mapStateMode == MapStateMode.NAVING) {
                    //Toast.makeText(NaviGuideActivity.this, "导航态", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStartYawing(String s) {

            }


            @Override
            public void onYawingSuccess() {

            }

            @Override
            public void onNotificationShow(String msg) {
                Log.e(TAG, msg);
            }

            @Override
            public void onHeavyTraffic() {
                Log.e(TAG, "onHeavyTraffic");
            }

            @Override
            public void onNaviGuideEnd() {
                NaviGuideActivity.this.finish();
            }

            @Override
            public void onPreferChanged(int i) {

            }
        });

        BaiduNaviManagerFactory.getRouteGuideManager().setNaviViewListener(
                new IBNaviViewListener() {
                    @Override
                    public void onMainInfoPanCLick() {

                    }

                    @Override
                    public void onNaviTurnClick() {

                    }

                    @Override
                    public void onFullViewButtonClick(boolean show) {

                    }

                    @Override
                    public void onFullViewWindowClick(boolean show) {

                    }

                    @Override
                    public void onNaviBackClick() {
                        Log.e(TAG, "onNaviBackClick");
                    }

                    @Override
                    public void onBottomBarClick(Action action) {

                    }

                    @Override
                    public void onNaviSettingClick() {
                        Log.e(TAG, "onNaviSettingClick");
                    }

                    @Override
                    public void onRefreshBtnClick() {

                    }

                    @Override
                    public void onZoomLevelChange(int level) {

                    }

                    @Override
                    public void onMapClicked(double x, double y) {

                    }

                    @Override
                    public void onMapMoved() {
                        Log.e(TAG, "onMapMoved");
                    }

                    @Override
                    public void onFloatViewClicked() {
                        try {
                            Intent intent = new Intent();
                            intent.setPackage(getPackageName());
                            intent.setClass(NaviGuideActivity.this,
                                    Class.forName(NaviGuideActivity.class.getName()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                            startActivity(intent);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initTTSListener() {
        // 注册同步内置tts状态回调
        BaiduNaviManagerFactory.getTTSManager().setOnTTSStateChangedListener(
                new IBNTTSManager.IOnTTSPlayStateChangedListener() {
                    @Override
                    public void onPlayStart() {
                        Log.e("BNSDKDemo", "ttsCallback.onPlayStart");
                    }

                    @Override
                    public void onPlayEnd(String speechId) {
                        Log.e("BNSDKDemo", "ttsCallback.onPlayEnd");
                    }

                    @Override
                    public void onPlayError(int code, String message) {
                        Log.e("BNSDKDemo",
                                "ttsCallback.onPlayError"+"code"+code+"message"+message);
                    }
                }
        );

        // 注册内置tts 异步状态消息
        BaiduNaviManagerFactory.getTTSManager().setOnTTSStateChangedHandler(
                new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        Log.e("BNSDKDemo", "ttsHandler.msg.what=" + msg.what);
                    }
                }
        );
    }

    private void unInitTTSListener() {
        BaiduNaviManagerFactory.getTTSManager().setOnTTSStateChangedListener(null);
        BaiduNaviManagerFactory.getTTSManager().setOnTTSStateChangedHandler(null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRouteGuideManager.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        try {
            mRouteGuideManager.onResume();
        }catch (Exception e){

        }

    }

    protected void onPause() {
        super.onPause();
        mRouteGuideManager.onPause();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRouteGuideManager.onStop();
        mRouteGuideManager.onBackground();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unInitTTSListener();
        if(mRouteGuideManager!=null){
            mRouteGuideManager.onDestroy(false);
            mRouteGuideManager = null;
        }
    }

    @Override
    public void onBackPressed() {
        mRouteGuideManager.onBackPressed(false, true);
    }

    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mRouteGuideManager.onConfigurationChanged(newConfig);
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {

    }

    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (!mRouteGuideManager.onKeyDown(keyCode, event)) {
            return super.onKeyDown(keyCode, event);
        }
        return true;

    }

    private boolean supportFullScreen() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            int color;
            if (Build.VERSION.SDK_INT >= 23) {
                color = Color.TRANSPARENT;
            } else {
                color = 0x2d000000;
            }
            window.setStatusBarColor(color);

            if (Build.VERSION.SDK_INT >= 23) {
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                int uiVisibility = window.getDecorView().getSystemUiVisibility();
                if (mMode == IBNaviListener.DayNightMode.DAY) {
                    uiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                window.getDecorView().setSystemUiVisibility(uiVisibility);
            } else {
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            return true;
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        mRouteGuideManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }




}
