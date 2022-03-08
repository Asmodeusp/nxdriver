package com.saimawzc.freight.weight.utils.trace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.analysis.DrivingBehaviorResponse;
import com.baidu.trace.api.analysis.OnAnalysisListener;
import com.baidu.trace.api.analysis.StayPoint;
import com.baidu.trace.api.analysis.StayPointRequest;
import com.baidu.trace.api.analysis.StayPointResponse;
import com.baidu.trace.api.entity.AddEntityRequest;
import com.baidu.trace.api.entity.AddEntityResponse;
import com.baidu.trace.api.entity.LocRequest;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.entity.UpdateEntityRequest;
import com.baidu.trace.api.fence.DeleteFenceRequest;
import com.baidu.trace.api.fence.FenceAlarmPushInfo;
import com.baidu.trace.api.fence.MonitoredAction;
import com.baidu.trace.api.track.LatestPoint;
import com.baidu.trace.api.track.LatestPointResponse;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.model.CoordType;
import com.baidu.trace.model.OnCustomAttributeListener;
import com.baidu.trace.model.OnTraceListener;
import com.baidu.trace.model.ProcessOption;
import com.baidu.trace.model.PushMessage;
import com.baidu.trace.model.StatusCodes;
import com.baidu.trace.model.TraceLocation;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseApplication;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.WaybillEncloDto;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.dto.sendcar.DriverTransDto;
import com.saimawzc.freight.ui.baidu.utils.CommonUtil;
import com.saimawzc.freight.ui.baidu.utils.Constants;
import com.saimawzc.freight.ui.baidu.utils.CurrentLocation;
import com.saimawzc.freight.ui.baidu.utils.MapUtil;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.weight.overtimer.OverallTimer;
import com.saimawzc.freight.weight.utils.api.order.OrderApi;
import com.saimawzc.freight.weight.utils.api.tms.TmsApi;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.http.Http;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import static android.content.Context.MODE_PRIVATE;
/**
 *百度鹰眼工具
 ****/
public class TraceUtils {
    private BaseApplication trackApp;
    /**
     * 地图工具
     */
    private MapUtil mapUtil = null;
    private UserInfoDto userInfoDto;
    private String entityName;
    private Timer onLineTimer = new Timer(true);//在线心跳

    private int alartTime;//停留时间
    private int stopAlaem;
    private String wayBllId;
    FenceAlarmPushInfo alarmPushInfo;
    public TmsApi tmsApi= Http.http.createApi(TmsApi.class);
    public OrderApi orderApi= Http.http.createApi(OrderApi.class);
    public String startsTATUS="";
    /**
     * 轨迹分析  停留点集合
     */
    private List<StayPoint> stayPoints = new ArrayList<>();

    private GeoCoder geoCoder;

    public TraceUtils(BaseApplication application,BaseActivity context){
        this.trackApp=application;
        mapUtil = MapUtil.getInstance();
        if(userInfoDto==null||TextUtils.isEmpty(userInfoDto.getRoleId())){
            userInfoDto= Hawk.get(PreferenceKey.USER_INFO);
        }
        if(userInfoDto==null){
            return;
        }
        if(TextUtils.isEmpty(userInfoDto.getRoleId())){
            return;
        }
        entityName=userInfoDto.getRoleId();
        if(trackApp.mClient==null){
            initView();
        }else {
            initTrace();
        }
    }
    /**
     * 轨迹服务监听器
     */
    private OnTraceListener traceListener = null;

    /**
     * 轨迹监听器(用于接收纠偏后实时位置回调)
     */
    private OnTrackListener trackListener = null;

    /**
     * Entity监听器(用于接收实时定位回调)
     */
    private OnEntityListener entityListener = null;

    private boolean isRealTimeRunning = true;
    /**
     * 打包周期
     */
    public int packInterval = Constants.DEFAULT_PACK_INTERVAL;

    private RealTimeLocRunnable realTimeLocRunnable = null;
    /**
     * 轨迹分析监听器
     */
    private OnAnalysisListener mAnalysisListener = null;
    /**
     * 停留点请求
     */
    private StayPointRequest stayPointRequest = new StayPointRequest();
    public  void  stopService(){
        if(trackApp.mClient==null||trackApp==null){
            return;
        }
        trackApp.mClient.stopTrace(trackApp.mTrace, traceListener);
        stopRealTimeLoc();
    }


