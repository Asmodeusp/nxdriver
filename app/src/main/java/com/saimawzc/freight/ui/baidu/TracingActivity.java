package com.saimawzc.freight.ui.baidu;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.api.entity.EntityListRequest;
import com.baidu.trace.api.entity.EntityListResponse;
import com.baidu.trace.api.entity.FilterCondition;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.entity.SearchResponse;
import com.baidu.trace.api.track.HistoryTrackRequest;
import com.baidu.trace.api.track.HistoryTrackResponse;
import com.baidu.trace.api.track.LatestPointResponse;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.api.track.SupplementMode;
import com.baidu.trace.api.track.TrackPoint;
import com.baidu.trace.model.CoordType;
import com.baidu.trace.model.ProcessOption;
import com.baidu.trace.model.StatusCodes;
import com.baidu.trace.model.TransportMode;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseApplication;
import com.saimawzc.freight.dto.travel.BeidouTravelDto;
import com.saimawzc.freight.dto.travel.PresupTravelDto;
import com.saimawzc.freight.presenter.travel.TravelPresenter;
import com.saimawzc.freight.ui.baidu.utils.CommonUtil;
import com.saimawzc.freight.ui.baidu.utils.MapUtil;
import com.saimawzc.freight.ui.my.car.RegisterCarFragment;
import com.saimawzc.freight.view.travel.TravelView;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.trace.TraceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
/**
 * 百度鹰眼  追踪轨迹
 */
