package com.saimawzc.freight.ui.sendcar.driver.numLock;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.order.PassNumDto;
import com.saimawzc.freight.dto.sendcar.DriverTransDto;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class GetPassNumFragment extends BaseFragment implements OnGetDistricSearchResultListener {
    private String location;
    @BindView(R.id.mapview) MapView mapView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.edNum) EditText edNum;
    @BindView(R.id.tvPass) TextView tvPass;
    private String dispatchCarId;
    private BaiduMap mBdMap;
    private int dealCode;//567,收货围栏，其他都是发货围栏
    DriverTransDto driverTransDto;
    private String tempLocation;
    int  type;
    @OnClick({R.id.imgScan,R.id.tvGetPass})
    public void click(View view){
        switch (view.getId()){
            case R.id.imgScan:
                scan();
                break;
            case R.id.tvGetPass:
                if(context.isEmptyStr(edNum)){
                    context.showMessage("请输入电子锁编码");
                    return;
                }

                getPass();

                break;
        }
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_getpassnum;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"获取电子锁密码");
        location=getArguments().getString("location");
        dispatchCarId=getArguments().getString("dispatchCarId");
        mBdMap=mapView.getMap();
        dealCode=getArguments().getInt("currentcode");
        driverTransDto= (DriverTransDto) getArguments().getSerializable("data");
        getLocation();
        if(driverTransDto!=null){
            Log.e("msg",driverTransDto.toString()+"~~~"+dealCode);
            dealWeiLan();
        }
    }

    @Override
   public void initData() {

    }
    /****
     * 扫描二维码
     * **/
    private void scan() {
        //获取扫描结果

        QrConfig qrConfig = new QrConfig.Builder()
                .setDesText("(扫一扫)")//扫描框下文字
                .setShowDes(false)//是否显示扫描框下面文字
                .setShowLight(true)//显示手电筒按钮
                .setShowTitle(true)//显示Title
                .setShowAlbum(true)//显示从相册选择按钮
                .setCornerColor(Color.WHITE)//设置扫描框颜色
                .setLineColor(Color.WHITE)//设置扫描线颜色
                .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
                .setScanType(QrConfig.TYPE_ALL)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE)//设置扫描框类型（二维码还是条形码，默认为二维码）
                .setCustombarcodeformat(QrConfig.BARCODE_I25)//此项只有在扫码类型为TYPE_CUSTOM时才有效
                .setPlaySound(true)//是否扫描成功后bi~的声音
                .setDingPath(R.raw.qrcode)//设置提示音(不设置为默认的Ding~)
                .setIsOnlyCenter(true)//是否只识别框中内容(默认为全屏识别)
                .setTitleText("扫一扫")//设置Tilte文字
                .setTitleBackgroudColor(Color.BLACK)//设置状态栏颜色
                .setTitleTextColor(Color.WHITE)//设置Title文字颜色
                .create();
        QrManager.getInstance().init(qrConfig).startScan(context, new QrManager.OnScanResultCallback() {
            @Override
            public void onScanSuccess(String result1) {
                final String result = result1.replaceAll(" ", ""); //结果文字
                if(TextUtils.isEmpty(result)){
                    context.showMessage("您的扫描有误");
                    return;
                }
               edNum.setText(result);
            }
        });
    }


    private void getPass(){
        context.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("passwordType",1);
            jsonObject.put("passwordCode",edNum.getText().toString());
            if(!TextUtils.isEmpty(location)){
                jsonObject.put("location",location);
            }else {
                jsonObject.put("location",tempLocation);
            }
            jsonObject.put("dispatchCarId",dispatchCarId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        context.tmsApi.getPassNum(body).enqueue(new CallBack<PassNumDto>() {
            @Override
            public void success(PassNumDto response) {
                context.dismissLoadingDialog();
                if(response!=null){
                    tvPass.setText(response.getPassword());
                }
            }
            @Override
            public void fail(String code, String message) {
                context.dismissLoadingDialog();
                context.showMessage(message);
            }
        });
    }

    /***
     * 获取经纬度
     * **/
    public  void getLocation(){

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
        //option.setScanSpan(3000);//是否需要连续定位
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

    @Override
    public void onGetDistrictResult(DistrictResult districtResult) {
        if (districtResult.error == SearchResult.ERRORNO.NO_ERROR){
            List<List<LatLng>> polyLines = districtResult.getPolylines();
            if (polyLines == null){
                return;
            }
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (List<LatLng> polyline : polyLines){
                for (LatLng latLng : polyline){
                    builder.include(latLng);
                }
                mBdMap.addOverlay(new PolygonOptions()
                        .points(polyline)
                        .fillColor(0x666495ED) // 填充颜色
                        .stroke(new Stroke(5, 0xE66495ED)));
            }
            mBdMap.setMapStatus(MapStatusUpdateFactory
                    .newLatLngBounds(builder.build()));

        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            if (location == null || mapView == null) {
                return;
            }else if(location.getLocType()==62){
                return;

            }
            tempLocation=location.getLongitude() + "," + location.getLatitude();
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
            mBdMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_gcoding);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(ll)
                    .perspective(true)
                    .draggable(true)
                    .flat(true)
                    .alpha(0.5f)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            mBdMap.addOverlay(option);
            mLocationClient.stop();
        }
    }
    private DistrictSearch mDistrictSearch;
    private void dealWeiLan(){
        if(dealCode==5||dealCode==6||dealCode==7){//收货围栏
            int toAddressType =driverTransDto.getToAddressType();//1地址，2 围栏
            if(toAddressType==1){//地址
                if(!TextUtils.isEmpty(driverTransDto.getToLocation())){
                    drawCircle(driverTransDto.getToLocation(),2000);
                }
            }else {
                int toEnclosureType=driverTransDto.getToEnclosureType();//1圆形 2行政区域 3多边形
                if(toEnclosureType==1){
                    drawCircle(driverTransDto.getToLocation(),driverTransDto.getToRadius());
                }else if(toEnclosureType==2){//行政区域围栏
                    mDistrictSearch = DistrictSearch.newInstance();
                    mDistrictSearch.setOnDistrictSearchListener(this);
                    mDistrictSearch.searchDistrict(new DistrictSearchOption().cityName(driverTransDto.getToRegion()));
                }else if(toEnclosureType==3){//多边形围栏
                    String locol=driverTransDto.getToRegion();
                    if(!TextUtils.isEmpty(locol)){
                        if(!locol.contains(";")){
                            return;
                        }
                        String[] node = locol.split(";");
                        for(int j=0;j<node.length;j++){
                            String[] lang = node[j].split(",");
                            LatLng center = new LatLng(Double.parseDouble(lang[1]), Double.parseDouble(lang[0]));
                            polygonPoints.add(center);
                        }
                    }
                    drawPolygon();
                }
            }


        }else {//发货围栏
            int fromAddressType =driverTransDto.getFromAddressType();//1地址，2 围栏
            if(fromAddressType==1){//地址
                if(!TextUtils.isEmpty(driverTransDto.getFromLocation())){
                    drawCircle(driverTransDto.getFromLocation(),2000);
                }
            }else {
                int fromEnclosureType=driverTransDto.getFromEnclosureType();//1圆形 2行政区域 3多边形
                if(fromEnclosureType==1){
                    drawCircle(driverTransDto.getFromLocation(),driverTransDto.getFromRadius());
                }else if(fromEnclosureType==2){//行政区域围栏
                    mDistrictSearch = DistrictSearch.newInstance();
                    mDistrictSearch.setOnDistrictSearchListener(this);
                    mDistrictSearch.searchDistrict(new DistrictSearchOption().cityName(driverTransDto.getFromRegion()));
                }else if(fromEnclosureType==3){//多边形围栏
                    String locol=driverTransDto.getFromRegion();
                    if(!TextUtils.isEmpty(locol)){
                        if(!locol.contains(";")){
                            return;
                        }
                        String[] node = locol.split(";");
                        for(int j=0;j<node.length;j++){
                            String[] lang = node[j].split(",");
                            LatLng center = new LatLng(Double.parseDouble(lang[1]), Double.parseDouble(lang[0]));
                            polygonPoints.add(center);
                        }
                    }
                    drawPolygon();
                }
            }

        }
    }
    // 当前的坐标点集合，主要用于进行地图的可视区域的缩放
    private LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
    /**
     * 绘制圆
     */
    private void drawCircle(String fromLocation, int circleradius) {
        LatLng center;
        int radius;
        if(!fromLocation.contains(",")){
            return;
        }
        String[] tempNode = fromLocation.split(",");
        if(Double.parseDouble(tempNode[1])<Double.parseDouble(tempNode[0])){
            center=new LatLng(Double.parseDouble(tempNode[1]), Double.parseDouble(tempNode[0]));
        }else {
            center=new LatLng(Double.parseDouble(tempNode[0]), Double.parseDouble(tempNode[1]));
        }
        radius = circleradius;
        // 绘制一个圆形
        if (center == null) {
            return;
        }
        mBdMap.addOverlay(new CircleOptions().center(center)
                .radius(radius)
                .fillColor(0x666495ED) // 填充颜色
                .stroke(new Stroke(3, 0xE66495ED)));
        boundsBuilder.include(center);
    }



    /**
     *  绘制Polygon
     */
    // 多边形围栏的边界点
    private List<LatLng> polygonPoints = new ArrayList<>();
    private void drawPolygon() {
        if (null == polygonPoints || polygonPoints.isEmpty()) {
            return;
        }

        for (LatLng point : polygonPoints) {
            boundsBuilder.include(point);
        }
        mBdMap.addOverlay(new PolygonOptions()
                .points(polygonPoints)
                .fillColor(0x666495ED) // 填充颜色
                .stroke(new Stroke(5, 0xE66495ED)));

        if (polygonPoints != null && polygonPoints.size() > 0) {
            polygonPoints.clear();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.stop();
        }
        if(mDistrictSearch!=null){
            mDistrictSearch.destroy();
        }

    }
}
