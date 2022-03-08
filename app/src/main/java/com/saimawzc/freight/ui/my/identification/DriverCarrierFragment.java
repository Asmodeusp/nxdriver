package com.saimawzc.freight.ui.my.identification;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.saimawzc.freight.base.TimeChooseListener;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.my.identification.DriviceerIdentificationDto;
import com.saimawzc.freight.dto.pic.PicDto;
import com.saimawzc.freight.presenter.mine.identification.DriveCarrierPresenter;
import com.saimawzc.freight.view.mine.identificaion.DriveLicesenCarrierView;
import com.saimawzc.freight.weight.utils.AreaChooseDialog;
import com.saimawzc.freight.weight.utils.FileUtil;
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
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
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

/**
 * Created by Administrator on 2020/8/3.
 * 司机认证
 */
public class DriverCarrierFragment extends BaseFragment
        implements DriveLicesenCarrierView{
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.imgidpositive)ImageView viewPositive;
    @BindView(R.id.imgidotherside)ImageView viewOtherSide;
    @BindView(R.id.imagedrivelicense)ImageView viewDrivelicense;
    @BindView(R.id.tvuser)EditText tvUser;
    @BindView(R.id.tvidcard)EditText tvIdCard;
    @BindView(R.id.tvArea)TextView tvArea;//地域
    private String stringImagePositive;
    private String stringImageOtherSide;
    private String stringDriveLicense;
    private DriveCarrierPresenter presenter;
    private int chooseType;
    @BindView(R.id.right_btn)TextView btnRight;
    AreaChooseDialog areaChooseDialog;
    private String proName;
    private String proId;
    private String citysName;
    private String citysId;
    private String countrysName;
    private String countrysId;
    private NormalDialog dialog;
    private String type;
    private String carriverType=1+"";//1司机 2船员
    @BindView(R.id.tvcarrier)TextView tvCarriverJob;
    @BindView(R.id.tvtime)TextView tvTime;
    @Override
    public int initContentView() {
        return R.layout.fragment_drivercarrive;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        personCenterDto=Hawk.get(PreferenceKey.PERSON_CENTER);
        presenter=new DriveCarrierPresenter(this,mContext);
        try {
            type=getArguments().getString("type");
        }catch (Exception e){
        }
        try {
            carriverType=getArguments().getString("carrivetype");
        }catch (Exception e){
            carriverType=1+"";
        }
        if(!TextUtils.isEmpty(carriverType)){
            if(carriverType.equals("2")){
                context.setToolbar(toolbar,"船员认证");
                tvCarriverJob.setText("船员");
            }else{
                context.setToolbar(toolbar,"司机认证");
                tvCarriverJob.setText("司机");
            }
        }else {
            context.setToolbar(toolbar,"司机认证");
            tvCarriverJob.setText("司机");
            carriverType=1+"";
        }
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText("提交审核");
        initpermissionChecker();
        personCenterDto= Hawk.get(PreferenceKey.PERSON_CENTER);
        if(personCenterDto!=null){
            if(personCenterDto.getAuthState()!=0){//已经认证
                presenter.getDrivicecarriInfo();
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
                    if(TextUtils.isEmpty(sringImgPositive())){
                        context.showMessage("身份证正面图片不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(sringImgOtherSide())){
                        context.showMessage("身份证反面图片不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(sringDriverLincense())){
                        context.showMessage("行驶证图片不能为空");
                        return;
                    }
                    if(context.isEmptyStr(tvUser.getText().toString())){
                        context.showMessage("用户名不能为空");
                        return;
                    }
                    if(context.isEmptyStr(tvIdCard.getText().toString())){
                        context.showMessage("身份证号不能为空");
                        return;
                    }
                }

                if(btnRight.getText().toString().equals("提交审核")){
                    int isConsistent=2;
                    if(driverName.equals(sfzName)&&driverName.equals(tvUser.getText().toString())&&
                    sfzName.equals(tvUser.getText().toString())&&driverIdCard.equals(sfzCard)&&
                    driverIdCard.equals(tvIdCard.getText().toString())&&sfzCard.equals(tvIdCard.getText().toString())){
                        isConsistent=1;
                    }else {
                        if(!TextUtils.isEmpty(type)){//重新提交
                            if(driverName.equals(tvUser.getText().toString())
                            &&driverIdCard.equals(tvIdCard.getText().toString())){
                                isConsistent=1;
                            }
                        }

                    }
                    if(TextUtils.isEmpty(type)){
                        presenter.carriveRz(isConsistent);
                    }else {
                        presenter.recarriveRz(isConsistent);
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

                                    viewPositive.setClickable(true);
                                    btnRight.setText("提交审核");
                                    viewOtherSide.setClickable(true);
                                    viewDrivelicense.setClickable(true);
                                    tvArea.setClickable(true);
                                    tvUser.setEnabled(true);
                                    tvIdCard.setEnabled(true);
                                    tvTime.setClickable(true);
                                    type="reidentification";
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
    }
    public static final int REQUEST_CODE_PIC = 1003;
    private TimeChooseDialogUtil timeChooseDialogUtil;
    @OnClick({R.id.imgidpositive,R.id.imgidotherside,R.id.imagedrivelicense,R.id.tvArea,R.id.tvtime})
    public  void click(View view){
            switch (view.getId()){
                case R.id.tvtime:
                    if(timeChooseDialogUtil==null){
                        timeChooseDialogUtil=new TimeChooseDialogUtil(mContext);
                    }
                    timeChooseDialogUtil.showDialog(new TimeChooseListener() {
                        @Override
                        public void getTime(String result) {
                            tvTime.setText(result);
                        }
                        @Override
                        public void cancel() {
                            timeChooseDialogUtil.dissDialog();
                        }
                    });
                    break;
                case R.id.imgidpositive://选择身份证正面
                    if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                        context.showMessage("未获取到相机权限,请在设置中开启权限");
                        permissionChecker.requestPermissions();
                        return;
                    }
                    initCamera(CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                    break;
                case R.id.imgidotherside://选择身份证反面
                    if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                        context.showMessage("未获取到相机权限,请在设置中开启权限");
                        permissionChecker.requestPermissions();
                        return;
                    }
                    initCamera(CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                    break;
                case R.id.imagedrivelicense:
                    if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                        context.showMessage("未获取到相机权限,请在设置中开启权限");
                        permissionChecker.requestPermissions();
                        return;
                    }
                    presenter.showCamera(mContext,1);
                    break;
                case R.id.tvArea:
                    if(!RepeatClickUtil.isFastClick()){
                        context.showMessage("您操作太频繁，请稍后再试");
                        return;
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
            }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CAMERA && resultCode == RESULT_OK){//拍照
            if(!context.isEmptyStr(tempImage)) {
                Uri uri = Uri.fromFile(new File(tempImage));
                final File file=BaseActivity.compress(mContext,new File(tempImage));
                ImageLoadUtil.displayImage(mContext, uri, viewDrivelicense);
                if(file!=null&&file.length()>0){
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            readDriver(file.getPath());
                        }
                    });

                }
                Uploadpic(file);
            }
        }
        if(requestCode==REQUEST_CODE_PIC && resultCode == RESULT_OK){
            String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
            String filePath = FileUtil.getSaveFile(mContext,tempImage).getAbsolutePath();
            if(!TextUtils.isEmpty(filePath)&&new File(filePath)!=null){
               final File file=BaseActivity.compress(mContext,new File(filePath));
                if(CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)){
                    chooseType=0;
                    recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    ImageLoadUtil.displayLocalImage(mContext, new File(filePath), viewPositive);
                }else if(CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)){
                    chooseType=1;
                    ImageLoadUtil.displayLocalImage(mContext, new File(filePath), viewOtherSide);
                }else if(CameraActivity.CONTENT_TYPE_GENERAL.equals(contentType)){
                    chooseType=2;
                    if(file!=null&&file.length()>0){
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                readDriver(file.getPath());
                            }
                        });

                    }
                    ImageLoadUtil.displayLocalImage(mContext, new File(filePath), viewDrivelicense);
                }
                Uploadpic(file);
            }
        }
    }
    private  void Uploadpic(File file){
        context.showLoadingDialog("图片上传中...");
        File temp=BaseActivity.compress(mContext,file);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), temp);
        //files 上传参数
        MultipartBody.Part part=
                MultipartBody.Part.createFormData("picture", temp.getName(), requestBody);
        context.authApi.loadImg(part).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {
                  context.dismissLoadingDialog();
                if(chooseType==0){
                    stringImagePositive=response.getUrl();
                    context.showMessage("身份证正面上传成功");
                }else if(chooseType==1){
                    stringImageOtherSide=response.getUrl();
                    context.showMessage("身份证反面上传成功");
                } else if(chooseType==2){
                    stringDriveLicense=response.getUrl();
                    context.showMessage("驾驶证上传成功");
                }
            }
            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
                context.dismissLoadingDialog();
            }
        });
    }
    private String sfzName="";
    private String sfzCard="";
    private void recIDCard(String idCardSide, String filePath) {
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
                    Log.e("msg","识别"+result.getName().toString());
                    if(!TextUtils.isEmpty(result.getIdNumber().toString())){
                        tvIdCard.setText(result.getIdNumber().toString());
                        sfzCard=result.getIdNumber().toString();
                    }
                    if(!TextUtils.isEmpty(result.getName().toString())){
                        tvUser.setText(result.getName().toString());
                        sfzName=result.getName().toString();
                    }
                }
            }
            @Override
            public void onError(OCRError error) {
                context.showMessage( error.getMessage());
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
        context.finish();
    }
    FunctionConfig functionConfig;
    @Override
    public void OnDealCamera(int type) {
        if(type==1){//驾驶证
            chooseType=2;
            showCameraAction();
        }else if(type==4){
            chooseType=2;
            if(functionConfig==null){
                functionConfig = GalleryUtils.getFbdtFunction(1);
            }
            GalleryFinal.openGalleryMuti(1001,
                    functionConfig, mOnHanlderResultCallback);
        }
    }
    @Override
    public String sringImgPositive() {
        return stringImagePositive;
    }
    @Override
    public String sringImgOtherSide() {
        return stringImageOtherSide;
    }
    @Override
    public String sringDriverLincense() {
        return stringDriveLicense;
    }
    @Override
    public String getUser() {
        return tvUser.getText().toString();
    }
    @Override
    public String getArea() {
        return tvArea.getText().toString();
    }
    @Override
    public String getIdNum() {
        return tvIdCard.getText().toString();
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
    public String driverType() {

        if(TextUtils.isEmpty(carriverType)){
            return 1+"";
        }else {
            return carriverType;
        }
    }


    @Override
    public void getInditifacationInfo(DriviceerIdentificationDto dto) {
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
            stringImagePositive=dto.getFrontIdCard();
            stringImageOtherSide=dto.getReverseIdCard();
            stringDriveLicense=dto.getDriverImg();
            ImageLoadUtil.displayImage(mContext, stringImagePositive, viewPositive);
            ImageLoadUtil.displayImage(mContext, stringImageOtherSide, viewOtherSide);
            ImageLoadUtil.displayImage(mContext, stringDriveLicense, viewDrivelicense);
            tvTime.setText(dto.getDriverOneTime());
            tvUser.setText(dto.getUserName());
            tvIdCard.setText(dto.getIdCard());
            tvArea.setText(dto.getAddress());
            proName=dto.getProvince();
            proId=dto.getProvinceId();
            citysName=dto.getCity();
            citysId=dto.getCityId();
            countrysName=dto.getArea();
            countrysId=dto.getAreaId();
            driverName=dto.getDriverName();
            driverIdCard=dto.getDriverIdCard();
            if(dto.getCheckStatus()==1){
                if(TextUtils.isEmpty(getArguments().getString("type"))){
                    viewPositive.setClickable(false);
                    btnRight.setText("重新认证");
                    viewOtherSide.setClickable(false);
                    viewDrivelicense.setClickable(false);
                    tvArea.setClickable(false);
                    tvUser.setEnabled(false);
                    tvIdCard.setEnabled(false);
                    tvTime.setClickable(false);
                }
            }
        }
    }

    @Override
    public String driverName() {
        return driverName;
    }

    @Override
    public String driverIdCard() {
        return driverIdCard;
    }

    @Override
    public String driverOneTime() {
        return tvTime.getText().toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.dissCamera();
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback =
            new GalleryFinal.OnHanlderResultCallback() {
                @Override
                public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
                    if (resultList != null) {
                        Uri uri = Uri.fromFile(new File(resultList.get(0).getPhotoPath()));
                        ImageLoadUtil.displayImage(mContext, uri, viewDrivelicense);
                        File file=BaseActivity.compress(mContext,new File(resultList.get(0).getPhotoPath()));
                        Uploadpic(file);
                        readDriver(file.getAbsolutePath());
                    }
                }
                @Override
                public void onHanlderFailure(int requestCode, String errorMsg) {
                    context.showMessage(errorMsg);
                }
    };

    private String driverName="";
    private String driverIdCard="";
    private void readDriver(String filePath){
        RecognizeService.recDrivingLicense(mContext,filePath,
                new RecognizeService.ServiceListener() {
                    @Override
                    public void onResult(String result) {
                        Log.e("msg","驾驶证"+result);
                        try {
                            JSONObject jsonObject=new JSONObject(result);
                            JSONObject contectObj=new JSONObject(jsonObject.getString("words_result"));
                            JSONObject xm=new JSONObject(contectObj.getString("姓名"));
                            driverName=xm.getString("words");
                            JSONObject sfz=new JSONObject(contectObj.getString("证号"));
                            driverIdCard=sfz.getString("words");
                            JSONObject data=new JSONObject(contectObj.getString("初次领证日期"));
                            tvTime.setText(BaseActivity.trantTime(data.getString("words"),"yyyyMMdd","yyyy-MM-dd"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
