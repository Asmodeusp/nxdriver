package com.saimawzc.freight.ui.order.face;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.FaceStatusNewEnum;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.baidu.idl.face.platform.listener.IInitCallback;
import com.baidu.idl.face.platform.model.ImageInfo;
import com.baidu.idl.face.platform.ui.FaceLivenessActivity;
import com.saimawzc.freight.BuildConfig;
import com.saimawzc.freight.base.BaseApplication;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.weight.face.QualityConfig;
import com.saimawzc.freight.weight.face.QualityConfigManager;
import com.saimawzc.freight.weight.utils.dialog.TimeoutDialog;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.Http;
import com.saimawzc.freight.weight.utils.http.RequestHeaderInterceptor;
import com.saimawzc.freight.weight.utils.http.RequestLogInterceptor;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static com.saimawzc.freight.base.BaseApplication.isLivenessRandom;
import static com.saimawzc.freight.base.BaseApplication.isOpenSound;
import static com.saimawzc.freight.constants.Constants.baseSwUrl;


public class FaceLivenessExpActivity extends FaceLivenessActivity implements
        TimeoutDialog.OnTimeoutDialogClickListener {

    private TimeoutDialog mTimeoutDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.livenessList.clear();
        BaseApplication.livenessList.add(LivenessTypeEnum.Eye);
        BaseApplication.livenessList.add(LivenessTypeEnum.Mouth);
        BaseApplication.livenessList.add(LivenessTypeEnum.HeadRight);
        initLicense();
    }

    @Override
    public void onLivenessCompletion(FaceStatusNewEnum status, String message,
                                     HashMap<String, ImageInfo> base64ImageCropMap,
                                     HashMap<String, ImageInfo> base64ImageSrcMap, int currentLivenessCount) {
        super.onLivenessCompletion(status, message, base64ImageCropMap, base64ImageSrcMap, currentLivenessCount);
        if (status == FaceStatusNewEnum.OK && mIsCompletion) {
            // ??????????????????
            getBestImage(base64ImageCropMap, base64ImageSrcMap);
        } else if (status == FaceStatusNewEnum.DetectRemindCodeTimeout) {
            if (mViewBg != null) {
                mViewBg.setVisibility(View.VISIBLE);
            }
            showMessageDialog();
        }
    }

    /**
     * ??????????????????
     * @param imageCropMap ????????????
     * @param imageSrcMap  ????????????
     */
    private void getBestImage(HashMap<String, ImageInfo> imageCropMap, HashMap<String, ImageInfo> imageSrcMap) {
        String bmpStr = null;
        // ???????????????????????????????????????????????????????????????????????????????????????????????????
        if (imageCropMap != null && imageCropMap.size() > 0) {
            List<Map.Entry<String, ImageInfo>> list1 = new ArrayList<>(imageCropMap.entrySet());
            Collections.sort(list1, new Comparator<Map.Entry<String, ImageInfo>>() {

                @Override
                public int compare(Map.Entry<String, ImageInfo> o1,
                                   Map.Entry<String, ImageInfo> o2) {
                    String[] key1 = o1.getKey().split("_");
                    String score1 = key1[2];
                    String[] key2 = o2.getKey().split("_");
                    String score2 = key2[2];
                    // ????????????
                    return Float.valueOf(score2).compareTo(Float.valueOf(score1));
                }
            });

            // ???????????????????????????????????????base64
//            int secType = mFaceConfig.getSecType();
//            String base64;
//            if (secType == 0) {
//                base64 = list1.get(0).getValue().getBase64();
//            } else {
//                base64 = list1.get(0).getValue().getSecBase64();
//            }
        }

        // ???????????????????????????????????????????????????????????????????????????????????????????????????
        if (imageSrcMap != null && imageSrcMap.size() > 0) {
            List<Map.Entry<String, ImageInfo>> list2 = new ArrayList<>(imageSrcMap.entrySet());
            Collections.sort(list2, new Comparator<Map.Entry<String, ImageInfo>>() {

                @Override
                public int compare(Map.Entry<String, ImageInfo> o1,
                                   Map.Entry<String, ImageInfo> o2) {
                    String[] key1 = o1.getKey().split("_");
                    String score1 = key1[2];
                    String[] key2 = o2.getKey().split("_");
                    String score2 = key2[2];
                    // ????????????
                    return Float.valueOf(score2).compareTo(Float.valueOf(score1));
                }
            });
            bmpStr = list2.get(0).getValue().getBase64();

            // ???????????????????????????????????????base64
//            int secType = mFaceConfig.getSecType();
//            String base64;
//            if (secType == 0) {
//                base64 = mBmpStr;
//            } else {
//                base64 = list2.get(0).getValue().getBase64();
//            }
        }

        // ????????????
       // IntentUtils.getInstance().setBitmap(bmpStr);
        UserInfoDto userInfoDto= Hawk.get(PreferenceKey.USER_INFO);
        if(userInfoDto!=null){
            UploadFace(userInfoDto,bmpStr);
        }


    }

    private void showMessageDialog() {
        mTimeoutDialog = new TimeoutDialog(this);
        mTimeoutDialog.setDialogListener(this);
        mTimeoutDialog.setCanceledOnTouchOutside(false);
        mTimeoutDialog.setCancelable(false);
        mTimeoutDialog.show();
        onPause();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onRecollect() {
        if (mTimeoutDialog != null) {
            mTimeoutDialog.dismiss();
        }
        if (mViewBg != null) {
            mViewBg.setVisibility(View.GONE);
        }
        onResume();
    }

    @Override
    public void onReturn() {
        if (mTimeoutDialog != null) {
            mTimeoutDialog.dismiss();
        }
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // ??????
        FaceSDKManager.getInstance().release();
    }

    private void initLicense() {
        boolean success = setFaceConfig();
        if (!success) {
            Log.e("msg","??????????????? = json????????????????????????");
            return;
        }
        String licenseID;
        String licenseFileName;
        if (BuildConfig.DEBUG) {
            licenseID="TestSj-face-android";
            licenseFileName="idl-license.face-android";
        }else {
            licenseID="TestSj-face-android";
            licenseFileName="idl-license.face-android";
        }
        FaceSDKManager.getInstance().initialize(getApplicationContext(), licenseID,
                licenseFileName, new IInitCallback() {
                    @Override
                    public void initSuccess() {
                        Log.e("msg","?????????????????????");
                    }
                    @Override
                    public void initFailure(final int errCode, final String errMsg) {
                        Log.e("msg","??????????????? = " + errCode + " " + errMsg);
                    }
                });
    }
    /**
     * ??????????????????
     */
    private boolean setFaceConfig() {
        FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();
        // SDK??????????????????????????????????????????????????????????????????????????????????????????????????????
        // ???????????????0????????????1????????????2????????????3???????????????
        // ???????????????????????????
        int qualityLevel= Constants.QUALITY_HIGH;
        // ???????????????????????????????????????????????????????????????????????????????????????set?????????????????????
        QualityConfigManager manager = QualityConfigManager.getInstance();
        manager.readQualityFile(this, qualityLevel);
        QualityConfig qualityConfig = manager.getConfig();
        if (qualityConfig == null) {
            return false;
        }
        // ?????????????????????
        config.setBlurnessValue(qualityConfig.getBlur());
        // ?????????????????????????????????0-255???
        config.setBrightnessValue(qualityConfig.getMinIllum());
        // ?????????????????????????????????0-255???
        config.setBrightnessMaxValue(qualityConfig.getMaxIllum());
        // ????????????????????????
        config.setOcclusionLeftEyeValue(qualityConfig.getLeftEyeOcclusion());
        // ????????????????????????
        config.setOcclusionRightEyeValue(qualityConfig.getRightEyeOcclusion());
        // ????????????????????????
        config.setOcclusionNoseValue(qualityConfig.getNoseOcclusion());
        // ????????????????????????
        config.setOcclusionMouthValue(qualityConfig.getMouseOcclusion());
        // ???????????????????????????
        config.setOcclusionLeftContourValue(qualityConfig.getLeftContourOcclusion());
        // ???????????????????????????
        config.setOcclusionRightContourValue(qualityConfig.getRightContourOcclusion());
        // ????????????????????????
        config.setOcclusionChinValue(qualityConfig.getChinOcclusion());
        // ???????????????????????????
        config.setHeadPitchValue(qualityConfig.getPitch());
        config.setHeadYawValue(qualityConfig.getYaw());
        config.setHeadRollValue(qualityConfig.getRoll());
        // ????????????????????????????????????
        config.setMinFaceSize(FaceEnvironment.VALUE_MIN_FACE_SIZE);
        // ?????????????????????????????????
        config.setNotFaceValue(FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
        // ??????????????????
        config.setEyeClosedValue(FaceEnvironment.VALUE_CLOSE_EYES);
        // ????????????????????????
        config.setCacheImageNum(FaceEnvironment.VALUE_CACHE_IMAGE_NUM);
        config.setLivenessTypeList(BaseApplication.livenessList);
        // ??????????????????????????????
        config.setLivenessRandom(isLivenessRandom);
        // ?????????????????????
        config.setSound(isOpenSound);
        // ??????????????????
        config.setScale(FaceEnvironment.VALUE_SCALE);
        // ???????????????????????????????????????????????????????????????????????????4???3
        config.setCropHeight(FaceEnvironment.VALUE_CROP_HEIGHT);
        config.setCropWidth(FaceEnvironment.VALUE_CROP_WIDTH);
        // ??????????????????????????????
        config.setEnlargeRatio(FaceEnvironment.VALUE_CROP_ENLARGERATIO);
        // ???????????????0???Base64??????????????????image_sec???false???1???????????????????????????????????????image_sec???true
        config.setSecType(FaceEnvironment.VALUE_SEC_TYPE);
        // ??????????????????
        config.setTimeDetectModule(FaceEnvironment.TIME_DETECT_MODULE);
        // ?????????????????????
        config.setFaceFarRatio(FaceEnvironment.VALUE_FAR_RATIO);
        config.setFaceClosedRatio(FaceEnvironment.VALUE_CLOSED_RATIO);
        FaceSDKManager.getInstance().setFaceConfig(config);
        return true;
    }

    private void UploadFace(final UserInfoDto userInfoDto, final String BASE64){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               final String url=baseSwUrl+"baidu/personVerifyForWaybill";
               final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("base64",BASE64);
                    jsonObject.put("oms_waybill_id",getIntent().getStringExtra("id"));
                    if(userInfoDto!=null&&!TextUtils.isEmpty(userInfoDto.getRoleId())){
                        jsonObject.put("roleId",userInfoDto.getRoleId());
                        jsonObject.put("role",userInfoDto.getRole());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)//???????????????   //???get??????????????????
                        .build();
                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(new RequestLogInterceptor())
                        .addInterceptor(new RequestHeaderInterceptor())
                        .hostnameVerifier(new Http.TrustAllHostnameVerifier())//????????????
                        .retryOnConnectionFailure(true)//??????
                        .connectTimeout(2, TimeUnit.MINUTES)
                        .readTimeout(2, TimeUnit.MINUTES)
                        .writeTimeout(2, TimeUnit.MINUTES)
                        .build();

                client.newCall(request).enqueue(new Callback()
                {
                    @Override
                    public void onFailure(Call call, IOException e)
                    {
                        Log.d("TAG  post??????",url);
                        Toast.makeText(FaceLivenessExpActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException
                    {
                        if(response.isSuccessful())
                        {
                            final  String content=response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("msg",content);

                                    try {
                                        JSONObject obj=new JSONObject(content);
                                        if(obj.getBoolean("success")){
                                            JSONObject ob1=new JSONObject(obj.getString("data"));
                                            double score=ob1.getDouble("score");

                                            if(score>=70){
                                                Toast.makeText(FaceLivenessExpActivity.this,"????????????"+score,Toast.LENGTH_LONG).show();
                                            }else {
                                                Toast.makeText(FaceLivenessExpActivity.this,"??????????????????",Toast.LENGTH_LONG).show();
                                            }
                                            Intent intent=new Intent();
                                            intent.putExtra("score",score);
                                            setResult(RESULT_OK, intent);
                                            finish();
                                            //Toast.makeText(FaceLivenessExpActivity.this,content+score,Toast.LENGTH_LONG).show();

                                        }else {
                                            Toast.makeText(FaceLivenessExpActivity.this,"??????????????????!",Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent();
                                            intent.putExtra("score",0);
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(FaceLivenessExpActivity.this,"????????????????????????",Toast.LENGTH_LONG).show();
                            Log.d("PostFail ",url);
                        }
                    }
                });
            }
        });
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}
