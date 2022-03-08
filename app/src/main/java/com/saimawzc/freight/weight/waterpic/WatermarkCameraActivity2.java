package com.saimawzc.freight.weight.waterpic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.ocr.ui.camera.Camera1Control;
import com.cjt2325.cameralibrary.util.ScreenUtils;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import com.saimawzc.freight.weight.utils.foucsurfaceview.FocusSurfaceView;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

import static android.Manifest.permission.CAMERA;

/**
 * Created by Wxk on 2019/6/20.
 */

public class WatermarkCameraActivity2 extends BaseActivity implements
        View.OnClickListener, SurfaceHolder.Callback {
    private FocusSurfaceView previewSFV;
    private Button mTakeBT;
    private String distance;
    private Camera mCamera;
    private SurfaceHolder mHolder;
    private boolean focus = false;
    private ImageButton btnClose;
    @BindView(R.id.rlphoto)
    RelativeLayout frameLayout;
    @BindView(R.id.image_flash)ImageView tvSg;
    @BindView(R.id.image_switch)ImageView tvHz;
    @BindView(R.id.image_setting)ImageView imgSetting;
    private  int Flash_STATUS=0;
    private boolean isQzPic=false;//是否前置拍照
    boolean isFocus=false;
    private int isCompleteJq=0;
    private boolean isPreviewActive=false;
    private String country;
    private String city;
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initCamera();
        setCameraParams();
    }
    @Override
    protected int getViewId() {
        return R.layout.activity_watermark_camera2;
    }
    @Override
    protected void init() {
        mContext=this;
        previewSFV = (FocusSurfaceView) findViewById(R.id.preview_sv);
        mHolder = previewSFV.getHolder();
        mHolder.addCallback(WatermarkCameraActivity2.this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mTakeBT = (Button) findViewById(R.id.take_bt);
        mTakeBT.setOnClickListener(this);
        tvHz.setOnClickListener(this);
        tvSg.setOnClickListener(this);
        btnClose = (ImageButton) findViewById(R.id.btn_close);
        btnClose.setOnClickListener(this);
        try {
            distance = getIntent().getStringExtra("distance");
        } catch (Exception e) {
            distance = "";
        }
        try {
            country=getIntent().getStringExtra("country");
        }catch (Exception e){

        }
        try {
            city=getIntent().getStringExtra("city");
        }catch (Exception e){

        }
    }

    private boolean faous;
    @Override
    protected void initListener() {

        previewSFV.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(myAutoFocusCallback!=null&&isCompleteJq==0&&event.getAction()==0&&isPreviewActive==true){
                    ++isCompleteJq;
                    mCamera.autoFocus(myAutoFocusCallback);
                }else {
                    //context.showMessage("正在聚焦中，请稍后");
                }
                return false;
            }
        });
        //选择系统分辨率
        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSingleAlertDialog(imgSetting);
            }
        });

    }
    private Camera.AutoFocusCallback myAutoFocusCallback = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            faous=success;
            isCompleteJq=0;
            mCamera.cancelAutoFocus();
            if(success){
                context.showMessage("聚焦成功");
            }else {
                context.showMessage("聚焦失败,请放正摄像头并点击屏幕重新聚焦");
            }
        }
    };

    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    private int cameraType=0;//0后置 1前置
    boolean isChang=false;
    private void initCamera() {
        if (checkPermission()) {
            try {
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.GINGERBREAD) {
                    //打开摄像头
                    mCamera = Camera.open();
                } else {
                    //打开摄像头
                    mCamera = Camera.open(cameraType);
                }
                if (mCamera == null) {
                    mCamera = Camera.open();
                }
                mCamera.setPreviewDisplay(mHolder);

            } catch (Exception e) {
                Snackbar.make(mTakeBT, "camera open failed!", Snackbar.LENGTH_SHORT).show();
                finish();
                e.printStackTrace();
            }
        } else {
            requestPermission();
        }
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, 10000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 10000:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        initCamera();
                        setCameraParams();
                    }
                }
                break;
        }
    }
    Camera.Parameters parameters;
    private void setCameraParams() {
        if (mCamera == null) {
            Snackbar.make(mTakeBT, "camera open failed!", Snackbar.LENGTH_SHORT).show();
            return;
        }
        try {
            if(parameters==null){
                parameters = mCamera.getParameters();
            }
            int orientation = judgeScreenOrientation();
            if (Surface.ROTATION_0 == orientation) {
                mCamera.setDisplayOrientation(90);
                parameters.setRotation(90);
            } else if (Surface.ROTATION_90 == orientation) {
                mCamera.setDisplayOrientation(0);
                parameters.setRotation(0);
            } else if (Surface.ROTATION_180 == orientation) {
                mCamera.setDisplayOrientation(180);
                parameters.setRotation(180);
            } else if (Surface.ROTATION_270 == orientation) {
                mCamera.setDisplayOrientation(180);
                parameters.setRotation(180);
            }
            if(isChang==false){

                List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();//获取所有支持的预览大小
                Camera.Size bestSize = getOptimalPreviewSize(sizeList, ScreenUtils.getScreenWidth(mContext), ScreenUtils.getScreenHeight(mContext));
                if(bestSize==null){
                    parameters.setPictureSize(1280, 720);
                    parameters.setPreviewSize(1280, 720);
                }else {
                    parameters.setPreviewSize(bestSize.width, bestSize.height);//设置预览大小
                    parameters.setPictureSize(1280, 720);

                }
            }else {
                parameters.setPictureSize(1280, 720);
                parameters.setPreviewSize(1280, 720);
            }
            mCamera.setParameters(parameters);
            mCamera.startPreview();
            isPreviewActive=true;
            if(myAutoFocusCallback!=null&&isCompleteJq==0){
                ++isCompleteJq;
                mCamera.autoFocus(myAutoFocusCallback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 最小预览界面的分辨率
     */
    private static final int MIN_PREVIEW_PIXELS = 1280 * 720;
    /**
     * 最大宽高比差
     */
    private static final double MAX_ASPECT_DISTORTION = 0.15;
    /**
     * 判断屏幕方向
     *
     * @return 0：竖屏 1：左横屏 2：反向竖屏 3：右横屏
     */
    private int judgeScreenOrientation() {
        return getWindowManager().getDefaultDisplay().getRotation();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        releaseCamera();
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.lock();
            mCamera.stopPreview();
            isPreviewActive=false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_bt:
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                if(myAutoFocusCallback!=null&&faous==false&&isCompleteJq==0&&isPreviewActive){
                    isCompleteJq++;
                    mCamera.autoFocus(myAutoFocusCallback);
                }
                if(faous==false){
                    context.showMessage("您当前未聚焦,拍摄可能模糊,请点击屏幕聚焦");
                }
                Log.e("msg","开始拍照");
                if (!focus&&isPreviewActive) {
                    mTakeBT.setClickable(false);
                    takePicture();
                }
                break;
            case R.id.btn_close:
                finish();
                break;
            case R.id.image_switch:
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                if(cameraType==0){
                    cameraType=1;
                    isQzPic=true;
                }else {
                    cameraType=0;
                    isQzPic=false;
                }
                releaseCamera();
                isChang=true;
                isFocus=false;
                if(mCamera!=null){
                    context.showMessage("release fail");
                }
                try {
                    changeCamera(cameraType);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.image_flash:
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                if(Flash_STATUS==0){
                    tvSg.setImageResource(R.drawable.ic_flash_on);
                    Flash_STATUS++;
                    turnOnFlash();
                }else {
                    tvSg.setImageResource(R.drawable.ic_flash_off);
                    Flash_STATUS=0;
                    turnOffFlash();
                }
                break;

        }
    }

    /**
     * 拍照
     */
    private void takePicture() {
        if (mCamera == null) {
            context.showMessage("未获取到系统相机");
            return;
        }
        mCamera.takePicture(new Camera.ShutterCallback() {
            @Override
            public void onShutter() {
            }
        }, null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(final byte[] data, Camera camera) {
                if (data == null) {
                    context.showMessage("未获取到相册");
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String path = saveToSDCard(data);//
                                if (TextUtils.isEmpty(path) || data == null) {
                                    context.showMessage("未获取到图片");
                                    return;
                                }
                                Bundle bundle = new Bundle();
                                bundle.putString("adress", getIntent().getStringExtra("adress") + "(" + getIntent().getStringExtra("location") + ")");
                                bundle.putString("photoPath", path);
                                bundle.putString("distance", distance);
                                bundle.putString("toadress", getIntent().getStringExtra("toadress"));
                                bundle.putString("location",getIntent().getStringExtra("location"));
                                if(!TextUtils.isEmpty(country)){
                                    bundle.putString("country", country);
                                }else {
                                    bundle.putString("country", "");
                                }
                                if(!TextUtils.isEmpty(city)){
                                    bundle.putString("city", city);
                                }else {
                                    bundle.putString("city", "");
                                }
                                bundle.putString("adresschange", getIntent().getStringExtra("adress"));

                                readyGoForResult(ViewPhoto.class, 2001, bundle);
                                if(mCamera!=null){
                                   // mCamera.startPreview();
                                }
                                mTakeBT.setClickable(true);
                            }
                        });
                    }
                }).start();

            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    /**
     * 用来监测左横屏和右横屏切换时旋转摄像头的角度
     */
    private class DetectScreenOrientation extends OrientationEventListener {
        DetectScreenOrientation(Context context) {
            super(context);
        }
        @Override
        public void onOrientationChanged(int orientation) {
            if (260 < orientation && orientation < 290) {
                setCameraParams();
            } else if (80 < orientation && orientation < 100) {
                setCameraParams();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2001 && resultCode == RESULT_OK) {
            if (data.getStringExtra("type").equals("retake")) {

            } else {
                Intent i = new Intent();
                i.putExtra("tempImage", data.getStringExtra("photo"));
                if(data.getIntExtra("positioningMode",1)==2){
                    i.putExtra("location",data.getStringExtra("location"));
                    i.putExtra("adress",data.getStringExtra("adress"));
                    i.putExtra("distance",data.getStringExtra("distance"));
                }
                i.putExtra("positioningMode",data.getIntExtra("positioningMode",1));
                context.setResult(Activity.RESULT_OK, i);
                context.finish();
            }
            context.finish();
            ;

        }
    }


    /**
     * 将拍下来的照片存放在SD卡中
     *
     * @param data
     * @throws IOException
     */
    public String saveToSDCard(byte[] data) {
        Bitmap croppedImage = null;
        String imagePath = null;
        try {
            croppedImage = decodeRegionCrop(data);
            imagePath = ImageUtil.saveToFile(ImageUtil.getSystemPhotoPath(WatermarkCameraActivity2.this), true,
                    croppedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(croppedImage!=null){
            croppedImage.recycle();
        }
        return imagePath;
    }

    private Bitmap decodeRegionCrop(byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        if(isQzPic){
            Matrix matrix = new Matrix();
            matrix.postRotate(180);
           bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }
    public void turnOnFlash(){
        if(mCamera != null){
            try {
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
                mCamera.startPreview();
                isPreviewActive=true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void turnOffFlash(){
        if(mCamera != null){
            try {
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
                mCamera.startPreview();
                isPreviewActive=true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        turnOffFlash();
    }
    private void changeCamera(int camera) throws IOException{
        mCamera=openCamera(camera);
        if(mCamera!=null){
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
            isPreviewActive=true;
            if(isChang==true){
                setCameraParams();
            }
        }

    }

    @SuppressLint("NewApi")
    private Camera openCamera(int type){
        int frontIndex =-1;
        int backIndex = -1;
        int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for(int cameraIndex = 0; cameraIndex<cameraCount; cameraIndex++){
            Camera.getCameraInfo(cameraIndex, info);
            if(info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                frontIndex = cameraIndex;
            }else if(info.facing == Camera.CameraInfo.CAMERA_FACING_BACK){
                backIndex = cameraIndex;
            }
        }
        if(type == 1){
            return Camera.open(frontIndex);
        }else if(type == 0){
            return Camera.open(backIndex);
        }
        return null;
    }
    /**
     * 获取最佳预览大小
     *
     * @param sizes 所有支持的预览大小
     * @param w     SurfaceView宽
     * @param h     SurfaceView高
     * @return
     */
    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null)
            return null;
        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        int targetHeight = h;
        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }
    private String chooseDips="720p";
    private AlertDialog alertDialog2; //单选框
    public void showSingleAlertDialog(View view){
        //720p（720×1280），1080p（1080×1920），2k（2560×1440）
        final String[] items = {"720*1280","1080*2160", "1080*1920", "1440*2560"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择分辨率");
        alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.e("msg","点击"+items[i]);
                 chooseDips=items[i];
            }
        });

        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog2.dismiss();
                if(chooseDips.equals("720p")){

                }else if(chooseDips.equals("1080p")){

                }else if(chooseDips.equals("2k")) {

                }

            }
        });

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog2.dismiss();
            }
        });

        alertDialog2 = alertBuilder.create();
        alertDialog2.show();
    }

    /**
     * 更新预览分辨率
     * @param
     */
    public void changePreviewViewSize(int with ,int height)
    {
        if(mCamera!=null){
            mCamera.stopPreview();
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewSize(with, height);
            mCamera.setParameters(parameters);
            mCamera.startPreview();
        }
    }

}