    public void startSercive(){

            try{
                if(userInfoDto==null){
                    userInfoDto=Hawk.get(PreferenceKey.USER_INFO);
                }
            }catch (Exception e){
            }
            if(userInfoDto==null){
                return;
            }
            if(trackApp.mClient==null||trackApp==null){
                return;
            }
            trackApp.mClient.setOnCustomAttributeListener(new OnCustomAttributeListener() {
                @Override
                public Map<String, String> onTrackAttributeCallback() {
                    Map<String, String> map = new HashMap<>();
                    map.put("entity_cp", Hawk.get(PreferenceKey.CURRENT_CAR_NO,""));
                    if(userInfoDto!=null){
                        map.put("entity_sj", userInfoDto.getName());
                    }
                    map.put("entity_desc",Hawk.get(PreferenceKey.CURRENT_CAR_NO,""));
                    return map;
                }
                @Override
                public Map<String, String> onTrackAttributeCallback(long locTime) {
                    System.out.println("onTrackAttributeCallback, locTime : " + locTime);
                    Map<String, String> map = new HashMap<>();
                    map.put("entity_cp", Hawk.get(PreferenceKey.CURRENT_CAR_NO,""));
                    if(userInfoDto!=null){
                        map.put("entity_sj", userInfoDto.getName());
                    }
                    AddEntityRequest request=new AddEntityRequest();
                    request.setServiceId(trackApp.serviceId);
                    request.setEntityName(entityName);
                    request.setEntityDesc(Hawk.get(PreferenceKey.CURRENT_CAR_NO,""));
                    request.setColumns(map);
                    trackApp.mClient.addEntity(request, new OnEntityListener() {
                        @Override
                        public void onAddEntityCallback(AddEntityResponse addEntityResponse) {
                            super.onAddEntityCallback(addEntityResponse);
                        }
                    });
                    UpdateEntityRequest updateEntityRequest=new UpdateEntityRequest();
                    updateEntityRequest.setServiceId(trackApp.serviceId);
                    updateEntityRequest.setEntityName(entityName);
                    updateEntityRequest.setEntityDesc(Hawk.get(PreferenceKey.CURRENT_CAR_NO,""));
                    updateEntityRequest.setColumns(map);
                    trackApp.mClient.updateEntity(updateEntityRequest, new OnEntityListener() {
                        @Override
                        public void onAddEntityCallback(AddEntityResponse addEntityResponse) {
                            super.onAddEntityCallback(addEntityResponse);
                        }
                    });
                    return map;
                }
            });
            trackApp.mClient.startTrace(trackApp.mTrace, traceListener);
            if (Constants.DEFAULT_PACK_INTERVAL != packInterval) {
                stopRealTimeLoc();
                startRealTimeLoc(packInterval);
            }

    }

    /****
     * 停止采集
     * */
    public void stopRealTimeLoc() {
        isRealTimeRunning = false;
        if (null != realTimeHandler && null != realTimeLocRunnable) {
            realTimeHandler.removeCallbacks(realTimeLocRunnable);
        }
        trackApp.mClient.stopRealTimeLoc();
    }
    public void startRealTimeLoc(int interval) {
        isRealTimeRunning = true;
        realTimeLocRunnable = new RealTimeLocRunnable(interval);
        realTimeHandler.post(realTimeLocRunnable);
    }
    /**
     * 实时定位任务
     *
     * @author baidu
     */
    class RealTimeLocRunnable implements Runnable {

        private int interval = 0;
        public RealTimeLocRunnable(int interval) {
            this.interval = interval;
        }

        @Override
        public void run() {
            if (isRealTimeRunning) {
                trackApp.getCurrentLocation(entityListener, trackListener);
                realTimeHandler.postDelayed(this, interval * 1000);
            }
        }
    }
    /**
     * 实时定位任务
     */
    private RealTimeHandler realTimeHandler = new RealTimeHandler();

