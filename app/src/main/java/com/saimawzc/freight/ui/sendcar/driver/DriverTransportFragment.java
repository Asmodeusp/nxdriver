package com.saimawzc.freight.ui.sendcar.driver;
import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.baidu.trace.api.fence.CircleFence;
import com.baidu.trace.api.fence.DeleteFenceRequest;
import com.baidu.trace.api.fence.DistrictFence;
import com.baidu.trace.api.fence.FenceInfo;
import com.baidu.trace.api.fence.FenceListRequest;
import com.baidu.trace.api.fence.FenceShape;
import com.baidu.trace.api.fence.FenceType;
import com.baidu.trace.api.fence.MonitoredStatus;
import com.baidu.trace.api.fence.MonitoredStatusByLocationRequest;
import com.baidu.trace.api.fence.MonitoredStatusInfo;
import com.baidu.trace.api.fence.PolygonFence;
import com.baidu.trace.api.fence.PolylineFence;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.ui.order.ShowArtActivity;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.overtimer.OverallTimer;
import com.saimawzc.freight.weight.utils.GalleryUtils;
import com.saimawzc.freight.weight.utils.dialog.PopupWindowUtil;
import com.saimawzc.freight.weight.waterpic.ViewPhoto;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviCommonParams;
import com.baidu.navisdk.adapter.BaiduNaviManagerFactory;
import com.baidu.navisdk.adapter.IBNRoutePlanManager;
import com.baidu.navisdk.adapter.struct.BNTruckInfo;
import com.baidu.navisdk.adapter.struct.VehicleConstant;
import com.baidu.trace.api.fence.AddMonitoredPersonResponse;
import com.baidu.trace.api.fence.CreateFenceRequest;
import com.baidu.trace.api.fence.CreateFenceResponse;
import com.baidu.trace.api.fence.DeleteFenceResponse;
import com.baidu.trace.api.fence.DeleteMonitoredPersonResponse;
import com.baidu.trace.api.fence.FenceListResponse;
import com.baidu.trace.api.fence.HistoryAlarmResponse;
import com.baidu.trace.api.fence.ListMonitoredPersonResponse;
import com.baidu.trace.api.fence.MonitoredStatusByLocationResponse;
import com.baidu.trace.api.fence.MonitoredStatusResponse;
import com.baidu.trace.api.fence.OnFenceListener;
import com.baidu.trace.api.fence.UpdateFenceResponse;
import com.baidu.trace.model.CoordType;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.sendcar.SendCarTrantAdatper;
import com.saimawzc.freight.adapter.sendcar.TrantsportProcessAdpater;
import com.saimawzc.freight.base.BaseApplication;
import com.saimawzc.freight.dto.my.RouteDto;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.pic.PicDto;
import com.saimawzc.freight.dto.sendcar.DriverTransDto;
import com.saimawzc.freight.dto.sendcar.TrantDto;
import com.saimawzc.freight.dto.sendcar.TrantProcessDto;
import com.saimawzc.freight.dto.sendcar.TrantSamllOrderDto;
import com.saimawzc.freight.presenter.sendcar.TrantPresenter;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.view.sendcar.DriverTransportView;
import com.saimawzc.freight.weight.BottomDialogUtil;
import com.saimawzc.freight.weight.utils.ContentRecyclerView;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import com.saimawzc.freight.weight.utils.trace.TraceUtils;
import com.yinglan.scrolllayout.ScrollLayout;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import static android.app.Activity.RESULT_OK;
import static com.saimawzc.freight.base.BaseActivity.PERMISSIONSS_LOCATION;
import static com.saimawzc.freight.base.BaseActivity.PERMISSIONS_CAMERA;

/***
 * ????????????
 * **/
