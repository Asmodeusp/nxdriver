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
 *??????????????????
 ****/
public class TraceUtils {
    private BaseApplication trackApp;
    /**
     * ????????????
     */
    private MapUtil mapUtil = null;
    private UserInfoDto userInfoDto;
    private String entityName;
    private Timer onLineTimer = new Timer(true);//????????????

    private int alartTime;//????????????
    private int stopAlaem;
    private String wayBllId;
    FenceAlarmPushInfo alarmPushInfo;
    public TmsApi tmsApi= Http.http.createApi(TmsApi.class);
    public OrderApi orderApi= Http.http.createApi(OrderApi.class);
    public String startsTATUS="";
    /**
     * ????????????  ???????????????
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
     * ?????????????????????
     */
    private OnTraceListener traceListener = null;

    /**
     * ???????????????(???????????????????????????????????????)
     */
    private OnTrackListener trackListener = null;

    /**
     * Entity?????????(??????????????????????????????)
     */
    private OnEntityListener entityListener = null;

    private boolean isRealTimeRunning = true;
    /**
     * ????????????
     */
    public int packInterval = Constants.DEFAULT_PACK_INTERVAL;

    private RealTimeLocRunnable realTimeLocRunnable = null;
    /**
     * ?????????????????????
     */
    private OnAnalysisListener mAnalysisListener = null;
    /**
     * ???????????????
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
     * ????????????
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
     * ??????????????????
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
     * ??????????????????
     */
    private RealTimeHandler realTimeHandler = new RealTimeHandler();

