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
 * ????????????
 */

public class RegisterCarFragment extends BaseFragment implements ResisterCarView,
        TextWatcher {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imgRoadTransport)
    ImageView viewRoadTransport;//???????????????
    @BindView(R.id.imgDrivinglicense)
    ImageView viewDrivinglicense;//?????????
    @BindView(R.id.imagecarinfo)
    ImageView viewCarInfo;//????????????
    @BindView(R.id.togglequalifications)
    SwitchButton switchQuali;//??????
    @BindView(R.id.toggleadd)
    SwitchButton switchAdd;//???????????????
    @BindView(R.id.tvplate)
    TextView tvPlate;
    @BindView(R.id.edcarnum)
    EditText editCarNum;
    @BindView(R.id.groupblue)
    RadioButton groupBlue;
    @BindView(R.id.groupyellow)
    RadioButton groupYellow;
    @BindView(R.id.carmodel)
    TextView tvCarModel;//????????????
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
    EditText edCaeLength;//????????????
    @BindView(R.id.edCarWith)
    EditText edCarWith;//????????????
    @BindView(R.id.edCarHeight)
    EditText edCarHeight;//????????????
    @BindView(R.id.edinvitEnter)
    TextView edinvitEnter;
    /***???????????????***/
    @BindView(R.id.toggdangerous)
    SwitchButton swIsDangerous;//???????????????
    /***?????????????????????***/
    @BindView(R.id.edxszdata)
    TextView tvDrivingData;
    /***????????????***/
    @BindView(R.id.edcarbottom)
    EditText edCarBottom;
    /***???????????????***/
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
    private int colorType = 1;//????????????
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
        context.setToolbar(toolbar, "????????????");
        btnRight.setText("????????????");
        presenter = new ResisterCarPresenter(this, mContext);
        initpermissionChecker();
        if (permissionChecker.isLackPermissions(PERMISSIONS)) {
            context.showMessage("????????????????????????,??????????????????????????????????????????");
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
        if (getArguments().getString("type").equals("carinfo")) {//??????????????????
            presenter.getCarInfo(getArguments().getString("id"));
            if (ismodify) {
                xiugia = 1 + "";
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText("????????????");
                viewRoadTransport.setEnabled(false);//???????????????
                viewDrivinglicense.setEnabled(false);//?????????
                viewCarInfo.setEnabled(false);//????????????
                switchQuali.setEnabled(false);//??????
                switchAdd.setEnabled(false);//???????????????
                tvPlate.setEnabled(false);
                editCarNum.setEnabled(false);
                groupBlue.setEnabled(false);
                groupYellow.setEnabled(false);
                tvCarModel.setEnabled(false);//????????????
                editcarWeight.setEnabled(false);
                tvCarBrand.setEnabled(false);
                editUserName.setEnabled(false);
                editPassNum.setEnabled(false);
               
                groupBtn.setEnabled(false);
                edCaeLength.setEnabled(false);//????????????
                edCarWith.setEnabled(false);//????????????
                edCarHeight.setEnabled(false);//????????????
                edinvitEnter.setEnabled(false);
                swIsDangerous.setEnabled(false);//???????????????
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
        if (getArguments().getString("type").equals("carinfo")) {//????????????
            if (ismodify = false) {
                return;
            }
        }
        switch (view.getId()) {
            case R.id.edxszdata://?????????????????????
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
            case R.id.imgRoadTransport://?????????
                presenter.showCamera(mContext, 0);
                break;
            case R.id.imgDrivinglicense://?????????
                presenter.showCamera(mContext, 1);
                break;
            case R.id.imagecarinfo://????????????
                presenter.showCamera(mContext, 2);
                break;
            case R.id.carmodel://????????????
                presenter.getCarType();
                break;
            case R.id.tvcarbrand://????????????
                if (TextUtils.isEmpty(tvCarModel.getText().toString())) {
                    context.showMessage("?????????????????????");
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
                if (hasFocus) { //????????????
                } else {//????????????
                    editPassNum.setError(null);
                }
            }
        });
        editCarNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) { //????????????
                } else {//????????????
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
                                Log.e("msg", "????????????????????????");
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
                if (hasFocus) { //????????????
                } else {//????????????
                    editUserName.setError(null);
                }
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RepeatClickUtil.isFastClick()) {
                    context.showMessage("????????????????????????????????????");
                    return;
                }
                
                if (btnRight.getText().toString().equals("????????????")) {
                    viewRoadTransport.setEnabled(true);//???????????????
                    viewDrivinglicense.setEnabled(true);//?????????
                    viewCarInfo.setEnabled(true);//????????????
                    switchQuali.setEnabled(true);//??????
                    switchAdd.setEnabled(true);//???????????????
                    tvPlate.setEnabled(true);
                    editCarNum.setEnabled(true);
                    groupBlue.setEnabled(true);
                    groupYellow.setEnabled(true);
                    tvCarModel.setEnabled(true);//????????????
                    editcarWeight.setEnabled(true);
                    tvCarBrand.setEnabled(true);
                    editUserName.setEnabled(true);
                    editPassNum.setEnabled(true);
                    btnRight.setEnabled(true);
                    groupBtn.setEnabled(true);
                    edCaeLength.setEnabled(true);//????????????
                    edCarWith.setEnabled(true);//????????????
                    edCarHeight.setEnabled(true);//????????????
                    edinvitEnter.setEnabled(true);
                    swIsDangerous.setEnabled(true);//???????????????
                    tvDrivingData.setEnabled(true);
                    edCarBottom.setEnabled(true);
                    edRib1.setEnabled(true);
                    edRib2.setEnabled(true);
                    edRib3.setEnabled(true);
                    edRib4.setEnabled(true);
                    edRib5.setEnabled(true);
                    dialog = new NormalDialog(mContext).isTitleShow(false)
                            .content("????????????????????????????????????????")
                            .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                            .btnNum(2).btnText("??????", "??????");
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
                                        btnRight.setText("????????????");
                                    }
                                }
                            });
                    if (dialog != null) {
                        dialog.show();
                    }
                } else {
                    if (switchQuali.isChecked()) {
                        if (TextUtils.isEmpty(stringTransport)) {
                            context.showMessage("?????????????????????????????????");
                            return;
                        }
                        if (TextUtils.isEmpty(stringDrivinglicense)) {
                            context.showMessage("?????????????????????");
                            return;
                        }
                        if (currenrtLocation == null) {
                            getLocation();
                            context.showMessage("????????????????????????????????????????????????????????????");
                            return;
                        }
                    }
                    if (TextUtils.isEmpty(stringCarInfo)) {
                        context.showMessage("??????????????????????????????????????????");
                        return;
                    }
                    if (context.isEmptyStr(editCarNum.getText().toString())) {
                        context.showMessage("?????????????????????");
                        return;
                    }
                    if (editCarNum.getText().toString().contains("(") || editCarNum.getText().toString().contains("???")) {
                        context.showMessage("?????????????????????");
                        return;
                    }
                    if (editCarNum.getText().toString().contains("???") || editCarNum.getText().toString().contains("???")
                            || editCarNum.getText().toString().contains("???")) {
                        context.showMessage("?????????????????????");
                        return;
                    }
                    if (context.isContainTemp(editCarNum.getText().toString())) {
                        context.showMessage("???????????????????????????");
                        return;
                    }

                    if (context.isEmptyStr(tvCarModel.getText().toString())) {
                        context.showMessage("??????????????????");
                        return;
                    }
                    if (context.isEmptyStr(edCaeLength.getText().toString())) {
                        context.showMessage("????????????????????????");
                        return;
                    }
                    if (context.isEmptyStr(edCarHeight.getText().toString())) {
                        context.showMessage("????????????????????????");
                        return;
                    }
                    if (context.isEmptyStr(edCarWith.getText().toString())) {
                        context.showMessage("????????????????????????");
                        return;
                    }
                    if (context.isEmptyStr(editUserName.getText().toString())) {
                        context.showMessage("????????????????????????");
                        return;
                    }
                    if (context.isEmptyStr(editPassNum.getText().toString())) {
                        context.showMessage("????????????????????????");
                        return;
                    }
                    if (context.isEmptyStr(editcarWeight)) {
                        context.showMessage("????????????????????????");
                        return;
                    }
                    try {
                        if (Double.parseDouble(editcarWeight.getText().toString()) == 0) {
                            context.showMessage("?????????????????????0");
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
                    if (ismodify == true || xiugia.equals("1")) {//??????
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
                context.showMessage("????????????????????????????????????");
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
                        if (chooseType == 3) {//???????????????
                            ImageLoadUtil.displayImage(mContext, uri, viewRoadTransport);
                            if (file != null && file.length() > 0) {
                                context.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ocrYshuzheng(file.getPath());
                                    }
                                });

                            }
                        } else if (chooseType == 4) {//???????????????
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
            Log.e("msg", "????????????" + tempImage);
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
        context.showLoadingDialog("???????????????...");
        File temp = BaseActivity.compress(mContext, file);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), temp);
        //files ????????????
        MultipartBody.Part part =
                MultipartBody.Part.createFormData("picture", temp.getName(), requestBody);
        context.authApi.loadImg(part).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {
                context.dismissLoadingDialog();
                if (chooseType == 0 || chooseType == 3) {
                    stringTransport = response.getUrl();
                    context.showMessage("???????????????????????????");
                } else if (chooseType == 1 || chooseType == 4) {
                    stringDrivinglicense = response.getUrl();
                    context.showMessage("?????????????????????");
                } else if (chooseType == 2 || chooseType == 5) {
                    stringCarInfo = response.getUrl();
                    context.showMessage("????????????????????????");
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
            return 1 + "";//??????
        } else {
            return 0 + "";
        }
    }

    @Override
    public String isDangerous() {
        if (swIsDangerous.isChecked()) {
            return 1 + "";//??????
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
        if (colorType == 0) {//??????
            return "2";
        } else {//??????
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

    /*????????????**
     * ??????
     * **/
    @Override
    public void getCarInfo(SearchCarDto dto) {
        if (dto != null) {
            if (dto.getCheckStatus() == 3) {//????????????
                dialog = new NormalDialog(mContext).isTitleShow(true)
                        .title("????????????")
                        .content(dto.getCheckOpinion())
                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                        .btnNum(1).btnText("??????");
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
            context.setToolbar(toolbar, "????????????");
            ImageLoadUtil.displayImage(mContext, dto.getVehicleSurvey(), viewCarInfo);
            ImageLoadUtil.displayImage(mContext, dto.getOperationLicense(), viewRoadTransport);
            ImageLoadUtil.displayImage(mContext, dto.getVehicleLicense(), viewDrivinglicense);
            stringTransport = dto.getOperationLicense();
            stringDrivinglicense = dto.getVehicleLicense();
            stringCarInfo = dto.getVehicleSurvey();
            if (dto.getIfQualification() == 0) {//????????????
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
            if (!ismodify && (TextUtils.isEmpty(this.isNeedAche))) {//????????????
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
            if (dto.getCarColor() == 2) {//??????
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

    /***????????????????????????
     *
     * **/
    private NormalDialog dialog;

    @Override
    public void isResister(CarIsRegsister dto) {
        if (dto == null || TextUtils.isEmpty(dto.getCarName())) {
            presenter.carriveRz(isConsistent);
        } else {
            dialog = new NormalDialog(mContext).isTitleShow(false)
                    .content("?????????????????????" + dto.getCarName() + "????????????????????????????")
                    .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                    .btnNum(2).btnText("??????", "??????");
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
     * ???????????????
     * **/
    private void scan() {
        //??????????????????
        QrConfig qrConfig = new QrConfig.Builder()
                .setDesText("(?????????)")//??????????????????
                .setShowDes(false)//?????????????????????????????????
                .setShowLight(true)//?????????????????????
                .setShowTitle(true)//??????Title
                .setShowAlbum(true)//???????????????????????????
                .setCornerColor(Color.WHITE)//?????????????????????
                .setLineColor(Color.WHITE)//?????????????????????
                .setLineSpeed(QrConfig.LINE_MEDIUM)//?????????????????????
                .setScanType(QrConfig.TYPE_ALL)//???????????????????????????????????????????????????????????????????????????????????????
                .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE)//????????????????????????????????????????????????????????????????????????
                .setCustombarcodeformat(QrConfig.BARCODE_I25)//??????????????????????????????TYPE_CUSTOM????????????
                .setPlaySound(true)//?????????????????????bi~?????????
                .setDingPath(R.raw.qrcode)//???????????????(?????????????????????Ding~)
                .setIsOnlyCenter(true)//???????????????????????????(?????????????????????)
                .setTitleText("?????????")//??????Tilte??????
                .setTitleBackgroudColor(Color.BLACK)//?????????????????????
                .setTitleTextColor(Color.WHITE)//??????Title????????????
                .create();
        QrManager.getInstance().init(qrConfig).startScan(context, new QrManager.OnScanResultCallback() {
            @Override
            public void onScanSuccess(String result1) {
                final String result = result1.replaceAll(" ", ""); //????????????
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
                        editCarNum.setError("??????????????????????????????????????????????????????");
                        editUserName.setError("?????????????????????????????????????????????????????????");
                        editPassNum.setError("?????????????????????????????????????????????????????????");
                        try {
                            JSONObject object = new JSONObject(result);
                            JSONArray array = new JSONArray(object.getString("words_result"));
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject ob = array.getJSONObject(i);
                                String str = ob.getString("words").trim();
                                if (!TextUtils.isEmpty(str)) {
                                    if (str.contains("??????")) {
                                        String regEx = "[^0-9]";
                                        Pattern p = Pattern.compile(regEx);
                                        Matcher m = p.matcher(str);
                                        picYsz = m.replaceAll("").trim();
                                        if (picYsz.contains("???")) {
                                            picYsz = picYsz.replaceAll("???", "");
                                        }
                                        editPassNum.setText(picYsz);
                                    } else if ((str.matches("[0-9]+"))) {
                                        if (i <= 6 && str.length() == 12) {
                                            editPassNum.setText(str);
                                        }
                                    }
                                    if (str.contains("????????????")) {
                                        int v2 = str.contains("????????????:") ? 5 : 4;
                                        String rest = str.substring(v2, str.length());
                                        if (rest.contains("???")) {
                                            rest = rest.replaceAll("???", "");
                                        }
                                        if (rest.contains("-")) {
                                            rest = rest.replaceAll("-", "");
                                        }
                                        editUserName.setText(rest);
                                        Log.e("msg", "????????????" + str.substring(v2, str.length()) + v2);
                                    }
                                    if (str.contains("????????????") || str.contains("????????????")) {
                                        int v3 = str.contains(":") ? 5 : 4;
                                        String rest = str.substring(v3, str.length());
                                        if (rest.contains("???")) {
                                            rest = rest.replaceAll("???", "");
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
                            JSONObject cn = new JSONObject(contectObj.getString("????????????"));
                            picCarNo = cn.getString("words");
                            JSONObject syr = new JSONObject(contectObj.getString("?????????"));
                            picName = syr.getString("words");

                            JSONObject data = new JSONObject(contectObj.getString("????????????"));
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
     * ???????????????
     * **/
    public void getLocation() {

        context.showLoadingDialog("???????????????...");

        mLocationClient = new LocationClient(mContext);
        //??????LocationClient???
        mLocationClient.registerLocationListener(myListener);
        //??????????????????
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        //?????????????????????????????????????????????????????????????????????false
        //?????????????????????????????????????????????????????????????????????true
        option.setNeedNewVersionRgc(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        //????????????????????????????????????????????????????????????????????????????????????true
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
            this.context.showMessage("????????????????????????100???");
        }


        if (!TextUtils.isEmpty(edCarHeight.getText().toString()) && !edCarHeight.getText().toString().equals(".")
                && Double.parseDouble(this.edCarHeight.getText().toString()) >= 10) {
            this.edCarHeight.setText("");
            this.context.showMessage("????????????????????????10???");
        }

        if (!TextUtils.isEmpty(this.edCarWith.getText().toString()) && !this.edCarWith.getText().toString().equals(".")
                && Double.parseDouble(this.edCarWith.getText().toString()) >= 10) {
            this.edCarWith.setText("");
            this.context.showMessage("????????????????????????10???");
        }

        if (!TextUtils.isEmpty(this.editcarWeight.getText().toString()) && !this.editcarWeight.getText().toString().equals(".")
                && Double.parseDouble(this.editcarWeight.getText().toString()) >= 1000) {
            this.editcarWeight.setText("");
            this.context.showMessage("??????????????????1000???");
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            context.dismissLoadingDialog();
            if (location == null) {
                context.showMessage("??????????????????????????????????????????");
                return;
            } else if (location.getLocType() == 62) {
                context.showMessage("??????????????????????????????????????????");
                return;
            }
            // context.showMessage("?????????????????????"+location.getLatitude()+location.getCity());
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
