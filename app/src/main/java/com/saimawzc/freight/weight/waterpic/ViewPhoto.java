package com.saimawzc.freight.weight.waterpic;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.saimawzc.freight.ui.sendcar.driver.DelationAdressMapActivity;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import com.bumptech.glide.Glide;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import butterknife.BindView;
import butterknife.OnClick;

public class ViewPhoto extends BaseActivity {

    @BindView(R.id.imgView) ImageView imageView;
    @BindView(R.id.tv_text) TextView tv_text;
    @BindView(R.id.tv_date)TextView tvData;
    @BindView(R.id.tv_distance)TextView tvDistance;
    @BindView(R.id.btn_save_pic)
    ImageButton btn_save_pic;
    @BindView(R.id.rlPhoto)RelativeLayout rlPhoto;
    @BindView(R.id.btn_close)ImageView btn_close;
    private String distance;
    private String country;
    private String city;
    private int positioningMode=1;
    @BindView(R.id.imgChange)ImageView imgChange;
    private String adress;
    private String changeAdress;
    private String type="";
    private String errorpic="";

    @Override
    protected int getViewId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题**/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.watercamera_layout;
    }

    @OnClick({R.id.btn_close,R.id.btn_save_pic})
    public void click(View view){
        Intent i;
        switch (view.getId()){
            case R.id.btn_close:
                i = new Intent();
                i.putExtra("type","retake");
                context.setResult(Activity.RESULT_OK, i);
                context.finish();
                break;
            case R.id.btn_save_pic:
                btn_save_pic.setVisibility(View.GONE);
                btn_close.setVisibility(View.GONE);
                imgChange.setVisibility(View.GONE);
                try {
                    Bitmap saveBitmap = getScreenPhoto(rlPhoto);
                    if(saveBitmap==null){
                        showMessage("获取图片失败");
                        return;
                    }
                    String photoPath = ImageUtil.saveToFile(ImageUtil.getSystemPhotoPath(ViewPhoto.this),
                            true, saveBitmap);
                     i = new Intent();
                     i.putExtra("type","finish");
                     i.putExtra("photo",photoPath);
                     if(longitude!=0&&latitude!=0){
                         i.putExtra("location",longitude+","+latitude);
                         i.putExtra("adress",tv_text.getText().toString());
                         i.putExtra("distance",TEMP);
                     }
                    i.putExtra("errorpic",errorpic);
                    i.putExtra("positioningMode",positioningMode);
                     context.setResult(Activity.RESULT_OK, i);
                     context.finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    /**
     * 截屏
     * @param waterPhoto waterPhoto
     * @return Bitmap
     */
    public Bitmap getScreenPhoto(RelativeLayout waterPhoto) {

        View view = waterPhoto;
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        int width = view.getWidth();
        int height = view.getHeight();
        Bitmap saveBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        view.destroyDrawingCache();
        bitmap = null;
        return saveBitmap;
    }
    @Override
    protected void init() {
        Glide.with(context.getApplicationContext())
                .load(getIntent().getStringExtra("photoPath"))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        btn_save_pic.setVisibility(View.VISIBLE);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        btn_save_pic.setVisibility(View.VISIBLE);
                        return false;

                    }
                })
                .into(imageView);

        distance=getIntent().getStringExtra("distance");
        if(!TextUtils.isEmpty(distance)){
            try{
                distance=ChangUtil(Double.parseDouble(distance));
            }catch (Exception e){
                distance="";
            }
            tvDistance.setVisibility(View.VISIBLE);
            if(Hawk.get(PreferenceKey.IS_INTOFENCE,false)==true){
                tvDistance.setText("距离目的地0m");
            }else {
                tvDistance.setText("距离目的地"+distance);
            }
        }else {
            tvDistance.setVisibility(View.GONE);
        }
        try{
            country=getIntent().getStringExtra("country");
        }catch (Exception e){
            country="";
        }
        try {
            city=getIntent().getStringExtra("city");
        }catch (Exception e){
            city="";

        }
        try {
            type=getIntent().getStringExtra("fromtype");
        }catch (Exception E){
            type="";
        }

        try{
            adress=getIntent().getStringExtra("adress");
        }catch (Exception E){
        }
        try {
            errorpic= getIntent().getStringExtra("errorpic");
        }catch (Exception e){
            errorpic="";
        }
        if(!TextUtils.isEmpty(type)){
            if(type.equals("arriver")){
                imgChange.setVisibility(View.VISIBLE);
            }
        }
        if(!TextUtils.isEmpty(adress)){
            if(adress.contains("(")){
                tv_text.setText(adress);
            }else {
                tv_text.setText(adress+
                        getIntent().getStringExtra("location"));
            }
        }else {
            tv_text.setText(adress+
                    getIntent().getStringExtra("location"));
        }
        if(TextUtils.isEmpty(adress)){
            getLocation();
        }
        tvData.setText(BaseActivity.getCurrentTime("yyyy-MM-dd HH:mm"));


    }
    private int CHOOSE_DelationCONTRACTS=105;
    @Override
    protected void initListener() {

        imgChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("area",country);
                bundle.putString("city",city);
                bundle.putString("location",getIntent().getStringExtra("location"));
                if(!TextUtils.isEmpty(getIntent().getStringExtra("adresschange"))){
                    bundle.putString("adress",getIntent().getStringExtra("adresschange"));
                }else {
                    bundle.putString("adress",changeAdress);
                }
                readyGoForResult(DelationAdressMapActivity.class,CHOOSE_DelationCONTRACTS,bundle);
            }
        });
    }

    @Override
    protected void onGetBundle(Bundle bundle) {
    }
    public String ChangUtil(double distangce){

        if(distangce<1000){
            BigDecimal b = new BigDecimal((distangce));
            double temp  = b.setScale(2,BigDecimal.ROUND_HALF_UP).
                    doubleValue();
            return temp+"m";
        }else {
            BigDecimal b = new BigDecimal((distangce/1000));
            double temp  = b.setScale(2,BigDecimal.ROUND_HALF_UP).
                    doubleValue();
            return temp+"km";
        }
    }

    private double latitude=0;
    private double longitude=0;
    String TEMP;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_DelationCONTRACTS && resultCode == RESULT_OK) {
            Bundle bundle= data.getExtras();
            if(bundle!=null){
                positioningMode=2;
                latitude=bundle.getDouble("latitude");
                longitude=bundle.getDouble("longitude");
                tv_text.setText(bundle.getString("name")+"("+longitude+","+latitude+")");
                 String  adressLong=getIntent().getStringExtra("toadress");
                 if(!TextUtils.isEmpty(adressLong)){
                     String[] lang =adressLong.split(",");
                     if(lang.length>1){
                         LatLng toLang = new LatLng(Double.parseDouble(lang[1]), Double.parseDouble(lang[0]));
                         LatLng locationLang = new LatLng(latitude,longitude);
                         TEMP = DistanceUtil.getDistance(toLang,locationLang)+"";
                         try{
                             distance=ChangUtil(Double.parseDouble(TEMP));
                         }catch (Exception e){
                             distance="";
                         }
                         if(Hawk.get(PreferenceKey.IS_INTOFENCE,false)==true){
                             tvDistance.setText("距离目的地0m");
                         }else {
                             tvDistance.setText("距离目的地"+distance);
                         }
                     }
                 }
            }
        }
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
        //option.setScanSpan(3000);//连续定位
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
            if(location==null){
                context.showMessage("获取位置信息失败，请检查是否开启定位");
                return;
            }else {
                if( location.getLocType()==62){
                    context.showMessage("获取位置信息失败，请检查是否开启定位");
                    return;
                }
            }
            country=location.getCity();
            city=location.getDistrict();
            changeAdress=location.getAddrStr();
            tv_text.setText(location.getAddrStr()+"("+location.getLongitude()+","+location.getLatitude()+")");
            mLocationClient.stop();

        }
    }

}
