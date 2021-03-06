package com.saimawzc.freight.ui.sendcar.driver;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.OcrRequestParams;
import com.baidu.ocr.sdk.model.OcrResponseResult;
import com.baidu.ocr.sdk.utils.DeviceUtil;
import com.baidu.ocr.sdk.utils.HttpUtil;
import com.baidu.ocr.sdk.utils.ImageUtil;
import com.baidu.ocr.sdk.utils.OcrResultParser;
import com.baidu.ocr.sdk.utils.Parser;
import com.baidu.trace.api.fence.FenceListRequest;
import com.baidu.trace.model.CoordType;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.sendcar.ArriveOrderAdpater;
import com.saimawzc.freight.adapter.sendcar.GridViewAdapter;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseApplication;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.order.SignWeightDto;
import com.saimawzc.freight.dto.pic.PicDto;
import com.saimawzc.freight.dto.sendcar.ArriverOrderDto;
import com.saimawzc.freight.presenter.sendcar.ArriverOrderPresenter;
import com.saimawzc.freight.ui.DriverMainActivity;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.view.sendcar.ArriverOrderView;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.overtimer.OverallTimer;
import com.saimawzc.freight.weight.utils.GalleryUtils;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.ocr.OcrResultCustomParser;
import com.saimawzc.freight.weight.utils.ocr.RecognizeService;
import com.saimawzc.freight.weight.utils.preview.PlusImageActivity;
import com.saimawzc.freight.weight.utils.trace.TraceUtils;
import com.saimawzc.freight.weight.waterpic.ViewPhoto;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import static android.app.Activity.RESULT_OK;
import static com.saimawzc.freight.ui.sendcar.driver.DriverTransportFragment.WaterPic;

