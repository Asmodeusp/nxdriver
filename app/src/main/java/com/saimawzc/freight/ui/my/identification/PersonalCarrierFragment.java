package com.saimawzc.freight.ui.my.identification;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.saimawzc.freight.dto.login.AreaDto;
import com.saimawzc.freight.dto.my.identification.CarrierIndenditicationDto;
import com.saimawzc.freight.dto.pic.PicDto;
import com.saimawzc.freight.presenter.mine.identification.PersonCarrierPresenter;
import com.saimawzc.freight.ui.MainActivity;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.view.mine.identificaion.PersonCarrierView;
import com.saimawzc.freight.weight.utils.AreaChooseDialog;
import com.saimawzc.freight.weight.utils.FileUtil;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.AreaListener;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import com.werb.permissionschecker.PermissionChecker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static com.saimawzc.freight.base.BaseActivity.PERMISSIONS;
import static com.saimawzc.freight.base.BaseActivity.PERMISSIONS_CAMERA;
import static com.saimawzc.freight.ui.my.identification.DriverCarrierFragment.REQUEST_CODE_PIC;

/**
 * Created by Administrator on 2020/8/1.
 * ???????????????
 */
public class PersonalCarrierFragment  extends BaseFragment implements PersonCarrierView{

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.imgidpositive)ImageView imageIdPositive;
    @BindView(R.id.imgidotherside)ImageView imageOtherSide;
    @BindView(R.id.tvuser)EditText tvUser;
    @BindView(R.id.tvidcard)EditText tvIdCard;
    @BindView(R.id.tvcarrier)TextView tvCarrier;
    @BindView(R.id.tvArea)TextView tvArea;
    @BindView(R.id.right_btn)TextView btnRight;
    private String imgStringPositive;
    private String imgStringOtherSide;
    @BindView(R.id.chooseidpositive)LinearLayout lineChoosePositive;
    @BindView(R.id.chooseidotherside)LinearLayout lineChooseidotherside;
    @BindView(R.id.edmaxAmcount)EditText edMaxAmcount;
    private NormalDialog dialog;
    PersonCarrierPresenter presenter;
    private int chooseType;
    AreaChooseDialog  areaChooseDialog;
    private String proName;
    private String proId;
    private String citysName;
    private String citysId;
    private String countrysName;
    private String countrysId;
    private String type;

    @Override
    public int initContentView() {
        return R.layout.fragment_personalcarrier;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"?????????????????????");
        personCenterDto=Hawk.get(PreferenceKey.PERSON_CENTER);
        presenter=new PersonCarrierPresenter(this,mContext);
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText("????????????");

        try {
            type=getArguments().getString("type");
        }catch (Exception e){

        }

       initpermissionChecker();
        if(areaChooseDialog==null){
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    areaChooseDialog =new AreaChooseDialog(mContext);
                    areaChooseDialog.initData();
                }
            });
        }
        if(personCenterDto!=null){
            if(personCenterDto.getAuthState()!=0){//????????????
                presenter.getIdentificationInfo();
            }
        }
    }
    @Override
    public void initData() {
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("????????????????????????????????????");
                    return;
                }
                if(btnRight.getText().toString().equals("????????????")){
                    if(TextUtils.isEmpty(imgStringPositive)){
                        context.showMessage("???????????????????????????");
                        return;
                    }
                    if(TextUtils.isEmpty(imgStringOtherSide)){
                        context.showMessage("???????????????????????????");
                        return;
                    }
                    if(context.isEmptyStr(tvUser.getText().toString())){
                        context.showMessage("?????????????????????");
                        return;
                    }
                    if(context.isEmptyStr(tvIdCard.getText().toString())){
                        context.showMessage("????????????????????????");
                        return;
                    }
                    if(context.isEmptyStr(tvArea.getText().toString())){
                        context.showMessage("??????????????????");
                        return;
                    }
                }

                if(btnRight.getText().toString().equals("????????????")){
                    if(TextUtils.isEmpty(type)){
                        presenter.carriveRz();
                    }else {
                        presenter.recarriveRz();
                    }
                }else if(btnRight.getText().toString().equals("????????????")){


                        dialog = new NormalDialog(mContext).isTitleShow(false)
                                .content("??????????????????????????????????????????????")
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
    }
    @OnClick({R.id.chooseidpositive,R.id.chooseidotherside,R.id.tvArea})
    public  void click(View view){
            switch (view.getId()){
                case R.id.chooseidpositive://?????????????????????
                    if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                        context.showMessage("????????????????????????,???????????????????????????");
                        permissionChecker.requestPermissions();
                        return;
                    }
                    initCamera(CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                    break;
                case R.id.chooseidotherside://?????????????????????
                    if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                        context.showMessage("????????????????????????,???????????????????????????");
                        permissionChecker.requestPermissions();
                        return;
                    }
                    initCamera(CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                    break;
                case R.id.tvArea://????????????
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
        if(requestCode==REQUEST_CODE_PIC && resultCode == RESULT_OK){
            String filePath = FileUtil.getSaveFile(mContext,tempImage).getAbsolutePath();
            String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
            if(!TextUtils.isEmpty(filePath)&& new File(filePath)!=null){
                if(CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)){
                    chooseType=0;
                    recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    ImageLoadUtil.displayLocalImage(mContext, new File(filePath), imageIdPositive);
                }else if(CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)){
                    chooseType=1;
                    ImageLoadUtil.displayLocalImage(mContext, new File(filePath), imageOtherSide);
                }
                Uploadpic(BaseActivity.compress(mContext,new File(filePath)));
            }
        }



    }
    private  void Uploadpic(File file){
        context.showLoadingDialog("???????????????...");
        File tempFile=BaseActivity.compress(mContext,file);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), tempFile);
        //files ????????????
        MultipartBody.Part part=
                MultipartBody.Part.createFormData("picture", tempFile.getName(), requestBody);
        context.authApi.loadImg(part).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {
               context.dismissLoadingDialog();
                if(chooseType==0){
                    imgStringPositive=response.getUrl();
                    context.showMessage("???????????????????????????");
                }else if(chooseType==1){
                    imgStringOtherSide=response.getUrl();
                    context.showMessage("???????????????????????????");
                }
            }
            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
                context.dismissLoadingDialog();
            }
        });
    }

    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // ????????????????????????
        param.setIdCardSide(idCardSide);
        // ??????????????????
        param.setDetectDirection(true);
        // ??????????????????????????????0-100, ??????????????????????????????????????????????????? ????????????????????????20
        param.setImageQuality(20);
        OCR.getInstance(mContext).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    if(result.getIdNumber()==null){
                        context.showMessage("?????????????????????");
                        return;
                    }
                    if(result.getName()==null){
                        context.showMessage("??????????????????");
                        return;
                    }
                    if(!TextUtils.isEmpty(result.getIdNumber().toString())){
                        tvIdCard.setText(result.getIdNumber().toString());
                    }
                    if(!TextUtils.isEmpty(result.getName().toString())){
                        tvUser.setText(result.getName().toString());
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
        context.dismissLoadingDialog();

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.dissCamera();
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
        context.showMessage("????????????");
        context.finish();
    }
    @Override
    public void OnDealCamera(int type) {//????????????

    }
    @Override
    public String sringImgPositive() {
        return imgStringPositive;
    }
    @Override
    public String sringImgOtherSide() {
        return imgStringOtherSide;
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
    public String invoiceMaxAmount() {
        return edMaxAmcount.getText().toString();
    }


    @Override
    public void IdentificationInfo(CarrierIndenditicationDto dto) {
        if(dto!=null){
            if(dto.getCheckStatus()==3){//????????????
                dialog = new NormalDialog(mContext).isTitleShow(true)
                        .title("????????????")
                        .content(dto.getCheckOpinion())
                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                        .btnNum(1).btnText("??????");
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
            imgStringPositive=dto.getFrontIdCard();
            ImageLoadUtil.displayImage(mContext, imgStringPositive, imageIdPositive);
            imgStringOtherSide=dto.getReverseIdCard();
            ImageLoadUtil.displayImage(mContext, imgStringOtherSide, imageOtherSide);
            tvUser.setText(dto.getUserName());
            tvIdCard.setText(dto.getIdCardNum());
            tvArea.setText(dto.getAddress());
            proName=dto.getProvince();
            proId=dto.getProvinceId();
            citysName=dto.getCity();
            citysId=dto.getCityId();
            countrysName=dto.getArea();
            countrysId=dto.getAreaId();
            edMaxAmcount.setText(dto.getInvoiceMaxAmount());
            if(dto.getCheckStatus()==1){
                if(TextUtils.isEmpty(getArguments().getString("type"))){
                    lineChoosePositive.setClickable(false);
                    btnRight.setText("????????????");
                    lineChooseidotherside.setClickable(false);
                    tvUser.setEnabled(false);
                    tvIdCard.setEnabled(false);
                }

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
