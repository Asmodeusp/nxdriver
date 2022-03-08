package com.saimawzc.freight.ui.my.identification;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.bigkoo.pickerview.TimePickerView;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseFragment;

import com.saimawzc.freight.base.TimeChooseListener;
import com.saimawzc.freight.dto.my.identification.CarrierIndenditicationDto;
import com.saimawzc.freight.dto.pic.PicDto;
import com.saimawzc.freight.presenter.mine.identification.NomalCarrivePresenter;
import com.saimawzc.freight.presenter.mine.identification.SamllCarrierPresenter;
import com.saimawzc.freight.ui.MainActivity;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.view.mine.identificaion.NomalTaxesCarriverView;
import com.saimawzc.freight.weight.utils.AreaChooseDialog;
import com.saimawzc.freight.weight.utils.GalleryUtils;
import com.saimawzc.freight.weight.utils.TimeChooseDialogUtil;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.AreaListener;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;
import com.saimawzc.freight.weight.utils.ocr.RecognizeService;
import com.saimawzc.freight.weight.utils.FileUtil;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
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
import static com.saimawzc.freight.base.BaseActivity.PERMISSIONS;
import static com.saimawzc.freight.base.BaseActivity.PERMISSIONS_CAMERA;
import static com.saimawzc.freight.ui.my.identification.DriverCarrierFragment.REQUEST_CODE_PIC;

/**
 * Created by Administrator on 2020/8/3.
 * 一般纳税人
 */

