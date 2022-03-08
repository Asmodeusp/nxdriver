package com.saimawzc.freight.ui.sendcar.driver;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

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
import com.saimawzc.freight.R;
import com.saimawzc.freight.dto.my.RouteDto;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.presenter.sendcar.MapTravelPresenter;
import com.saimawzc.freight.view.sendcar.MapTravelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/****
 * 地图轨迹
 * **/
public class MapTravelFragment extends BaseFragment implements MapTravelView {

    private String id;
    private MapTravelPresenter presenter;
    @BindView(R.id.mapview) MapView mapView;
    private BaiduMap baiduMap;
    @OnClick({R.id.back})
    public void click(View view){
        switch (view.getId()){
            case R.id.back:
                context.finish();
                break;
        }
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_maptravel;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        id=getArguments().getString("id");
        baiduMap=mapView.getMap();
        presenter=new MapTravelPresenter(this,mContext);
        presenter.getData(id);
    }

    @Override
    public void initData() {

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

                    //构造InfoWindow
                    //point 描述的位置点
                    //-100 InfoWindow相对于point在y轴的偏移量
                    InfoWindow mInfoWindow = new InfoWindow(view, marker.getPosition(), -47);
                    //使InfoWindow生效
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
    @Override
    public void getRolte(final RouteDto dto) {

        if(dto!=null){

            if(dto.getPath()!=null){
                String tempLocation=dto.getDestination();
                String[] tempNode = tempLocation.split(",");
                LatLng tempPoint = new LatLng(Double.parseDouble(tempNode[1]), Double.parseDouble(tempNode[0]));
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(tempPoint).zoom(50.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));


                //////描绘主线路
                List<LatLng> points = new ArrayList<LatLng>();
                Log.e("msg",dto.getPath().length+"");
                if(dto.getPath()!=null){
                    for(int i=0;i<dto.getPath().length;i++){
                        String node=dto.getPath()[i];
                        String[] nodeStartarr = node.split(",");
                        LatLng p = new LatLng(Double.parseDouble(nodeStartarr[1]),
                                Double.parseDouble(nodeStartarr[0]));
                        points.add(p);
                    }
                }
                OverlayOptions mOverlayOptions = new PolylineOptions()
                        .width(10)
                        .color(Color.RED)
                        .points(points);
                Overlay mPolyline = baiduMap.addOverlay(mOverlayOptions);
                if(points.size()>=2){
                    try{
                        LatLng t = new LatLng(points.get(0).latitude, points.get(0).longitude);
                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                .fromResource(R.drawable.ico_map_start);
                        OverlayOptions option = new MarkerOptions()
                                .position(t)
                                .icon(bitmap);
                        baiduMap.addOverlay(option);
                    }catch (Exception e){

                    }
                    try{
                        LatLng t2 = new LatLng(points.get(points.size()-1).latitude, points.get(points.size()-1).longitude);
                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                .fromResource(R.drawable.icon_end);
                        //构 建MarkerOption，用于在地图上添加Marker
                        OverlayOptions option = new MarkerOptions()
                                .position(t2)
                                .icon(bitmap);
                        //在                   地图上添加Marker，并显示
                        baiduMap.addOverlay(option);
                    }catch (Exception w){

                    }

                }
            }


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mapView!=null){
            mapView.onDestroy();
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


}
