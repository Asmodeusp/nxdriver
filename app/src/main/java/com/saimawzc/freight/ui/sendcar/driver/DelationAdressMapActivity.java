package com.saimawzc.freight.ui.sendcar.driver;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Projection;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.weight.utils.GPSUtil;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/***
 * 地图选择详细地址
 * **/
public class DelationAdressMapActivity extends BaseActivity
        implements OnGetDistricSearchResultListener, OnGetGeoCoderResultListener {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.mapView)
    MapView mMapView;
    private DistrictSearch mDistrictSearch;
    private BaiduMap mBaiduMap;
    private String area;
    private String city;
    @BindView(R.id.searchkey) AutoCompleteTextView mKeyWordsView;
    @BindView(R.id.sug_list) ListView mSugListView;
    private SuggestionSearch mSuggestionSearch ;
    List<HashMap<String, String>> suggest = new ArrayList<>();
    SimpleAdapter simpleAdapter;
    private String location;
    /**
     * 反地理编码
     */
    private GeoCoder geoCoder;
    // 默认逆地理编码半径范围
    private static final int sDefaultRGCRadius = 500;
    LatLng mCenter;

    @Override
    protected int getViewId() {
        return R.layout.activity_mapdaress;
    }

    @Override
    protected void init() {
        setToolbar(toolbar,"详细地址");
        mBaiduMap=mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        area=getIntent().getStringExtra("area");
        city=getIntent().getStringExtra("city");
        //显示城市轮廓
//        mDistrictSearch = DistrictSearch.newInstance();
//        mDistrictSearch.setOnDistrictSearchListener(this);
//        mDistrictSearch.searchDistrict(new DistrictSearchOption().cityName(area));

        mSuggestionSearch= SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);
        // 当输入关键字变化时，动态更新建议列表
        mKeyWordsView.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (TextUtils.isEmpty(cs.toString())) {
                    mSugListView.setVisibility(View.GONE);
                    return;
                }
                if(mSuggestionSearch!=null){
                    mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                            .city(city)
                            .citylimit(false)
                            .keyword(cs.toString()));
                }
            }
        });



        location=getIntent().getStringExtra("location");
        if(!TextUtils.isEmpty(location)){
            if(location.contains(",")){
                String[] statrNode = location.split(",");
                final   LatLng startPoint = new LatLng(Double.parseDouble(statrNode[1]),Double.parseDouble(statrNode[0]));
                // 设置初始中心点为北京
                mCenter = startPoint;
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(mCenter, 16);
                mBaiduMap.setMapStatus(mapStatusUpdate);
                mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        createCenterMarker();
                        reverseRequest(mCenter);
                    }
                });

            }

        }

    }
    /**
     * 逆地理编码请求
     *
     * @param latLng
     */
    private void reverseRequest(LatLng latLng) {
        if (null == latLng) {
            return;
        }

        ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption().location(latLng)
                .newVersion(1) // 建议请求新版数据
                .radius(sDefaultRGCRadius);

        if (null == geoCoder) {
            geoCoder = GeoCoder.newInstance();
        }

        geoCoder.setOnGetGeoCodeResultListener(this);
        geoCoder.reverseGeoCode(reverseGeoCodeOption);
    }
    private boolean mStatusChangeByItemClick = false;
    @Override
    protected void initListener() {
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
            }
            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
                mStatusChangeByItemClick=true;
                showInfoWindow(mapPoi.getName(),mapPoi.getPosition());
            }
        });
        mSugListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(suggest.size()<=position){
                    return;
                }
                if(!TextUtils.isEmpty(suggest.get(position).get("latitude"))){
                    double latuye=Double.parseDouble(suggest.get(position).get("latitude"));
                    double longtute=Double.parseDouble(suggest.get(position).get("longitude"));
                    LatLng latLng=new LatLng(latuye,longtute);
                    Log.e("msg",suggest.get(position).get("key")+latuye+"``"+longtute);
                    mSugListView.setVisibility(View.GONE);
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(latLng).zoom(18.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                    showInfoWindow(suggest.get(position).get("key"),latLng);
                }else {
                    context.showMessage("获取位置有误");
                }
            }
        });
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            //地图状态开始改变。
            public void onMapStatusChangeStart(MapStatus status) {
            }
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {
            }
            //地图状态改变结束
            public void onMapStatusChangeFinish(MapStatus status) {

                LatLng newCenter = status.target;

                // 如果是点击poi item导致的地图状态更新，则不用做后面的逆地理请求，
                if (mStatusChangeByItemClick) {
                    if (!GPSUtil.isLatlngEqual(mCenter, newCenter)) {
                        mCenter = newCenter;
                    }
                    mStatusChangeByItemClick = false;
                    return;
                }

                if (!GPSUtil.isLatlngEqual(mCenter, newCenter)) {
                    mCenter = newCenter;
                    reverseRequest(mCenter);
                }
            }
            //地图状态变化中
            public void onMapStatusChange(MapStatus status) {

            }
        });


    }

    @Override
    protected void onGetBundle(Bundle bundle) {
    }

    @Override
    public void onGetDistrictResult(DistrictResult districtResult) {
        if (districtResult.error == SearchResult.ERRORNO.NO_ERROR){
            List<List<LatLng>> polyLines = districtResult.getPolylines();
            if (polyLines == null||mBaiduMap==null){
                return;
            }
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (List<LatLng> polyline : polyLines){
                PolylineOptions ooPolyline11 = new PolylineOptions().width(4)
                        .points(polyline).dottedLine(true).color(Color.RED);
                mBaiduMap.addOverlay(ooPolyline11);
                for (LatLng latLng : polyline){
                    builder.include(latLng);
                }
            }
            mBaiduMap.setMapStatus(MapStatusUpdateFactory
                    .newLatLngBounds(builder.build()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(null!=mMapView){
            mMapView.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDistrictSearch!=null){
            mDistrictSearch.destroy();
        }
        if(mSuggestionSearch!=null){
            mSuggestionSearch.destroy();
        }
        if(mBaiduMap!=null){
            mBaiduMap.clear();
            mBaiduMap=null;
        }
        if(mMapView!=null){
            mMapView.onDestroy();
            mMapView = null;
        }
        //释放资源
        if (geoCoder != null) {
            geoCoder.destroy();
        }

    }

    OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult suggestionResult) {
            //处理sug检索结果
            if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
                return;
            }
            suggest.clear();
            mSugListView.setVisibility(View.VISIBLE);
            for (SuggestionResult.SuggestionInfo info : suggestionResult.getAllSuggestions()) {
                if (info.getKey() != null && info.getDistrict() != null && info.getCity() != null) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("key",info.getKey());
                    map.put("city",info.getCity());
                    map.put("dis",info.getDistrict());
                    if(info.getPt()!=null){
                        map.put("latitude",info.getPt().latitude+"");
                        map.put("longitude",info.getPt().longitude+"");
                    }
                    suggest.add(map);
                }
            }
            if(simpleAdapter==null){
                simpleAdapter= new SimpleAdapter(getApplicationContext(),
                        suggest,
                        R.layout.item_autoview,
                        new String[]{"key", "city","dis"},
                        new int[]{R.id.sug_key, R.id.sug_city, R.id.sug_dis});
            }
            mSugListView.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
        }
    };
    int temp=0;

    private void showInfoWindow(String name, LatLng location){

        if(mBaiduMap==null||location==null){
            context.showMessage("暂未获取到位置信息，请稍后");
            return;
        }

        if(temp>0){
            mBaiduMap.clear();
        }

        View view= LayoutInflater.from(DelationAdressMapActivity.this).
                inflate(R.layout.map_show_delation,null);
        TextView tvName=view.findViewById(R.id.tvName);
        TextView tvLocation=view.findViewById(R.id.tvlocation);
        tvName.setText(name);
        tvLocation.setText("经度"+location.longitude+"纬度："+location.latitude);
        InfoWindow mInfoWindow = new InfoWindow(view, location, -47);
        //使InfoWindow生效
        mBaiduMap.showInfoWindow(mInfoWindow);

        final Bundle  bundle=new Bundle();
        bundle.putDouble("latitude",location.latitude);
        bundle.putDouble("longitude",location.longitude);
        bundle.putString("name",name);
        TextView imgDaoahng=view.findViewById(R.id.imgDaoahng);
        imgDaoahng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        if(reverseGeoCodeResult==null){
            return;
        }

        showInfoWindow(reverseGeoCodeResult.getAddress(),reverseGeoCodeResult.getLocation());


    }

    @Override
    protected void onPause() {
        super.onPause();
        if(null!=mMapView){
            mMapView.onPause();
        }

    }

    /**
     * 创建地图中心点marker
     */
    private void createCenterMarker() {
        Projection projection = mBaiduMap.getProjection();
        if (null == projection) {
            return;
        }

        Point point = projection.toScreenLocation(mCenter);
        BitmapDescriptor bitmapDescriptor =
                BitmapDescriptorFactory.fromResource(R.drawable.icon_binding_point);
        if (null == bitmapDescriptor) {
            return;
        }

        MarkerOptions markerOptions = new MarkerOptions()
                .position(mCenter)
                .icon(bitmapDescriptor)
                .flat(false)
                .fixedScreenPosition(point);
        mBaiduMap.addOverlay(markerOptions);
        bitmapDescriptor.recycle();
    }
}
