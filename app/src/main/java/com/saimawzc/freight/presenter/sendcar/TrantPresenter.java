package com.saimawzc.freight.presenter.sendcar;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.modle.sendcar.imple.TrantModelImple;
import com.saimawzc.freight.modle.sendcar.model.TrantModel;
import com.saimawzc.freight.view.sendcar.DriverTransportView;
import com.saimawzc.freight.weight.utils.ScreenUtil;
import com.yinglan.scrolllayout.ScrollLayout;

/**
 * Created by Administrator on 2020/7/30.
 */

public class TrantPresenter {

    private Context mContext;
    DriverTransportView view;
    TrantModel model;
    BaseActivity activity;
    ScrollLayout scrollLayout;
    private String oPerType="";//       100异常上报  101  获取密码
    public TrantPresenter(DriverTransportView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new TrantModelImple() ;
        activity=(BaseActivity) mContext;
    }

    public void getData(String id , int type){
        model.getTrant(view,id,type);
    }

    public void daka(String id , String type,BDLocation location,String pic){
        model.daka(view,id,type,location,pic);
    }
    public void showCamera(Context context){
        model.showCamera(context,view);
    }
    public void getRoute(String id,int type){
        model.roulete(view,id,type);
    }

    public void getWayBillRolete(String id,String type){
        model.getWayBillRolete(view,id,type );
    }
    public void isFeeced(String id,String location,String operType){
        model.isFenceClock(view,id,location, operType);
    }
    /***
     *
     * 初始化
     * **/
    public void init(ScrollLayout mScrollLayout){
        scrollLayout=mScrollLayout;
        /**设置 setting*/
       // mScrollLayout.setMinOffset(0);
        mScrollLayout.setMaxOffset((int) (ScreenUtil.getScreenHeight(activity) * 0.5));
        mScrollLayout.setExitOffset(ScreenUtil.dip2px(activity, 250));
       // mScrollLayout.setIsSupportExit(true);
        mScrollLayout.setAllowHorizontalScroll(true);
        mScrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        //mScrollLayout.setToExit();
        mScrollLayout.getBackground().setAlpha(255);
        mScrollLayout.setToOpen();//设置初始状态打开
    }



    /***
     * 获取经纬度
     * **/
    public  void getLocation(String  oPerType){

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
        this.oPerType=oPerType;
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
            view.location(location,oPerType);
            mLocationClient.stop();
        }

    }


    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if (currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent > 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
                scrollLayout.getBackground().setAlpha(255 - (int) precent);
            }

        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {//到达底部
            }
        }

        @Override
        public void onChildScroll(int top) {
        }
    };






}