    static class RealTimeHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    /***
     *??????????????????????????????
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
             * ????????????????????????
             * @param errorNo  ?????????
             * @param message ??????
             *                <p>
             *                <pre>0????????? </pre>
             *                <pre>1?????????</pre>
             */
            @Override
            public void onBindServiceCallback(int errorNo, String message) {
               // Log.e("msg","????????????????????????"+errorNo+message);
            }
            /**
             * ????????????????????????
             * @param errorNo ?????????
             * @param message ??????
             *                <p>
             *                <pre>0????????? </pre>
             *                <pre>10000?????????????????????</pre>
             *                <pre>10001?????????????????????</pre>
             *                <pre>10002???????????????</pre>
             *                <pre>10003?????????????????????</pre>
             *                <pre>10004??????????????????</pre>
             *                <pre>10005?????????????????????</pre>
             *                <pre>10006??????????????????</pre>
             */
            @Override
            public void onStartTraceCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo
                        || StatusCodes.START_TRACE_NETWORK_CONNECT_FAILED <= errorNo) {
                    if(stateChangeListener!=null){
                        stateChangeListener.StateChange( message);
                    }
                    // ????????????
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
             * ????????????????????????
             * @param errorNo ?????????
             * @param message ??????
             *                <p>
             *                <pre>0?????????</pre>
             *                <pre>11000?????????????????????</pre>
             *                <pre>11001?????????????????????</pre>
             *                <pre>11002??????????????????</pre>
             *                <pre>11003?????????????????????</pre>
             */
            @Override
            public void onStopTraceCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.CACHE_TRACK_NOT_UPLOAD == errorNo) {
                    trackApp.isTraceStarted = false;
                    trackApp.isGatherStarted = false;
                    // ??????????????????????????????is_trace_started??????????????????????????????????????????????????????????????????????????????

                     if(OverallTimer.getOverAllTimer()!=null){
                         Log.e("msg","??????????????????");
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
             * ????????????????????????
             * @param errorNo ?????????
             * @param message ??????
             *                <p>
             *                <pre>0?????????</pre>
             *                <pre>12000?????????????????????</pre>
             *                <pre>12001?????????????????????</pre>
             *                <pre>12002??????????????????</pre>
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
             * ????????????????????????
             * @param errorNo ?????????
             * @param message ??????
             *                <p>
             *                <pre>0?????????</pre>
             *                <pre>13000?????????????????????</pre>
             *                <pre>13001?????????????????????</pre>
             *                <pre>13002??????????????????</pre>
             */
            @Override
            public void onStopGatherCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STOPPED == errorNo) {
                    if(trackApp.trackConf==null){
                        trackApp.trackConf=trackApp.getSharedPreferences("track_conf", MODE_PRIVATE);
                    }
                    if(OverallTimer.getOverAllTimer()!=null){
                        Log.e("msg","??????????????????");
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
             * ????????????????????????
             *
             * @param messageType ?????????
             * @param pushMessage ??????
             *                  <p>
             *                  <pre>0x01???????????????</pre>
             *                  <pre>0x02???????????????</pre>
             *                  <pre>0x03??????????????????????????????</pre>
             *                  <pre>0x04???????????????????????????</pre>
             *                  <pre>0x05~0x40???????????????</pre>
             *                  <pre>0x41~0xFF?????????????????????</pre>
             */
            @Override
            public void onPushCallback(byte messageType, PushMessage pushMessage) {
                if (messageType < 0x03 || messageType > 0x04) {
                    return;
                }
                Log.e("msg","????????????"+pushMessage.toString());
                alarmPushInfo= pushMessage.getFenceAlarmPushInfo();
                if (null == alarmPushInfo) {
                    return;
                }
                /**
                 * ????????????????????????
                 */
//                alarmPushInfo.getFenceId();//????????????id
//                alarmPushInfo.getMonitoredPerson();//????????????????????????
//                alarmPushInfo.getFenceName();//??????????????????
//                alarmPushInfo.getPrePoint();//??????????????????????????????
//                AlarmPoint alarmPoin = alarmPushInfo.getCurrentPoint();//?????????????????????????????????
//                alarmPoin.getCreateTime();//???????????????????????????????????????
//                alarmPoin.getLocTime();//?????????????????????????????????

                if(alarmPushInfo.getFenceName().contains("yd")){
                    pushInfoList.add(alarmPushInfo);
                }
                if(alarmPushInfo.getMonitoredAction() == MonitoredAction.enter){//????????????
                    //????????????
                    pushWeiLan(alarmPushInfo);

                }else if(alarmPushInfo.getMonitoredAction() == MonitoredAction.exit){
                }
            }
            @Override
            public void onInitBOSCallback(int errorNo, String message) {
            }
        };
        /***
         * ???????????????
         * ****/
        mAnalysisListener = new OnAnalysisListener() {
            @Override
            public void onStayPointCallback(StayPointResponse response) {
                Log.e("msg","????????????"+response.toString());
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
                Log.e("msg","??????????????????"+response.toString());
            }
        };
    }
    public void initView(){

        trackApp.mClient = new LBSTraceClient(trackApp);
        trackApp.mTrace = new Trace(trackApp.serviceId, entityName);
        trackApp.locRequest = new LocRequest(trackApp.serviceId);
        // ????????????(??????:???)
        int gatherInterval = 10;
        // ??????????????????(??????:???)
        int packInterval = 60;
        // ???????????????????????????
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
            if(alarmPushInfo.getFenceName().contains("yd")){//??????????????????????????????????????????
                entryNum++;
                new Handler().postDelayed(new Runnable()
                {
                    public void run()
                    {
                        WaybillEncloDto autoSignDto=Hawk.get(PreferenceKey.AUTO_SIGN_DTO);
                        if(autoSignDto!=null) {
                            if(autoSignDto.getStartData()!=null&&autoSignDto.getStartData().size()>0){//???????????????????????????
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
                                Toast.makeText(trackApp,"??????????????????",Toast.LENGTH_LONG).show();
                                Bundle bundle=new Bundle();
                                bundle.putString("id",wayBillDto.getId());
                                bundle.putString("waybillid",wayBillDto.getWayBillId());
                                bundle.putString("code",7+"");
                                if(alarmPushInfo.getCurrentPoint()!=null){
                                    bundle.putString("location",alarmPushInfo.getCurrentPoint().getLocation().getLongitude()+","+alarmPushInfo.getCurrentPoint().getLocation().getLatitude());
                                    bundle.putString("toadress",wayBillDto.getToLocation());//?????????????????????
                                }
                                bundle.putString("city","");
                                bundle.putString("country","");
                                bundle.putString("adress","");
                                DriverTransDto driverTransDto=new DriverTransDto();
                                driverTransDto.setToAddressType(2);//????????????
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
                                    && autoSignDto.getUnStartData().size() > 0) {//???????????????????????????
//
                            }
                    }
                }
            }, 5000);

        }else {//???????????????????????????????????????????????????
            Hawk.put(PreferenceKey.IS_INTOFENCE,true);
            JSONObject jsonObject=new JSONObject();
            try {
                if(userInfoDto!=null||TextUtils.isEmpty(userInfoDto.getRoleId())){
                    jsonObject.put("id",userInfoDto.getId());//??????ID
                }else {
                    userInfoDto=Hawk.get(PreferenceKey.USER_INFO);
                    if(userInfoDto!=null){
                        jsonObject.put("id",userInfoDto.getId());//??????ID
                    }
                }
                jsonObject.put("wayBillId",alarmPushInfo.getFenceName());//??????id
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
                    //????????????????????????
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
     * ???????????????
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
            }, 2000, 5*60*1000);// ???????????????,???2????????????,????????????5?????????????????????
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
     * ??????????????????
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
    // ??????????????????
    public interface StateChangeListener {
        // ????????????
        void StateChange(String State);
    }
   public StateChangeListener stateChangeListener;
    // ?????????????????????????????????
    public void setOnChangeListener(StateChangeListener stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
    }

}