    static class RealTimeHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    /***
     *初始化轨迹服务监听器
     * **/
    public void initTrace(){
        trackListener = new OnTrackListener() {

            @Override
            public void onLatestPointCallback(LatestPointResponse response) {
                if (StatusCodes.SUCCESS != response.getStatus()) {
                    return;
                }
                LatestPoint point = response.getLatestPoint();
                if (null == point || CommonUtil.isZeroPoint(point.getLocation().getLatitude(),
                        point.getLocation()
                                .getLongitude())) {
                    return;
                }
                LatLng currentLatLng = mapUtil.convertTrace2Map(point.getLocation());
                if (null == currentLatLng) {
                    return;
                }
                CurrentLocation.locTime = point.getLocTime();
                CurrentLocation.latitude = currentLatLng.latitude;
                CurrentLocation.longitude = currentLatLng.longitude;
                if (null != mapUtil) {
                    mapUtil.updateStatus(currentLatLng, true);
                }
            }
        };


        entityListener = new OnEntityListener() {

            @Override
            public void onReceiveLocation(TraceLocation location) {

                if (StatusCodes.SUCCESS != location.getStatus() || CommonUtil.isZeroPoint(location.getLatitude(),
                        location.getLongitude())) {
                    return;
                }
                LatLng currentLatLng = mapUtil.convertTraceLocation2Map(location);
                if (null == currentLatLng) {
                    return;
                }
                CurrentLocation.locTime = CommonUtil.toTimeStamp(location.getTime());
                CurrentLocation.latitude = currentLatLng.latitude;
                CurrentLocation.longitude = currentLatLng.longitude;
                if (null != mapUtil) {
                    mapUtil.updateStatus(currentLatLng, true);
                }
            }
        };

        traceListener = new OnTraceListener() {

            /**
             * 绑定服务回调接口
             * @param errorNo  状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功 </pre>
             *                <pre>1：失败</pre>
             */
            @Override
            public void onBindServiceCallback(int errorNo, String message) {
               // Log.e("msg","鹰眼服务开启结果"+errorNo+message);
            }
            /**
             * 开启服务回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功 </pre>
             *                <pre>10000：请求发送失败</pre>
             *                <pre>10001：服务开启失败</pre>
             *                <pre>10002：参数错误</pre>
             *                <pre>10003：网络连接失败</pre>
             *                <pre>10004：网络未开启</pre>
             *                <pre>10005：服务正在开启</pre>
             *                <pre>10006：服务已开启</pre>
             */
            @Override
            public void onStartTraceCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo
                        || StatusCodes.START_TRACE_NETWORK_CONNECT_FAILED <= errorNo) {
                    if(stateChangeListener!=null){
                        stateChangeListener.StateChange( message);
                    }
                    // 开启采集
                    trackApp.mClient.startGather(traceListener);
                    trackApp.isTraceStarted = true;
                    startsTATUS=message;

                    if(trackApp.trackConf==null){
                        trackApp.trackConf=trackApp.getSharedPreferences("track_conf", MODE_PRIVATE);
                    }
                    if(trackApp.trackConf!=null){
                        SharedPreferences.Editor editor = trackApp.trackConf.edit();
                        editor.putBoolean("is_trace_started", true);
                        editor.commit();
                    }
                }
            }
            /**
             * 停止服务回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功</pre>
             *                <pre>11000：请求发送失败</pre>
             *                <pre>11001：服务停止失败</pre>
             *                <pre>11002：服务未开启</pre>
             *                <pre>11003：服务正在停止</pre>
             */
            @Override
            public void onStopTraceCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.CACHE_TRACK_NOT_UPLOAD == errorNo) {
                    trackApp.isTraceStarted = false;
                    trackApp.isGatherStarted = false;
                    // 停止成功后，直接移除is_trace_started记录（便于区分用户没有停止服务，直接杀死进程的情况）

                     if(OverallTimer.getOverAllTimer()!=null){
                         Log.e("msg","异常停止鹰眼");
                         EventBus.getDefault().post(com.saimawzc.freight.constants.Constants.openTruck);
                     }
                   if( trackApp.trackConf==null){
                       trackApp.trackConf=trackApp.getSharedPreferences("track_conf", MODE_PRIVATE);
                   }
                    if(trackApp.trackConf!=null){
                        SharedPreferences.Editor editor = trackApp.trackConf.edit();
                        editor.remove("is_trace_started");
                        editor.remove("is_gather_started");
                        editor.commit();
                    }

                }
            }
            /**
             * 开启采集回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功</pre>
             *                <pre>12000：请求发送失败</pre>
             *                <pre>12001：采集开启失败</pre>
             *                <pre>12002：服务未开启</pre>
             */
            @Override
            public void onStartGatherCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STARTED == errorNo) {
                    trackApp.isGatherStarted = true;
                    if(stopAlaem==1){
                        startTimer();
                    }
                    if(trackApp.trackConf==null){
                        trackApp.trackConf=trackApp.getSharedPreferences("track_conf", MODE_PRIVATE);
                    }
                    if(trackApp.trackConf!=null){
                        SharedPreferences.Editor editor = trackApp.trackConf.edit();
                        editor.putBoolean("is_gather_started", true);
                        editor.commit();
                    }
                }
            }
            /**
             * 停止采集回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功</pre>
             *                <pre>13000：请求发送失败</pre>
             *                <pre>13001：采集停止失败</pre>
             *                <pre>13002：服务未开启</pre>
             */
            @Override
            public void onStopGatherCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STOPPED == errorNo) {
                    if(trackApp.trackConf==null){
                        trackApp.trackConf=trackApp.getSharedPreferences("track_conf", MODE_PRIVATE);
                    }
                    if(OverallTimer.getOverAllTimer()!=null){
                        Log.e("msg","异常停止采集");
                        EventBus.getDefault().post(com.saimawzc.freight.constants.Constants.openTruck);
                    }
                    if(trackApp.trackConf!=null){
                        trackApp.isGatherStarted = false;
                        SharedPreferences.Editor editor = trackApp.trackConf.edit();
                        editor.remove("is_gather_started");
                        editor.commit();
                    }
                }
            }
            /**
             * 推送消息回调接口
             *
             * @param messageType 状态码
             * @param pushMessage 消息
             *                  <p>
             *                  <pre>0x01：配置下发</pre>
             *                  <pre>0x02：语音消息</pre>
             *                  <pre>0x03：服务端围栏报警消息</pre>
             *                  <pre>0x04：本地围栏报警消息</pre>
             *                  <pre>0x05~0x40：系统预留</pre>
             *                  <pre>0x41~0xFF：开发者自定义</pre>
             */
            @Override
            public void onPushCallback(byte messageType, PushMessage pushMessage) {
                if (messageType < 0x03 || messageType > 0x04) {
                    return;
                }
                Log.e("msg","围栏返回"+pushMessage.toString());
                alarmPushInfo= pushMessage.getFenceAlarmPushInfo();
                if (null == alarmPushInfo) {
                    return;
                }
                /**
                 * 获取报警推送消息
                 */
//                alarmPushInfo.getFenceId();//获取围栏id
//                alarmPushInfo.getMonitoredPerson();//获取监控对象标识
//                alarmPushInfo.getFenceName();//获取围栏名称
//                alarmPushInfo.getPrePoint();//获取上一个点经度信息
//                AlarmPoint alarmPoin = alarmPushInfo.getCurrentPoint();//获取报警点经纬度等信息
//                alarmPoin.getCreateTime();//获取此位置上传到服务端时间
//                alarmPoin.getLocTime();//获取定位产生的原始时间

                if(alarmPushInfo.getFenceName().contains("yd")){
                    pushInfoList.add(alarmPushInfo);
                }
                if(alarmPushInfo.getMonitoredAction() == MonitoredAction.enter){//动作类型
                    //进入围栏
                    pushWeiLan(alarmPushInfo);

                }else if(alarmPushInfo.getMonitoredAction() == MonitoredAction.exit){
                }
            }
            @Override
            public void onInitBOSCallback(int errorNo, String message) {
            }
        };
        /***
         * 停留点分析
         * ****/
        mAnalysisListener = new OnAnalysisListener() {
            @Override
            public void onStayPointCallback(StayPointResponse response) {
                Log.e("msg","停留返回"+response.toString());
                if (StatusCodes.SUCCESS != response.getStatus()) {
                    return;
                }
                if (0 == response.getStayPointNum()) {
                    return;
                }
                stayPoints.addAll(response.getStayPoints());

                if(stayPoints.size()>0){
                    uploadStay(stayPoints.get(stayPoints.size()-1));
                }

            }
            @Override
            public void onDrivingBehaviorCallback(DrivingBehaviorResponse response) {
                Log.e("msg","驾驶行为返回"+response.toString());
            }
        };
    }
    public void initView(){

        trackApp.mClient = new LBSTraceClient(trackApp);
        trackApp.mTrace = new Trace(trackApp.serviceId, entityName);
        trackApp.locRequest = new LocRequest(trackApp.serviceId);
        // 定位周期(单位:秒)
        int gatherInterval = 10;
        // 打包回传周期(单位:秒)
        int packInterval = 60;
        // 设置定位和打包周期
        trackApp.mClient.setInterval(gatherInterval, packInterval);
        if(userInfoDto==null||TextUtils.isEmpty(userInfoDto.getRoleId())){
            userInfoDto=Hawk.get(PreferenceKey.USER_INFO);
        }
        if(userInfoDto==null||TextUtils.isEmpty(userInfoDto.getRoleId())){
            return;
        }
        trackApp.mClient.setOnCustomAttributeListener(new OnCustomAttributeListener() {
            @Override
            public Map<String, String> onTrackAttributeCallback() {
                Map<String, String> map = new HashMap<>();
                map.put("entity_cp", "1111");
                if(userInfoDto!=null){
                    map.put("entity_sj", userInfoDto.getName());
                }
                return map;
            }
            @Override
            public Map<String, String> onTrackAttributeCallback(long locTime) {
                System.out.println("onTrackAttributeCallback, locTime : " + locTime);
                Map<String, String> map = new HashMap<>();
                map.put("entity_cp", "11111");
                map.put("entity_sj", userInfoDto.getName());
                return map;
            }
        });
        initTrace();
    }
    String id="";
    String adress;
    private String yundanId="";
    private List<FenceAlarmPushInfo>pushInfoList=new ArrayList<>();
    int entryNum=0;
    private List<String>alreadFence=new ArrayList<>();
    private void pushWeiLan( final FenceAlarmPushInfo alarmPushInfo){

        if(!alarmPushInfo.getFenceName().contains("xx")){
            if(alarmPushInfo.getFenceName().contains("yd")){//进入围栏自动签收或者自动开始
                entryNum++;
                new Handler().postDelayed(new Runnable()
                {
                    public void run()
                    {
                        WaybillEncloDto autoSignDto=Hawk.get(PreferenceKey.AUTO_SIGN_DTO);
                        if(autoSignDto!=null) {
                            if(autoSignDto.getStartData()!=null&&autoSignDto.getStartData().size()>0){//有已经开始运输的单
                                WaybillEncloDto.yundanData tempYdDto=  autoSignDto.getStartData().get(0);
                                if(tempYdDto!=null){
                                    return;
                                }

                                if(tempYdDto==null){
                                    return;
                                }

                                WaybillEncloDto.wayBillId wayBillDto=tempYdDto.getWayBillId().get(0);
                                if(wayBillDto==null){
                                    return;
                                }
                                Toast.makeText(trackApp,"进入收货围栏",Toast.LENGTH_LONG).show();
                                Bundle bundle=new Bundle();
                                bundle.putString("id",wayBillDto.getId());
                                bundle.putString("waybillid",wayBillDto.getWayBillId());
                                bundle.putString("code",7+"");
                                if(alarmPushInfo.getCurrentPoint()!=null){
                                    bundle.putString("location",alarmPushInfo.getCurrentPoint().getLocation().getLongitude()+","+alarmPushInfo.getCurrentPoint().getLocation().getLatitude());
                                    bundle.putString("toadress",wayBillDto.getToLocation());//到达地址经纬度
                                }
                                bundle.putString("city","");
                                bundle.putString("country","");
                                bundle.putString("adress","");
                                DriverTransDto driverTransDto=new DriverTransDto();
                                driverTransDto.setToAddressType(2);//围栏地址
                                driverTransDto.setToLocation(wayBillDto.getToLocation());
                                driverTransDto.setToEnclosureType(wayBillDto.getToEnclosureType());
                                driverTransDto.setToRadius(wayBillDto.getToRadius());
                                driverTransDto.setToRegion(wayBillDto.getToRegion());
                                bundle.putSerializable("data",driverTransDto);
                                bundle.putString("tranttype",1+"");
                                bundle.putString("from","arriveorder");
                                trackApp.readyGo(OrderMainActivity.class,bundle);
                                pushInfoList.clear();
                                entryNum=0;
                            } else  if (autoSignDto.getUnStartData() != null
                                    && autoSignDto.getUnStartData().size() > 0) {//没有开始的运输的单
//
                            }
                    }
                }
            }, 5000);

        }else {//进入围栏之后监听停留预警，偏移轨迹
            Hawk.put(PreferenceKey.IS_INTOFENCE,true);
            JSONObject jsonObject=new JSONObject();
            try {
                if(userInfoDto!=null||TextUtils.isEmpty(userInfoDto.getRoleId())){
                    jsonObject.put("id",userInfoDto.getId());//司机ID
                }else {
                    userInfoDto=Hawk.get(PreferenceKey.USER_INFO);
                    if(userInfoDto!=null){
                        jsonObject.put("id",userInfoDto.getId());//司机ID
                    }
                }
                jsonObject.put("wayBillId",alarmPushInfo.getFenceName());//小单id
                jsonObject.put("type",2);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON,jsonObject.toString());
            tmsApi.pushWeiLan(body).enqueue(new CallBack<EmptyDto>() {
                @Override
                public void success(EmptyDto response) {
                    List<Long> deleteFenceIds = new ArrayList<>();
                    deleteFenceIds.add(alarmPushInfo.getFenceId());
                    DeleteFenceRequest deleteRequest = DeleteFenceRequest.buildServerRequest
                            (trackApp.getTag(),trackApp.serviceId, entityName, deleteFenceIds);
                    //发起删除围栏请求
                    trackApp.mClient.deleteFence(deleteRequest , null);

                }
                @Override
                public void fail(String code, String message) {

                }
            });
        }
    }
}
    /**
     * 查询停留点
     */
    private void queryStayPoint() {
        trackApp.initRequest(stayPointRequest);
        stayPointRequest.setEntityName(trackApp.entityName);
        long startTime=BaseActivity.dateToStamp(BaseActivity.getCurrentTime("yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss")-60*30;
        stayPointRequest.setStartTime(startTime);
        long endTime=BaseActivity.dateToStamp(BaseActivity.getCurrentTime("yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss");
        stayPointRequest.setEndTime(endTime);
        stayPointRequest.setServiceId(trackApp.serviceId);
        stayPointRequest.setStayRadius(20);
        stayPointRequest.setCoordTypeOutput(CoordType.bd09ll);
        ProcessOption processOption=new ProcessOption();
        processOption.setNeedDenoise(true);
        stayPointRequest.setProcessOption(processOption);
        stayPointRequest.setStayTime(alartTime*60);
        trackApp.mClient.queryStayPoint(stayPointRequest, mAnalysisListener);
    }


    private void startTimer() {
        if (onLineTimer != null) {
            onLineTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    queryStayPoint();
                }
            }, 2000, 5*60*1000);// 程序启动后,过2秒再执行,然后每隔5分钟执行一次。
        }
    }
    public void setAlarmTime(int time){
        this.alartTime=time;
    }
    public void setStopArlm(int time){
        this.stopAlaem=time;
    }
    public void setWayBillId(String wayBillId){
        this.wayBllId=wayBillId;
    }
    private double latitude;
    private double longtatude;
    /***
     * 上传停留时间
     * **/
    private void uploadStay(final StayPoint point){

        if(point.getLocation().latitude==latitude&&point.getLocation().longitude==longtatude){
            return;
        }
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("second",point.getDuration());//
            jsonObject.put("waybillId",wayBllId);//
            jsonObject.put("location",point.getLocation().longitude+","+point.getLocation().latitude);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.uplaodStayTime(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                latitude=point.getLocation().latitude;
                longtatude=point.getLocation().longitude;
            }
            @Override
            public void fail(String code, String message) {

            }
        });
    }
    // 状态变化监听
    public interface StateChangeListener {
        // 回调方法
        void StateChange(String State);
    }
   public StateChangeListener stateChangeListener;
    // 提供注册事件监听的方法
    public void setOnChangeListener(StateChangeListener stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
    }

}
