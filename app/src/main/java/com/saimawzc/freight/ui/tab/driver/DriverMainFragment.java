package com.saimawzc.freight.ui.tab.driver;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.trace.api.fence.MonitoredStatus;
import com.baidu.trace.api.fence.MonitoredStatusByLocationRequest;
import com.baidu.trace.api.fence.MonitoredStatusInfo;
import com.baidu.trace.api.fence.PolylineFence;
import com.hdgq.locationlib.LocationOpenApi;
import com.hdgq.locationlib.listener.OnResultListener;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.order.mainindex.RobOrderAdapter;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.order.mainindex.RobOrderDto;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.ui.order.waybill.manage.OrderManageMapActivity;
import com.saimawzc.freight.weight.NoData;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.overtimer.OverallTimer;
import com.saimawzc.freight.weight.utils.LoadMoreListener;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.baidu.mapapi.model.LatLng;
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
import com.baidu.trace.api.fence.MonitoredStatusByLocationResponse;
import com.baidu.trace.api.fence.MonitoredStatusResponse;
import com.baidu.trace.api.fence.OnFenceListener;
import com.baidu.trace.api.fence.PolygonFence;
import com.baidu.trace.api.fence.UpdateFenceResponse;
import com.baidu.trace.model.CoordType;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseApplication;
import com.saimawzc.freight.base.BaseImmersionFragment;
import com.saimawzc.freight.dto.order.NeedOpenFenceDto;
import com.saimawzc.freight.presenter.drivermain.DriverMainPresent;
import com.saimawzc.freight.view.drivermain.DriverMainView;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import com.saimawzc.freight.weight.utils.trace.TraceUtils;
import com.gyf.immersionbar.ImmersionBar;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/7/31.
 * 首页
 */