public class ArrivalConfirmationFragment extends BaseFragment
        implements ArriverOrderView {
    ArriverOrderPresenter presenter;
    private ArriveOrderAdpater adpater;
    private List<ArriverOrderDto.materialsDto>mDatas=new ArrayList<>();
    @BindView(R.id.toolbar) Toolbar toolbar;
    private String id;
    @BindView(R.id.rv) RecyclerView rvGoods;
    @BindView(R.id.tvSignWeght)TextView tvSignWeght;
    @BindView(R.id.gridView) GridView gridView;
    private GridViewAdapter mGridViewAddImgAdapter;
    private ArrayList<String> mPicList = new ArrayList<>();
    @BindView(R.id.tvOrder) TextView tvOrder;
    private String distance;//????????????????????????
    @BindView(R.id.right_btn)TextView tvRightBtn;
    private String isFenceClock;

    boolean isAblun=false;
    private String poundAlarm;
    private int errorNum=0;//??????????????????

    private String dblocation;

    @Override
    public int initContentView() {
        return R.layout.fragment_arrivaconfima;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        tvRightBtn.setVisibility(View.VISIBLE);
        tvRightBtn.setText("???????????????");
        if(getArguments().getString("code").equals("7")){
            context.setToolbar(toolbar,"????????????");
        }else  if(getArguments().getString("code").equals("8")){
            context.setToolbar(toolbar,"????????????");
        }
        id=getArguments().getString("id");
        if(!TextUtils.isEmpty(getArguments().getString("toadress",""))&&
                !TextUtils.isEmpty( getArguments().getString("location",""))){
           if(!getArguments().getString("location","").contains("E")
           &&!getArguments().getString("location","").contains("e")){
               String[] lang =getArguments().getString("toadress","").split(",");
               String[] current =getArguments().getString("location","").split(",");
               if(lang.length>1&&current.length>1){
                   LatLng toLang = new LatLng(Double.parseDouble(lang[1]), Double.parseDouble(lang[0]));
                   LatLng locationLang = new LatLng(Double.parseDouble(current[1]), Double.parseDouble(current[0]));
                   distance=DistanceUtil.getDistance(toLang,locationLang)+"";
                   dblocation=getArguments().getString("location","");
               }
           }else {
               initLocation();
           }
        }
        adress=getArguments().getString("adress");
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        adpater=new ArriveOrderAdpater(mDatas,mContext,getArguments().getString("tranttype"),getArguments().getString("code"));
        rvGoods.setLayoutManager(layoutManager);
        rvGoods.setAdapter(adpater);
        presenter=new ArriverOrderPresenter(this,mContext);
        presenter.getData(id);
        presenter.getSignWeight(id);
        try{
            isFenceClock=getArguments().getString("isfencece");
        }catch (Exception e){
        }
        try {
            isAblun=getArguments().getBoolean("isuserablum");
        }catch (Exception E){
            isAblun=false;
        }
        try {
            poundAlarm=getArguments().getString("poundAlarm");
        }catch (Exception e){
            poundAlarm="";
        }
    }
    private String isSHowPic;
    @Override
    public void initData() {
        mGridViewAddImgAdapter = new GridViewAdapter(mContext, mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount()-1) {
                    //?????????????????????????????????????????????????????????????????????????????????????????????????????????5??????????????????
                    if (mPicList.size() == 20) {
                        //????????????5?????????
                         viewPluImg(position);
                    } else {
                        if(!RepeatClickUtil.isFastClick()){
                            context.showMessage("????????????????????????????????????");
                            return;
                        }
                        //??????????????????
                        if( TextUtils.isEmpty(distance)){
                            context.showMessage("??????????????????????????????????????????");
                            if(!TextUtils.isEmpty(getArguments().getString("toadress",""))&&!TextUtils.isEmpty( getArguments().getString("location",""))){
                                String[] lang =getArguments().getString("toadress","").split(",");
                                String[] current =getArguments().getString("location","").split(",");
                               if(lang.length>1&&current.length>1){
                                   LatLng toLang = new LatLng(Double.parseDouble(lang[1]), Double.parseDouble(lang[0]));
                                   LatLng locationLang = new LatLng(Double.parseDouble(current[1]), Double.parseDouble(current[0]));
                                   distance=DistanceUtil.getDistance(toLang,locationLang)+"";
                               }
                            }
                            return;
                        }
                        context.showLoadingDialog("?????????...");
                        isSHowPic="true";
                        initLocation();

                    }
                } else {
                    viewPluImg(position);
                }
            }
        });
        tvRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("from","eaxmgoodlist");
                bundle.putString("id",getArguments().getString("waybillid"));
                readyGo(OrderMainActivity.class,bundle);
            }
        });
    }

    @Override
    public void getData(List<ArriverOrderDto.materialsDto> datas) {
        if(datas!=null){
            mDatas.addAll(datas);
            adpater.notifyDataSetChanged();
        }
    }

    @Override
    public void ondealCamera(int type) {
        if(type==0){
            showCameraAction();
        }else if(type==1){
            FunctionConfig functionConfig  = GalleryUtils.getFbdtFunction(1);
            GalleryFinal.openGalleryMuti(1001,
                    functionConfig, mOnHanlderResultCallback);
        }
    }
    @Override
    public void getSignWeiht(SignWeightDto dto) {
        if(dto!=null){
            tvSignWeght.setText(""+dto.getWeight()+"???");
        }else {
            tvSignWeght.setText(""+"0???");
        }
    }
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback =
            new GalleryFinal.OnHanlderResultCallback() {
                @Override
                public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
                    if (resultList != null&&resultList.size()>0) {

                        if(!TextUtils.isEmpty(poundAlarm)){
                            if(poundAlarm.equals("1")){
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        final File file=BaseActivity.compress(mContext,
                                                new File((resultList.get(0).getPhotoPath())));
                                        context.showLoadingDialog("???????????????...");
                                        readDriver(file.getPath());
                                    }
                                }).start();
                                return;
                            }
                        }
                        go(resultList.get(0).getPhotoPath(),"");

                    }
                }
                @Override
                public void onHanlderFailure(int requestCode, String errorMsg) {
                    context.showMessage(errorMsg);
                }
            };
    private int positioningMode=1;
    private String tuneLocation="";//?????????
    private String adress="";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CAMERA && resultCode == RESULT_OK){//??????
            if(!context.isEmptyStr(tempImage)) {
                if(!TextUtils.isEmpty(poundAlarm)){
                    if(poundAlarm.equals("1")){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final File file=BaseActivity.compress(mContext,new File(tempImage));
                                context.showLoadingDialog("???????????????...");
                                readDriver(file.getPath());
                            }
                        }).start();
                        return;
                    }
                }
                go(tempImage,"");
            }
        }
        if(requestCode==WaterPic && resultCode == RESULT_OK){
            String imgUrl=data.getStringExtra("photo");
            if(TextUtils.isEmpty(imgUrl)){
                return;
            }
            if(mPicList.size()==0){
                positioningMode=data.getIntExtra("positioningMode",1);
                if(positioningMode==2){//???????????????
                    tuneLocation=data.getStringExtra("location");
                    distance=data.getStringExtra("distance");
                    adress=data.getStringExtra("adress");
                }
            }
            String errorpic=data.getStringExtra("errorpic");
            if(!TextUtils.isEmpty(errorpic)){
                errorNum++;
            }
            mPicList.add(imgUrl);
            mGridViewAddImgAdapter.notifyDataSetChanged();
           // ocrBangdan("",imgUrl);
        }
        if(requestCode==11 && resultCode == RESULT_OK){
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra("imglist"); //???????????????????????????
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
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

        if(getArguments().getString("code").equals("7")
                ||getArguments().getString("code").equals("8")){
            Intent intent=new Intent();
            intent.putExtra("code",getArguments().getString("code"));
            context.setResult(RESULT_OK,intent);
            EventBus.getDefault().post(Constants.reshTrant);
            EventBus.getDefault().post(Constants.reshAuroAdto);
            context.finish();
        }
    }

    @Override
    public void oncomplete(String errorCode) {
        Bundle bundle;
        if(errorCode.equals("6001")){
            bundle =new Bundle();
            bundle.putString("from","bindlock");
            bundle.putString("dispatchCarId",id);
            readyGo(OrderMainActivity.class,bundle);
        }else if(errorCode.equals("6002")){
            bundle=new Bundle();
            bundle.putInt("currentcode",Integer.parseInt(getArguments().getString("code")));
            bundle.putSerializable("data",getArguments().getSerializable("data"));
            bundle.putString("dispatchCarId",id);
            bundle.putString("from","getnumpass");
            bundle.putString("location",getArguments().getString("location",""));
            readyGo(OrderMainActivity.class,bundle);
        }
    }
    private NormalDialog dialog;
    @Override
    public void isFence(int isFenceClock, String message) {
        if(isFenceClock==1){
            UploadpMore(mPicList);
        }else {
            dialog = new NormalDialog(mContext).isTitleShow(false)
                    .content("??????????????????????????????????????????")
                    .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                    .btnNum(2).btnText("??????", "??????");
            dialog.setOnBtnClickL(
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            if(!context.isDestroy(context)){
                                dialog.dismiss();
                            }
                        }
                    },
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            UploadpMore(mPicList);
                            if(!context.isDestroy(context)){
                                dialog.dismiss();
                            }
                        }
                    });
            dialog.show();
        }



    }

    //????????????
    private void viewPluImg(int position) {
        Intent intent = new Intent(mContext, PlusImageActivity.class);
        intent.putStringArrayListExtra("imglist", mPicList);
        intent.putExtra("currentpos", position);
        startActivityForResult(intent, 11);
    }
    List<ArriverOrderDto.materialsDto>tempList=new ArrayList<>();
    @OnClick({R.id.tvOrder})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvOrder:
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("????????????????????????????????????");
                    return;
                }
                if(mDatas==null){
                    context.showMessage("????????????????????????");
                    return;
                }
                if(tempList==null){
                    context.showMessage("??????????????????");
                    return;
                }

                if(mDatas.size()<=0){
                    context.showMessage("?????????????????????");
                    return;
                }
                int count=0;
                if(mDatas.size()>0){
                    tempList.clear();
                    for(int i=0;i<mDatas.size();i++){
                       // Log.e("msg",""+mDatas.get(i).getSignWeight());
                        if(getArguments().getString("code").equals("7")){
                            if(TextUtils.isEmpty(mDatas.get(i).getSignWeight()+"")){
                                count++;
                            }else {
                                if(mDatas.get(i).getSignWeight().equals("0.000")){
                                    count++;
                                }
                            }
                        }else {
                            if(TextUtils.isEmpty(mDatas.get(i).getWeighing()+"")){
                                count++;
                            }else {
                                if(mDatas.get(i).getWeighing()==0){
                                    count++;
                                }
                            }
                        }
                        tempList.add(mDatas.get(i));
                    }

                }
                if(count>=mDatas.size()){
                    context.showMessage("????????????????????????");
                    return;
                }
                if(tempList.size()<=0){
                    context.showMessage("?????????????????????");
                    return;
                }
                if(mPicList.size()<=0){
                    context.showMessage("?????????????????????");
                    return;
                }
                if(!TextUtils.isEmpty(isFenceClock)){
                    if(isFenceClock.equals("1")){//???????????????
                        if(positioningMode==2){
                            presenter.isFeeced(id,dblocation);
                        }else {
                            presenter.isFeeced(id,dblocation);
                        }
                    }else {

                        UploadpMore(mPicList);
                    }
                }else {
                    UploadpMore(mPicList);
                }
                break;
        }
    }
    TraceUtils utils;
    private  void UploadpMore(List<String> paths){
        if(context==null){
            return;
        }
        if(paths==null||paths.size()<=0){
            context.showMessage("????????????????????????");
            return;
        }
        if(utils==null){
            utils=new TraceUtils((BaseApplication) mContext.getApplicationContext(),context);
        }
        if(utils!=null){
            utils.stopService();
            OverallTimer.getInstance().cancelTimer();
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        context.showLoadingDialog("???????????????...");
        for (String path : paths) {//List<String> paths??????????????????????????????
            File tempFile=new File(path);
            if(tempFile!=null){
                File file = BaseActivity.compress(mContext,tempFile);
                RequestBody requestBody = RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"), file);
                builder.addFormDataPart("files",file.getName(),requestBody);//files ??????????????????
            }
        }
        List<MultipartBody.Part> parts = builder.build().parts();
         if(parts==null){
            return;
          }
        if(errorNum>0){
            if(!TextUtils.isEmpty(getArguments().getString("code"))){
                if(getArguments().getString("code").equals("7")){
                    presenter.isErrorPic(getArguments().getString("waybillid"),
                            "8");
                }else {
                    presenter.isErrorPic(getArguments().getString("waybillid"),
                            "7");
                }
            }
        }
        context.authApi.uploadMorepic(parts).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {
                context.dismissLoadingDialog();
                if(!TextUtils.isEmpty(response.getUrl())){
                    presenter.daka(id,getArguments().getString("code")+"",adress,
                           dblocation ,response.getUrl(),tempList,distance,positioningMode,tuneLocation);
                }
            }
            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
                context.dismissLoadingDialog();
            }
        });

    }

    private void ocrBangdan(String sign,String url){
        OcrRequestParams param = new OcrRequestParams();
        param.putParam("templateSign", "e54dc27f9c32407ad35ffc4b402bfbfb");//??????ID
        param.setImageFile(new File(url));
        initAccessToken(param);
    }


    private void ocrbangdan(String failPath){
        RecognizeService.recAccurateBasic(mContext.getApplicationContext(), failPath,
                new RecognizeService.ServiceListener() {
                    @Override
                    public void onResult(String result) {
                        Log.e("msg",result);
                        try {
                            JSONObject object=new JSONObject(result);
                            JSONArray array=new JSONArray(object.getString("words_result"));
                            for(int i=0;i<array.length();i++){
                                JSONObject ob=array.getJSONObject(i);
                                String str=ob.getString("words").trim();
                                if(!TextUtils.isEmpty(str)){
                                    if(str.contains("??????")){
                                        String res=machStr(str);
                                        if(!TextUtils.isEmpty(res)){
                                            if(mDatas.size()>0){
                                                mDatas.get(0).setSignWeight(res);
                                                adpater.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    private String machStr(String str){
        if(str.contains("??????")){
            int index=str.indexOf("??????");
            String temp=str.substring(index,str.length());
            String result="";
            if(!TextUtils.isEmpty(temp)){
                for(int i=0;i<temp.length();i++){
                    char item=temp.charAt(i);
                    if(Character.isDigit(item)||String.valueOf(item).equals(".")){
                        result+=String.valueOf(item);
                        continue;
                    }else {
                        if(String.valueOf(item).equals("???")||String.valueOf(item).equals("???")){
                            continue;
                        }else {
                            return result;
                        }
                    }
                }
                return result;
            }else {
                return result;
            }
        }else {
            return "";
        }
    }
    String url="https://aip.baidubce.com/rest/2.0/solution/v1/iocr/recognise?";
    private void http( OcrRequestParams params,String token){
        File imageFile = params.getImageFile();
        final File tempImage = new File(this.context.getCacheDir(), String.valueOf(System.currentTimeMillis()));
        ImageUtil.resize(imageFile.getAbsolutePath(), tempImage.getAbsolutePath(), 1280, 1280);
        params.setImageFile(tempImage);
        final Parser<OcrResponseResult> ocrResultParser = new OcrResultCustomParser();
        HttpUtil.getInstance().post(urlAppendCommonParams(url,token), params, ocrResultParser, new OnResultListener<OcrResponseResult >() {
                    public void onResult(OcrResponseResult result) {
                        tempImage.delete();
                        Log.e("msg",result.getJsonRes());
                    }
                    public void onError(OCRError error) {
                        tempImage.delete();
                        Log.e("msg",error.toString());
                    }
                });
    }
    private String urlAppendCommonParams(String url,String token) {
        StringBuilder sb = new StringBuilder(url);
        sb.append("access_token=").append(token);
        sb.append("&aipSdk=Android");
        sb.append("&aipSdkVersion=").append("1_4_4");
        sb.append("&aipDevid=").append(DeviceUtil.getDeviceId(this.context));
        return sb.toString();
    }
    public void initAccessToken(final OcrRequestParams params) {
        AccessToken token=  OCR.getInstance(mContext).getAccessToken();
        http(params,token.getAccessToken());
    }
    private void readDriver(final String filePath){
        if(TextUtils.isEmpty(filePath)){
            context.showMessage("????????????");
            return;
        }
        try {
            RecognizeService.recNumbers(mContext,filePath,
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            Log.e("msg","????????????"+result);
                            try {
                                JSONObject jsonObject=new JSONObject(result);
                                JSONArray array=new JSONArray(jsonObject.getString("words_result"));
                                if(array.length()<=0){
                                    context.showMessage("????????????????????????????????????");
                                    go(filePath,"1");
                                }else {
                                    go(filePath,"");
                                }
                            }catch (Exception e){
                                go(filePath,"");
                            }
                        }
                    });
        }catch (Exception e){
            go(filePath,"");
        }
    }

    private void  go(String filePath,String iserrorpic){
        Bundle bundle=new Bundle();
        bundle.putString("fromtype", "arriver");
        bundle.putString("adress", adress);
        bundle.putString("photoPath",filePath);
        bundle.putString("distance", distance);
        bundle.putString("toadress", getArguments().getString("toadress",""));
        if(mPicList.size()==0){
            bundle.putString("country",getArguments().getString("country"));
            bundle.putString("city",getArguments().getString("city"));
        }
        if(!TextUtils.isEmpty(iserrorpic)){
            bundle.putString("errorpic",iserrorpic);
        }else {
            bundle.putString("errorpic","");
        }
        bundle.putString("location",dblocation);
        bundle.putString("adresschange",adress);
        readyGoForResult(ViewPhoto.class, WaterPic, bundle);
        isSHowPic="";
    }

    /**
     * ?????????????????????
     */
    private LocationClient mClient;
    private MyLocationListener myLocationListener;
    private void initLocation() {
        // ?????????????????????
        mClient = new LocationClient(getActivity());
        myLocationListener = new MyLocationListener();
        // ??????????????????
        mClient.registerLocationListener(myLocationListener);
        LocationClientOption mOption = new LocationClientOption();
        mOption.setIsNeedAddress(true);
        //?????????????????????????????????????????????????????????????????????false
        //?????????????????????????????????????????????????????????????????????true
        mOption.setNeedNewVersionRgc(true);
        // ???????????????gcj02??????????????????????????????????????????????????????????????????????????????????????????bd09ll;
        mOption.setCoorType("bd09ll");
        // ???????????????false?????????????????????Gps??????
        mOption.setOpenGps(true);
        // ??????????????????
        mClient.setLocOption(mOption);
        mClient.start();
        if(Build.VERSION.SDK_INT >= 26){
            if(!mClient.isStarted()){
                mClient.restart();
            }
        }

    }
    /**
     * ??????????????????
     */
    class  MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            context.dismissLoadingDialog();
            if(bdLocation==null){
                context.showMessage("??????????????????????????????????????????????????????");
                return;
            }else {
                if( bdLocation.getLocType()==62){
                    context.showMessage("??????????????????????????????????????????????????????");
                    return;
                }
            }
            dblocation=bdLocation.getLongitude()+","+bdLocation.getLatitude();
            String[] lang =getArguments().getString("toadress","").split(",");
            if(lang.length>1){
                LatLng toLang = new LatLng(Double.parseDouble(lang[1]), Double.parseDouble(lang[0]));
                LatLng locationLang = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                distance=DistanceUtil.getDistance(toLang,locationLang)+"";
            }
            Log.e("msg","????????????");
            adress=bdLocation.getAddrStr();
            if(!TextUtils.isEmpty(isSHowPic)){
                if(isAblun==false){
                    showCameraAction();
                }else {
                    presenter.showCamera(mContext);
                }

            }
        }
    }
}