public class NomalTaxesCarriverFragment extends BaseFragment
        implements NomalTaxesCarriverView{
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.imgviewpositive)ImageView imgviewPositive;
    @BindView(R.id.imgviewotherside)ImageView imgViewOtherSide;
    @BindView(R.id.imgviewbaz)ImageView imageViewnomalTaxes;//一般纳税人备案证
    @BindView(R.id.imgviewyyzz)ImageView imageViewBusinesslicense;//营业执照
    @BindView(R.id.imagetransport)ImageView imageViewimageTransport;//道路运输许可证
    @BindView(R.id.imageviewentrust)ImageView imageViewEntrust;//委托书
    @BindView(R.id.legalpersonIdcard)ImageView imageViewlegalpersonIdcard;//法人身份证
    @BindView(R.id.tvuser)EditText tvUser;
    @BindView(R.id.tvidcard)EditText tvIdcard;
    @BindView(R.id.tvArea)TextView tvArea;
    @BindView(R.id.tvcompany)EditText tvCompanyName;
    @BindView(R.id.tvfaname)EditText tvIegalName;
    @BindView(R.id.tvfrcard)EditText tvIegalCardNum;
    @BindView(R.id.tvyezz)EditText tvBusissNum;//营业执照
    @BindView(R.id.tvysz)EditText edRoadNum;//道路编号
    @BindView(R.id.groupnomal)RadioButton noamlButton;
    @BindView(R.id.groupforver)RadioButton foreverButton;
    @BindView(R.id.right_btn)TextView btnRight;
    @BindView(R.id.tvyezzTime)TextView tvBussissTime;//营业执照有效期
    @BindView(R.id.radio)RadioGroup group;

    private String sringPositive;
    private String sringOtherSide;
    private String sringnomalTaxe;
    private String sringBussiselices;
    private String sringTransport;
    private String sringEntrust;
    private String sringFrIdcard;//法人身份证
    private NomalCarrivePresenter presenter;
    private int chooseTpye;//照片
    private int bussisstype=0;//默认普通
    private TimeChooseDialogUtil timeChooseDialogUtil;
    AreaChooseDialog areaChooseDialog;
    private String proName;
    private String proId;
    private String citysName;
    private String citysId;
    private String countrysName;
    private String countrysId;
    private String type;
    @BindView(R.id.edmaxAmcount)EditText edmaxAmcount;

    @Override
    public int initContentView() {
        return R.layout.fragment_nomaltaxi;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"一般纳税人认证");
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText("提交审核");
        personCenterDto= Hawk.get(PreferenceKey.PERSON_CENTER);
        presenter=new NomalCarrivePresenter(this,mContext);
        initpermissionChecker();
        try {
            type=getArguments().getString("type");
        }catch (Exception e){

        }
        if(personCenterDto!=null){
            if(personCenterDto.getAuthState()!=0){//已经认证
                presenter.getIdentificationInfo();
            }
        }
        if(areaChooseDialog==null){
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    areaChooseDialog =new AreaChooseDialog(mContext);
                    areaChooseDialog.initData();
                }
            });
        }
    }
    private NormalDialog dialog;
    @Override
    public void initData() {
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                if(btnRight.getText().toString().equals("提交审核")){
                    if(TextUtils.isEmpty(sringPositive)){
                        context.showMessage("身份证正面不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(sringOtherSide)){
                        context.showMessage("身份证反面不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(sringnomalTaxe)){
                        context.showMessage("一般纳税人不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(sringBussiselices)){
                        context.showMessage("营业执照不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(sringTransport)){
                        context.showMessage("道路运输许可证不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(sringEntrust)){
                        context.showMessage("授权委托书不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(sringFrIdcard)){
                        context.showMessage("法人身份证不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(tvUser.getText().toString())){
                        context.showMessage("用户不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(tvArea.getText().toString())){
                        context.showMessage("地区不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(tvCompanyName.getText().toString())){
                        context.showMessage("公司名称不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(tvIegalName.getText().toString())){
                        context.showMessage("法人姓名不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(tvIegalCardNum.getText().toString())){
                        context.showMessage("法人身份证不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(tvBusissNum.getText().toString())){
                        context.showMessage("营业执照不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(tvBusissNum.getText().toString())){
                        context.showMessage("营业执照不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(edRoadNum.getText().toString())){
                        context.showMessage("道路运输证编号不能为空");
                        return;
                    }
                }

                if(btnRight.getText().toString().equals("提交审核")){
                    if(TextUtils.isEmpty(type)){
                        presenter.indenfication();
                    }else {
                        presenter.recarriveRz();
                    }
                }else if(btnRight.getText().toString().equals("重新认证")){

                    dialog = new NormalDialog(mContext).isTitleShow(false)
                            .content("重新认证需要再次审核，是否继续?")
                            .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                            .btnNum(2).btnText("取消", "确定");
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
                                    Bundle bundle=new Bundle();
                                    bundle.putString("from","useridentification");
                                    bundle.putString("type","reidentification");
                                    readyGo(PersonCenterActivity.class,bundle);
                                    if(!context.isDestroy(context)){
                                        dialog.dismiss();
                                    }
                                }
                            });
                    if(dialog!=null){
                        dialog.show();
                    }

                }


            }
        });
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.groupnomal:
                        tvBussissTime.setVisibility(View.VISIBLE);
                        bussisstype=0;
                        break;
                    case R.id.groupforver:
                        bussisstype=1;
                        tvBussissTime.setVisibility(View.GONE);
                        break;

                }
            }
        });

    }
    @OnClick({R.id.imgviewpositive,R.id.imgviewotherside,R.id.imgviewbaz,
            R.id.imgviewyyzz,R.id.imagetransport,
    R.id.imageviewentrust,R.id.legalpersonIdcard,R.id.tvyezzTime,R.id.tvArea})
    public void click(View view){
        switch (view.getId()){
            case R.id.imgviewpositive://身份证正面
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("未获取到相机权限,请在设置中开启权限");
                    permissionChecker.requestPermissions();
                    return;
                }
                initCamera(CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                break;
            case R.id.imgviewotherside://身份证反面
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("未获取到相机权限,请在设置中开启权限");
                    permissionChecker.requestPermissions();
                    return;
                }
                initCamera(CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                break;
            case R.id.imgviewbaz://一般纳税人备案证
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("未获取到相机权限,请在设置中开启权限");
                    permissionChecker.requestPermissions();
                    return;
                }
                presenter.showCamera(mContext,2);
                break;
            case R.id.imgviewyyzz://营业执照
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("未获取到相机权限,请在设置中开启权限");
                    permissionChecker.requestPermissions();
                    return;
                }
                initCamera( CameraActivity.CONTENT_TYPE_GENERAL);
                break;
            case R.id.imagetransport://道路运输许可证
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("未获取到相机权限,请在设置中开启权限");
                    permissionChecker.requestPermissions();
                    return;
                }
                presenter.showCamera(mContext,4);
                break;
            case R.id.imageviewentrust://授权委托书
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("未获取到相机权限,请在设置中开启权限");
                    permissionChecker.requestPermissions();
                    return;
                }
                presenter.showCamera(mContext,5);
                break;
            case R.id.legalpersonIdcard://法人身份证
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("未获取到相机权限,请在设置中开启权限");
                    permissionChecker.requestPermissions();
                    return;
                }
                chooseTpye=6;
                initCamera(CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                break;
            case R.id.tvArea://
                areaChooseDialog.show(new AreaListener() {
                    @Override
                    public void getArea(String area, String proviceName, String cityName, String countryName, String proivceid, String cityId, String countryId) {
                        tvArea.setText(area);
                        proName=proviceName;
                        proId=proivceid;
                        citysName=cityName;
                        citysId=cityId;
                        countrysName=countryName;
                        countrysId=countryId;
                    }
                });
                break;
            case R.id. tvyezzTime:
                if(timeChooseDialogUtil==null){
                    timeChooseDialogUtil=new TimeChooseDialogUtil(mContext);
                }
                timeChooseDialogUtil.showDialog(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        tvBussissTime.setText(result);
                    }
                    @Override
                    public void cancel() {
                        timeChooseDialogUtil.dissDialog();
                    }
                });
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE_PIC && resultCode == RESULT_OK){
            String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
            String filePath = FileUtil.getSaveFile(mContext,tempImage).getAbsolutePath();
            if(!TextUtils.isEmpty(filePath)&&new File(filePath)!=null){
                if(CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)){
                    if(chooseTpye==6){//法人身份证
                        ImageLoadUtil.displayLocalImage(mContext, new File(filePath), imageViewlegalpersonIdcard);
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath,1);
                    }else {
                        chooseTpye=0;
                        ImageLoadUtil.displayLocalImage(mContext, new File(filePath), imgviewPositive);
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath,0);
                    }

                }else if(CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)){
                    chooseTpye=1;
                    ImageLoadUtil.displayLocalImage(mContext, new File(filePath), imgViewOtherSide);
                }else if(CameraActivity.CONTENT_TYPE_GENERAL.equals(contentType)){
                    chooseTpye=3;
                    ImageLoadUtil.displayLocalImage(mContext, new File(filePath), imageViewBusinesslicense);
                    readBussiness(filePath);
                }

                Uploadpic(BaseActivity.compress(mContext,new File(filePath)));
            }
        }

        if(requestCode==REQUEST_CODE_CAMERA && resultCode == RESULT_OK){//拍照
            if(!context.isEmptyStr(tempImage)){
                Uri uri = Uri.fromFile(new File(tempImage));
                final File file=BaseActivity.compress(mContext,new File(tempImage));
                if(chooseTpye==0){//身份证正面
                    ImageLoadUtil.displayImage(mContext, uri, imgviewPositive);
                    try{
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, file.getPath(),0);
                            }
                        }).start();
                    }catch (Exception e){
                    }
                }
                if(chooseTpye==1){//身份证反面
                    ImageLoadUtil.displayImage(mContext, uri, imgViewOtherSide);
                }
                if(chooseTpye==2){//一般纳税人备案证
                    ImageLoadUtil.displayImage(mContext, uri, imageViewnomalTaxes);

                }
                if(chooseTpye==3){//营业执照
                    ImageLoadUtil.displayImage(mContext, uri, imageViewBusinesslicense);
                    readBussiness(file.getPath());
                }
                if(chooseTpye==4){//道路运输许可证
                    ImageLoadUtil.displayImage(mContext, uri, imageViewimageTransport);
                }
                if(chooseTpye==5){//授权委托书
                    ImageLoadUtil.displayImage(mContext, uri, imageViewEntrust);
                }
                if(chooseTpye==6){//法人身份证
                    ImageLoadUtil.displayImage(mContext, uri, imageViewlegalpersonIdcard);
                    try{
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, file.getPath(),1);
                    }catch (Exception e){
                    }
                }
                Uploadpic(file);

            }
        }
    }

    private  void Uploadpic(File file){
        context.showLoadingDialog("图片上传中....");
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), file);
        //files 上传参数
        MultipartBody.Part part=
                MultipartBody.Part.createFormData("picture", file.getName(), requestBody);

        context.authApi.loadImg(part).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {
                context.dismissLoadingDialog();
                if(chooseTpye==0||chooseTpye==7){
                    sringPositive=response.getUrl();
                    context.showMessage("身份证正面上传成功");
                }else if(chooseTpye==1||chooseTpye==8){
                    sringOtherSide=response.getUrl();
                    context.showMessage("身份证反面上传成功");
                }else if(chooseTpye==2||chooseTpye==9){
                    sringnomalTaxe=response.getUrl();
                    context.showMessage("一般纳税人上传成功");
                }else if(chooseTpye==3||chooseTpye==10){
                    sringBussiselices=response.getUrl();
                    context.showMessage("营业执照上传成功");
                }else if(chooseTpye==4||chooseTpye==11){
                    sringTransport=response.getUrl();
                    context.showMessage("道路运输许可证上传成功");
                }else if(chooseTpye==5||chooseTpye==12){
                    sringEntrust=response.getUrl();
                    context.showMessage("授权委托书上传成功");
                }else if(chooseTpye==6||chooseTpye==13){
                    sringFrIdcard=response.getUrl();
                    context.showMessage("法人身份证上传成功");
                }
            }
            @Override
            public void fail(String code, String message) {
                context.dismissLoadingDialog();
            }
        });
    }
    private void recIDCard(String idCardSide, String filePath, final int type ) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);
        OCR.getInstance(mContext).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    if(result.getIdNumber()==null){
                        context.showMessage("未识别到身份证");
                        return;
                    }
                    if(result.getName()==null){
                        context.showMessage("未识别到姓名");
                        return;
                    }
                    if(type==0){
                        tvIdcard.setText(result.getIdNumber().toString());
                        tvUser.setText(result.getName().toString());
                    }else if(type==1){
                        tvIegalName.setText(result.getName().toString());
                        tvIegalCardNum.setText(result.getIdNumber().toString());
                    }

                }
            }
            @Override
            public void onError(OCRError error) {
                context.showMessage( error.getMessage());
            }
        });
    }

     //识别营业执照
    private void readBussiness(String path){
        RecognizeService.recBusinessLicense(mContext, path,
                new RecognizeService.ServiceListener() {
                    @Override
                    public void onResult(String result) {
                        Log.e("msg",result);
                        try {
                            JSONObject object=new JSONObject(result);
                            //JSONObject path=object.getJSONObject("path");
                            JSONObject words_result=object.getJSONObject("words_result");
                            JSONObject xinoyngDai=words_result.getJSONObject("社会信用代码");
                            tvBusissNum.setText(xinoyngDai.getString("words"));
                            JSONObject companyobject=words_result.getJSONObject("单位名称");
                            tvCompanyName.setText(companyobject.getString("words"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
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
        context.showMessage("添加成功");
        readyGo(MainActivity.class);

    }
    FunctionConfig functionConfig;
    @Override
    public void OnDealCamera(int type) {//处理相册选择
        chooseTpye=type;

        if(type<=6){//身份证拍照
            showCameraAction();
        }else if(type>6&&type<100) {
            if(functionConfig==null){
                functionConfig  = GalleryUtils.getFbdtFunction(1);
            }

            GalleryFinal.openGalleryMuti(1001,
                    functionConfig, mOnHanlderResultCallback);
        }

    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback =
            new GalleryFinal.OnHanlderResultCallback() {
                @Override
                public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
                    if (resultList != null) {
                        Uri uri = Uri.fromFile(new File(resultList.get(0).getPhotoPath()));
                        File file=BaseActivity.compress(mContext,new File(resultList.get(0).getPhotoPath()));
                        if(chooseTpye==7){//身份证正面
                            ImageLoadUtil.displayImage(mContext, uri, imgviewPositive);
                            try{
                                recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, file.getPath(),0);
                            }catch (Exception e){
                            }
                        }
                        if(chooseTpye==8){//身份证反面
                            ImageLoadUtil.displayImage(mContext, uri, imgViewOtherSide);
                        }
                        if(chooseTpye==9){//一般纳税人备案证
                            ImageLoadUtil.displayImage(mContext, uri, imageViewnomalTaxes);
                        }
                        if(chooseTpye==10){//营业执照
                            ImageLoadUtil.displayImage(mContext, uri, imageViewBusinesslicense);
                            readBussiness(file.getPath());
                        }
                        if(chooseTpye==11){//道路运输许可证
                            ImageLoadUtil.displayImage(mContext, uri, imageViewimageTransport);
                        }
                        if(chooseTpye==12){//授权委托书
                            ImageLoadUtil.displayImage(mContext, uri, imageViewEntrust);
                        }
                        if(chooseTpye==13){//法人身份证
                            ImageLoadUtil.displayImage(mContext, uri, imageViewlegalpersonIdcard);
                            try{
                                recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, file.getPath(),1);
                            }catch (Exception e){
                            }
                        }
                        Uploadpic(file);
                    }
                }
                @Override
                public void onHanlderFailure(int requestCode, String errorMsg) {
                    context.showMessage(errorMsg);
                }
            };

    @Override
    public String sringImgPositive() {
        return sringPositive;
    }
    @Override
    public String sringImgOtherSide() {
        return sringOtherSide;
    }
    @Override
    public String sringnomalTaxe() {
        return sringnomalTaxe;
    }
    @Override
    public String sringBussiselices() {
        return sringBussiselices;
    }
    @Override
    public String sringTransport() {
        return sringTransport;
    }
    @Override
    public String sringEntrust() {
        return sringEntrust;
    }
    @Override
    public String sringFrIdcard() {
        return sringFrIdcard;
    }
    @Override
    public String getUser() {
        return tvUser.getText().toString();
    }
    @Override
    public String getIdNum() {
        return tvIdcard.getText().toString();
    }
    @Override
    public String getArea() {
        return tvArea.getText().toString();
    }
    @Override
    public String getCompanyName() {
        return tvCompanyName.getText().toString();
    }
    @Override
    public String getIegalName() {
        return tvIegalName.getText().toString();
    }
    @Override
    public String getIegalCardNum() {
        return tvIegalCardNum.getText().toString();
    }
    @Override
    public String getBusissNum() {
        return tvBusissNum.getText().toString();
    }
    @Override
    public String getzzType() {
        if(bussisstype==0){
            return tvBussissTime.getText().toString();
        }else {
            return "长期";
        }
    }
    @Override
    public String getRoadNum() {
        return edRoadNum.getText().toString();
    }
    @Override
    public String proviceName() {
        return proName;
    }
    @Override
    public String proviceId() {
        return proId;
    }
    @Override
    public String cityName() {
        return citysName;
    }
    @Override
    public String cityId() {
        return citysId;
    }
    @Override
    public String countryName() {
        return countrysName;
    }
    @Override
    public String countryId() {
        return countrysId;
    }

    @Override
    public String invoiceMaxAmount() {
        return edmaxAmcount.getText().toString();
    }


    @Override
    public void identificationInfo(CarrierIndenditicationDto dto) {
        if(dto!=null){
            if(dto.getCheckStatus()==3){//认证失败
                dialog = new NormalDialog(mContext).isTitleShow(true)
                        .title("认证失败")
                        .content(dto.getCheckOpinion())
                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                        .btnNum(1).btnText("确定");
                dialog.setOnBtnClickL(
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if(!context.isDestroy(context)){
                                    dialog.dismiss();
                                }
                            }
                        });
                if(dialog!=null){
                    dialog.show();
                }
            }
            sringPositive=dto.getFrontIdCard();
            ImageLoadUtil.displayImage(mContext, sringPositive, imgviewPositive);
            sringOtherSide=dto.getReverseIdCard();
            ImageLoadUtil.displayImage(mContext, sringOtherSide, imgViewOtherSide);
            sringnomalTaxe=dto.getRecordCard() ;//备案证
            ImageLoadUtil.displayImage(mContext, sringnomalTaxe, imageViewnomalTaxes);
            sringBussiselices=dto.getBusinessLicense();
            ImageLoadUtil.displayImage(mContext, sringBussiselices, imageViewBusinesslicense);
            sringTransport=dto.getRoadPermit();
            ImageLoadUtil.displayImage(mContext, sringTransport, imageViewimageTransport);
            sringEntrust=dto.getPowerAttorney();
            ImageLoadUtil.displayImage(mContext, sringEntrust, imageViewEntrust);
            sringFrIdcard=dto.getFrontCorporateIdCard();
            ImageLoadUtil.displayImage(mContext, sringFrIdcard, imageViewlegalpersonIdcard);
            tvUser.setText(dto.getUserName());
            tvIdcard.setText(dto.getIdCardNum());
            tvArea.setText(dto.getAddress());
            tvCompanyName.setText(dto.getCompanyName());
            tvIegalName.setText(dto.getLegalPerson());
            tvIegalCardNum.setText(dto.getPersonIdCard());
            tvBusissNum.setText(dto.getBusinessNum());
            edRoadNum.setText(dto.getRoadNum());
            edmaxAmcount.setText(dto.getInvoiceMaxAmount());
            if(dto.getBusinessStatus()==1){//普通
                tvBussissTime.setVisibility(View.VISIBLE);
                noamlButton.setChecked(true);
                foreverButton.setChecked(false);
                bussisstype=0;
                tvBussissTime.setText(dto.getBusinessTime());
            }else if(dto.getBusinessStatus()==2){//长期
                bussisstype=1;
                tvBussissTime.setVisibility(View.GONE);
                noamlButton.setChecked(false);
                foreverButton.setChecked(true);
            }
            proName=dto.getProvince();
            proId=dto.getProvinceId();
            citysName=dto.getCity();
            citysId=dto.getCityId();
            countrysName=dto.getArea();
            countrysId=dto.getAreaId();
            if(dto.getCheckStatus()==1){
                Log.e("msg","type="+getArguments().getString("type"));
                if(TextUtils.isEmpty(getArguments().getString("type"))){
                    imgviewPositive.setClickable(true);
                    imgViewOtherSide.setClickable(true);
                    imageViewnomalTaxes.setClickable(true);
                    imageViewBusinesslicense.setClickable(true);
                    imageViewEntrust.setClickable(true);
                    imageViewimageTransport.setClickable(true);
                    imageViewlegalpersonIdcard.setClickable(true);
                    tvUser.setEnabled(true);
                    tvIdcard.setEnabled(true);
                    tvArea.setClickable(true);
                    tvCompanyName.setEnabled(true);
                    tvIegalName.setEnabled(true);
                    btnRight.setText("重新认证");
                    tvIegalCardNum.setEnabled(true);
                    tvBusissNum.setEnabled(true);
                    edRoadNum.setEnabled(true);
                    tvBussissTime.setEnabled(true);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.dissCamera();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
