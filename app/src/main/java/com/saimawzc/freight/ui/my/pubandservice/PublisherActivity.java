package com.saimawzc.freight.ui.my.pubandservice;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import com.baidu.ocr.ui.camera.CameraActivity;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.TimeChooseListener;
import com.saimawzc.freight.dto.login.TaxiAreaDto;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.dto.my.PersonCenterDto;
import com.saimawzc.freight.dto.pic.PicDto;
import com.saimawzc.freight.dto.taxi.TjSWDto;
import com.saimawzc.freight.dto.taxi.TjSubmitDto;
import com.saimawzc.freight.presenter.order.taxi.PublisherPresenter;
import com.saimawzc.freight.view.order.taxi.PublisherView;
import com.saimawzc.freight.weight.utils.FileUtil;
import com.saimawzc.freight.weight.utils.TimeChooseDialogUtil;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;
import com.saimawzc.freight.weight.utils.ocr.RecognizeService;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import static com.saimawzc.freight.ui.my.identification.DriverCarrierFragment.REQUEST_CODE_PIC;
import static com.saimawzc.freight.weight.utils.AninalUtils.fadeIn;
import static com.saimawzc.freight.weight.utils.AninalUtils.fadeOut;

public class PublisherActivity extends BaseActivity
        implements PublisherView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.llcontect)
    LinearLayout llContect;
    boolean isShow=false;
    @BindView(R.id.imgdown)
    ImageView imageView;
    @BindView(R.id.imgBusiness)
    ImageView imgBusbess;
    @BindView(R.id.edCompanyName)
    EditText edCompanyName;
    @BindView(R.id.ednsrnum)
    EditText  edtaxiPersonNum;
    @BindView(R.id.edMainPerson)
    EditText edMainPersonName;
    @BindView(R.id.edphone)
    EditText edPhone;
    @BindView(R.id.zcMoney)
    EditText edZcMoney;
    @BindView(R.id.tvadress)
    TextView tvAdress;
    @BindView(R.id.edxxadress)
    EditText edAdressDelation;
    @BindView(R.id.tvtype)
    TextView tvType;
    @BindView(R.id.edjyfw)
    EditText edJyFws;
    @BindView(R.id.edswjg)
    EditText edTaxtAuthor;
    @BindView(R.id.zctime)
    TextView tvZcTime;
    @BindView(R.id.tvclData)
    TextView tvClData;
    TaxiAreaDto areaDto;
    private OptionsPickerView optionsPickerView;//??????????????????
    TimeChooseDialogUtil chooseDialogUtil;
    private List<String> statusList=new ArrayList<>();
    private String bussisImg;
    private PublisherPresenter person;
    private UserInfoDto userInfoDto;
    private PersonCenterDto personCenterDto;
    @BindView(R.id.tvOrder)TextView tvOrder;

    @Override
    protected int getViewId() {
        return R.layout.activity_publisher;
    }

    @Override
    protected void init() {
        setToolbar(toolbar,"?????????????????????");
        initpermissionChecker();
        person=new PublisherPresenter(this,this);
        userInfoDto= Hawk.get(PreferenceKey.USER_INFO);
        personCenterDto=Hawk.get(PreferenceKey.PERSON_CENTER);
        if(userInfoDto!=null){
            if(!TextUtils.isEmpty(userInfoDto.getRoleId())){
                person.getData(userInfoDto.getRoleId());
                if(personCenterDto!=null){
                    edCompanyName.setEnabled(false);
                    edCompanyName.setText(personCenterDto.getCompanyName());
                }else {
                    showMessage("????????????????????????");
                }

            }
        }

    }

    @OnClick({R.id.tvqeyi,R.id.tvadress,R.id.imgBusiness,R.id.tvtype
    ,R.id.zctime,R.id.tvclData,R.id.tvOrder})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvqeyi:
                if(isShow){
                    isShow=false;
                    fadeOut(llContect);
                    imageView.setImageResource(R.drawable.ico_taxi_down);

                }else {
                    fadeIn(llContect);
                    isShow=true;
                    imageView.setImageResource(R.drawable.ico_taxi_top);
                }
                break;
            case R.id.imgBusiness:
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("????????????????????????,???????????????????????????");
                    permissionChecker.requestPermissions();
                    return;
                }
                initCamera( CameraActivity.CONTENT_TYPE_GENERAL);

                break;

            case R.id.tvadress:
                readyGoForResult(ChooseTaxiAdressActivity.class,100);
                break;
            case R.id.tvtype:
                statusList.clear();
                statusList.add("?????????????????????");
                statusList.add("???????????????");
                optionsPickerView = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        String str = statusList.get(options1);
                        tvType.setText(str);
                    }
                }).setCancelColor(Color.GRAY).
                        setSubmitColor(Color.RED).build();
                optionsPickerView.setNPicker(statusList,null,null);
                optionsPickerView.show();
                break;
            case R.id.zctime:
                if(chooseDialogUtil==null){
                    chooseDialogUtil  =new TimeChooseDialogUtil(mContext);
                }
                chooseDialogUtil.showDialog(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                       tvZcTime.setText(result);
                    }

                    @Override
                    public void cancel() {
                        chooseDialogUtil.dissDialog();
                    }
                });
                break;
            case R.id.tvclData:
                if(chooseDialogUtil==null){
                    chooseDialogUtil  =new TimeChooseDialogUtil(mContext);
                }
                chooseDialogUtil.showDialog(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        tvClData.setText(result);
                    }
                    @Override
                    public void cancel() {
                        chooseDialogUtil.dissDialog();
                    }
                });
                break;
            case R.id.tvOrder:
                if(TextUtils.isEmpty(bussisImg)){
                    showMessage("???????????????????????????");
                    return;
                }
                if(context.isEmptyUserInfo(userInfoDto)){
                    showMessage("??????????????????");
                    return;
                }
                if(isEmptyStr(edCompanyName)){
                    showMessage("?????????????????????");
                    return;
                }
                if(isEmptyStr(edtaxiPersonNum)){
                    showMessage("?????????????????????????????????");
                    return;
                }
                if(isEmptyStr(edMainPersonName)){
                    showMessage("???????????????????????????");
                    return;
                }
                if(isEmptyStr(edPhone)){
                    showMessage("???????????????????????????");
                    return;
                }
                if(isEmptyStr(edZcMoney)){
                    showMessage("?????????????????????");
                    return;
                }
                if(isEmptyStr(tvAdress)){
                    showMessage("???????????????");
                    return;
                }
                if(isEmptyStr(edAdressDelation)){
                    showMessage("?????????????????????");
                    return;
                }
                if(isEmptyStr(tvType)){
                    showMessage("?????????????????????");
                    return;
                }
                if(isEmptyStr(edJyFws)){
                    showMessage("???????????????????????????");
                    return;
                }
                if(isEmptyStr(edTaxtAuthor)){
                    showMessage("?????????????????????");
                    return;
                }
                if(isEmptyStr(tvZcTime)){
                    showMessage("?????????????????????");
                    return;
                }
                if(isEmptyStr(tvClData)){
                    showMessage("?????????????????????");
                    return;
                }
                TjSubmitDto dto=new TjSubmitDto();
                dto.setBussnessImg(bussisImg);
                dto.setId(id);
                dto.setCompanyName(edCompanyName.getText().toString());
                dto.setNsrSbNum(edtaxiPersonNum.getText().toString());
                dto.setMainPerson(edMainPersonName.getText().toString());
                dto.setPhone(edPhone.getText().toString());
                dto.setZcmoney(edZcMoney.getText().toString());
                dto.setAdress(areaDto.getId());
                dto.setRole(userInfoDto.getRole());
                dto.setRoleId(userInfoDto.getRoleId());
                dto.setAdressDelation(edAdressDelation.getText().toString());
                if(tvType.getText().toString().contains("?????????")){
                    dto.setType(0+"");
                }else {
                    dto.setType(1+"");
                }
                dto.setJyfw(edJyFws.getText().toString());
                dto.setSwjg(edTaxtAuthor.getText().toString());
                dto.setZctime(tvZcTime.getText().toString());
                dto.setCityId(areaDto.getId());
                dto.setTvclData(tvClData.getText().toString());

                person.submit(dto);


                break;

        }

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PIC && resultCode == RESULT_OK) {
            String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
            String filePath = FileUtil.getSaveFile(mContext, tempImage).getAbsolutePath();
            if (!TextUtils.isEmpty(filePath) && new File(filePath) != null) {
                if (CameraActivity.CONTENT_TYPE_GENERAL.equals(contentType)) {
                    ImageLoadUtil.displayLocalImage(mContext, new File(filePath), imgBusbess);
                    readBussiness(filePath);
                }
                Uploadpic(BaseActivity.compress(mContext, new File(filePath)));
            }
        } if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {//??????
            if (!context.isEmptyStr(tempImage)) {
                Uri uri = Uri.fromFile(new File(tempImage));
                final File file = BaseActivity.compress(mContext, new File(tempImage));
                ImageLoadUtil.displayImage(mContext, uri, imgBusbess);
                readBussiness(file.getPath());
                Uploadpic(file);
            }
        }else if(requestCode == 100 && resultCode == RESULT_OK){
            areaDto= (TaxiAreaDto) data.getSerializableExtra("data");
            if(areaDto!=null){
                tvAdress.setText(areaDto.getName());
            }
        }
    }
    private  void Uploadpic(File file){
        context.showLoadingDialog("???????????????....");
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), file);
        //files ????????????
        MultipartBody.Part part=
                MultipartBody.Part.createFormData("picture", file.getName(), requestBody);

        context.authApi.loadImg(part).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {
                context.dismissLoadingDialog();
                context.showMessage("????????????????????????");
                bussisImg=response.getUrl();
                if(isShow==false){
                    fadeIn(llContect);
                    isShow=true;
                    imageView.setImageResource(R.drawable.ico_taxi_top);
                }
            }
            @Override
            public void fail(String code, String message) {
                context.dismissLoadingDialog();
                context.showMessage("????????????????????????");
            }
        });
    }

    //??????????????????
    private void readBussiness(String path){
        RecognizeService.recBusinessLicense(mContext, path,
                new RecognizeService.ServiceListener() {
                    @Override
                    public void onResult(String result) {
                        Log.e("msg",result);
                        try {
                            JSONObject object=new JSONObject(result);
                            JSONObject words_result=object.getJSONObject("words_result");
                            JSONObject xinoyngDai=words_result.getJSONObject("??????????????????");
                            edtaxiPersonNum.setText(xinoyngDai.getString("words"));

                           // JSONObject companyobject=words_result.getJSONObject("????????????");
                           // edCompanyName.setText(companyobject.getString("words"));

                            JSONObject obTime=words_result.getJSONObject("????????????");

                            String time=obTime.getString("words");
                            if(!TextUtils.isEmpty(time)){
                                tvClData.setText(transTimehenggang(time,"yyyy-MM-dd"));
                            }
                            JSONObject fr=words_result.getJSONObject("??????");
                            edMainPersonName.setText(fr.getString("words"));
                            JSONObject zcmonty=words_result.getJSONObject("????????????");
                            edZcMoney.setText(zcmonty.getString("words"));

                            JSONObject jyfw=words_result.getJSONObject("????????????");
                            edJyFws.setText(jyfw.getString("words"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }

    private String id;
    @Override
    public void getTaxiDto(final TjSWDto dtos) {
        if(dtos!=null){

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    id=dtos.getId();
                    tvOrder.setText("??????");
                    bussisImg=dtos.getYyzzfjBos();
                    if(!TextUtils.isEmpty(dtos.getNsrsbh())){
                        edtaxiPersonNum.setEnabled(false);
                    }
                    edCompanyName.setEnabled(false);
                    ImageLoadUtil.displayImage(mContext, bussisImg, imgBusbess);
                    if(!TextUtils.isEmpty(dtos.getNsrmc())){
                        edCompanyName.setText(dtos.getNsrmc());
                        edCompanyName.setEnabled(false);
                    }
                    edtaxiPersonNum.setText(dtos.getNsrsbh());
                    edMainPersonName.setText(dtos.getFddbr());
                    edPhone.setText(dtos.getQyyddh());
                    edZcMoney.setText(dtos.getZczb());
                    areaDto= new TaxiAreaDto();
                    areaDto.setId(dtos.getSsdq());
                    areaDto.setName(dtos.getSsdqStr());
                    tvAdress.setText(areaDto.getName());
                    edAdressDelation.setText(dtos.getXxdz());
                    if(dtos.getNsrlx().equals("1")){
                        tvType.setText("???????????????");
                    }else {
                        tvType.setText("?????????????????????");
                    }

                    edJyFws.setText(dtos.getJyfw());
                    edTaxtAuthor.setText(dtos.getZgswjg());
                    tvZcTime.setText(dtos.getPtzcsj());
                    tvClData.setText(dtos.getSlrq());
                    if(isShow==false){
                        fadeIn(llContect);
                        isShow=true;
                        imageView.setImageResource(R.drawable.ico_taxi_top);
                    }
                }
            });

        }

    }

    @Override
    public Activity getActivity() {
        return this;
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
        if(TextUtils.isEmpty(str)||str.equals("????????????")){
            return;
        }
        context.showMessage(str);
    }

    @Override
    public void oncomplete() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showMessage("????????????");
                finish();
            }
        });


    }
}