public class DriverTransportFragment extends BaseFragment
        implements DriverTransportView {
    @BindView(R.id.yingScrollyay) ScrollLayout mScrollLayout;
    @BindView(R.id.trantCy)
    ContentRecyclerView trantCv;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.ll_dot) LinearLayout llDot;
    private SparseBooleanArray isLarge;
    @BindView(R.id.mapview) MapView mapView;

    private int dotSize = 12;
    private int dotSpace = 12;
    private Animator animatorToLarge;
    private Animator animatorToSmall;
    private SendCarTrantAdatper adatper;
    private List<DriverTransDto> mdatas=new ArrayList<>();
    private TrantsportProcessAdpater processAdpater;
    private List<TrantProcessDto>processDtos=new ArrayList<>();
    private String id;
    private TrantPresenter  presenter;
    private  BDLocation currenrtLocation;
    private String yundanId;
    public static final int WaterPic=10086;
    private BaiduMap baiduMap;
    List<LatLng> allPointList = new ArrayList<LatLng>();
    private BaseApplication trackApp;
    private TraceUtils utils;
    private String location;
    private String wayBillId;
    private TrantDto weiLanDto;
    public  final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    @BindView(R.id.rlTop)
    RelativeLayout rlTop;

    @BindView(R.id.satrt_navation) Button btnNavation;
    /***??????????????????***/
    private  DriverTransDto driverTransDto;
    private int currentCode=0;
    /***?????????????????????***/
    private String htmlContext;
    private int fenceClock=2;//?????????????????????????????? 1 ??????????????????  2  ?????????????????????
    private String poundAlarm;

    private int isWlRange;
    private int currentposition;//????????????
    private int yundanPosition;//???????????????????????????
    private int highEnclosureAlarm=0;//??????????????????????????????

    @Override
    public int initContentView() {
        return R.layout.fragment_driver_trans;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        initpermissionChecker();
        if(permissionChecker.isLackPermissions(PERMISSIONS)){
            permissionChecker.requestPermissions();
            context.showMessage("??????????????????");
        }

        baiduMap=mapView.getMap();
        userInfoDto= getUserInfoDto(userInfoDto);
        animatorToLarge = AnimatorInflater.loadAnimator(mContext, R.animator.scale_to_large);
        animatorToSmall = AnimatorInflater.loadAnimator(mContext, R.animator.scale_to_small);
        adatper = new SendCarTrantAdatper(mContext, mdatas);
        viewPager.setAdapter(adatper);
        trackApp= (BaseApplication) mContext.getApplicationContext();
        /**?????? recycleview*/
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        processAdpater=new TrantsportProcessAdpater(processDtos,mContext);
        trantCv.setLayoutManager(layoutManager);
        trantCv.setAdapter(processAdpater);
        id=getArguments().getString("id");
        presenter=new TrantPresenter(this,mContext);
        if(!TextUtils.isEmpty(id)){
            presenter.getData(id,1);
            presenter.init(mScrollLayout);
            presenter.getRoute(id,1);
        }else {
            context.showMessage("??????ID??????");
        }

    }
    @Override
    public void initData() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(final int position) {
                // ???????????????View???????????????????????????
                presenter.getData(mdatas.get(position).getId(),2);
                yundanId=mdatas.get(position).getId();
                fenceClock=mdatas.get(position).getFenceClock();
                wayBillId=mdatas.get(position).getWayBillId();
                driverTransDto=mdatas.get(position);
                poundAlarm=mdatas.get(position).getPoundAlarm();
                highEnclosureAlarm=mdatas.get(position).getHighEnclosureAlarm();
                htmlContext=mdatas.get(position).getContext();
                for (int i = 0; i < llDot.getChildCount(); i++) {
                    if (i == position ) {// ?????????
                        llDot.getChildAt(i).setBackgroundResource(R.drawable.dot_selected);
                        if (!isLarge.get(i)) {
                            animatorToLarge.setTarget(llDot.getChildAt(i));
                            animatorToLarge.start();
                            isLarge.put(i, true);
                        }
                    } else {// ????????????
                        llDot.getChildAt(i).setBackgroundResource(R.drawable.dot_unselected);
                        if (isLarge.get(i)) {
                            animatorToSmall.setTarget(llDot.getChildAt(i));
                            animatorToSmall.start();
                            isLarge.put(i, false);
                        }
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        adatper.setOnItemClickListener(new SendCarTrantAdatper.OnItemClickListener() {
            @Override
            public void onItemClick(String str, View view, final int position) {

                if(position>=mdatas.size()){
                    return;
                }
                yundanPosition=position;
                if(TextUtils.isEmpty(str)){
                    presenter.getWayBillRolete(yundanId,"2");
                }else if(str.equals("resh")){
                    //presenter.getData(mdatas.get(position).getId(),1);
                    presenter.getData(id,1);

                }else if(str.equals("tvmore")){
                    final PopupWindowUtil popupWindowUtil = new PopupWindowUtil.Builder()
                            .setContext(mContext) //?????? context
                            .setContentView(R.layout.dialog_dianzisuo) //??????????????????
                            .setOutSideCancel(true) //????????????????????????
                            .setwidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                            .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
                            .setFouse(true)
                            .builder()
                            .showAsLaction(view, Gravity.LEFT,0,0);

                    popupWindowUtil.setOnClickListener(new int[]{R.id.tvRole, R.id.bandLock,
                            R.id.getLockPass,R.id.tverrorcord,R.id.reback_}, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle;
                            popupWindowUtil.dismiss();
                            switch (v.getId()){
                                case R.id.tvRole:
                                    presenter.getWayBillRolete(yundanId,"2");
                                    break;
                                case R.id.tverrorcord://????????????
                                    context.showLoadingDialog("???????????????...");
                                    presenter.getLocation(100+"");
                                    break;
                                case R.id.bandLock:
                                    bundle=new Bundle();
                                    bundle.putString("from","bindlock");
                                    bundle.putString("dispatchCarId",mdatas.get(position).getId());
                                    readyGo(OrderMainActivity.class,bundle);
                                    break;
                                case R.id.getLockPass:
                                    context.showLoadingDialog("???????????????...");
                                    presenter.getLocation(101+"");
                                    break;
                                case R.id.reback_:
                                    bundle=new Bundle();
                                    bundle.putString("from","recallback");
                                    bundle.putString("dispatchCarId",mdatas.get(position).getId());
                                    readyGo(OrderMainActivity.class,bundle);
                                    break;
                            }
                        }
                    });

                }
            }
        });

        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                View view= LayoutInflater.from(mContext).inflate(R.layout.map_show,null);
                if((marker.getExtraInfo()!=null)){
                    TextView tvName=view.findViewById(R.id.tvName);
                    tvName.setText(marker.getExtraInfo().getString("name"));
                    LatLng ll = new LatLng(marker.getExtraInfo().getDouble("latute"),
                            marker.getExtraInfo().getDouble("lontute"));
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(18.0f);
                    baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                    //??????InfoWindow
                    //point ??????????????????
                    //-100 InfoWindow?????????point???y???????????????
                    InfoWindow mInfoWindow = new InfoWindow(view, marker.getPosition(), -47);
                    //???InfoWindow??????
                    baiduMap.showInfoWindow(mInfoWindow);
                    return true;
                }else {
                    return  false;
                }
            }
        });
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                baiduMap.hideInfoWindow();
            }

            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
            }
        });
    }

    private static  final int mPageType =  1 << 1;;
    @OnClick({R.id.back,R.id.satrt_navation})
    public void click(View view){
        switch (view.getId()){
            case R.id.back:
                context.finish();
                break;
            case R.id.satrt_navation://????????????
                if (BaiduNaviManagerFactory.getBaiduNaviManager().isInited()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(BNaviCommonParams.RoutePlanKey.VEHICLE_TYPE,
                            IBNRoutePlanManager.Vehicle.TRUCK);
                    bundle.putString(BNaviCommonParams.RoutePlanKey.ASSIGN_ROUTE, "assign_key");
                    routePlanToNavi(bundle);
                }else {
                    context.showMessage("????????????");
                }
                break;
        }
    }
    private void setIndicator() {
        isLarge = new SparseBooleanArray();
        // ?????????????????????????????????????????????????????????????????????
        llDot.removeAllViews();
        for (int i = 0; i < mdatas.size(); i++) {
            View view = new View(mContext);
            view.setBackgroundResource(R.drawable.dot_unselected);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dotSize, dotSize);
            layoutParams.leftMargin = dotSpace / 2;
            layoutParams.rightMargin = dotSpace / 2;
            layoutParams.topMargin = dotSpace / 2;
            layoutParams.bottomMargin = dotSpace / 2;
            llDot.addView(view, layoutParams);
            isLarge.put(i, false);
        }
        llDot.getChildAt(0).setBackgroundResource(R.drawable.dot_selected);
        animatorToLarge.setTarget(llDot.getChildAt(0));
        animatorToLarge.start();
        isLarge.put(0, true);
    }
    public void initCarInfo(String carNo,float length,float width,float height,
                            float laodweight,int type) {
        try{
            Hawk.put(PreferenceKey.CURRENT_CAR_NO,carNo);
            BaiduNaviManagerFactory.getBaiduNaviManager().enableOutLog(true);
            // ??????????????????
            BaiduNaviManagerFactory.getCommonSettingManager().setCarNum(carNo);
            int color=VehicleConstant.PlateType.BLUE;
            if(type==1){//??????
                color= VehicleConstant.PlateType.YELLOW;
            }else  if(type==2){//??????
                color= VehicleConstant.PlateType.BLUE;
            }
            // ????????????
            BNTruckInfo truckInfo = new BNTruckInfo.Builder()
                    .plate(carNo)
                    .axlesNumber(2)
                    .axlesWeight(1)
                    .emissionLimit(VehicleConstant.EmissionStandard.S3)
                    .length(length)
                    .weight(laodweight)
                    .loadWeight(laodweight)
                    .height(height)
                    .width(width)
                    .loadWeight(laodweight)
                    .oilCost("40000")
                    .plateType(color)//????????????
                    .powerType(VehicleConstant.PowerType.OIL)
                    .truckType(VehicleConstant.TruckType.HEAVY)
                    .build();
            // ???????????????????????????????????????????????????????????????
            BaiduNaviManagerFactory.getCommonSettingManager().setTruckInfo(truckInfo);
        }catch (Exception e){
        }
    }
    private BNRoutePlanNode startNode;
    private BNRoutePlanNode endNode;
    String start = "";
    String end = "";
    private void routePlanToNavi(final Bundle bundle) {
        List<BNRoutePlanNode> list = new ArrayList<>();
        list.add(getStartNode(mContext));
        list.add(getEndNode(mContext));
        // ???????????????
        if (BaiduNaviManagerFactory.getCruiserManager().isCruiserStarted()) {
            BaiduNaviManagerFactory.getCruiserManager().stopCruise();
        }
        BaiduNaviManagerFactory.getRoutePlanManager().routePlanToNavi(
                list,
                IBNRoutePlanManager.RoutePlanPreference.ROUTE_PLAN_PREFERENCE_DEFAULT,
                bundle, handler);
    }

    public BNRoutePlanNode getStartNode(Context context) {
        if (!TextUtils.isEmpty(start)) {
            String[] node = start.split(",");
            startNode = new BNRoutePlanNode.Builder()
                    .longitude(Double.parseDouble(node[0]))
                    .latitude(Double.parseDouble(node[1]))
                    .build();
        }
        return startNode;
    }
    public BNRoutePlanNode getEndNode(Context context) {
        if (!TextUtils.isEmpty(end)) {
            String[] node = end.split(",");
            double latitu;
            double longtitu;
            if(Double.parseDouble(node[1])>Double.parseDouble(node[0])){
                longtitu=Double.parseDouble(node[1]);
                latitu=Double.parseDouble(node[0]);
            }else {
                longtitu=Double.parseDouble(node[0]);
                latitu=Double.parseDouble(node[1]);
            }
            endNode = new BNRoutePlanNode.Builder()
                    .longitude(longtitu)
                    .latitude(latitu)
                    .build();
        }
        return endNode;
    }
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_START:
                    context.showLoadingDialog("?????????????????????");
                    context.showMessage("????????????");
                    break;
                case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_SUCCESS:
                    context.dismissLoadingDialog();
                    context.showMessage("????????????");
                    // ??????????????????
                    Bundle infoBundle = (Bundle) msg.obj;
                    if (infoBundle != null) {
                        String info = infoBundle
                                .getString(BNaviCommonParams.BNRouteInfoKey.TRAFFIC_LIMIT_INFO);
                        Log.e("OnSdkDemo", "info = " + info);
                    }
                    break;
                case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_FAILED:
                    context.dismissLoadingDialog();
                    context.showMessage("????????????");

                    break;
                case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_TO_NAVI:
                    context.dismissLoadingDialog();
                    context.showMessage("??????????????????????????????");
                    switch (mPageType) {
                        case 1 << 1:
                            gotoNavi(mContext);
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public static void gotoNavi(Context activity) {
        Intent it = new Intent(activity, NaviGuideActivity.class);
        activity.startActivity(it);
    }

    private int tag;
    private NormalDialog dialog;
    private Timer onLineTimer = new Timer(true);//????????????

    private TrantDto dto;
    @Override
    public void getData(final TrantDto dto, final int type) {
        //??????????????????????????????
        if(dto!=null){
            //?????????????????????
            processDtos.clear();
            //??????????????????
            this.dto=dto;
            //??????????????????1
            if(type==1){
                //??????????????????
                weiLanDto=dto;
                mdatas.clear();
                yundanId=dto.getId();
                start=dto.getFromLocation();
                end=dto.getToLocation();
                //????????????????????????
                mdatas.addAll(dto.getWaybillList());
                //??????????????????
                if(dto.getWaybillList()!=null){
                    //????????????????????????0
                    if(dto.getWaybillList().size()>0){
                        driverTransDto=dto.getWaybillList().get(0);
                        wayBillId=dto.getWaybillList().get(0).getWayBillId();
                        htmlContext=dto.getWaybillList().get(0).getContext();
                        fenceClock=dto.getWaybillList().get(0).getFenceClock();
                        poundAlarm=dto.getWaybillList().get(0).getPoundAlarm();
                        highEnclosureAlarm=dto.getWaybillList().get(0).getHighEnclosureAlarm();
                    }
                }
                location=dto.getToLocation();
                setIndicator();
                adatper.notifyDataSetChanged();
                if(dto.getTranType()==2){
                    btnNavation.setVisibility(View.GONE);
                }
                initCarInfo(dto.getCarNo(),dto.getCarLength(),dto.getCarWigth(),
                        dto.getCarHeigth(),dto.getCarLoad(),dto.getCarColor());
            }

            processAdpater.addMoreData(dto.getTransportStatusList());
            //?????????????????????
            if(dto.getTransportStatusList()!=null){
                //????????????
                for(int i=0;i<dto.getTransportStatusList().size();i++){
                    //?????????????????????????????????????????????????????????????????????
                    if(dto.getTransportStatusList().get(i).isNextclockInFlag()){
                        currentCode=dto.getTransportStatusList().get(i).getCode();
                    }
                }
            }
            processAdpater.setOnTabClickListener(new BaseAdapter.OnTabClickListener() {
                @Override
                public void onItemClick(final String leixing, final int position) {
                    if(processDtos.size()<=position){
                        return;
                    }
                    if(permissionChecker.isLackPermissions(PERMISSIONSS_LOCATION)){
                        permissionChecker.requestPermissions();
                        context.showMessage("????????????????????????");
                        return;
                    }
                    if(permissionChecker.isLackPermissions(PERMISSIONS)){
                        permissionChecker.requestPermissions();
                        context.showMessage("????????????????????????");
                        return;
                    }
                    if(permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)){
                        permissionChecker.requestPermissions();
                        context.showMessage("????????????????????????");
                        return;
                    }
                    Hawk.put(PreferenceKey.CURRENT_CAR_NO,dto.getCarNo());
                    if(position==0){
                        if(utils==null){
                            utils=new TraceUtils((BaseApplication) mContext.getApplicationContext(),context);
                        }
                        if(utils!=null){
                            utils.startSercive();
                            utils.startRealTimeLoc(30);
                            OverallTimer.getInstance().startTimer();
                            Log.e("msg","??????????????????,????????????????????????");
                        }
                    }
                    currentposition=position;
                    context.showLoadingDialog("???????????????...");
                    presenter.getLocation(leixing);
                }
            });
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CAMERA && resultCode == RESULT_OK){
            if(!context.isEmptyStr(tempImage)){
                if(currenrtLocation==null){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("adress", currenrtLocation.getAddress().address + "(" + currenrtLocation.getLongitude()+","+currenrtLocation.getLatitude() + ")");
                bundle.putString("photoPath",tempImage);
                bundle.putString("distance", "");
                bundle.putString("toadress", "");
                bundle.putString("country", "");
                bundle.putString("city", "");
                bundle.putString("fromtype", "other");
                bundle.putString("location",currenrtLocation.getLongitude()+","+currenrtLocation.getLatitude());
                bundle.putString("adresschange", currenrtLocation.getAddress().address);
                readyGoForResult(ViewPhoto.class, WaterPic, bundle);
            }
        }
        if(requestCode==200 && resultCode == RESULT_OK){
            final String code=data.getStringExtra("code");
            if(code.equals("7")){
                List<String>list=Hawk.get(PreferenceKey.ISREAD_AQXZ);
                if(list!=null){
                    list.remove(yundanId);
                    Hawk.put(PreferenceKey.ISREAD_AQXZ,list);
                }
                context.finish();
            }else {
                presenter.getData(yundanId,2);
            }

        }
        if(requestCode==WaterPic && resultCode == RESULT_OK){
            final String imgUrl=data.getStringExtra("photo");
            if(!TextUtils.isEmpty(imgUrl)){
                final BottomDialogUtil bottomDialogUtil = new BottomDialogUtil.Builder()
                        .setContext(context) //?????? context
                        .setContentView(R.layout.dialog_trant) //??????????????????
                        .setOutSideCancel(false) //????????????????????????
                        .builder()
                        .show();
                ImageView imageView= (ImageView) bottomDialogUtil.getItemView(R.id.imgview);
                ImageLoadUtil.displayLocalImage(mContext,new File(imgUrl),imageView);
                TextView tvType= (TextView) bottomDialogUtil.getItemView(R.id.tvType);
                TextView tvTime= (TextView) bottomDialogUtil.getItemView(R.id.tvTime);
                TextView tvAdress= (TextView) bottomDialogUtil.getItemView(R.id.tvAdress);
                tvType.setText(processDtos.get(tag).getName());
                tvTime.setText("???????????????"+BaseActivity.getCurrentTime("yyyy-MM-dd HH:mm"));
                if(currenrtLocation!=null){
                    tvAdress.setText("???????????????"+currenrtLocation.getAddress().address);
                }
                bottomDialogUtil.setOnClickListener(R.id.tvCannel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomDialogUtil.dismiss();
                    }
                });
                bottomDialogUtil.setOnClickListener(R.id.tvOrder, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomDialogUtil.dismiss();
                        Uploadpic(BaseActivity.compress(mContext,new File(imgUrl)));
                    }
                });

            }
        }
    }

    @Override
    public void location(BDLocation dblocation,final String opertype) {
        context.dismissLoadingDialog();
        if(dblocation==null){
            context.showMessage("??????????????????????????????????????????????????????");
            return;
        }else {
            if( dblocation.getLocType()==62){
                context.showMessage("??????????????????????????????????????????????????????");
                return;
            }
        }
        currenrtLocation=dblocation;
        if(!TextUtils.isEmpty(opertype)){
            if(opertype.equals("100")){//????????????
               Bundle bundle=new Bundle();
                bundle.putString("from","error");
                bundle.putString("dispatchCarId",mdatas.get(yundanPosition).getId());
                bundle.putString("location",currenrtLocation.getLongitude()+","+currenrtLocation.getLatitude());
                readyGo(OrderMainActivity.class,bundle);
                return;
            }
            if(opertype.equals("101")){//????????????
               Bundle bundle=new Bundle();
                bundle.putInt("currentcode",currentCode);
                bundle.putSerializable("data",driverTransDto);
                bundle.putString("dispatchCarId",mdatas.get(yundanPosition).getId());
                bundle.putString("from","getnumpass");
                bundle.putString("location",currenrtLocation.getLongitude()+","+currenrtLocation.getLatitude());
                readyGo(OrderMainActivity.class,bundle);
                return;
            }
            if(!TextUtils.isEmpty(htmlContext)){
                List<String>list=Hawk.get(PreferenceKey.ISREAD_AQXZ);
                if(list==null||!list.contains(yundanId)){
                    Bundle bundle=new Bundle();
                    bundle.putString("yundanId",yundanId);
                    bundle.putString("data",htmlContext);
                    readyGo(ShowArtActivity.class,bundle);
                    return;
                }
            }
            if(fenceClock==1){
                if(!TextUtils.isEmpty(yundanId)){
                    presenter.isFeeced(yundanId,currenrtLocation.getLongitude()+","+currenrtLocation.getLatitude(),opertype);
                }
            }else {
                Log.e("msg","??????"+processDtos.get(currentposition).getCode());
                if(processDtos.get(currentposition).getCode()==7||
                        processDtos.get(currentposition).getCode()==8){
                    Bundle bundle=new Bundle();
                    bundle.putString("id",yundanId);
                    bundle.putBoolean("isuserablum",processDtos.get(currentposition).isAlbumFlag());
                    bundle.putString("waybillid",wayBillId);
                    bundle.putString("code",processDtos.get(currentposition).getCode()+"");
                    bundle.putString("country",currenrtLocation.getCity());
                    bundle.putString("city",currenrtLocation.getDistrict());
                    bundle.putString("adress",currenrtLocation.getAddress().address);
                    bundle.putString("location",currenrtLocation.getLongitude()+","+currenrtLocation.getLatitude());
                    bundle.putString("isfencece",fenceClock+"");
                    if(TextUtils.isEmpty(dto.getToLocation())){
                        bundle.putString("toadress",location);//?????????????????????
                    }else {
                        bundle.putString("toadress",dto.getToLocation());//?????????????????????
                    }
                    bundle.putString("poundAlarm",poundAlarm);
                    bundle.putSerializable("data",driverTransDto);
                    bundle.putString("tranttype",getArguments().getString("tranttype"));
                    bundle.putString("from","arriveorder");
                    readyGoForResult(OrderMainActivity.class,200,bundle);
                }else {
                    if (dto.getWaybillList() != null && dto.getWaybillList().size() > 0) {
                        if (utils != null) {
                            utils.setStopArlm(dto.getWaybillList().get(0).getStopAlarm());
                            utils.setAlarmTime(dto.getWaybillList().get(0).getAlarmTime());
                            utils.setWayBillId(dto.getWaybillList().get(0).getWayBillId());
                        }
                    }
                    if (processDtos.get(currentposition).isPictureFlag()) {//????????????
                        tag = currentposition;
                        if (processDtos.get(currentposition).isAlbumFlag()) {
                            presenter.showCamera(mContext);
                        } else {
                            showCameraAction();
                        }
                    } else {//???????????????
                        dialog = new NormalDialog(mContext).isTitleShow(false)
                                .content("??????" + processDtos.get(currentposition).getName() + "??????")
                                .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                                .btnNum(2).btnText("??????", "??????");
                        dialog.setOnBtnClickL(
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        if (!context.isDestroy(context)) {
                                            dialog.dismiss();
                                        }
                                    }
                                },
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        presenter.daka(yundanId, opertype, currenrtLocation, "");
                                        if (!context.isDestroy(context)) {
                                            dialog.dismiss();
                                        }
                                    }
                                });
                        dialog.show();
                    }
                }
                if(currentposition==0){//????????????
                    if(getUserInfoDto(userInfoDto)==null){
                        return;
                    }
                    if(trackApp.mClient==null){
                        return;
                    }
                    String monitoredPerson = getUserInfoDto(userInfoDto).getRoleId();//??????ID
                    FenceListRequest fenceListRequest  =FenceListRequest.buildServerRequest(trackApp.getTag(), trackApp.serviceId,
                            monitoredPerson, null, CoordType.bd09ll, 0, 0);
                    trackApp.mClient.queryFenceList(fenceListRequest,mFenceListener);
                }

            }
        }
    }
    @Override
    public void getRolte(final RouteDto dto,final int type) {
        if(dto!=null){
            if(dto.getPath()!=null){
                //?????????????????????
                String tempLocation=dto.getDestination();
                if(TextUtils.isEmpty(tempLocation)){
                    return;
                }
                String[] tempNode = tempLocation.split(",");
                LatLng tempPoint = new LatLng(Double.parseDouble(tempNode[1]), Double.parseDouble(tempNode[0]));
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(tempPoint).zoom(18.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                if(!TextUtils.isEmpty(dto.getOrigin())){
                    String[] statrNode = dto.getOrigin().split(",");
                    LatLng startPoint = new LatLng(Double.parseDouble(statrNode[1]), Double.parseDouble(statrNode[0]));
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.ico_map_start);
                    OverlayOptions option = new MarkerOptions()
                            .position(startPoint)
                            .icon(bitmap);
                    baiduMap.addOverlay(option);
                }
                if(!TextUtils.isEmpty(dto.getDestination())){
                    String[] endNode = dto.getDestination().split(",");
                    LatLng endPoint = new LatLng(Double.parseDouble(endNode[1]), Double.parseDouble(endNode[0]));
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.icon_end);
                    //??? ???MarkerOption???????????????????????????Marker
                    OverlayOptions option = new MarkerOptions()
                            .position(endPoint)
                            .icon(bitmap);
                    //??????????????????Marker????????????
                    baiduMap.addOverlay(option);
                }
                //////???????????????
                List<LatLng> points = new ArrayList<LatLng>();
                if(dto.getPath()!=null){
                    for(int i=0;i<dto.getPath().length;i++){
                        String node=dto.getPath()[i];
                        String[] nodeStartarr = node.split(",");
                        LatLng p = new LatLng(Double.parseDouble(nodeStartarr[1]), Double.parseDouble(nodeStartarr[0]));
                        points.add(p);
                    }
                }
                allPointList=points;
                OverlayOptions mOverlayOptions = new PolylineOptions()
                        .width(10)
                        .color(Color.RED)
                        .points(points);
                Overlay mPolyline1 = baiduMap.addOverlay(mOverlayOptions);
            }
        }
    }
    /***
     * ????????????????????????
     * ***/
    Overlay mPolyline;
    @Override
    public void getSmallOrderRolte(final TrantSamllOrderDto dto) {
        if(dto!=null){
            if(dto.getPath()!=null){
                Log.e("msg","???????????????");
                if(mPolyline!=null){
                    baiduMap.clear();
                    if(!TextUtils.isEmpty(dto.getOrigin())){
                        String[] statrNode = dto.getOrigin().split(",");
                        LatLng startPoint = new LatLng(Double.parseDouble(statrNode[1]), Double.parseDouble(statrNode[0]));
                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                .fromResource(R.drawable.ico_map_start);
                        OverlayOptions option = new MarkerOptions()
                                .position(startPoint)
                                .icon(bitmap);
                        baiduMap.addOverlay(option);
                    }
                    if(!TextUtils.isEmpty(dto.getDestination())){
                        String[] endNode = dto.getDestination().split(",");
                        LatLng endPoint = new LatLng(Double.parseDouble(endNode[1]), Double.parseDouble(endNode[0]));
                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                .fromResource(R.drawable.icon_end);
                        //??? ???MarkerOption???????????????????????????Marker
                        OverlayOptions option = new MarkerOptions()
                                .position(endPoint)
                                .icon(bitmap);
                        //???                   ???????????????Marker????????????
                        baiduMap.addOverlay(option);
                    }

                }
                if(allPointList.size()>=2){
                    OverlayOptions mOverlayOptions1 = new PolylineOptions()
                            .width(10)
                            .color(Color.RED)
                            .points(allPointList);

                    Overlay mPolyline1 = baiduMap.addOverlay(mOverlayOptions1);

                    List<LatLng> points = new ArrayList<LatLng>();
                    Log.e("msg",dto.getPath()[dto.getPath().length-1]);
                    for(int i=0;i<dto.getPath().length;i++){
                        String nodeStart=dto.getPath()[i];
                        String[] nodeStartarr = nodeStart.split(",");
                        LatLng p = new LatLng(Double.parseDouble(nodeStartarr[1]), Double.parseDouble(nodeStartarr[0]));
                        points.add(p);
                    }
                    OverlayOptions mOverlayOptions = new PolylineOptions()
                            .width(10)
                            .color(Color.BLUE)
                            .points(points);
                    mPolyline=baiduMap.addOverlay(mOverlayOptions);
                }
            }
        }
    }


    @Override
    public void getCode(String code) {
        Bundle bundle;
        if(code.equals("6001")){
            bundle =new Bundle();
            bundle.putString("from","bindlock");
            bundle.putString("dispatchCarId",yundanId);
            readyGo(OrderMainActivity.class,bundle);
        }else if(code.equals("6002")){
            presenter.getLocation("101");

        }

    }

    @Override
    public void isFence(int isFenceC,String message,final String opertype) {
        isWlRange=isFenceC;//
        if(isWlRange==2) {//??????????????????
            if(processDtos.get(currentposition).getCode()==4||
                    processDtos.get(currentposition).getCode()==7){
                dialog = new NormalDialog(mContext).isTitleShow(false)
                        .content("??????????????????????????????????????????")
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
                                if(processDtos.get(currentposition).getCode()==7){
                                    Bundle bundle=new Bundle();
                                    bundle.putString("id",yundanId);
                                    bundle.putBoolean("isuserablum",processDtos.get(currentposition).isAlbumFlag());
                                    bundle.putString("waybillid",wayBillId);
                                    bundle.putString("code",processDtos.get(currentposition).getCode()+"");
                                    bundle.putString("country",currenrtLocation.getCity());
                                    bundle.putString("city",currenrtLocation.getDistrict());
                                    bundle.putString("adress",currenrtLocation.getAddress().address);
                                    bundle.putString("location",currenrtLocation.getLongitude()+","+currenrtLocation.getLatitude());
                                    if(TextUtils.isEmpty(dto.getToLocation())){
                                        bundle.putString("toadress",location);//?????????????????????
                                    }else {
                                        bundle.putString("toadress",dto.getToLocation());//?????????????????????
                                    }
                                    bundle.putString("poundAlarm",poundAlarm);
                                    bundle.putString("isfencece",fenceClock+"");
                                    bundle.putSerializable("data",driverTransDto);
                                    bundle.putString("tranttype",getArguments().getString("tranttype"));
                                    bundle.putString("from","arriveorder");
                                    readyGoForResult(OrderMainActivity.class,200,bundle);
                                    if(!context.isDestroy(context)){
                                        dialog.dismiss();
                                    }
                                }else {
                                    if(processDtos.get(currentposition).isPictureFlag()){//????????????
                                        tag=currentposition;
                                        if(processDtos.get(currentposition).isAlbumFlag()){
                                            presenter.showCamera(mContext);
                                        }else {
                                            showCameraAction();
                                        }
                                        if(!context.isDestroy(context)){
                                            dialog.dismiss();
                                        }
                                    }else {//???????????????
                                        presenter.daka(yundanId,opertype,currenrtLocation,"");
                                        if(!context.isDestroy(context)){
                                            dialog.dismiss();
                                        }
                                    }
                                }
                            }
                        });
                dialog.show();
            }else {//???????????????????????????4???7start
                Log.e("msg","dsfsdf");
                if(processDtos.get(currentposition).getCode()==8){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", yundanId);
                    bundle.putBoolean("isuserablum", processDtos.get(currentposition).isAlbumFlag());
                    bundle.putString("waybillid", wayBillId);
                    bundle.putString("code", processDtos.get(currentposition).getCode() + "");
                    bundle.putString("country", currenrtLocation.getCity());
                    bundle.putString("city", currenrtLocation.getDistrict());
                    bundle.putString("adress", currenrtLocation.getAddress().address);
                    bundle.putString("location", currenrtLocation.getLongitude() + "," + currenrtLocation.getLatitude());
                    if (TextUtils.isEmpty(dto.getToLocation())) {
                        bundle.putString("toadress", location);//?????????????????????
                    } else {
                        bundle.putString("toadress", dto.getToLocation());//?????????????????????
                    }
                    bundle.putString("poundAlarm", poundAlarm);
                    bundle.putString("isfencece", fenceClock + "");
                    bundle.putSerializable("data", driverTransDto);
                    bundle.putString("tranttype", getArguments().getString("tranttype"));
                    bundle.putString("from", "arriveorder");
                    readyGoForResult(OrderMainActivity.class, 200, bundle);
                    if (!context.isDestroy(context)) {
                        dialog.dismiss();
                    }
                }else {
                    if(processDtos.get(currentposition).isPictureFlag()){//????????????
                        tag=currentposition;
                        if(processDtos.get(currentposition).isAlbumFlag()){
                            presenter.showCamera(mContext);
                        }else {
                            showCameraAction();
                        }
                    }else {//???????????????
                        dialog = new NormalDialog(mContext).isTitleShow(false)
                                .content("??????" + processDtos.get(currentposition).getName() + "??????")
                                .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                                .btnNum(2).btnText("??????", "??????");
                        dialog.setOnBtnClickL(
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        if (!context.isDestroy(context)) {
                                            dialog.dismiss();
                                        }
                                    }
                                },
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        presenter.daka(yundanId, opertype, currenrtLocation, "");
                                        if (!context.isDestroy(context)) {
                                            dialog.dismiss();
                                        }
                                    }
                                });
                        dialog.show();
                    }
                }
            }
            if (currentposition == 0) {//????????????
                if (getUserInfoDto(userInfoDto) == null) {
                    return;
                }
                if (trackApp.mClient == null) {
                    return;
                }
                String monitoredPerson = getUserInfoDto(userInfoDto).getRoleId();//??????ID
                FenceListRequest fenceListRequest = FenceListRequest.buildServerRequest(trackApp.getTag(), trackApp.serviceId,
                        monitoredPerson, null, CoordType.bd09ll, 0, 0);
                trackApp.mClient.queryFenceList(fenceListRequest, mFenceListener);
            }
            /***
             * ???????????????????????????4???7end
             * ***/
        } else {//??????????????????start
            if (processDtos.get(currentposition).getCode() == 7 ||
                    processDtos.get(currentposition).getCode() == 8) {
                Bundle bundle = new Bundle();
                bundle.putString("id", yundanId);
                bundle.putBoolean("isuserablum", processDtos.get(currentposition).isAlbumFlag());
                bundle.putString("waybillid", wayBillId);
                bundle.putString("code", processDtos.get(currentposition).getCode() + "");
                bundle.putString("country", currenrtLocation.getCity());
                bundle.putString("city", currenrtLocation.getDistrict());
                bundle.putString("adress", currenrtLocation.getAddress().address);
                bundle.putString("location", currenrtLocation.getLongitude() + "," + currenrtLocation.getLatitude());
                bundle.putString("isfencece", fenceClock + "");
                if (TextUtils.isEmpty(dto.getToLocation())) {
                    bundle.putString("toadress", location);//?????????????????????
                } else {
                    bundle.putString("toadress", dto.getToLocation());//?????????????????????
                }
                bundle.putString("poundAlarm", poundAlarm);
                bundle.putSerializable("data", driverTransDto);
                bundle.putString("tranttype", getArguments().getString("tranttype"));
                bundle.putString("from", "arriveorder");
                readyGoForResult(OrderMainActivity.class, 200, bundle);
            } else {
                if (dto.getWaybillList() != null && dto.getWaybillList().size() > 0) {
                    if (utils != null) {
                        utils.setStopArlm(dto.getWaybillList().get(0).getStopAlarm());
                        utils.setAlarmTime(dto.getWaybillList().get(0).getAlarmTime());
                        utils.setWayBillId(dto.getWaybillList().get(0).getWayBillId());
                    }
                }
                if (processDtos.get(currentposition).isPictureFlag()) {//????????????
                    tag = currentposition;
                    if (processDtos.get(currentposition).isAlbumFlag()) {
                        presenter.showCamera(mContext);
                    } else {
                        showCameraAction();
                    }

                } else {//???????????????
                    dialog = new NormalDialog(mContext).isTitleShow(false)
                            .content("??????" + processDtos.get(currentposition).getName() + "??????")
                            .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                            .btnNum(2).btnText("??????", "??????");
                    dialog.setOnBtnClickL(
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    if (!context.isDestroy(context)) {
                                        dialog.dismiss();
                                    }
                                }
                            },
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    presenter.daka(yundanId, opertype, currenrtLocation, "");
                                    if (!context.isDestroy(context)) {
                                        dialog.dismiss();
                                    }
                                }
                            });
                    dialog.show();
                }
            }
            if (currentposition == 0) {//????????????
                if (getUserInfoDto(userInfoDto) == null) {
                    return;
                }
                if (trackApp.mClient == null) {
                    return;
                }
                String monitoredPerson = getUserInfoDto(userInfoDto).getRoleId();//??????ID
                FenceListRequest fenceListRequest = FenceListRequest.buildServerRequest(trackApp.getTag(), trackApp.serviceId,
                        monitoredPerson, null, CoordType.bd09ll, 0, 0);
                trackApp.mClient.queryFenceList(fenceListRequest, mFenceListener);
            }
        }
        //??????????????????end
    }
    /****
     * ???????????????????????? 0??????  1 ????????????  ????????????
     * ***/
    @Override
    public void showCamera(int type) {
        if(type==0){
            showCameraAction();
        }else if(type==1){
            FunctionConfig functionConfig  = GalleryUtils.getFbdtFunction(1);
            GalleryFinal.openGalleryMuti(1001,
                    functionConfig, mOnHanlderResultCallback);
        }
    }
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback =
            new GalleryFinal.OnHanlderResultCallback() {
                @Override
                public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
                    if (resultList != null||resultList.size()>0) {
                        if(currenrtLocation==null){
                            return;
                        }
                        Bundle bundle=new Bundle();
                        bundle.putString("adress", currenrtLocation.getAddress().address + "(" + currenrtLocation.getLongitude()+","+currenrtLocation.getLatitude() + ")");
                        bundle.putString("photoPath",resultList.get(0).getPhotoPath());
                        bundle.putString("distance", "");
                        bundle.putString("toadress", "");
                        bundle.putString("country", "");
                        bundle.putString("city", "");
                        bundle.putString("fromtype", "other");
                        bundle.putString("location",currenrtLocation.getLongitude()+","+currenrtLocation.getLatitude());
                        bundle.putString("adresschange", currenrtLocation.getAddress().address);
                        readyGoForResult(ViewPhoto.class, WaterPic, bundle);
                    }
                }
                @Override
                public void onHanlderFailure(int requestCode, String errorMsg) {
                    context.showMessage(errorMsg);
                }
            };
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
        context.showMessage(str);
    }

    @Override
    public void oncomplete() {
        presenter.getData(yundanId,2);

        if(processDtos.size()<=currentposition){
            if(currentposition==1){
                EventBus.getDefault().post(Constants.reshAuroAdto);
            }
        }else {
            if(processDtos.get(currentposition).getCode()==1){
                if(highEnclosureAlarm==1){
                    EventBus.getDefault().post(Constants.reshAuroAdto);
                }
            }else {
                if(currentposition==1){
                    EventBus.getDefault().post(Constants.reshAuroAdto);
                }
            }
        }
    }
    private  void Uploadpic(File file){
        if(file==null){
            context.showMessage("????????????????????????");
            return;
        }
        context.showLoadingDialog("???????????????...");
        final RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), file);
        //files ????????????
        MultipartBody.Part part=
                MultipartBody.Part.createFormData("picture", file.getName(), requestBody);
        context.authApi.loadImg(part).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {
                context.dismissLoadingDialog();
                presenter.daka(yundanId,processDtos.get(tag).getCode()+"",currenrtLocation,response.getUrl());
            }
            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
                context.dismissLoadingDialog();
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(baiduMap!=null){
            baiduMap.clear();
        }
        if(mapView!=null){
            mapView.onDestroy();
        }
    }
    int isRunning=0;
    // ????????????????????????
    OnFenceListener mFenceListener = new OnFenceListener() {
        // ??????????????????
        @Override
        public void onCreateFenceCallback(CreateFenceResponse response) {
            if(response.getTag()==6){
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(isRunning==0){
                            startTimer();
                        }
                        isRunning++;
                    }
                }, 5000);//3????????????Runnable??????run??????
            }
        }
        // ??????????????????
        @Override
        public void onUpdateFenceCallback(UpdateFenceResponse response) {
        }
        // ??????????????????
        @Override
        public void onDeleteFenceCallback(DeleteFenceResponse response) {
            if(weiLanDto!=null){
                startWeiLan(weiLanDto);
            }
        }
        // ??????????????????
        @Override
        public void onFenceListCallback(FenceListResponse response) {
            if(response==null||response.getFenceInfos()==null){
                if(weiLanDto!=null){
                    startWeiLan(weiLanDto);
                }
                return;
            }
            if (FenceType.server == response.getFenceType()) {
                List<Long> deleteFenceIds = new ArrayList<>();
                List<FenceInfo> infos= response.getFenceInfos();
                for(int i=0;i<infos.size();i++){
                    if(infos.get(i).getFenceShape()== FenceShape.circle){
                        CircleFence circleFence = infos.get(i).getCircleFence();
                        if(!circleFence.getFenceName().contains("yd")){
                            deleteFenceIds.add(circleFence.getFenceId());
                        }
                    }else if(infos.get(i).getFenceShape()== FenceShape.polygon){
                        PolygonFence polygonFence = infos.get(i).getPolygonFence();
                        if(!polygonFence.getFenceName().contains("yd")){
                            deleteFenceIds.add(polygonFence.getFenceId());
                        }
                    } else if(infos.get(i).getFenceShape()== FenceShape.district){
                        DistrictFence districtFence = infos.get(i).getDistrictFence();
                        if(!districtFence.getFenceName().contains("yd")){
                            deleteFenceIds.add(districtFence.getFenceId());
                        }
                    }else if(infos.get(i).getFenceShape()== FenceShape.polyline){
                        PolylineFence districtFence = infos.get(i).getPolylineFence();
                        deleteFenceIds.add(districtFence.getFenceId());
                    }
                }
                if(trackApp.mClient==null){
                    return;
                }
                DeleteFenceRequest deleteRequest = DeleteFenceRequest.buildServerRequest
                        (trackApp.getTag(),trackApp.serviceId, trackApp.entityName, deleteFenceIds);
                //????????????????????????
                trackApp.mClient.deleteFence(deleteRequest , mFenceListener);

            }
        }
        // ??????????????????
        @Override
        public void onMonitoredStatusCallback(MonitoredStatusResponse
                                                      response) {}
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
        }

        @Override
        public void onListMonitoredPersonCallback(ListMonitoredPersonResponse listMonitoredPersonResponse) {

        }
    };
    public static com.baidu.trace.model.LatLng convertMap2Trace(LatLng latLng) {
        return new com.baidu.trace.model.LatLng(latLng.latitude, latLng.longitude);
    }
    /***
     * ????????????
     * **/
    private void uploadStay(String waybillId){

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id","");//??????ID
            jsonObject.put("waybillId",waybillId);//??????id
            jsonObject.put("warnType",3);
            jsonObject.put("second","");
            jsonObject.put("location",currenrtLocation.getLongitude()+","+
                    currenrtLocation.getLatitude());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        context.orderApi.uplaodStayTime(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
            }
            @Override
            public void fail(String code, String message) {
            }
        });
    }
    private void  startWeiLan(TrantDto dto){
        // ????????????
        int tag = 0;
        // ????????????ID
        long serviceId = trackApp.serviceId;
        // ????????????
        CoordType coordType = CoordType.bd09ll;
        // ????????????????????????????????????
        CreateFenceRequest  request=null;
        String fenceName;// ????????????
        double radius;
        int denoise;
        if(getUserInfoDto(userInfoDto)==null){
            return;
        }
        if(trackApp.mClient==null){
            return;
        }
        String monitoredPerson = getUserInfoDto(userInfoDto).getRoleId();//??????ID
        /**
         * ????????????????????????????????????
         */
        List<com.baidu.trace.model.LatLng> traceVertexes = new ArrayList<>();
        for(int i=0;i<dto.getWaybillList().size();i++){
            try {
                tag=6;
                fenceName=dto.getWaybillList().get(i).getId() ;//??????ID
                wayBillId=dto.getWaybillList().get(i).getWayBillId();
                // ????????????
                fenceName="xx"+fenceName;
                denoise= dto.getWaybillList().get(i).getToErrorRange();
                if(dto.getWaybillList().get(i).getDeviationAlarm()==1){//??????????????????????????????(1-??? 2-???)
                    if(dto.getWaybillList().get(i).getPath()!=null){
                        if(dto.getWaybillList().get(i).getPath().length<=0){
                            return;
                        }
                        String [] rolePath=dto.getWaybillList().get(i).getPath();
                        int wlNum = (int) Math.ceil(dto.getWaybillList().get(i).getDistance()/100);//????????????
                        int count = (int) Math.floor(rolePath.length / wlNum);//???????????????????????????
                        for(int k=0;k<wlNum;k++) {
                            String[] strs1 = Arrays.copyOfRange(rolePath, k * count, count * (k + 1));
                            double step = Math.floor(strs1.length / 100);
                            if(step<=0){
                                step=1;
                            }
                            for (int n = 0; n < strs1.length; n +=step) {
                                String[] lang = strs1[n].split(",");
                                LatLng center = new LatLng(Double.parseDouble(lang[1]), Double.parseDouble(lang[0]));
                                if (traceVertexes.size() <= 99) {
                                    traceVertexes.add(convertMap2Trace(center));
                                }
                            }
                            request = CreateFenceRequest.buildServerPolylineRequest(tag, trackApp.serviceId,
                                    fenceName, monitoredPerson, traceVertexes, 200, denoise,
                                    CoordType.bd09ll);
                            if (trackApp.mClient != null) {
                                trackApp.mClient.createFence(request, mFenceListener);
                            }
                        }
                    }
                }
            }catch (Exception e){
            }
            fenceName=dto.getWaybillList().get(i).getId() ;//??????ID
            if(dto.getWaybillList().get(i).getToAddressType()==2){//????????????
                if(dto.getWaybillList().get(i).getEnclosureStatus()==1){
                    tag=3;
                    // ????????????????????? : ??????
                    radius= dto.getWaybillList().get(i).getToRadius();
                    // ????????????
                    denoise= dto.getWaybillList().get(i).getToErrorRange();
                    if(dto.getWaybillList().get(i).getToEnclosureType()==1){//??????
//                          // ????????????
                        String locol=dto.getWaybillList().get(i).getToLocation();
                        String[] node = locol.split(",");
                        LatLng center = new LatLng(Double.parseDouble(node[1]), Double.parseDouble(node[0]));
                        request= CreateFenceRequest.buildServerCircleRequest(tag, trackApp.serviceId, fenceName,
                                monitoredPerson, convertMap2Trace(center), radius, denoise,
                                coordType);
                        // ????????????????????????
                        if(trackApp.mClient!=null){
                            trackApp.mClient.createFence(request, mFenceListener);
                        }
                        // ???????????????????????????
                    }else if(dto.getWaybillList().get(i).getToEnclosureType()==2){//????????????
                        tag=4;
                        String locol=dto.getWaybillList().get(i).getToRegion();
                        if(!TextUtils.isEmpty(locol)){
                            request = CreateFenceRequest.buildServerDistrictRequest(tag,
                                    serviceId, fenceName, monitoredPerson, locol, denoise);
                            if(trackApp.mClient!=null){
                                trackApp.mClient.createFence(request, mFenceListener);
                            }
                        }
                    }else if(dto.getWaybillList().get(i).getToEnclosureType()==3){//3?????????
                        Log.e("msg","???????????????????????????");
                        traceVertexes.clear();
                        tag=5;
                        String locol=dto.getWaybillList().get(i).getToRegion();
                        if(!TextUtils.isEmpty(locol)){
                            String[] node = locol.split(";");
                            for(int j=0;j<node.length;j++){
                                String[] lang = node[j].split(",");
                                LatLng center = new LatLng(Double.parseDouble(lang[1]), Double.parseDouble(lang[0]));
                                traceVertexes.add(convertMap2Trace(center));
                            }
                            request = CreateFenceRequest.buildServerPolygonRequest(tag, trackApp.serviceId, fenceName
                                    , monitoredPerson, traceVertexes, denoise, coordType);
                            if(trackApp.mClient!=null){
                                trackApp.mClient.createFence(request, mFenceListener);
                            }
                        }

                    }
                }
            }
        }
    }
    private void startTimer() {
        if (onLineTimer != null) {
            onLineTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    presenter.getLocation("");
                    if(getUserInfoDto(userInfoDto)==null){
                        return;
                    }
                    if(trackApp.mClient==null){
                        return;
                    }
                    FenceListRequest fenceListRequest =FenceListRequest.buildServerRequest(trackApp.getTag(), trackApp.serviceId,
                            getUserInfoDto(userInfoDto).getRoleId(), null, CoordType.bd09ll, 0, 0);
                    trackApp.mClient.queryFenceList(fenceListRequest,mFenceListener1);
                }
            }, 2000, 5*60*1000);// ???????????????,???2????????????,????????????5?????????????????????
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
        public void onDeleteFenceCallback(DeleteFenceResponse response) {}
        // ??????????????????
        @Override
        public void onFenceListCallback(FenceListResponse response) {
            if(response==null||response.getFenceInfos()==null){
                return;
            }
            List<Long> deleteFenceIds = new ArrayList<>();
            List<FenceInfo> infos= response.getFenceInfos();
            for(int i=0;i<infos.size();i++){
                if(infos.get(i).getFenceShape()== FenceShape.polyline){
                    PolylineFence districtFence = infos.get(i).getPolylineFence();
                    deleteFenceIds.add(districtFence.getFenceId());
                }
            }
            if(deleteFenceIds.size()>0) {
                if(currenrtLocation==null){
                    context.showMessage("??????????????????????????????");
                    return;
                }
                if(getUserInfoDto(userInfoDto)==null){
                    return;
                }
                if(trackApp.mClient==null){
                    return;
                }
                com.baidu.trace.model.LatLng latLng=new com.baidu.trace.model.LatLng(currenrtLocation.getLatitude(),currenrtLocation.getLongitude());
                MonitoredStatusByLocationRequest request = MonitoredStatusByLocationRequest.buildServerRequest
                        (6, trackApp.serviceId, getUserInfoDto(userInfoDto).getRoleId(), deleteFenceIds, latLng, CoordType.bd09ll);
                trackApp.mClient.queryMonitoredStatusByLocation(request, mFenceListener1);
            }
        }
        // ??????????????????
        @Override
        public void onMonitoredStatusCallback(MonitoredStatusResponse
                                                      response) {}
        // ??????????????????????????????
        @Override
        public void onMonitoredStatusByLocationCallback(MonitoredStatusByLocationResponse response) {
            if(response==null||response.getMonitoredStatusInfos()==null){
                return;
            }
            //????????????????????????????????????
            List<MonitoredStatusInfo> monitoredStatusInfos = response.getMonitoredStatusInfos();
            int temp=0;
            for (MonitoredStatusInfo monitoredStatusInfo : monitoredStatusInfos) {
                monitoredStatusInfo.getFenceId();
                MonitoredStatus status = monitoredStatusInfo.getMonitoredStatus();//????????????
                if (status == MonitoredStatus.in) {
                    temp++;
                }
            }
            if (temp == 0) {
                uploadStay(wayBillId);
            }
        }
        // ??????????????????
        @Override
        public void onHistoryAlarmCallback(HistoryAlarmResponse response) {}
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
}
