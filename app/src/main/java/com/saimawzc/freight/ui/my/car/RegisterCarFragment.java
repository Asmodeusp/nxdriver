package com.saimawzc.freight.ui.my.car;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.base.TimeChooseListener;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.my.car.CarIsRegsister;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.dto.my.car.TrafficDto;
import com.saimawzc.freight.dto.pic.PicDto;
import com.saimawzc.freight.presenter.mine.car.ResisterCarPresenter;
import com.saimawzc.freight.view.mine.car.ResisterCarView;
import com.saimawzc.freight.weight.LengthFilter;
import com.saimawzc.freight.weight.SwitchButton;
import com.saimawzc.freight.weight.utils.GalleryUtils;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import com.saimawzc.freight.weight.utils.TimeChooseDialogUtil;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;
import com.saimawzc.freight.weight.utils.ocr.RecognizeService;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.login.LoginException;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static com.saimawzc.freight.base.BaseActivity.PERMISSIONSS_LOCATION;


/**
 * Created by Administrator on 2020/8/4.
 * 注册车辆
 */

public class RegisterCarFragment extends BaseFragment implements ResisterCarView,
        TextWatcher {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imgRoadTransport)
    ImageView viewRoadTransport;//道路运输证
    @BindView(R.id.imgDrivinglicense)
    ImageView viewDrivinglicense;//行驶证
    @BindView(R.id.imagecarinfo)
    ImageView viewCarInfo;//车辆信息
    @BindView(R.id.togglequalifications)
    SwitchButton switchQuali;//资质
    @BindView(R.id.toggleadd)
    SwitchButton switchAdd;//允许被添加
    @BindView(R.id.tvplate)
    TextView tvPlate;
    @BindView(R.id.edcarnum)
    EditText editCarNum;
    @BindView(R.id.groupblue)
    RadioButton groupBlue;
    @BindView(R.id.groupyellow)
    RadioButton groupYellow;
    @BindView(R.id.carmodel)
    TextView tvCarModel;//车辆类型
    @BindView(R.id.carweight)
    EditText editcarWeight;
    @BindView(R.id.tvcarbrand)
    TextView tvCarBrand;
    @BindView(R.id.edusername)
    EditText editUserName;
    @BindView(R.id.edPassNum)
    EditText editPassNum;
    @BindView(R.id.right_btn)
    TextView btnRight;
    @BindView(R.id.group)
    RadioGroup groupBtn;
    @BindView(R.id.edCarLength)
    EditText edCaeLength;//车辆长度
    @BindView(R.id.edCarWith)
    EditText edCarWith;//车辆宽度
    @BindView(R.id.edCarHeight)
    EditText edCarHeight;//车辆高度
    @BindView(R.id.edinvitEnter)
    TextView edinvitEnter;
    /***是否危化品***/
    @BindView(R.id.toggdangerous)
    SwitchButton swIsDangerous;//允许被添加
    /***行驶证注册日期***/
    @BindView(R.id.edxszdata)
    TextView tvDrivingData;
    /***车板底高***/
    @BindView(R.id.edcarbottom)
    EditText edCarBottom;
    /***加强筋位置***/
    @BindView(R.id.rib1)
    EditText edRib1;
    @BindView(R.id.rib2)
    EditText edRib2;
    @BindView(R.id.rib3)
    EditText edRib3;
    @BindView(R.id.rib4)
    EditText edRib4;
    @BindView(R.id.rib5)
    EditText edRib5;
    private String stringTransport;
    private String stringDrivinglicense;
    private String stringCarInfo;
    private int colorType = 1;//默认黄色
    private ResisterCarPresenter presenter;
    private int chooseType;
    private String carTypeIds;
    private String carBrandIds;
    @BindView(R.id.llcar)
    LinearLayout llCar;
    boolean ismodify = false;
    private String xiugia = 0 + "";
    private GalleryFinal galleryFinal;


    private TimeChooseDialogUtil timeChooseDialogUtil;
    private BDLocation currenrtLocation;
    SearchCarDto searchCarDto;
    private String isNeedAche;

    @BindView(R.id.rlcarNo)
    RelativeLayout rlCarNo;
    @BindView(R.id.rlyuname)
    LinearLayout rlYhName;
    @BindView(R.id.llysz)
    LinearLayout llYsz;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    public final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    private String carNo;

    @Override
    public int initContentView() {
        return R.layout.fragment_register_car;
    }

    @Override
    public void initView() {
        mContext = getActivity();
        context.setToolbar(toolbar, "车辆注册");
        btnRight.setText("提交审核");
        presenter = new ResisterCarPresenter(this, mContext);
        initpermissionChecker();
        if (permissionChecker.isLackPermissions(PERMISSIONS)) {
            context.showMessage("未获取到相应权限,请在设置中开启相机或存储权限");
            permissionChecker.requestPermissions();
        }

        getLocation();
        edCaeLength.setFilters(new InputFilter[]{new LengthFilter(2)});
        edCarHeight.setFilters(new InputFilter[]{new LengthFilter(2)});
        edCarWith.setFilters(new InputFilter[]{new LengthFilter(2)});
        editcarWeight.setFilters(new InputFilter[]{new LengthFilter(3)});
        edCaeLength.addTextChangedListener(this);
        edCarHeight.addTextChangedListener(this);
        edCarWith.addTextChangedListener(this);
        editcarWeight.addTextChangedListener(this);
        try {
            ismodify = getArguments().getBoolean("modify");
        } catch (Exception e) {
        }
        if (getArguments().getString("type").equals("carinfo")) {//查看车辆详情
            presenter.getCarInfo(getArguments().getString("id"));
            if (ismodify) {
                xiugia = 1 + "";
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText("车辆修改");
                viewRoadTransport.setEnabled(false);//道路运输证
                viewDrivinglicense.setEnabled(false);//行驶证
                viewCarInfo.setEnabled(false);//车辆信息
                switchQuali.setEnabled(false);//资质
                switchAdd.setEnabled(false);//允许被添加
                tvPlate.setEnabled(false);
                editCarNum.setEnabled(false);
                groupBlue.setEnabled(false);
                groupYellow.setEnabled(false);
                tvCarModel.setEnabled(false);//车辆类型
                editcarWeight.setEnabled(false);
                tvCarBrand.setEnabled(false);
                editUserName.setEnabled(false);
                editPassNum.setEnabled(false);
               
                groupBtn.setEnabled(false);
                edCaeLength.setEnabled(false);//车辆长度
                edCarWith.setEnabled(false);//车辆宽度
                edCarHeight.setEnabled(false);//车辆高度
                edinvitEnter.setEnabled(false);
                swIsDangerous.setEnabled(false);//允许被添加
                tvDrivingData.setEnabled(false);
                edCarBottom.setEnabled(false);
                edRib1.setEnabled(false);
                edRib2.setEnabled(false);
                edRib3.setEnabled(false);
                edRib4.setEnabled(false);
                edRib5.setEnabled(false);
            } else {
                btnRight.setVisibility(View.GONE);
            }
        } else {
            btnRight.setVisibility(View.VISIBLE);
            isNeedAche = "needAche";
            if (searchCarDto == null) {
                searchCarDto = new SearchCarDto();
            }

            this.presenter.getCarInfo("");
        }
    }

    @OnClick({R.id.imgRoadTransport, R.id.imgDrivinglicense,
            R.id.imagecarinfo, R.id.carmodel, R.id.tvcarbrand, R.id.edxszdata})
    public void click(View view) {
        if (getArguments().getString("type").equals("carinfo")) {//车辆详情
            if (ismodify = false) {
                return;
            }
        }
        switch (view.getId()) {
            case R.id.edxszdata://行驶证注册日期
                if (timeChooseDialogUtil == null) {
                    timeChooseDialogUtil = new TimeChooseDialogUtil(mContext);
                }
                timeChooseDialogUtil.showDialog(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        tvDrivingData.setText(result);
                    }

                    @Override
                    public void cancel() {
                        timeChooseDialogUtil.dissDialog();
                    }
                });
                break;
            case R.id.imgRoadTransport://运输证
                presenter.showCamera(mContext, 0);
                break;
            case R.id.imgDrivinglicense://行驶证
                presenter.showCamera(mContext, 1);
                break;
            case R.id.imagecarinfo://车辆信息
                presenter.showCamera(mContext, 2);
                break;
            case R.id.carmodel://车辆类型
                presenter.getCarType();
                break;
            case R.id.tvcarbrand://车辆品牌
                if (TextUtils.isEmpty(tvCarModel.getText().toString())) {
                    context.showMessage("请选择车辆类型");
                    return;
                }
                presenter.getCarBrand(tvCarModel.getText().toString());
                break;
        }
    }

    private int isConsistent = 2;

    @Override
    public void initData() {
        groupBtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.groupblue:
                        colorType = 0;
                        break;
                    case R.id.groupyellow:
                        colorType = 1;
                        break;
                }
                if (!TextUtils.isEmpty(editCarNum.getText().toString())) {
                    if (colorType != 1) {
                        presenter.getTrafficDto(editCarNum.getText().toString(), 2);
                    } else {
                        presenter.getTrafficDto(editCarNum.getText().toString(), 1);
                    }
                }
            }
        });
        switchQuali.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    llCar.setVisibility(View.VISIBLE);
                } else {
                    llCar.setVisibility(View.GONE);
                }

            }
        });

        editPassNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) { //获得焦点
                } else {//失去焦点
                    editPassNum.setError(null);
                }
            }
        });
        editCarNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) { //获得焦点
                } else {//失去焦点
                    editCarNum.setError(null);
                    if (!TextUtils.isEmpty(editCarNum.getText().toString())) {
                        if (!TextUtils.isEmpty(carNo)) {
                            if (!carNo.equals(editCarNum.getText().toString())) {
                                if (colorType != 1) {
                                    presenter.getTrafficDto(editCarNum.getText().toString(), 2);
                                } else {
                                    presenter.getTrafficDto(editCarNum.getText().toString(), 1);
                                }
                            } else {
                                Log.e("msg", "未修改不需要获取");
                            }
                        } else {
                            if (colorType != 1) {
                                presenter.getTrafficDto(editCarNum.getText().toString(), 2);
                            } else {
                                presenter.getTrafficDto(editCarNum.getText().toString(), 1);
                            }
                        }
                    }
                    carNo = editCarNum.getText().toString();
                }
            }
        });
        editUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) { //获得焦点
                } else {//失去焦点
                    editUserName.setError(null);
                }
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RepeatClickUtil.isFastClick()) {
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                
                if (btnRight.getText().toString().equals("车辆修改")) {
                    viewRoadTransport.setEnabled(true);//道路运输证
                    viewDrivinglicense.setEnabled(true);//行驶证
                    viewCarInfo.setEnabled(true);//车辆信息
                    switchQuali.setEnabled(true);//资质
                    switchAdd.setEnabled(true);//允许被添加
                    tvPlate.setEnabled(true);
                    editCarNum.setEnabled(true);
                    groupBlue.setEnabled(true);
                    groupYellow.setEnabled(true);
                    tvCarModel.setEnabled(true);//车辆类型
                    editcarWeight.setEnabled(true);
                    tvCarBrand.setEnabled(true);
                    editUserName.setEnabled(true);
                    editPassNum.setEnabled(true);
                    btnRight.setEnabled(true);
                    groupBtn.setEnabled(true);
                    edCaeLength.setEnabled(true);//车辆长度
                    edCarWith.setEnabled(true);//车辆宽度
                    edCarHeight.setEnabled(true);//车辆高度
                    edinvitEnter.setEnabled(true);
                    swIsDangerous.setEnabled(true);//允许被添加
                    tvDrivingData.setEnabled(true);
                    edCarBottom.setEnabled(true);
                    edRib1.setEnabled(true);
                    edRib2.setEnabled(true);
                    edRib3.setEnabled(true);
                    edRib4.setEnabled(true);
                    edRib5.setEnabled(true);
                    dialog = new NormalDialog(mContext).isTitleShow(false)
                            .content("修改需要再次审核，是否继续?")
                            .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                            .btnNum(2).btnText("取消", "确定");
                    dialog.setOnBtnClickL(
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    if (!context.isDestroy(context)) {
                                        dialog.dismiss();
                                    }
                                }
                            },
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    if (!context.isDestroy(context)) {
                                        dialog.dismiss();
                                        btnRight.setText("提交审核");
                                    }
                                }
                            });
                    if (dialog != null) {
                        dialog.show();
                    }
                } else {
                    if (switchQuali.isChecked()) {
                        if (TextUtils.isEmpty(stringTransport)) {
                            context.showMessage("道路运输许可证不能为空");
                            return;
                        }
                        if (TextUtils.isEmpty(stringDrivinglicense)) {
                            context.showMessage("行驶证不能为空");
                            return;
                        }
                        if (currenrtLocation == null) {
                            getLocation();
                            context.showMessage("未获取到当前位置信息，请确认定位是否开启");
                            return;
                        }
                    }
                    if (TextUtils.isEmpty(stringCarInfo)) {
                        context.showMessage("车辆信息图片为空，请重新上传");
                        return;
                    }
                    if (context.isEmptyStr(editCarNum.getText().toString())) {
                        context.showMessage("车牌号不能为空");
                        return;
                    }
                    if (editCarNum.getText().toString().contains("(") || editCarNum.getText().toString().contains("（")) {
                        context.showMessage("车牌号格式有误");
                        return;
                    }
                    if (editCarNum.getText().toString().contains("黄") || editCarNum.getText().toString().contains("蓝")
                            || editCarNum.getText().toString().contains("色")) {
                        context.showMessage("车牌号格式有误");
                        return;
                    }
                    if (context.isContainTemp(editCarNum.getText().toString())) {
                        context.showMessage("车牌号不能包含空格");
                        return;
                    }

                    if (context.isEmptyStr(tvCarModel.getText().toString())) {
                        context.showMessage("车型不能为空");
                        return;
                    }
                    if (context.isEmptyStr(edCaeLength.getText().toString())) {
                        context.showMessage("车辆长度不能为空");
                        return;
                    }
                    if (context.isEmptyStr(edCarHeight.getText().toString())) {
                        context.showMessage("车辆高度不能为空");
                        return;
                    }
                    if (context.isEmptyStr(edCarWith.getText().toString())) {
                        context.showMessage("车牌宽度不能为空");
                        return;
                    }
                    if (context.isEmptyStr(editUserName.getText().toString())) {
                        context.showMessage("业主名称不能为空");
                        return;
                    }
                    if (context.isEmptyStr(editPassNum.getText().toString())) {
                        context.showMessage("运输证号不能为空");
                        return;
                    }
                    if (context.isEmptyStr(editcarWeight)) {
                        context.showMessage("车辆载重不能为空");
                        return;
                    }
                    try {
                        if (Double.parseDouble(editcarWeight.getText().toString()) == 0) {
                            context.showMessage("车辆载重不能为0");
                            return;
                        }

                    } catch (Exception E) {

                    }

                    if (switchQuali.isChecked()) {
                        if (!context.isEmptyStr(picYsz) && !context.isEmptyStr(picName) && !context.isEmptyStr(picCarNo)) {
                            if (editPassNum.getText().toString().contains(picYsz)
                                    && picCarNo.equals(editCarNum.getText().toString()) && picName.equals(editUserName.getText().toString())) {
                                isConsistent = 1;
                            }
                        } else {
                            isConsistent = 2;
                        }

                    } else {
                        isConsistent = 2;
                    }
                    isNeedAche = "";
                    if (ismodify == true || xiugia.equals("1")) {//修改
                        presenter.updateCarInfo(getArguments().getString("id"), isConsistent);
                    } else {
                        presenter.isResister(editCarNum.getText().toString());
                    }
                }
            }
        });
        edinvitEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
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
        EventBus.getDefault().post(Constants.reshCar);
        context.finish();
    }

    FunctionConfig functionConfig;

    @Override
    public void OnDealCamera(int type) {
        chooseType = type;
        if (type <= 2) {
            if (permissionChecker.isLackPermissions(PERMISSIONS)) {
                permissionChecker.requestPermissions();
                context.showMessage("未获取到存储或者相机权限");
                return;
            }
            showCameraAction();
        } else if (type < 100) {
            if (galleryFinal == null) {
                galleryFinal = new GalleryFinal();
            }
            functionConfig = GalleryUtils.getFbdtFunction(1);
            galleryFinal.openGalleryMuti(1001,
                    functionConfig, mOnHanlderResultCallback);
        }
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback =
            new GalleryFinal.OnHanlderResultCallback() {
                @Override
                public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
                    if (resultList != null) {
                        Uri uri = Uri.fromFile(new File(resultList.get(0).getPhotoPath()));
                        final File file = BaseActivity.compress(mContext, new File(resultList.get(0).getPhotoPath()));
                        if (chooseType == 3) {//身份证正面
                            ImageLoadUtil.displayImage(mContext, uri, viewRoadTransport);
                            if (file != null && file.length() > 0) {
                                context.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ocrYshuzheng(file.getPath());
                                    }
                                });

                            }
                        } else if (chooseType == 4) {//身份证反面
                            ImageLoadUtil.displayImage(mContext, uri, viewDrivinglicense);
                            if (file != null && file.length() > 0) {
                                context.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ocrXsz(file.getPath());
                                    }
                                });

                            }
                        } else if (chooseType == 5) {
                            ImageLoadUtil.displayImage(mContext, uri, viewCarInfo);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            Log.e("msg", "拍照返回" + tempImage);
            if (!context.isEmptyStr(tempImage)) {
                Uri uri = Uri.fromFile(new File(tempImage));
                final File file = BaseActivity.compress(mContext, new File(tempImage));
                if (chooseType == 0) {//
                    ImageLoadUtil.displayImage(mContext, uri, viewRoadTransport);
                    if (file != null && file.length() > 0) {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ocrYshuzheng(file.getPath());
                            }
                        });

                    }
                } else if (chooseType == 1) {
                    ImageLoadUtil.displayImage(mContext, uri, viewDrivinglicense);
                    if (file != null && file.length() > 0) {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ocrXsz(file.getPath());
                            }
                        });

                    }
                } else if (chooseType == 2) {
                    ImageLoadUtil.displayImage(mContext, uri, viewCarInfo);
                }
                Uploadpic(file);
            }
        }
    }

    private void Uploadpic(File file) {
        context.showLoadingDialog("图片上传中...");
        File temp = BaseActivity.compress(mContext, file);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), temp);
        //files 上传参数
        MultipartBody.Part part =
                MultipartBody.Part.createFormData("picture", temp.getName(), requestBody);
        context.authApi.loadImg(part).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {
                context.dismissLoadingDialog();
                if (chooseType == 0 || chooseType == 3) {
                    stringTransport = response.getUrl();
                    context.showMessage("道路运输证上传成功");
                } else if (chooseType == 1 || chooseType == 4) {
                    stringDrivinglicense = response.getUrl();
                    context.showMessage("行驶证上传成功");
                } else if (chooseType == 2 || chooseType == 5) {
                    stringCarInfo = response.getUrl();
                    context.showMessage("车辆信息上传成功");
                }
            }

            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
                context.dismissLoadingDialog();
            }
        });
    }

    @Override
    public String stringTransport() {
        return stringTransport;
    }

    @Override
    public String stringDrivinglicense() {
        return stringDrivinglicense;
    }

    @Override
    public String stringCarInfo() {
        return stringCarInfo;
    }

    @Override
    public String getIsAllowAdd() {
        if (switchAdd.isChecked()) {
            return 1 + "";//允许
        } else {
            return 0 + "";
        }
    }

    @Override
    public String isDangerous() {
        if (swIsDangerous.isChecked()) {
            return 1 + "";//允许
        } else {
            return 0 + "";
        }
    }

    @Override
    public String boardHeight() {
        return edCarBottom.getText().toString();
    }

    @Override
    public String licenseRegTime() {
        return tvDrivingData.getText().toString();
    }

    @Override
    public String rib1() {
        return edRib1.getText().toString();
    }

    @Override
    public String rib2() {
        return edRib2.getText().toString();
    }

    @Override
    public String rib3() {
        return edRib3.getText().toString();
    }

    @Override
    public String rib4() {
        return edRib4.getText().toString();
    }

    @Override
    public String rib5() {
        return edRib5.getText().toString();
    }

    @Override
    public String location() {
        if (currenrtLocation == null) {
            return "";
        } else {
            return currenrtLocation.getLongitude() + "," + currenrtLocation.getLatitude();
        }
    }

    @Override
    public TrafficDto tfaffic() {
        return trafficDto;
    }

    @Override
    public String carwith() {
        return edCarWith.getText().toString();
    }

    @Override
    public String carLength() {
        return edCaeLength.getText().toString();
    }

    @Override
    public String getAllowQuali() {
        if (switchQuali.isChecked()) {
            return 1 + "";
        } else {
            return 0 + "";
        }
    }

    @Override
    public String carHeight() {
        return edCarHeight.getText().toString();
    }

    @Override
    public String getCarNum() {
        return editCarNum.getText().toString().trim().toUpperCase();
    }

    @Override
    public String getCarColor() {
        if (colorType == 0) {//蓝色
            return "2";
        } else {//黄色
            return "1";
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.dissCamera();
    }

    @Override
    public String getCarModel() {
        return tvCarModel.getText().toString();
    }

    @Override
    public String getCarWeight() {
        return editcarWeight.getText().toString();
    }

    @Override
    public String getCarBrand() {
        return tvCarBrand.getText().toString();
    }

    @Override
    public String getUser() {
        return editUserName.getText().toString();
    }

    @Override
    public String getTransportNum() {
        return editPassNum.getText().toString();
    }

    @Override
    public String getInvitEnter() {
        return edinvitEnter.getText().toString();
    }

    @Override
    public String gettranCarNo() {
        return picCarNo;
    }

    @Override
    public String gettranCarName() {
        return picName;
    }

    @Override
    public String getpictureTranNo() {
        return picYsz;
    }

    @Override
    public void carTypeName(String cartypeName) {
        tvCarModel.setText(cartypeName);
    }

    @Override
    public void carTypeId(String carTypeId) {
        carTypeIds = carTypeId;
    }

    @Override
    public void carBrandName(String carBrandName) {
        tvCarBrand.setText(carBrandName);
    }

    @Override
    public void carBrandid(String carBrandId) {
        carBrandIds = carBrandId;
    }

    @Override
    public String getCarTypeId() {
        return carTypeIds;
    }

    @Override
    public String getCarBrandId() {
        return carBrandIds;
    }

    /*车辆详情**
     * 获取
     * **/
    @Override
    public void getCarInfo(SearchCarDto dto) {
        if (dto != null) {
            if (dto.getCheckStatus() == 3) {//认证失败
                dialog = new NormalDialog(mContext).isTitleShow(true)
                        .title("认证失败")
                        .content(dto.getCheckOpinion())
                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                        .btnNum(1).btnText("确定");
                dialog.setOnBtnClickL(
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if (!context.isDestroy(context)) {
                                    dialog.dismiss();
                                }
                            }
                        });
                if (dialog != null) {
                    dialog.show();
                }
            }
            switchQuali.setClickable(false);
            switchAdd.setClickable(false);
            edinvitEnter.setClickable(false);
            context.setToolbar(toolbar, "车辆详情");
            ImageLoadUtil.displayImage(mContext, dto.getVehicleSurvey(), viewCarInfo);
            ImageLoadUtil.displayImage(mContext, dto.getOperationLicense(), viewRoadTransport);
            ImageLoadUtil.displayImage(mContext, dto.getVehicleLicense(), viewDrivinglicense);
            stringTransport = dto.getOperationLicense();
            stringDrivinglicense = dto.getVehicleLicense();
            stringCarInfo = dto.getVehicleSurvey();
            if (dto.getIfQualification() == 0) {//没有资质
                switchQuali.setChecked(false);
                llCar.setVisibility(View.GONE);
            } else {
                switchQuali.setChecked(true);
                llCar.setVisibility(View.VISIBLE);
            }
            picYsz = dto.getPictureTranNo();
            picName = dto.getTranCarName();
            picCarNo = dto.getTranCarNo();
            carTypeIds = dto.getCarTypeId();
            carBrandIds = dto.getCarBrandId();
            if (!ismodify && (TextUtils.isEmpty(this.isNeedAche))) {//不是修改
                viewRoadTransport.setClickable(false);
                viewDrivinglicense.setClickable(false);
                viewCarInfo.setClickable(false);
                edCarWith.setEnabled(false);
                edCarHeight.setEnabled(false);
                edCaeLength.setEnabled(false);
                editcarWeight.setEnabled(false);
                editUserName.setEnabled(false);
                editPassNum.setEnabled(false);
                tvCarModel.setClickable(false);
                tvCarBrand.setClickable(false);
                editCarNum.setEnabled(false);
            }

            if (dto.getAllowAdd() == 0) {
                switchAdd.setChecked(false);
            } else {
                switchAdd.setChecked(true);
            }
            editCarNum.setText(dto.getCarNo());
            if (dto.getCarColor() == 2) {//蓝色
                colorType = 0;
                groupBlue.setChecked(true);
            } else {
                groupYellow.setChecked(true);
            }
            tvCarModel.setText(dto.getCarTypeName());
            edCaeLength.setText(dto.getCarLength());
            edCarWith.setText(dto.getCarWigth());
            edCarHeight.setText(dto.getCarHeigth());
            editcarWeight.setText(dto.getCarLoad());
            tvCarBrand.setText(dto.getCarBrandName());
            editUserName.setText(dto.getCarName());
            editPassNum.setText(dto.getTranNo());
            edinvitEnter.setText(dto.getInvitEnter());

            if (dto.getIsDangerCarType() == 1) {
                swIsDangerous.setChecked(true);
            } else {
                swIsDangerous.setChecked(false);

            }
            tvDrivingData.setText(dto.getLicenseRegTime());
            edCarBottom.setText(dto.getBoardHeight());
            edRib1.setText(dto.getRib1());
            edRib2.setText(dto.getRib2());
            edRib3.setText(dto.getRib3());
            edRib4.setText(dto.getRib4());
            edRib5.setText(dto.getRib5());
        }
    }

    /***车辆被是否被注册
     *
     * **/
    private NormalDialog dialog;

    @Override
    public void isResister(CarIsRegsister dto) {
        if (dto == null || TextUtils.isEmpty(dto.getCarName())) {
            presenter.carriveRz(isConsistent);
        } else {
            dialog = new NormalDialog(mContext).isTitleShow(false)
                    .content("该车牌号已经被" + dto.getCarName() + "注册，是否继续注册?")
                    .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                    .btnNum(2).btnText("取消", "确定");
            dialog.setOnBtnClickL(
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            if (!context.isDestroy(context)) {
                                dialog.dismiss();
                            }
                        }
                    },
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            presenter.carriveRz(isConsistent);
                            if (!context.isDestroy(context)) {
                                dialog.dismiss();
                            }
                        }
                    });
            dialog.show();
        }
    }

    private TrafficDto trafficDto;

    @Override
    public void getTrafficDto(TrafficDto dto) {
        if (dto != null) {
            if (TextUtils.isEmpty(dto.getCmpNm())) {
                return;
            }
            trafficDto = dto;
            edCaeLength.setText((Math.round((dto.getVclLng() / 1000) * 100) / 100.0) + "");
            edCarHeight.setText((Math.round((dto.getVclHgt() / 1000) * 100) / 100.0) + "");
            edCarWith.setText((Math.round((dto.getVclWdt() / 1000) * 100) / 100.0) + "");
            editcarWeight.setText((Math.round((dto.getLdTn() / 1000) * 1000) / 1000.0) + "");
        }
    }

    /****
     * 扫描二维码
     * **/
    private void scan() {
        //获取扫描结果
        QrConfig qrConfig = new QrConfig.Builder()
                .setDesText("(扫一扫)")//扫描框下文字
                .setShowDes(false)//是否显示扫描框下面文字
                .setShowLight(true)//显示手电筒按钮
                .setShowTitle(true)//显示Title
                .setShowAlbum(true)//显示从相册选择按钮
                .setCornerColor(Color.WHITE)//设置扫描框颜色
                .setLineColor(Color.WHITE)//设置扫描线颜色
                .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
                .setScanType(QrConfig.TYPE_ALL)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE)//设置扫描框类型（二维码还是条形码，默认为二维码）
                .setCustombarcodeformat(QrConfig.BARCODE_I25)//此项只有在扫码类型为TYPE_CUSTOM时才有效
                .setPlaySound(true)//是否扫描成功后bi~的声音
                .setDingPath(R.raw.qrcode)//设置提示音(不设置为默认的Ding~)
                .setIsOnlyCenter(true)//是否只识别框中内容(默认为全屏识别)
                .setTitleText("扫一扫")//设置Tilte文字
                .setTitleBackgroudColor(Color.BLACK)//设置状态栏颜色
                .setTitleTextColor(Color.WHITE)//设置Title文字颜色
                .create();
        QrManager.getInstance().init(qrConfig).startScan(context, new QrManager.OnScanResultCallback() {
            @Override
            public void onScanSuccess(String result1) {
                final String result = result1.replaceAll(" ", ""); //结果文字
                edinvitEnter.setText(result);

            }
        });

    }

    private String picYsz = "";

    private void ocrYshuzheng(String failPath) {
        RecognizeService.recAccurateBasic(mContext.getApplicationContext(), failPath,
                new RecognizeService.ServiceListener() {
                    @Override
                    public void onResult(String result) {
                        Log.e("msg", result);
                        editCarNum.setError("请仔细核对车牌号是否和运输许可证一致");
                        editUserName.setError("请仔细核对业户名称是否和运输许可证一致");
                        editPassNum.setError("请仔细核对运输证号是否和运输许可证一致");
                        try {
                            JSONObject object = new JSONObject(result);
                            JSONArray array = new JSONArray(object.getString("words_result"));
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject ob = array.getJSONObject(i);
                                String str = ob.getString("words").trim();
                                if (!TextUtils.isEmpty(str)) {
                                    if (str.contains("交运")) {
                                        String regEx = "[^0-9]";
                                        Pattern p = Pattern.compile(regEx);
                                        Matcher m = p.matcher(str);
                                        picYsz = m.replaceAll("").trim();
                                        if (picYsz.contains("一")) {
                                            picYsz = picYsz.replaceAll("一", "");
                                        }
                                        editPassNum.setText(picYsz);
                                    } else if ((str.matches("[0-9]+"))) {
                                        if (i <= 6 && str.length() == 12) {
                                            editPassNum.setText(str);
                                        }
                                    }
                                    if (str.contains("业户名称")) {
                                        int v2 = str.contains("业户名称:") ? 5 : 4;
                                        String rest = str.substring(v2, str.length());
                                        if (rest.contains("一")) {
                                            rest = rest.replaceAll("一", "");
                                        }
                                        if (rest.contains("-")) {
                                            rest = rest.replaceAll("-", "");
                                        }
                                        editUserName.setText(rest);
                                        Log.e("msg", "业户名称" + str.substring(v2, str.length()) + v2);
                                    }
                                    if (str.contains("车牌号码") || str.contains("车辆号牌")) {
                                        int v3 = str.contains(":") ? 5 : 4;
                                        String rest = str.substring(v3, str.length());
                                        if (rest.contains("一")) {
                                            rest = rest.replaceAll("一", "");
                                        }
                                        if (rest.contains("-")) {
                                            rest = rest.replaceAll("-", "");
                                        }
                                        if (rest.contains("(")) {
                                            try {
                                                rest = rest.substring(0, rest.indexOf("("));
                                            } catch (Exception E) {
                                            }
                                        }
                                        if (TextUtils.isEmpty(rest)) {
                                            if (i < array.length() - 1) {
                                                JSONObject tob = array.getJSONObject(i + 1);
                                                String re = tob.getString("words").trim();
                                                if (re.contains("(")) {
                                                    re = re.substring(0, re.indexOf("("));
                                                }
                                                editCarNum.setText(re.toUpperCase());
                                            }
                                        } else {
                                            editCarNum.setText(rest.toUpperCase());
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

    private String picName = "";
    private String picCarNo = "";

    private void ocrXsz(String filePath) {
        RecognizeService.recVehicleLicense(mContext.getApplicationContext(), filePath,
                new RecognizeService.ServiceListener() {
                    @Override
                    public void onResult(String result) {
                        Log.e("msg", result);
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            JSONObject contectObj = new JSONObject(jsonObject.getString("words_result"));
                            JSONObject cn = new JSONObject(contectObj.getString("号牌号码"));
                            picCarNo = cn.getString("words");
                            JSONObject syr = new JSONObject(contectObj.getString("所有人"));
                            picName = syr.getString("words");

                            JSONObject data = new JSONObject(contectObj.getString("注册日期"));
                            String driverdata = data.getString("words");
                            tvDrivingData.setText(BaseActivity.trantTime(driverdata, "yyyyMMdd", "yyyy-MM-dd"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (galleryFinal != null) {
            GalleryUtils.functionConfig = null;
            //getActivity().onBackPressed();
        }
    }

    /***
     * 获取经纬度
     * **/
    public void getLocation() {

        context.showLoadingDialog("正在定位中...");

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
        mLocationClient.start();
        if (Build.VERSION.SDK_INT >= 26) {
            if (!mLocationClient.isStarted()) {
                mLocationClient.restart();
            }
        }
    }

    public LocationClient mLocationClient = null;

    private MyLocationListener myListener = new MyLocationListener();

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        if (!TextUtils.isEmpty(this.edCaeLength.getText().toString()) && !edCaeLength.getText().toString().equals(".")
                && Double.parseDouble(this.edCaeLength.getText().toString()) >= 100) {
            this.edCaeLength.setText("");
            this.context.showMessage("车辆长度必须小于100米");
        }


        if (!TextUtils.isEmpty(edCarHeight.getText().toString()) && !edCarHeight.getText().toString().equals(".")
                && Double.parseDouble(this.edCarHeight.getText().toString()) >= 10) {
            this.edCarHeight.setText("");
            this.context.showMessage("车辆高度必须小于10米");
        }

        if (!TextUtils.isEmpty(this.edCarWith.getText().toString()) && !this.edCarWith.getText().toString().equals(".")
                && Double.parseDouble(this.edCarWith.getText().toString()) >= 10) {
            this.edCarWith.setText("");
            this.context.showMessage("车辆宽度必须小于10米");
        }

        if (!TextUtils.isEmpty(this.editcarWeight.getText().toString()) && !this.editcarWeight.getText().toString().equals(".")
                && Double.parseDouble(this.editcarWeight.getText().toString()) >= 1000) {
            this.editcarWeight.setText("");
            this.context.showMessage("载重必须小于1000吨");
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            context.dismissLoadingDialog();
            if (location == null) {
                context.showMessage("定位失败，请检查是否开启定位");
                return;
            } else if (location.getLocType() == 62) {
                context.showMessage("定位失败，请检查是否开启定位");
                return;
            }
            // context.showMessage("定位成功，信息"+location.getLatitude()+location.getCity());
            currenrtLocation = location;
            if (mLocationClient != null) {
                mLocationClient.stop();
            }
        }
    }

    public void onPause() {
        super.onPause();
        if (!TextUtils.isEmpty(this.isNeedAche)) {
            SearchCarDto v0 = this.searchCarDto;
            if (v0 == null) {
                return;
            } else {
                v0.setOperationLicense(this.stringTransport);
                this.searchCarDto.setVehicleLicense(this.stringDrivinglicense);
                this.searchCarDto.setVehicleSurvey(this.stringCarInfo);
                if (this.switchQuali.isChecked()) {
                    this.searchCarDto.setIfQualification(1);
                } else {
                    this.searchCarDto.setIfQualification(0);
                }

                if (this.switchAdd.isChecked()) {
                    this.searchCarDto.setAllowAdd(1);
                } else {
                    this.searchCarDto.setAllowAdd(0);
                }

                if (this.swIsDangerous.isChecked()) {
                    this.searchCarDto.setIsDangerCarType(1);
                } else {
                    this.searchCarDto.setIsDangerCarType(0);
                }

                this.searchCarDto.setCarNo(this.editCarNum.getText().toString());
                carNo = editCarNum.getText().toString();
                if (this.colorType == 0) {
                    this.searchCarDto.setCarColor(2);
                    groupBlue.setChecked(true);
                } else {
                    this.searchCarDto.setCarColor(1);
                    groupYellow.setChecked(true);
                }

                this.searchCarDto.setCarTypeName(this.tvCarModel.getText().toString());
                this.searchCarDto.setCarLength(this.edCaeLength.getText().toString());
                this.searchCarDto.setCarWigth(this.edCarWith.getText().toString());
                this.searchCarDto.setCarHeigth(this.edCarHeight.getText().toString());
                this.searchCarDto.setCarLoad(this.editcarWeight.getText().toString());
                this.searchCarDto.setCarBrandName(this.tvCarBrand.getText().toString());
                this.searchCarDto.setCarName(this.editUserName.getText().toString());
                this.searchCarDto.setTranNo(this.editPassNum.getText().toString());
                this.searchCarDto.setInvitEnter(this.edinvitEnter.getText().toString());
                this.searchCarDto.setLicenseRegTime(this.tvDrivingData.getText().toString());
                this.searchCarDto.setBoardHeight(this.edCarBottom.getText().toString());
                this.searchCarDto.setRib1(this.edRib1.getText().toString());
                this.searchCarDto.setRib2(this.edRib2.getText().toString());
                this.searchCarDto.setRib3(this.edRib3.getText().toString());
                this.searchCarDto.setRib4(this.edRib4.getText().toString());
                this.searchCarDto.setRib5(this.edRib5.getText().toString());
                this.searchCarDto.setCarTypeId(this.carTypeIds);
                this.searchCarDto.setCarBrandId(this.carBrandIds);
                Hawk.put(PreferenceKey.ISCAR_ACHE, searchCarDto);
            }
        }
    }


}
