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
    private int dealCode;//567,???????????????????????????????????????
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
                    context.showMessage("????????????????????????");
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
        context.setToolbar(toolbar,"?????????????????????");
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
     * ???????????????
     * **/
    private void scan() {
        //??????????????????

        QrConfig qrConfig = new QrConfig.Builder()
                .setDesText("(?????????)")//??????????????????
                .setShowDes(false)//?????????????????????????????????
                .setShowLight(true)//?????????????????????
                .setShowTitle(true)//??????Title
                .setShowAlbum(true)//???????????????????????????
                .setCornerColor(Color.WHITE)//?????????????????????
                .setLineColor(Color.WHITE)//?????????????????????
                .setLineSpeed(QrConfig.LINE_MEDIUM)//?????????????????????
                .setScanType(QrConfig.TYPE_ALL)//???????????????????????????????????????????????????????????????????????????????????????
                .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE)//????????????????????????????????????????????????????????????????????????
                .setCustombarcodeformat(QrConfig.BARCODE_I25)//??????????????????????????????TYPE_CUSTOM????????????
                .setPlaySound(true)//?????????????????????bi~?????????
                .setDingPath(R.raw.qrcode)//???????????????(?????????????????????Ding~)
                .setIsOnlyCenter(true)//???????????????????????????(?????????????????????)
                .setTitleText("?????????")//??????Tilte??????
                .setTitleBackgroudColor(Color.BLACK)//?????????????????????
                .setTitleTextColor(Color.WHITE)//??????Title????????????
                .create();
        QrManager.getInstance().init(qrConfig).startScan(context, new QrManager.OnScanResultCallback() {
            @Override
            public void onScanSuccess(String result1) {
                final String result = result1.replaceAll(" ", ""); //????????????
                if(TextUtils.isEmpty(result)){
                    context.showMessage("??????????????????");
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
     * ???????????????
     * **/
    public  void getLocation(){

        mLocationClient = new LocationClient(mContext);
        //??????LocationClient???
        mLocationClient.registerLocationListener(myListener);
        //??????????????????
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        //?????????????????????????????????????????????????????????????????????false
        //?????????????????????????????????????????????????????????????????????true
        option.setNeedNewVersionRgc(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        //option.setScanSpan(3000);//????????????????????????
        //????????????????????????????????????????????????????????????????????????????????????true
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
                        .fillColor(0x666495ED) // ????????????
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
            //??????MarkerOption???????????????????????????Marker
            OverlayOptions option = new MarkerOptions()
                    .position(ll)
                    .perspective(true)
                    .draggable(true)
                    .flat(true)
                    .alpha(0.5f)
                    .icon(bitmap);
            //??????????????????Marker????????????
            mBdMap.addOverlay(option);
            mLocationClient.stop();
        }
    }
    private DistrictSearch mDistrictSearch;
    private void dealWeiLan(){
        if(dealCode==5||dealCode==6||dealCode==7){//????????????
            int toAddressType =driverTransDto.getToAddressType();//1?????????2 ??????
            if(toAddressType==1){//??????
                if(!TextUtils.isEmpty(driverTransDto.getToLocation())){
                    drawCircle(driverTransDto.getToLocation(),2000);
                }
            }else {
                int toEnclosureType=driverTransDto.getToEnclosureType();//1?????? 2???????????? 3?????????
                if(toEnclosureType==1){
                    drawCircle(driverTransDto.getToLocation(),driverTransDto.getToRadius());
                }else if(toEnclosureType==2){//??????????????????
                    mDistrictSearch = DistrictSearch.newInstance();
                    mDistrictSearch.setOnDistrictSearchListener(this);
                    mDistrictSearch.searchDistrict(new DistrictSearchOption().cityName(driverTransDto.getToRegion()));
                }else if(toEnclosureType==3){//???????????????
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


        }else {//????????????
            int fromAddressType =driverTransDto.getFromAddressType();//1?????????2 ??????
            if(fromAddressType==1){//??????
                if(!TextUtils.isEmpty(driverTransDto.getFromLocation())){
                    drawCircle(driverTransDto.getFromLocation(),2000);
                }
            }else {
                int fromEnclosureType=driverTransDto.getFromEnclosureType();//1?????? 2???????????? 3?????????
                if(fromEnclosureType==1){
                    drawCircle(driverTransDto.getFromLocation(),driverTransDto.getFromRadius());
                }else if(fromEnclosureType==2){//??????????????????
                    mDistrictSearch = DistrictSearch.newInstance();
                    mDistrictSearch.setOnDistrictSearchListener(this);
                    mDistrictSearch.searchDistrict(new DistrictSearchOption().cityName(driverTransDto.getFromRegion()));
                }else if(fromEnclosureType==3){//???????????????
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
    // ???????????????????????????????????????????????????????????????????????????
    private LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
    /**
     * ?????????
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
        // ??????????????????
        if (center == null) {
            return;
        }
        mBdMap.addOverlay(new CircleOptions().center(center)
                .radius(radius)
                .fillColor(0x666495ED) // ????????????
                .stroke(new Stroke(3, 0xE66495ED)));
        boundsBuilder.include(center);
    }



    /**
     *  ??????Polygon
     */
    // ???????????????????????????
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
                .fillColor(0x666495ED) // ????????????
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
