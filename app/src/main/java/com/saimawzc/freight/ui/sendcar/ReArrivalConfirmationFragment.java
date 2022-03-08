package com.saimawzc.freight.ui.sendcar;

import android.content.Intent;
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
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.OcrRequestParams;
import com.baidu.ocr.sdk.model.OcrResponseResult;
import com.baidu.ocr.sdk.utils.DeviceUtil;
import com.baidu.ocr.sdk.utils.HttpUtil;
import com.baidu.ocr.sdk.utils.ImageUtil;
import com.baidu.ocr.sdk.utils.Parser;
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
import static com.saimawzc.freight.base.BaseActivity.PERMISSIONSS_LOCATION;
import static com.saimawzc.freight.base.BaseActivity.PERMISSIONS_CAMERA;
import static com.saimawzc.freight.ui.sendcar.driver.DriverTransportFragment.WaterPic;

/**
 * 重新到货确认
 * ***/
public class ReArrivalConfirmationFragment extends BaseFragment
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

    @Override
    public int initContentView() {
        return R.layout.fragment_arrivaconfima;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        initpermissionChecker();
        if(getArguments().getString("tag").equals("7")){
            context.setToolbar(toolbar,"到货确认");
        }else {
            context.setToolbar(toolbar,"出场过磅");
        }

        id=getArguments().getString("id");
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        adpater=new ArriveOrderAdpater(mDatas,mContext,1+"",getArguments().getString("tag"));
        rvGoods.setLayoutManager(layoutManager);
        rvGoods.setAdapter(adpater);
        presenter=new ArriverOrderPresenter(this,mContext);
        presenter.getData(id);
        presenter.getSignWeight(id);
    }
    @Override
    public void initData() {
        mGridViewAddImgAdapter = new GridViewAdapter(mContext, mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount()-1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过5张，才能点击
                    if (mPicList.size() == 20) {
                        //最多添加5张图片
                         viewPluImg(position);
                    } else {
                        if (!RepeatClickUtil.isFastClick()) {
                            context.showMessage("您操作太频繁，请稍后再试");
                            return;
                        }
                        presenter.showCamera(mContext);
                    }
                } else {
                    viewPluImg(position);
                }
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
            if(permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)){
                permissionChecker.requestPermissions();
                context.showMessage("未获取到相机权限");
                return;
            }
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
            tvSignWeght.setText(""+dto.getWeight()+"吨");
        }else {
            tvSignWeght.setText(""+"0吨");
        }
    }
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback =
            new GalleryFinal.OnHanlderResultCallback() {
                @Override
                public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
                    if (resultList != null&&resultList.size()>0) {
                        mPicList.add(resultList.get(0).getPhotoPath());
                        mGridViewAddImgAdapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onHanlderFailure(int requestCode, String errorMsg) {
                    context.showMessage(errorMsg);
                }
            };
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CAMERA && resultCode == RESULT_OK){//拍照
            if(!context.isEmptyStr(tempImage)) {
                mPicList.add(tempImage);
                mGridViewAddImgAdapter.notifyDataSetChanged();
            }
        }else if(requestCode==11 && resultCode == RESULT_OK){
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra("imglist"); //要删除的图片的集合
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
        EventBus.getDefault().post(Constants.reshReQS);
        context.finish();
    }
    @Override
    public void oncomplete(String errorCode) {

    }
    @Override
    public void isFence(int isFenceClock, String message) {
    }
    //查看大图
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
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                if(mDatas==null){
                    context.showMessage("订单列表不能为空");
                    return;
                }
                if(tempList==null){
                    context.showMessage("图片不能为空");
                    return;
                }

                if(mDatas.size()<=0){
                    context.showMessage("请输入确认数量");
                    return;
                }
                int count=0;
                if(mDatas.size()>0){
                    tempList.clear();
                    for(int i=0;i<mDatas.size();i++){
                        if(getArguments().getString("tag").equals("7")){
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
                    context.showMessage("必须输入确认数量");
                    return;
                }
                if(tempList.size()<=0){
                    context.showMessage("请输入确认数量");
                    return;
                }
                if(mPicList.size()<=0){
                    context.showMessage("请选择回单图片");
                    return;
                }

                    UploadpMore(mPicList);

                break;
        }
    }
    TraceUtils utils;
    private  void UploadpMore(List<String> paths){
        if(context==null){
            return;
        }
        if(paths==null||paths.size()<=0){
            context.showMessage("图片文件不能为空");
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
        context.showLoadingDialog("图片上传中...");
        for (String path : paths) {//List<String> paths多个文件本地路径地址
            File tempFile=new File(path);
            if(tempFile!=null){
                File file = BaseActivity.compress(mContext,tempFile);
                RequestBody requestBody = RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"), file);
                builder.addFormDataPart("files",file.getName(),requestBody);//files 文件上传参数
            }
        }
        List<MultipartBody.Part> parts = builder.build().parts();
         if(parts==null){
            return;
          }

        context.authApi.uploadMorepic(parts).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {
                context.dismissLoadingDialog();
                if(!TextUtils.isEmpty(response.getUrl())){
                    presenter.daka(id,response.getUrl(),tempList,getArguments().getString("tag"));
                }
            }
            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
                context.dismissLoadingDialog();
            }
        });

    }




}
