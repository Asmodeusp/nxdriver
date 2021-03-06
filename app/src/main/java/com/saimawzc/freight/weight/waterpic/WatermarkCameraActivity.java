package com.saimawzc.freight.weight.waterpic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.weight.utils.GLView;
import com.saimawzc.freight.weight.utils.MySurfaceView;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Wxk on 2019/6/20.
 */

public class WatermarkCameraActivity extends BaseActivity implements
        SurfaceHolder.Callback {

    private SurfaceView surfaceView;
    private TextView tvText;
    private TextView tvDate;
    private ImageButton btnTakePic;
    private LinearLayout llSetting;
    private ImageView ivPhoto;
    private RelativeLayout rlPhoto;
    private ImageButton btnSavePic;
    private ImageButton btnClose;
    private RelativeLayout rlTopBar;
    private ImageButton btnLamp;
    @BindView(R.id.rlbaocun)RelativeLayout rlbaocun;

    private View focusIndex;
    private Camera.Parameters parameters = null;
    private Camera cameraInst = null;
    private float pointX, pointY;
    static final int FOCUS = 1;            // ??????
    static final int ZOOM = 2;            // ??????
    private int mode;                      //0????????? 1?????????
    private float dist;
    private int topBarHeight;// ?????????????????????
    private int statusBarHeight;// ?????????????????????
    private int settingBarHeight;// ???????????????
    private int navigationBarHeight;// ?????????????????????
    private float surfaceHeightScale;// ????????????
    private float topBarHeightScale;// ???????????????????????????????????????
    private String photoPath;
    public static final int REQUEST_WATERMARK_CAMERA = 115;
    private Handler handler = new Handler();
    private String distance;


    // ????????????
    public void onPointFocus(View view) {
        try {
            pointFocus((int) pointX, (int) pointY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(focusIndex.getLayoutParams());
        layout.setMargins((int) pointX - 60, (int) pointY - 60, 0, 0);
        focusIndex.setLayoutParams(layout);
        focusIndex.setVisibility(View.VISIBLE);
        ScaleAnimation sa = new ScaleAnimation(3f, 1f, 3f, 1f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(800);
        focusIndex.startAnimation(sa);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                focusIndex.setVisibility(View.INVISIBLE);
            }
        }, 800);
    }



    // ?????????????????????
    public void onSwitch(View view) {
        turnLight(cameraInst);
    }

    // ????????????
    public void onClose(View view) {
        ivPhoto.setVisibility(View.GONE);
        surfaceView.setVisibility(View.VISIBLE);
        btnSavePic.setVisibility(View.GONE);
        btnTakePic.setVisibility(View.VISIBLE);
        btnClose.setVisibility(View.GONE);
        finish();
    }


    // ??????
    private boolean isBreak = true;
    public void onTakePic(View view) {
      //  isBreak = true;

        if(isBreak==true){
            isBreak=false;
            cameraInst.takePicture(null, null, new MyPictureCallback());
            cameraInst.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    //focus = success;
                    if (success) {
                        cameraInst.cancelAutoFocus();
                        cameraInst.takePicture(new Camera.ShutterCallback() {
                            @Override
                            public void onShutter() {
                            }
                        }, null, null, new Camera.PictureCallback() {
                            @Override
                            public void onPictureTaken(byte[] data, Camera camera) {
                                photoPath = saveToSDCard(data);
                                surfaceView.setVisibility(View.VISIBLE);
                                btnSavePic.setVisibility(View.GONE);//????????????
                                tvText.setVisibility(View.VISIBLE);
                                tvDate.setVisibility(View.VISIBLE);
                                btnTakePic.setVisibility(View.GONE);
                                btnClose.setVisibility(View.GONE);
                                Bundle bundle=new Bundle();
                                bundle.putString("adress",getIntent().getStringExtra("adress"));
                                bundle.putString("photoPath",photoPath);
                                bundle.putString("distance",distance);
                                readyGoForResult(ViewPhoto.class,2001,bundle);
                            }
                        });
                    }
                }
            });
        }else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isBreak = true;
                }
            }, 5000);
        }


    }



    @Override
    protected int getViewId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_watermark_camera;
    }

    @Override
    protected void init() {

        initView();

        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    // ????????????
                    case MotionEvent.ACTION_DOWN:
                        pointX = event.getX();
                        pointY = event.getY();
                        mode = FOCUS;
                        break;
                    // ????????????
                    case MotionEvent.ACTION_POINTER_DOWN:
                        dist = spacing(event);
                        // ??????????????????????????????10???????????????????????????
                        if (spacing(event) > 10f) {
                            mode = ZOOM;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = FOCUS;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == FOCUS) {
                            //pointFocus((int) event.getRawX(), (int) event.getRawY());
                        } else if (mode == ZOOM) {
                            float newDist = spacing(event);
                            if (newDist > 10f) {
                                float tScale = (newDist - dist) / dist;
                                if (tScale < 0) {
                                    tScale = tScale * 10;
                                }
                                addZoomIn((int) tScale);
                            }
                        }
                        break;
                }
                return false;
            }
        });
    }
    @Override
    protected void initListener() {
    }
    @Override
    protected void onGetBundle(Bundle bundle) {
    }
    // ????????????
    public void onTextEdit(View view) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        final Dialog dialog = new Dialog(this);
        //????????????????????????
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_edit, null);
        final EditText etText = (EditText) inflate.findViewById(R.id.et_text);
        final ImageButton btnClear = (ImageButton) inflate.findViewById(R.id.btn_clear);
        final RelativeLayout rlCancel = (RelativeLayout) inflate.findViewById(R.id.rl_cancel);
        final RelativeLayout rlConfirm = (RelativeLayout) inflate.findViewById(R.id.rl_confirm);
        etText.setText(tvText.getText().toString());
        etText.setSelection(etText.getText().length());
        // ??????
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etText.setText("");
            }
        });
        // ??????
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                imm.showSoftInput(etText, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });
        // ??????
        rlConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(etText.getText()))
                    tvText.setText(etText.getText());
                dialog.dismiss();
                imm.showSoftInput(etText, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });
        //??????????????????Dialog
        dialog.setContentView(inflate);
        //????????????Activity???????????????
        Window dialogWindow = dialog.getWindow();
        //??????Dialog?????????????????????
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                imm.showSoftInput(etText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        dialog.show();//???????????????
    }

    /**
     * ??????
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

    /**
     * ???????????????
     */
    private float spacing(MotionEvent event) {
        if (event == null) {
            return 0;
        }
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }



    private void initView() {
        surfaceView = (SurfaceView) findViewById(R.id.sfv_camera);
        tvText = (TextView) findViewById(R.id.tv_text);
        if(!TextUtils.isEmpty(getIntent().getStringExtra("adress"))){
            tvText.setText(getIntent().getStringExtra("adress"));
        }
        try{
            distance=getIntent().getStringExtra("distance");
        }catch (Exception e){
            distance="";

        }


        tvDate = (TextView) findViewById(R.id.tv_date);
        tvDate.setText(BaseActivity.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        btnTakePic = (ImageButton) findViewById(R.id.btn_take_pic);
        llSetting = (LinearLayout) findViewById(R.id.ll_setting);
        ivPhoto = (ImageView) findViewById(R.id.iv_photo);
        rlPhoto = (RelativeLayout) findViewById(R.id.rl_photo);
        btnSavePic = (ImageButton) findViewById(R.id.btn_save_pic);

        btnClose = (ImageButton) findViewById(R.id.btn_close);
        rlTopBar = (RelativeLayout) findViewById(R.id.rl_top_bar);
        btnLamp = (ImageButton) findViewById(R.id.btn_lamp);
        focusIndex = findViewById(R.id.focus_index);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.setKeepScreenOn(true);
        surfaceView.setFocusable(true);
        surfaceView.setBackgroundColor(TRIM_MEMORY_BACKGROUND);
        surfaceView.getHolder().addCallback(new SurfaceCallback());//???SurfaceView?????????????????????????????????
    }

    //????????????
    int curZoomValue = 0;
    private void addZoomIn(int delta) {

        try {
            Camera.Parameters params = cameraInst.getParameters();
            Log.d("Camera", "Is support Zoom " + params.isZoomSupported());
            if (!params.isZoomSupported()) {
                return;
            }
            curZoomValue += delta;
            if (curZoomValue < 0) {
                curZoomValue = 0;
            } else if (curZoomValue > params.getMaxZoom()) {
                curZoomValue = params.getMaxZoom();
            }

            if (!params.isSmoothZoomSupported()) {
                params.setZoom(curZoomValue);
                cameraInst.setParameters(params);
                return;
            } else {
                cameraInst.startSmoothZoom(curZoomValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
//            cameraInst = Camera.open(mCameraId);
//            Camera.getCameraInfo(mCameraId, cameraInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private final class MyPictureCallback implements Camera.PictureCallback {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            photoPath = saveToSDCard(data);

            surfaceView.setVisibility(View.VISIBLE);
            btnSavePic.setVisibility(View.GONE);//????????????
            tvText.setVisibility(View.VISIBLE);
            tvDate.setVisibility(View.VISIBLE);
            btnTakePic.setVisibility(View.GONE);
            btnClose.setVisibility(View.GONE);
            Bundle bundle=new Bundle();
            bundle.putString("adress",getIntent().getStringExtra("adress"));
            bundle.putString("photoPath",photoPath);
            bundle.putString("distance",distance);
            readyGoForResult(ViewPhoto.class,2001,bundle);


            btnSavePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bitmap saveBitmap = getScreenPhoto(rlPhoto);
                    try {
                        String photoPath = ImageUtil.saveToFile(ImageUtil.getSystemPhotoPath(WatermarkCameraActivity.this), true, saveBitmap);
                        Intent i = new Intent();
                        i.putExtra("tempImage",photoPath);
                        context.setResult(Activity.RESULT_OK, i);
                        context.finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private final class SurfaceCallback implements SurfaceHolder.Callback {

        public void surfaceDestroyed(SurfaceHolder holder) {
            try {
                if (cameraInst != null) {
                    cameraInst.stopPreview();
                    cameraInst.release();
                    cameraInst = null;
                }
            } catch (Exception e) {
                //??????????????????
            }

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (null == cameraInst) {
                try {
                    topBarHeight = rlTopBar.getHeight();
                    settingBarHeight = llSetting.getHeight();
                    settingBarHeight = 0;
                    statusBarHeight = AppUtils.getStatusBarHeight(WatermarkCameraActivity.this);
                    if(AppUtils.checkDeviceHasNavigationBar(WatermarkCameraActivity.this))
                        navigationBarHeight = AppUtils.getNavigationBarHeight(WatermarkCameraActivity.this);
                    surfaceHeightScale =  (float) (DensityUtils.getDisplayHeight(WatermarkCameraActivity.this) - statusBarHeight - topBarHeight - settingBarHeight - navigationBarHeight) / (float) DensityUtils.getDisplayHeight(WatermarkCameraActivity.this);
                    topBarHeightScale = (float) (topBarHeight) / (float) DensityUtils.getDisplayHeight(WatermarkCameraActivity.this);
                    cameraInst = Camera.open();
                    cameraInst.setPreviewDisplay(holder);
                    initCamera();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            autoFocus();
        }
    }

    //??????????????????
    private void autoFocus() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(100);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (cameraInst == null) {
                    return;
                }
                cameraInst.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        if (success) {
                            initCamera();//??????????????????????????????
                        }
                    }
                });
            }
        };
    }

    private Camera.Size adapterSize = null;
    private Camera.Size previewSize = null;

    private void initCamera() {
        parameters = cameraInst.getParameters();
        parameters.setPictureFormat(PixelFormat.JPEG);
        if (previewSize == null && adapterSize == null) {
            setUpPicSize(parameters);
            setUpPreviewSize(parameters);
        }
        if (previewSize != null && adapterSize != null) {
            parameters.setPreviewSize(previewSize.width, previewSize.height);
            parameters.setPictureSize(adapterSize.width, adapterSize.height);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);//1????????????
        } else {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }

        setDispaly(parameters, cameraInst);
        try {
            cameraInst.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cameraInst.startPreview();
        cameraInst.cancelAutoFocus();// 2????????????????????????????????????????????????????????????
    }

    private void setUpPicSize(Camera.Parameters parameters) {

        if (adapterSize != null) {
            return;
        } else {
            adapterSize = findBestPictureResolution();
            return;
        }
    }

    private void setUpPreviewSize(Camera.Parameters parameters) {

        if (previewSize != null) {
            return;
        } else {
            previewSize = findBestPreviewResolution();
        }
    }

    /**
     * ??????????????????????????????
     */
    private static final int MIN_PREVIEW_PIXELS = 480 * 320;
    /**
     * ??????????????????
     */
    private static final double MAX_ASPECT_DISTORTION = 0.15;
    private static final String TAG = "Camera";


    //?????????????????????????????????
    private void setDispaly(Camera.Parameters parameters, Camera camera) {
        if (Build.VERSION.SDK_INT >= 8) {
            setDisplayOrientation(camera, 90);
        } else {
            parameters.setRotation(90);
        }
    }

    //??????????????????????????????
    private void setDisplayOrientation(Camera camera, int i) {
        Method downPolymorphic;
        try {
            downPolymorphic = camera.getClass().getMethod("setDisplayOrientation",
                    int.class);
            if (downPolymorphic != null) {
                downPolymorphic.invoke(camera, i);
            }
        } catch (Exception e) {
            Log.e("Came_e", "????????????");
        }
    }

    /**
     * ??????????????????????????????SD??????
     *
     * @param data
     * @throws IOException
     */
    public String saveToSDCard(byte[] data) {
        Bitmap croppedImage = null;
        String imagePath = null;
        try {
            croppedImage = decodeRegionCrop(data);
            imagePath = ImageUtil.saveToFile(ImageUtil.getSystemPhotoPath(WatermarkCameraActivity.this), true,
                    croppedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        croppedImage.recycle();
        return imagePath;
    }

    private Bitmap decodeRegionCrop(byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Matrix matrix = new Matrix();
       matrix.preRotate(90);
       bitmap = Bitmap.createBitmap(bitmap , (int) (bitmap.getWidth() * topBarHeightScale), 0, (int) (bitmap.getWidth() * surfaceHeightScale), bitmap.getHeight(), matrix, true);

        return bitmap;
    }

    /**
     * ???????????????   ???->???->??????
     *
     * @param mCamera
     */
    private void turnLight(Camera mCamera) {
        if (mCamera == null || mCamera.getParameters() == null
                || mCamera.getParameters().getSupportedFlashModes() == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        String flashMode = mCamera.getParameters().getFlashMode();
        List<String> supportedModes = mCamera.getParameters().getSupportedFlashModes();
        if (Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)
                && supportedModes.contains(Camera.Parameters.FLASH_MODE_ON)) {//????????????
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
            mCamera.setParameters(parameters);
            btnLamp.setBackgroundResource(R.drawable.fresh_open);
        } else if (Camera.Parameters.FLASH_MODE_ON.equals(flashMode)) {//????????????
            if (supportedModes.contains(Camera.Parameters.FLASH_MODE_AUTO)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                btnLamp.setBackgroundResource(R.drawable.fresh_auto);
                mCamera.setParameters(parameters);
            } else if (supportedModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                btnLamp.setBackgroundResource(R.drawable.fresh_close);
                mCamera.setParameters(parameters);
            }
        } else if (Camera.Parameters.FLASH_MODE_AUTO.equals(flashMode)
                && supportedModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
            btnLamp.setBackgroundResource(R.drawable.fresh_close);
        }
    }

    /**
     * ???????????????????????????????????????
     *
     * @return
     */
    private Camera.Size findBestPreviewResolution() {
        Camera.Parameters cameraParameters = cameraInst.getParameters();
        Camera.Size defaultPreviewResolution = cameraParameters.getPreviewSize();

        List<Camera.Size> rawSupportedSizes = cameraParameters.getSupportedPreviewSizes();
        if (rawSupportedSizes == null) {
            return defaultPreviewResolution;
        }

        // ?????????????????????????????????
        List<Camera.Size> supportedPreviewResolutions = new ArrayList<Camera.Size>(rawSupportedSizes);
        Collections.sort(supportedPreviewResolutions, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size a, Camera.Size b) {
                int aPixels = a.height * a.width;
                int bPixels = b.height * b.width;
                if (bPixels < aPixels) {
                    return -1;
                }
                if (bPixels > aPixels) {
                    return 1;
                }
                return 0;
            }
        });

        StringBuilder previewResolutionSb = new StringBuilder();
        for (Camera.Size supportedPreviewResolution : supportedPreviewResolutions) {
            previewResolutionSb.append(supportedPreviewResolution.width).append('x').append(supportedPreviewResolution.height)
                    .append(' ');
        }
        Log.v(TAG, "Supported preview resolutions: " + previewResolutionSb);


        // ?????????????????????????????????
        double screenAspectRatio = (double) getResources().getDisplayMetrics().widthPixels
                / (double) getResources().getDisplayMetrics().heightPixels;
        Iterator<Camera.Size> it = supportedPreviewResolutions.iterator();
        while (it.hasNext()) {
            Camera.Size supportedPreviewResolution = it.next();
            int width = supportedPreviewResolution.width;
            int height = supportedPreviewResolution.height;

            // ?????????????????????????????????????????????????????????
            if (width * height < MIN_PREVIEW_PIXELS) {
                it.remove();
                continue;
            }

            // ???camera????????????????????????????????????????????????????????????????????????????????????????????????
            // ??????camera???????????????width>height??????????????????portrait????????????width<height
            // ???????????????????????????preview?????????????????????
            boolean isCandidatePortrait = width > height;
            int maybeFlippedWidth = isCandidatePortrait ? height : width;
            int maybeFlippedHeight = isCandidatePortrait ? width : height;
            double aspectRatio = (double) maybeFlippedWidth / (double) maybeFlippedHeight;
            double distortion = Math.abs(aspectRatio - screenAspectRatio);
            if (distortion > MAX_ASPECT_DISTORTION) {
                it.remove();
                continue;
            }

            // ????????????????????????????????????????????????????????????????????????
            if (maybeFlippedWidth == getResources().getDisplayMetrics().widthPixels
                    && maybeFlippedHeight == getResources().getDisplayMetrics().heightPixels) {
                return supportedPreviewResolution;
            }
        }

        // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        if (!supportedPreviewResolutions.isEmpty()) {
            Camera.Size largestPreview = supportedPreviewResolutions.get(0);
            return largestPreview;
        }

        // ??????????????????????????????????????????

        return defaultPreviewResolution;
    }

    private Camera.Size findBestPictureResolution() {
        Camera.Parameters cameraParameters = cameraInst.getParameters();
        List<Camera.Size> supportedPicResolutions = cameraParameters.getSupportedPictureSizes(); // ????????????????????????

        StringBuilder picResolutionSb = new StringBuilder();
        for (Camera.Size supportedPicResolution : supportedPicResolutions) {
            picResolutionSb.append(supportedPicResolution.width).append('x')
                    .append(supportedPicResolution.height).append(" ");
        }
        Log.d(TAG, "Supported picture resolutions: " + picResolutionSb);

        Camera.Size defaultPictureResolution = cameraParameters.getPictureSize();
        Log.d(TAG, "default picture resolution " + defaultPictureResolution.width + "x"
                + defaultPictureResolution.height);

        // ??????
        List<Camera.Size> sortedSupportedPicResolutions = new ArrayList<Camera.Size>(
                supportedPicResolutions);
        Collections.sort(sortedSupportedPicResolutions, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size a, Camera.Size b) {
                int aPixels = a.height * a.width;
                int bPixels = b.height * b.width;
                if (bPixels < aPixels) {
                    return -1;
                }
                if (bPixels > aPixels) {
                    return 1;
                }
                return 0;
            }
        });

        // ?????????????????????????????????
        double screenAspectRatio = (double) getResources().getDisplayMetrics().widthPixels
                / (double) getResources().getDisplayMetrics().heightPixels;
        Iterator<Camera.Size> it = sortedSupportedPicResolutions.iterator();
        while (it.hasNext()) {
            Camera.Size supportedPreviewResolution = it.next();
            int width = supportedPreviewResolution.width;
            int height = supportedPreviewResolution.height;

            // ???camera????????????????????????????????????????????????????????????????????????????????????????????????
            // ??????camera???????????????width>height??????????????????portrait????????????width<height
            // ????????????????????????????????????????????????
            boolean isCandidatePortrait = width > height;
            int maybeFlippedWidth = isCandidatePortrait ? height : width;
            int maybeFlippedHeight = isCandidatePortrait ? width : height;
            double aspectRatio = (double) maybeFlippedWidth / (double) maybeFlippedHeight;
            double distortion = Math.abs(aspectRatio - screenAspectRatio);
            if (distortion > MAX_ASPECT_DISTORTION) {
                it.remove();
                continue;
            }
        }

        // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        if (!sortedSupportedPicResolutions.isEmpty()) {
            return sortedSupportedPicResolutions.get(0);
        }

        // ??????????????????????????????????????????
        return defaultPictureResolution;
    }

    //????????????
    private void pointFocus(int x, int y) {
        cameraInst.cancelAutoFocus();
        parameters = cameraInst.getParameters();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            showPoint(x, y);
        }
        cameraInst.setParameters(parameters);
        autoFocus();
    }

    private void showPoint(int x, int y) {
        if (parameters.getMaxNumMeteringAreas() > 0) {
            List<Camera.Area> areas = new ArrayList<Camera.Area>();
            //xy?????????
            int rectY = -x * 2000 / getResources().getDisplayMetrics().widthPixels + 1000;
            int rectX = y * 2000 / getResources().getDisplayMetrics().heightPixels - 1000;

            int left = rectX < -900 ? -1000 : rectX - 100;
            int top = rectY < -900 ? -1000 : rectY - 100;
            int right = rectX > 900 ? 1000 : rectX + 100;
            int bottom = rectY > 900 ? 1000 : rectY + 100;
            Rect area1 = new Rect(left, top, right, bottom);
            areas.add(new Camera.Area(area1, 800));
            parameters.setMeteringAreas(areas);
        }

        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2001 && resultCode == RESULT_OK){
            if(data.getStringExtra("type").equals("retake")){
                btnTakePic.setVisibility(View.VISIBLE);
                btnClose.setVisibility(View.VISIBLE);
            }else {
                Intent i = new Intent();
                i.putExtra("tempImage",data.getStringExtra("photo"));
                context.setResult(Activity.RESULT_OK, i);
                context.finish();
            }

            context.finish();;

        }
    }
}