public class TracingActivity extends BaseActivity
        implements View.OnClickListener , TravelView, CompoundButton.OnCheckedChangeListener {
    private BaseApplication trackApp = null;

    @BindView(R.id.toolbar)Toolbar toolbar;
    private String travelId="";
    /**
     * 地图工具
     */
    private OnTrackListener trackListener = null;
    // 请求标识
    int tag = 1;
    // 轨迹服务ID
    long serviceId ;
    // 设备标识
    String entityName = "";
    @BindView(R.id.tracing_mapView)MapView mapView;
    private BaiduMap baiduMap;
    /**
     * 查询轨迹的开始时间
     */
    private long startTime ;
    /**
     * 查询轨迹的结束时间
     */
    private long endTime ;
    /**
     * 轨迹排序规则
     */
    int pageSize = 1000;
    int page=0;
    private TraceUtils utils;
    // 创建历史轨迹请求实例
    HistoryTrackRequest historyTrackRequest;
    private String type;
    private TravelPresenter presenter;
    private  Overlay overbeidou;
    private List<Overlay>overlayList=new ArrayList<>();
    private  Overlay overyushe;
    @BindView(R.id.check_ys_gj) CheckBox check_Yushe;
    @BindView(R.id.check_bd_gj)CheckBox check_bd;
    @BindView(R.id.check_yy_gj)CheckBox check_yingyan;
    @BindView(R.id.ll_choose) LinearLayout llChoose;
    @Override
    protected int getViewId() {
        return R.layout.activity_tracing;
    }

    @Override
    protected void init() {
        getlocal();
        trackApp = (BaseApplication) getApplicationContext();
        setToolbar(toolbar,"轨迹追踪");
        serviceId=trackApp.serviceId;
        entityName= getIntent().getStringExtra("id");
        type=getIntent().getStringExtra("type");
        presenter=new TravelPresenter(this,mContext);
        try {
            travelId=getIntent().getStringExtra("travelId");
        }catch (Exception e){
        }
        if(!TextUtils.isEmpty(type)){
            if(type.equals("guiji")){
                startTime= (long) getIntent().getDoubleExtra("startTime",0)/1000;
                endTime= (long) getIntent().getDoubleExtra("endTime",0)/1000;
                countTime(startTime,endTime,page);
            }
        }
        baiduMap=mapView.getMap();
        historyTrackRequest = new HistoryTrackRequest(tag, serviceId, entityName);
        // 设置开始时间
        historyTrackRequest.setStartTime(startTime);
        // 设置结束时间
        historyTrackRequest.setEndTime(endTime);
        historyTrackRequest.setPageSize(pageSize);
        // 设置需要纠偏
        historyTrackRequest.setProcessed(true);
        // 创建纠偏选项实例
        ProcessOption processOption = new ProcessOption();
        // 设置需要去噪
        processOption.setNeedDenoise(true);
        // 设置需要抽稀
        processOption.setNeedVacuate(true);
        // 设置需要绑路
        processOption.setNeedMapMatch(true);
        // 设置精度过滤值(定位精度大于100米的过滤掉)
        processOption.setRadiusThreshold(10);
        // 设置交通方式为驾车
        processOption.setTransportMode(TransportMode.driving);
        // 设置纠偏选项
        historyTrackRequest.setProcessOption(processOption);
        // 设置里程填充方式为驾车
        historyTrackRequest.setSupplementMode(SupplementMode.driving);

    }
    OnEntityListener entityListener;
    @Override
    protected void initListener() {
        trackListener = new OnTrackListener() {
            private List<LatLng> trackPoints = new ArrayList<>();
            @Override
            public void onHistoryTrackCallback(HistoryTrackResponse response) {
                super.onHistoryTrackCallback(response);
                int total = response.getTotal();
                StringBuffer sb = new StringBuffer(256);
                if(!travelId.equals("siji")){
                    presenter.getBeiDou(travelId);
                    presenter.getSuptravel(travelId);
                }else {
                    //presenter.getBeiDou(travelId);
                    llChoose.setVisibility(View.GONE);
                }
                if (StatusCodes.SUCCESS != response.getStatus()) {
                } else if (0 == total) {
                    queryHistroy();
                } else {
                    List<TrackPoint> points = response.getTrackPoints();
                    if (null != points) {
                        for (TrackPoint trackPoint : points) {
                            if (!CommonUtil.isZeroPoint(trackPoint.getLocation().getLatitude(),
                                    trackPoint.getLocation().getLongitude())) {
                                trackPoints.add(MapUtil.convertTrace2Map(trackPoint.getLocation()));
                            }
                        }
                    }
                    if(trackPoints.size()<=2){
                        queryHistroy();
                        if(trackPoints.size()>0){
                            LatLng tempPoint = new LatLng(trackPoints.get(0).latitude, trackPoints.get(0).longitude);
                            MapStatus.Builder builder = new MapStatus.Builder();
                            builder.target(tempPoint).zoom(18.0f);
                            baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                        }
                    }else {
                        int center= Math.round(trackPoints.size()/2);
                        LatLng tempPoint = new LatLng(trackPoints.get(center).latitude,
                                trackPoints.get(center).longitude);
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.target(tempPoint).zoom(18.0f);
                        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                        OverlayOptions mOverlayOptions = new PolylineOptions()
                                .width(10)
                                .color(Color.GREEN)
                                .points(trackPoints);
                         Overlay overyingyan = baiduMap.addOverlay(mOverlayOptions);
                        overlayList.add(overyingyan);
                        queryHistroy();
                    }
                }
            }
            @Override
            public void onLatestPointCallback(LatestPointResponse response) {
            }
        };
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });
        List<String>list=new ArrayList<>();
        list.add(entityName);
        FilterCondition filterCondition=new FilterCondition();
        filterCondition.setActiveTime(CommonUtil.getCurrentTime());
        filterCondition.setEntityNames(list);
        EntityListRequest listRequest=new EntityListRequest( tag, serviceId,filterCondition,
                CoordType.bd09ll
                ,  1,  20);
        entityListener = new OnEntityListener() {
            @Override
            public void onEntityListCallback(EntityListResponse arg0) {
                if(arg0!=null){
                    if(arg0.getEntities().size()>0){
                        double latatu=arg0.getEntities().get(0).getLatestLocation().getLocation().getLatitude();
                        double longtatude=arg0.getEntities().get(0).getLatestLocation().getLocation().getLongitude();
                        LatLng point = new LatLng(latatu,longtatude);

                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                .fromResource(R.drawable.map_blue);
                        OverlayOptions option = new MarkerOptions()
                                .position(point)
                                .icon(bitmap);
                        baiduMap.addOverlay(option);
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.target(point).zoom(18.0f);
                        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                    }
                }
            }
            // 查询entity回调接口，返回查询结果列表
            @Override
            public void onSearchEntityCallback(SearchResponse arg0) {
            }
        };

        if(!TextUtils.isEmpty(type)){
            if(type.equals("guiji")){
                if(trackApp.mClient==null){
                    return;
                }
                // 查询轨迹
                if(trackListener!=null&&historyTrackRequest!=null){
                    trackApp.mClient.queryHistoryTrack(historyTrackRequest, trackListener);
                }
            }else {//实时位置
                if(entityListener!=null&&historyTrackRequest!=null){
                    trackApp.mClient.queryEntityList(listRequest,entityListener);
                }
            }
        }else {
            if(entityListener!=null){
                trackApp.mClient.queryEntityList(listRequest,entityListener);
            }
        }

        check_bd.setOnCheckedChangeListener(this);
        check_yingyan.setOnCheckedChangeListener(this);
        check_Yushe.setOnCheckedChangeListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        context.finish();
    }
    @Override
    protected void onGetBundle(Bundle bundle) {
    }
    @Override
    public void onClick(View v) {
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(mapView!=null){
            mapView.onPause();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mapView!=null){
            mapView.onDestroy();
        }
        if(baiduMap!=null){
            baiduMap.clear();
        }
    }
   int totlDays;
    private void countTime(long startsj,long endsj,int page){
        totlDays= (int)(((endsj-startsj)/(24*60 * 60)));
        if(totlDays==0){//总共只有一天
            startTime=startsj;
            endTime=endsj;
        }else {//总时长超过一天
            if(page<=totlDays){
                startTime=startsj+24*( 60 * 60)*page;
                endTime=startTime+24*(60 * 60);
                if(endTime>endsj){
                    endTime=endsj;
                }
            }
        }
    }
    private void queryHistroy(){
        if(totlDays>page){
            page++;
            countTime((long) getIntent().getDoubleExtra("startTime",0)/1000,
                    (long) getIntent().getDoubleExtra("endTime",0)/1000,
                    page);
            historyTrackRequest = new HistoryTrackRequest(tag, serviceId, entityName);
            // 设置开始时间
            historyTrackRequest.setStartTime(startTime);
            // 设置结束时间
            historyTrackRequest.setEndTime(endTime);
            historyTrackRequest.setPageSize(pageSize);
            // 设置需要纠偏
            historyTrackRequest.setProcessed(true);
            // 创建纠偏选项实例
            ProcessOption processOption = new ProcessOption();
            // 设置需要去噪
            processOption.setNeedDenoise(true);
            // 设置需要抽稀
            processOption.setNeedVacuate(true);
            // 设置需要绑路
            processOption.setNeedMapMatch(true);
            // 设置精度过滤值(定位精度大于100米的过滤掉)
            processOption.setRadiusThreshold(100);
            // 设置交通方式为驾车
            processOption.setTransportMode(TransportMode.driving);
            // 设置纠偏选项
            historyTrackRequest.setProcessOption(processOption);
            // 设置里程填充方式为驾车
            historyTrackRequest.setSupplementMode(SupplementMode.driving);
            if(trackListener!=null){
                trackApp.mClient.queryHistoryTrack(historyTrackRequest, trackListener);
            }
        }
    }

    private int isShow=0;
    private NormalDialog dialog;
    @Override
    public void getBeiDouTravel(BeidouTravelDto dto) {
        if(dto==null){
            if(overbeidou==null&&overlayList.size()==0){
                    dialog = new NormalDialog(mContext).isTitleShow(true)
                            .title("提示")
                            .content("未获取到轨迹信息，请检查设备是否正常")
                            .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                            .btnNum(1).btnText("确定");
                    dialog.setOnBtnClickL(
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    if (!context.isDestroy(context)) {
                                        dialog.dismiss();
                                    }
                                }
                            });
                    if (dialog != null) {
                        dialog.show();
                    }
            }
            return;
        }
        if(dto!=null){
            List<LatLng> allPointList = new ArrayList<LatLng>();
            for(int i=0;i<dto.getTrackInfo().size();i++){
                LatLng latLng=new LatLng(dto.getTrackInfo().get(i).getLat(),dto.getTrackInfo().get(i).getLon());
                allPointList.add(latLng);
            }
            if(allPointList.size()>=2){
                OverlayOptions mOverlayOptions1 = new PolylineOptions()
                        .width(10)
                        .color(Color.RED)
                        .points(allPointList);
                 overbeidou = baiduMap.addOverlay(mOverlayOptions1);

                 if( isShow<=0){
                     if(allPointList.size()>0){
                         LatLng tempPoint = new LatLng(allPointList.get(0).latitude, allPointList.get(0).longitude);
                         MapStatus.Builder builder = new MapStatus.Builder();
                         builder.target(tempPoint).zoom(18.0f);
                         baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                         isShow++;
                     }
                 }

            }
        }
        if(overbeidou==null&&overlayList.size()==0){
                dialog = new NormalDialog(mContext).isTitleShow(true)
                        .title("提示")
                        .content("未获取到轨迹信息，请检查设备是否正常")
                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                        .btnNum(1).btnText("确定");
                dialog.setOnBtnClickL(
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if (!context.isDestroy(context)) {
                                    dialog.dismiss();
                                }
                            }
                        });
                if (dialog != null) {
                    dialog.show();
                }

        }

    }

    @Override
    public void getPreSupTravel(PresupTravelDto dto) {
        if(dto!=null){
            List<LatLng> allPointList = new ArrayList<LatLng>();
            for(int i=0;i<dto.getPath().length;i++){
                String str=dto.getPath()[i];
                String[] statrNode = str.split(",");
                LatLng startPoint = new LatLng(Double.parseDouble(statrNode[1]),Double.parseDouble(statrNode[0]));
                allPointList.add(startPoint);
            }
            if(allPointList.size()>=2){
                OverlayOptions mOverlayOptions = new PolylineOptions()
                        .width(10)
                        .color(Color.BLUE)
                        .points(allPointList);
                   overyushe = baiduMap.addOverlay(mOverlayOptions);
                    try{
                        LatLng tempPoint = new LatLng(allPointList.get(0).latitude, allPointList.get(0).longitude);
                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                .fromResource(R.drawable.ico_map_start);
                        OverlayOptions option = new MarkerOptions()
                                .position(tempPoint)
                                .icon(bitmap);
                        baiduMap.addOverlay(option);
                    }catch (Exception e){

                    }
                    try{
                        LatLng tempPoint1 = new LatLng(allPointList.get(allPointList.size()-1).latitude, allPointList.get(allPointList.size()-1).longitude);
                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                .fromResource(R.drawable.icon_end);
                        //构 建MarkerOption，用于在地图上添加Marker
                        OverlayOptions option = new MarkerOptions()
                                .position(tempPoint1)
                                .icon(bitmap);
                        //在                   地图上添加Marker，并显示
                        baiduMap.addOverlay(option);
                    }catch (Exception w){

                    }

                }
                if( isShow<=0){
                    if(allPointList.size()>0){
                        LatLng tempPoint = new LatLng(allPointList.get(0).latitude, allPointList.get(0).longitude);
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.target(tempPoint).zoom(18.0f);
                        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                        isShow++;
                    }
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
        context.showMessage(str);
    }

    @Override
    public void oncomplete() {
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
        if(check_Yushe==compoundButton){
            if(overyushe==null){
                return;
            }
            if(check){
                overyushe.setVisible(true);
            }else {
                overyushe.setVisible(false);
            }
        }else if(check_yingyan==compoundButton){
            if(overlayList==null||overlayList.size()<=0){
                return;
            }
            for(int i=0;i<overlayList.size();i++){
                if(check){
                    overlayList.get(i).setVisible(true);
                }else {
                    overlayList.get(i).setVisible(false);
                }
            }
        }else if(check_bd==compoundButton){
            if(overbeidou==null){
                return;
            }
            if(check){
                overbeidou.setVisible(true);
            }else {
                overbeidou.setVisible(false);
            }
        }
    }

    /***
     * 获取经纬度
     * **/
    public  void getlocal(){

        mLocationClient = new LocationClient(mContext);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true
        option.setNeedNewVersionRgc(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        //可选，设置是否需要最新版本的地址信息。默认需要，即参数为true
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        if(Build.VERSION.SDK_INT >= 26){
            if(!mLocationClient.isStarted()){
                mLocationClient.restart();
            }
        }
    }
    public LocationClient mLocationClient = null;

    private MyLocationListener myListener = new MyLocationListener();
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            context.dismissLoadingDialog();
            if (location == null) {
                context.showMessage("定位失败");
                return;
            }else if(location.getLocType()==62){
                context.showMessage("定位失败");
                return;
            }
            LatLng tempPoint = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(tempPoint).zoom(18.0f);
            baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            if(mLocationClient!=null){
                mLocationClient.stop();
            }
        }
    }
}