public class DriverMainFragment extends BaseImmersionFragment
        implements DriverMainView {
    @BindView(R.id.SwipeRefreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.cv) RecyclerView rv;
    private RobOrderAdapter adapter;
    private List<RobOrderDto.robOrderData>mDatas=new ArrayList<>();
    private int page=1;
    private LoadMoreListener loadMoreListener;
    private DriverMainPresent present;
    private BaseApplication trackApp;
    private  BDLocation currenrtLocation;
    private  String wayBillId;
    @BindView(R.id.rlTop) RelativeLayout rlTop;
    @BindView(R.id.nodata) NoData noData;

    @Override
    public int initContentView() {
        return R.layout.driver_main;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        userInfoDto= Hawk.get(PreferenceKey.USER_INFO);
        trackApp= (BaseApplication) mContext.getApplicationContext();
        adapter=new RobOrderAdapter(mDatas,mContext);
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        setNeedOnBus(true);
        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                present.getData(page);
            }
        };
        rv.setOnScrollListener(loadMoreListener);
        present=new DriverMainPresent(this,mContext);
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                present.getNeedFenceList();
                present.getData(page);
            }
        });
    }
    @Override
    public void initData() {
        adapter.setOnTabClickListener(new RobOrderAdapter.OnTabClickListener() {
            @Override
            public void onItemClick(String type, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle;
                int wayBillStatus=mDatas.get(position).getWaybillType();
                if(wayBillStatus==2){//预运单
                    if(type.equals("tab1")){//清单
                        bundle =new Bundle();
                        bundle.putString("type","rob");
                        bundle.putString("id",mDatas.get(position).getWaybillId());
                        bundle.putSerializable("data",mDatas.get(position));
                        bundle.putString("from","biddqingdan");
                        readyGo(OrderMainActivity.class,bundle);
                    }else if(type.equals("tab2")){//参与竞价
                        bundle =new Bundle();
                        bundle.putString("type",mDatas.get(position).getWaybillType()+"");
                        bundle.putString("id",mDatas.get(position).getId());
                        bundle.putString("endTime",mDatas.get(position).getEndTime());
                        bundle.putString("from","joinbidd");
                        readyGo(OrderMainActivity.class,bundle);
                    }
                }else {//参与竞价
                    bundle =new Bundle();
                    bundle.putString("type",mDatas.get(position).getWaybillType()+"");
                    bundle.putString("id",mDatas.get(position).getId());
                    bundle.putString("endTime",mDatas.get(position).getEndTime());
                    bundle.putString("from","joinbidd");
                    readyGo(OrderMainActivity.class,bundle);
                }
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                present.getData(page);
            }
        });
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle;
                if(mDatas.get(position).getWaybillType()==3){//调度单
                    bundle=new Bundle();
                    bundle.putString("id",mDatas.get(position).getWaybillId());
                    readyGo(OrderManageMapActivity.class,bundle);
                }else {
                    bundle =new Bundle();
                    bundle.putString("from","orderdelation");
                    bundle.putString("id",mDatas.get(position).getWaybillId());
                    bundle.putString("type","delation");
                    bundle.putInt("waybillstatus",mDatas.get(position).getWaybillType());
                    readyGo(OrderMainActivity.class,bundle);
                }
            }
            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }
    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).titleBar(rlTop).
                navigationBarColor(R.color.bg).
                statusBarDarkFont(true).init();
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
                context.showMessage(str);
            }
        }else {
             context.showMessage(str);
            if(mDatas.size()!= 0 && mDatas != null) {
                noData.setVisibility(View.GONE);
            }else {
                noData.setVisibility(View.VISIBLE);
            }
        }

    }
    @Override
    public void oncomplete() {
    }
    private NeedOpenFenceDto datas;
    TraceUtils  utils;
    @Override
    public void getNeedFence(final NeedOpenFenceDto dto) {

        if(dto==null||dto.getData().size()<=0){
            return;
        }
        if(userInfoDto==null){
            return;
        }
        Hawk.put(PreferenceKey.CURRENT_CAR_NO,dto.getCarNo());
        if(utils==null){
            utils =new TraceUtils((BaseApplication)
                    mContext.getApplicationContext(),context);
        }
        utils.startSercive();
        utils.startRealTimeLoc(30);

        if(dto.getData()!=null&&dto.getData().size()>0){
            utils.setAlarmTime(dto.getData().get(0).getAlarmTime());
            utils.setStopArlm(dto.getData().get(0).getStopAlarm());
            utils.setWayBillId(dto.getData().get(0).getWayBillId());
            OverallTimer.getInstance().startTimer();
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                datas=dto;
                if(trackApp==null){
                    return;
                }
                if(trackApp.mClient==null){
                    return;
                }
                if(userInfoDto!=null){
                    String monitoredPerson = userInfoDto.getRoleId();//司机ID
                    if(TextUtils.isEmpty(monitoredPerson)){
                        return;
                    }
                    FenceListRequest fenceListRequest  =FenceListRequest.buildServerRequest(trackApp.getTag(), trackApp.serviceId,
                            monitoredPerson, null, CoordType.bd09ll, 0, 0);
                    trackApp.mClient.queryFenceList(fenceListRequest,mFenceListener);
                }

            }
        }, 3000);//3秒后执行Runnable中的run方
    }

    @Override
    public void getPlanOrderList(List<RobOrderDto.robOrderData> dtos) {
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
            if(dtos==null||dtos.size()<=0){
                noData.setVisibility(View.VISIBLE);
            }else {
                noData.setVisibility(View.GONE);
            }
        }
        adapter.addMoreData(dtos);
    }
    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);
    }
    @Override
    public void isLastPage(boolean isLastPage) {
        if(isLastPage){
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }else {
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }
    }

    int isRuning=0;
    // 初始化围栏监听器
    OnFenceListener mFenceListener = new OnFenceListener() {
        // 创建围栏回调
        @Override
        public void onCreateFenceCallback(CreateFenceResponse response) {
            Log.e("msg","创建围栏成功"+response.toString());
            if(response==null){
                return;
            }
            if(response.getTag()==6){
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(isRuning==0){
                            startTimer();
                        }
                        isRuning++;
                    }
                }, 3000);//3秒后执行Runnable中的run方法
            }
        }
        // 更新围栏回调
        @Override
        public void onUpdateFenceCallback(UpdateFenceResponse response) {
        }
        // 删除围栏回调
        @Override
        public void onDeleteFenceCallback(DeleteFenceResponse response) {
            startWeiLan();
            if(response==null){
                return;
            }
        }
        // 围栏列表回调
        @Override
        public void onFenceListCallback(FenceListResponse response) {
            if(response==null||response.getFenceInfos()==null){
                startWeiLan();
                return;
            }
            if (response.getSize()>=0) {
                if (FenceType.server == response.getFenceType()) {
                    List<Long> deleteFenceIds = new ArrayList<>();
                    if(response.getFenceInfos()==null||response.getFenceInfos().size()<=0){
                        startWeiLan();
                        return;
                    }
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
                    DeleteFenceRequest deleteRequest = DeleteFenceRequest.buildServerRequest
                            (trackApp.getTag(),trackApp.serviceId, trackApp.entityName, deleteFenceIds);
                    //发起删除围栏请求
                    trackApp.mClient.deleteFence(deleteRequest , mFenceListener);
                }
            }
            List<Long> deleteFenceIds = new ArrayList<>();
            if(response.getFenceInfos()==null||response.getFenceInfos().size()<=0){
                return;
            }
            List<FenceInfo> infos= response.getFenceInfos();
            for(int i=0;i<infos.size();i++){
                if(infos.get(i).getFenceShape()== FenceShape.polyline){
                    PolylineFence districtFence = infos.get(i).getPolylineFence();
                    deleteFenceIds.add(districtFence.getFenceId());
                }
            }
            if(deleteFenceIds.size()>0) {
                if(currenrtLocation==null){
                    context.showMessage("未获取到当前位置信息");
                    return;
                }
                if(context.isEmptyUserInfo(userInfoDto)){
                    return;
                }
                com.baidu.trace.model.LatLng latLng=new com.baidu.trace.model.LatLng(currenrtLocation.getLatitude(),currenrtLocation.getLongitude());
                MonitoredStatusByLocationRequest request = MonitoredStatusByLocationRequest.buildServerRequest
                        (6, trackApp.serviceId, userInfoDto.getRoleId(), deleteFenceIds, latLng, CoordType.bd09ll);
                trackApp.mClient.queryMonitoredStatusByLocation(request, mFenceListener1);
            }
        }
        // 监控状态回调
        @Override
        public void onMonitoredStatusCallback(MonitoredStatusResponse response) {}
        // 指定位置监控状态回调
        @Override
        public void onMonitoredStatusByLocationCallback(MonitoredStatusByLocationResponse
                                                                response) {
            if(response==null||response.getMonitoredStatusInfos()==null){
                return;
            }
            if(response.getMonitoredStatusInfos().size()<=0){
                return;
            }
            Log.e("TAG", "11111" );
            //查询监控对象状态响应结果
            List<MonitoredStatusInfo> monitoredStatusInfos = response.getMonitoredStatusInfos();
            int temp=0;
            for (MonitoredStatusInfo monitoredStatusInfo : monitoredStatusInfos) {
                monitoredStatusInfo.getFenceId();
                MonitoredStatus status = monitoredStatusInfo.getMonitoredStatus();//获取状态
                if (status == MonitoredStatus.in) {
                    temp++;
                }
            }
            Log.e("temp", temp+"" );
            if (temp == 0) {
                uploadStay(wayBillId);
            }
        }
        // 历史报警回调
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

    public  com.baidu.trace.model.LatLng convertMap2Trace(LatLng latLng) {
        try{
            return new com.baidu.trace.model.LatLng(latLng.latitude, latLng.longitude);
        }catch (Exception e){

        }
        return null;

    }

    // 初始化围栏监听器
    OnFenceListener mFenceListener1 = new OnFenceListener() {
        // 创建围栏回调
        @Override
        public void onCreateFenceCallback(CreateFenceResponse response) {
            Log.e("msg","创建围栏成功1111"+response.toString());
        }
        // 更新围栏回调
        @Override
        public void onUpdateFenceCallback(UpdateFenceResponse response) {
        }
        // 删除围栏回调
        @Override
        public void onDeleteFenceCallback(DeleteFenceResponse response) {}
        // 围栏列表回调
        @Override
        public void onFenceListCallback(FenceListResponse response) {
            if(response==null||response.getFenceInfos()==null){
                return;
            }
            List<Long> deleteFenceIds = new ArrayList<>();
            if(response.getFenceInfos()==null||response.getFenceInfos().size()<=0){
                return;
            }
            List<FenceInfo> infos= response.getFenceInfos();
            for(int i=0;i<infos.size();i++){
                if(infos.get(i).getFenceShape()== FenceShape.polyline){
                    PolylineFence districtFence = infos.get(i).getPolylineFence();
                    deleteFenceIds.add(districtFence.getFenceId());
                }
            }
            if(deleteFenceIds.size()>0) {
                if(currenrtLocation==null){
                    context.showMessage("未获取到当前位置信息");
                    return;
                }
                if(context.isEmptyUserInfo(userInfoDto)){
                    return;
                }
                com.baidu.trace.model.LatLng latLng=new com.baidu.trace.model.LatLng(currenrtLocation.getLatitude(),currenrtLocation.getLongitude());
                MonitoredStatusByLocationRequest request = MonitoredStatusByLocationRequest.buildServerRequest
                        (6, trackApp.serviceId, userInfoDto.getRoleId(), deleteFenceIds, latLng, CoordType.bd09ll);
                trackApp.mClient.queryMonitoredStatusByLocation(request, mFenceListener1);
            }
        }
        // 监控状态回调
        @Override
        public void onMonitoredStatusCallback(MonitoredStatusResponse
                                                      response) {}
        // 指定位置监控状态回调
        @Override
        public void onMonitoredStatusByLocationCallback(MonitoredStatusByLocationResponse
                                                                response) {
            if(response==null||response.getMonitoredStatusInfos()==null){
                return;
            }
            if(response.getMonitoredStatusInfos().size()<=0){
                return;
            }
            Log.e("TAG", "11111" );
            //查询监控对象状态响应结果
            List<MonitoredStatusInfo> monitoredStatusInfos = response.getMonitoredStatusInfos();
            int temp=0;

            for (MonitoredStatusInfo monitoredStatusInfo : monitoredStatusInfos) {
                monitoredStatusInfo.getFenceId();
                MonitoredStatus status = monitoredStatusInfo.getMonitoredStatus();//获取状态
                if (status == MonitoredStatus.in) {
                    temp++;
                }
            }
            Log.e("temp", temp+"" );
            if (temp == 0) {
                uploadStay(wayBillId);
            }
        }
        // 历史报警回调
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

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            if(location==null||location.getLocType()==62){
                return;

            }
            currenrtLocation=location;
            if(trackApp.mClient==null){
                return;
            }
            if(context.isEmptyUserInfo(userInfoDto)){
                return;
            }
            FenceListRequest fenceListRequest =FenceListRequest.buildServerRequest(trackApp.getTag(), trackApp.serviceId,
                    userInfoDto.getRoleId(), null, CoordType.bd09ll, 0, 0);
            trackApp.mClient.queryFenceList(fenceListRequest,mFenceListener1);
            mLocationClient.stop();
        }
    }
    private Timer onLineTimer = new Timer(true);//在线心跳
    private void startTimer() {
        if (onLineTimer != null) {
            onLineTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(mLocationClient==null){
                        mLocationClient = new LocationClient(mContext);
                    }
                    //声明LocationClient类
                    mLocationClient.registerLocationListener(myListener);
                    //注册监听函数
                    LocationClientOption option = new LocationClientOption();
                    option.setIsNeedAddress(true);
                    //可选，是否需要地址信息，默认为不需要，即参数为false
                    //如果开发者需要获得当前点的地址信息，此处必须为true
                    option.setNeedNewVersionRgc(true);
                    //可选，设置是否需要最新版本的地址信息。默认需要，即参数为true
                    mLocationClient.setLocOption(option);
                    mLocationClient.start();
                    if(Build.VERSION.SDK_INT >= 26){
                        if(!mLocationClient.isStarted()){
                            mLocationClient.restart();
                        }
                    }
                }

            }, 2000, 3*60*1000);// 程序启动后,过2秒再执行,然后每隔5分钟执行一次。
        }
    }
    /***
     * 上传偏离
     * **/
    private void uploadStay(String waybillId){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id","");//司机ID
            jsonObject.put("waybillId",waybillId);//小单id'
            jsonObject.put("warnType",3);
            jsonObject.put("second","");
            jsonObject.put("location",currenrtLocation.getLongitude()+","+
                    currenrtLocation.getLatitude());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.uplaodStayTime(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
//                Log.e("1111111111",response.toString());
            }
            @Override
            public void fail(String code, String message) {
            }
        });
    }
    private void startWeiLan(){
        if(datas==null){
            return;
        }
        // 请求标识
        if(trackApp==null){
            return;
        }
        if(trackApp.mClient==null||userInfoDto==null){
            return;
        }
        if(context.isEmptyUserInfo(userInfoDto)){
            return;
        }
        int tag = 0;
        // 轨迹服务ID
        long serviceId = trackApp.serviceId;
        // 围栏名称
        CoordType coordType = CoordType.bd09ll;
        // 创建本地圆形围栏请求实例
        CreateFenceRequest request=null;
        String fenceName;// 监控对象
        double radius;
        int denoise;
        String monitoredPerson = userInfoDto.getRoleId();//司机ID
        List<com.baidu.trace.model.LatLng> traceVertexes = new ArrayList<>();
        if(datas.getData()==null||datas.getData().size()<=0){
            return;
        }
        for(int i=0;i<datas.getData().size();i++){
            fenceName=datas.getData().get(i).getId() ;//小单ID
            // 围栏半径（单位 : 米）
            radius= datas.getData().get(i).getToRadius();
            // 去噪精度
            denoise= datas.getData().get(i).getToErrorRange();
            try{
                tag=6;
                wayBillId=datas.getData().get(i).getWayBillId();
                fenceName="xx"+fenceName;
                if(datas.getData().get(i).getDeviationAlarm()==1) {//是否开启路线偏离预警(1-是 2-否)
                    if(datas.getData().get(i).getPath()!=null){
                        String[] rolePath = datas.getData().get(i).getPath();
                        int wlNum = (int) Math.ceil(datas.getData().get(i).getDistance()/100);//数组个数
                        int count = (int) Math.floor(rolePath.length / wlNum);//新数组分多少个元素
                        for(int k=0;k<wlNum;k++){
                            String[]strs1= Arrays.copyOfRange(rolePath,k*count,count*(k+1));
                            double step = Math.floor(strs1.length / 100);
                            if(step<=0){
                                step=1;
                            }

                            for (int n = 0; n < strs1.length; n+=step ) {
                                if(n<strs1.length){
                                    String[] lang = strs1[n].split(",");
                                    LatLng center = new LatLng(Double.parseDouble(lang[1]), Double.parseDouble(lang[0]));
                                    if(traceVertexes.size()<=99){
                                        if(convertMap2Trace(center)!=null){
                                            traceVertexes.add(convertMap2Trace(center));
                                        }
                                    }
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
            fenceName=datas.getData().get(i).getId() ;
            if(trackApp.mClient==null){
                return;
            }
            if(datas.getData().get(i).getToEnclosureType()==1){//圆形
                //围栏圆心
                tag=3;
                String locol=datas.getData().get(i).getToLocation();
                String[] node = locol.split(",");
                LatLng center = new LatLng(Double.parseDouble(node[1]), Double.parseDouble(node[0]));
                if(convertMap2Trace(center)!=null){
                    request= CreateFenceRequest.buildServerCircleRequest(tag, trackApp.serviceId, fenceName,
                            monitoredPerson, convertMap2Trace(center), radius, denoise,
                            coordType);
                    // 创建本地圆形围栏
                    trackApp.mClient.createFence(request, mFenceListener);
                    Log.e("msg","创建圆形围栏");
                }
                // 查询围栏监控者状态
            }else if(datas.getData().get(i).getToEnclosureType()==2){//行政区域
                tag=4;
                String locol=datas.getData().get(i).getToLocation();
                request = CreateFenceRequest.buildServerDistrictRequest(tag,
                        serviceId, fenceName, monitoredPerson, locol, denoise);
                trackApp.mClient.createFence(request, mFenceListener);
            }else if(datas.getData().get(i).getToEnclosureType()==3){//3多边形
                tag=5;
                String locol=datas.getData().get(i).getToLocation();
                String[] node = locol.split(";");
                for(int j=0;j<node.length;j++){
                    String[] lang = node[j].split(",");
                    LatLng center = new LatLng(Double.parseDouble(lang[1]), Double.parseDouble(lang[0]));
                    if(convertMap2Trace(center)!=null){
                        traceVertexes.add(convertMap2Trace(center));
                    }
                }
                request = CreateFenceRequest.buildServerPolygonRequest(tag, trackApp.serviceId, fenceName
                        , monitoredPerson, traceVertexes, denoise, coordType);
                trackApp.mClient.createFence(request, mFenceListener);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshDriveMain(String str) {
        if(!TextUtils.isEmpty(str)){
            if(str.equals(Constants.reshChange)||str.equals(Constants.reshCall)){
                Log.e("msg","刷新"+str);
                if(present!=null){
                    present.getNeedFenceList();
                }
                return;
            }
        }
    }


    private void test(String appId,String appSecurity,String enterpriseSenderCode,String environment ){
        //在启动页或 app 首页中，初始化 sdk 服务。context 必须为 activity。
        LocationOpenApi.init(mContext, appId, appSecurity, enterpriseSenderCode, environment, new OnResultListener() {
            @Override
            public void onSuccess() {
                Log.e("msg","成功啦哈哈哈哈哈");
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e("msg","失败啊s="+s+"s1="+s1);
            }
        });
    }
    //test("com.es.freight","bdcbef223b664de3bec6afa0f22e82510469a892b54e40c6a0e1c298546cf196",
    //  "6491640122554181587B","debug");
}
