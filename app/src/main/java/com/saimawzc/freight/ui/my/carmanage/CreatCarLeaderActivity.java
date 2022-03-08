package com.saimawzc.freight.ui.my.carmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.pic.PicDto;
import com.saimawzc.freight.dto.wallet.BindBankDto;
import com.saimawzc.freight.dto.wallet.MsBankDto;
import com.saimawzc.freight.presenter.mine.carleader.CreatTeamPresenter;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.view.mine.carleader.CreatTeamView;
import com.saimawzc.freight.weight.utils.FileUtil;
import com.saimawzc.freight.weight.utils.IdcardValidator;
import com.saimawzc.freight.weight.utils.WheelDialog;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.WheelListener;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;
import com.saimawzc.freight.weight.utils.ocr.RecognizeService;

import org.greenrobot.eventbus.EventBus;
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

public class CreatCarLeaderActivity extends BaseActivity
        implements CreatTeamView, TextWatcher {


     private CreatTeamPresenter presenter;
    @BindView(R.id.edTeamName)EditText edTeamName;
    @BindView(R.id.imgPositive)ImageView imgPositive;
    @BindView(R.id.imgback)ImageView imgBack;
    @BindView(R.id.tvresister_type)
    TextView tvResisterType;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.teamsfz)
    RelativeLayout rlTeamIdCard;
    @BindView(R.id.viewCard)
    View viewLine;


    /***
     * 个人账户
     * **/
    @BindView(R.id.llperson)
    LinearLayout llPerson;
    @BindView(R.id.ed_idcard)
    EditText edPersonIdCard;
    @BindView(R.id.tvsex)TextView tvPersonSex;
    @BindView(R.id.edardnum)EditText edPersonCarNum;
    @BindView(R.id.tvopenbank)TextView tvPersonOpenBank;
    @BindView(R.id.edphone)EditText edPersonPhone;
    @BindView(R.id.deleteperosncard)
    ImageView deletePersonCard;
    @BindView(R.id.deletepersonphone)ImageView deletePersonPhne;
    @BindView(R.id.deleteperosoname)ImageView deleteperosoname;
    @BindView(R.id.edrealName)EditText edRealName;
    @BindView(R.id.edTeamCarId)EditText edTeamCarId;

    private String bankName;
    /****
     * 机构账户
     * **/
    @BindView(R.id.llauthor) LinearLayout llAuthor;
    @BindView(R.id.ed_authorcard)EditText edAuthorCompanyNum;
    @BindView(R.id.edauthorName)EditText edauthorName;
    @BindView(R.id.edfrname)EditText edAuthorFrName;
    @BindView(R.id.edfrid_card)EditText edAuthorFrIdCard;
    @BindView(R.id.edjbrname)EditText edAuthorJbrName;
    @BindView(R.id.edjbridcard)EditText edAuthorJbrIdCard;
    @BindView(R.id.tvauthor_cardNum)EditText edAuthorBankCardNum;
    @BindView(R.id.tvauthor_openbank)TextView tvAuthorOpenBank;
    @BindView(R.id.edauthor_phone)EditText edAuthorPhone;
    @BindView(R.id.deleteauthorname)ImageView delAuthorName;
    @BindView(R.id.deleteauthorcard)ImageView delAuthorCarNum;
    @BindView(R.id.deleteFrCard)ImageView delAuthorFrCard;
    @BindView(R.id.deleteauthorphone)ImageView delAuthorPhone;
    @BindView(R.id.deletejbrcard)ImageView delAuthorJbrCardNum;

    @BindView(R.id.edTeamDzName)EditText edTeanQuenName;
    @BindView(R.id.teamname)RelativeLayout rlTeanName;
    @BindView(R.id.viewCard1)View viewLine2;


    private WheelDialog wheelDialog;
    private List<String> stringList=new ArrayList<>();
    private String type="";
    private String isCarBin="";

    private String strigImgFront;
    private String stringImgBack;
    private int chooseType;

    @Override
    protected int getViewId() {
        return R.layout.activity_creat_leader;
    }
    @Override
    protected void init() {
        setToolbar(toolbar,"创建车队长");
        mContext=this;
        initpermissionChecker();
        wheelDialog=new WheelDialog(mContext);
        presenter=new CreatTeamPresenter(this,this);
        edPersonIdCard.addTextChangedListener(this);
        edPersonPhone.addTextChangedListener(this);
        edRealName.addTextChangedListener(this);
        edAuthorCompanyNum.addTextChangedListener(this);
        edAuthorCompanyNum.addTextChangedListener(this);
        edAuthorFrIdCard.addTextChangedListener(this);
        edAuthorJbrIdCard.addTextChangedListener(this);
        edAuthorPhone.addTextChangedListener(this);
        edauthorName.addTextChangedListener(this);


    }
    @Override
    protected void initListener() {
        edPersonCarNum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    Log.e("msg","已经输入完成");
                    presenter.cardBin(edPersonCarNum.getText().toString());
                }
                return false;
            }
        });
        edPersonCarNum.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    if(!TextUtils.isEmpty(edPersonCarNum.getText().toString())){
                        if(edPersonCarNum.getText().toString().length()>=16){
                            presenter.cardBin(edPersonCarNum.getText().toString());
                        }
                    }
                }
            }
        });
    }
    @Override
    protected void onGetBundle(Bundle bundle) {
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(!context.isEmptyStr(edPersonIdCard)){
            deletePersonCard.setVisibility(View.VISIBLE);
        }else {
            deletePersonCard.setVisibility(View.GONE);
        }
        if(!context.isEmptyStr(edPersonPhone)){
            deletePersonPhne.setVisibility(View.VISIBLE);
        }else {
            deletePersonPhne.setVisibility(View.GONE);
        }
        if(!context.isEmptyStr(edRealName)){
            deleteperosoname.setVisibility(View.VISIBLE);
        }else {
            deleteperosoname.setVisibility(View.GONE);
        }
        if(!context.isEmptyStr(edauthorName)){
            delAuthorName.setVisibility(View.VISIBLE);
        }else {
            delAuthorName.setVisibility(View.GONE);
        }
        if(!context.isEmptyStr(edAuthorCompanyNum)){
            delAuthorCarNum.setVisibility(View.VISIBLE);
        }else {
            delAuthorCarNum.setVisibility(View.GONE);
        }
        if(!context.isEmptyStr(edAuthorFrIdCard)){
            delAuthorFrCard.setVisibility(View.VISIBLE);
        }else {
            delAuthorFrCard.setVisibility(View.GONE);
        }
        if(!context.isEmptyStr(edAuthorJbrIdCard)){
            delAuthorJbrCardNum.setVisibility(View.VISIBLE);
        }else {
            delAuthorJbrCardNum.setVisibility(View.GONE);
        }
        if(!context.isEmptyStr(edAuthorPhone)){
            delAuthorPhone.setVisibility(View.VISIBLE);
        }else {
            delAuthorPhone.setVisibility(View.GONE);
        }
    }

    @Override
    public void getBigBankList(List<MsBankDto> dtos) {
        if(dtos==null||dtos.size()<=0){
            return;
        }
        stringList.clear();
        for(int i=0;i<dtos.size();i++){
            stringList.add(dtos.get(i).getOpenBranch());
        }
        WheelDialog  wheelDialog=new WheelDialog(mContext,dtos,stringList);
        wheelDialog.Show(new WheelListener() {
            @Override
            public void callback(String name, String id) {
                //tvBigBankNum.setText(name);
            }
        });
    }

    @Override
    public void getCarBin(MsBankDto carBinDto) {
        msBankDto=carBinDto;
        bankName=carBinDto.getBankName();
        if(!TextUtils.isEmpty(isCarBin)){
            Bundle bundle=new Bundle();
            bundle.putString("from","choosebigbank");
            bundle.putString("bankname",bankName);
            readyGoForResult(PersonCenterActivity.class,2000,bundle);
            isCarBin="";
        }
    }

    @Override
    public void showLoading() {
        showLoadingDialog();

    }

    @Override
    public void dissLoading() {
        dismissLoadingDialog();

    }
    @Override
    public void Toast(String str) {
        showMessage(str);
    }

    @Override
    public void oncomplete() {
        EventBus.getDefault().post(Constants.reshCarLeaderList);
        finish();

    }

    @OnClick({R.id.rl_user_type,R.id.rl_user_sex,R.id.imgchoosePerson,R.id.rlpersonbank,R.id.deleteperosoname
            ,R.id.deleteperosncard,R.id.deletepersonphone,R.id.deleteauthorcard,R.id.deleteFrCard,R.id.deletejbrcard,R.id.deleteauthorname
            ,R.id.rlauthoropenbank,R.id.deleteauthorphone,
            R.id.tvSubmit,R.id.imgchooseAuthorBank,R.id.imgPositive,R.id.imgback})
    public void click(final View view){
        Bundle bundle;
        switch (view.getId()){
            case R.id.rl_user_type://注册类型
                stringList.clear();
                stringList.add("个人");
                stringList.add("机构");
                if(wheelDialog==null){
                    wheelDialog=new WheelDialog(mContext);
                }
                wheelDialog.Show(new WheelListener() {
                    @Override
                    public void callback(String name, String id) {
                        tvResisterType.setText(name);
                        if(name.equals("个人")){
                            llPerson.setVisibility(View.VISIBLE);
                            llAuthor.setVisibility(View.GONE);
                            rlTeamIdCard.setVisibility(View.GONE);
                            viewLine.setVisibility(View.GONE);
                            rlTeanName.setVisibility(View.GONE);
                            viewLine2.setVisibility(View.GONE);
                        }else {
                            llPerson.setVisibility(View.GONE);
                            llAuthor.setVisibility(View.VISIBLE);
                            rlTeamIdCard.setVisibility(View.VISIBLE);
                            viewLine.setVisibility(View.VISIBLE);
                            rlTeanName.setVisibility(View.VISIBLE);
                            viewLine2.setVisibility(View.VISIBLE);
                        }
                    }
                },stringList);
                break;
            case R.id.imgPositive:
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("未获取到相应权限,请在设置中开启权限");
                    permissionChecker.requestPermissions();
                    return;
                }
                initCamera(CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                break;
            case R.id.imgback:
                if (permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)) {
                    context.showMessage("未获取到相应权限,请在设置中开启权限");
                    permissionChecker.requestPermissions();
                    return;
                }
                initCamera(CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                break;
            case R.id.rl_user_sex://性别
                stringList.clear();
                stringList.add("男");
                stringList.add("女");
                if(wheelDialog==null){
                    wheelDialog=new WheelDialog(mContext);
                }
                wheelDialog.Show(new WheelListener() {
                    @Override
                    public void callback(String name, String id) {
                        tvPersonSex.setText(name);
                    }
                },stringList);
                break;
            case R.id.imgchoosePerson://个人银行卡号
                type="person";
                initCamera(CameraActivity.CONTENT_TYPE_BANK_CARD);
                break;
            case R.id.deleteperosncard:
                edPersonIdCard.setText("");
                break;
            case R.id.deletepersonphone:
                edPersonPhone.setText("");
                break;
            case R.id.deleteperosoname:
                edRealName.setText("");
                break;
            case R.id.rlpersonbank://大额行号
                if(TextUtils.isEmpty(bankName)){
                    if(!TextUtils.isEmpty(edPersonCarNum.getText().toString())){
                        isCarBin="kabin";
                        presenter.cardBin(edPersonCarNum.getText().toString());
                    }else {
                        context.showMessage("银行卡号不能为空");
                    }
                    return;
                }
                bundle=new Bundle();
                bundle.putString("from","choosebigbank");
                bundle.putString("bankname",bankName);
                readyGoForResult(PersonCenterActivity.class,2000,bundle);
                break;
            /***
             * 机构
             * **/
            case R.id.deleteauthorcard:
                edAuthorCompanyNum.setText("");
                break;
            case R.id.deleteFrCard:
                edAuthorFrIdCard.setText("");
                break;
            case R.id.deletejbrcard:
                edAuthorJbrIdCard.setText("");
                break;
            case R.id.rlauthoropenbank:
                bundle=new Bundle();
                bundle.putString("from","choosebank");
                readyGoForResult(PersonCenterActivity.class,2001,bundle);
                break;
            case R.id.deleteauthorphone:
                edAuthorPhone.setText("");
                break;
            case R.id.deleteauthorname:
                edauthorName.setText("");
                break;
            case R.id.imgchooseAuthorBank://机构识别银行卡
                type="author";
                initCamera(CameraActivity.CONTENT_TYPE_BANK_CARD);
                break;
            case R.id.tvSubmit://提交

                if(isEmptyStr(edTeamName)){
                    context.showMessage("车队长名称不能为空");
                    return;
                }
                if(TextUtils.isEmpty(strigImgFront)){
                    context.showMessage("身份证正面不能为空");
                    return;
                }
                if(TextUtils.isEmpty(stringImgBack)){
                    context.showMessage("身份证反面不能为空");
                    return;
                }
                IdcardValidator  idcardValidator=new IdcardValidator();
                if(tvResisterType.getText().toString().equals("个人")){
                    if(context.isEmptyStr(edRealName)){
                        context.showMessage("请输入真实姓名");
                        return;
                    }
                    if(context.isEmptyStr(edPersonIdCard)){
                        context.showMessage("请输入身份证号");
                        return;
                    }
                    if(context.isEmptyStr(edPersonCarNum)){
                        context.showMessage("请输入银行卡号");
                        return;
                    }

                    if(!idcardValidator.isIdcard(edPersonIdCard.getText().toString())){
                        context.showMessage("身份证信息不合法");
                        return;
                    }


                    if(context.isEmptyStr(tvPersonOpenBank)){
                        context.showMessage("请选择开户银行");
                        return;
                    }
                    if(context.isEmptyStr(edPersonPhone)){
                        context.showMessage("请输入预留手机号");
                        return;
                    }
                    if(msBankDto==null){
                        context.showMessage("请选择开户银行");
                        return;
                    }
                    if(bigBankDto==null){
                        context.showMessage("请选择开户银行");
                        return;
                    }

                    BindBankDto dto=new BindBankDto();
                    dto.setClientType(1);
                    dto.setClientName(edRealName.getText().toString());
                    dto.setSfzzm(strigImgFront);
                    dto.setSfzfm(stringImgBack);
                    dto.setUserName(edTeamName.getText().toString());
                    dto.setIdCode(edPersonIdCard.getText().toString());
                    dto.setMobile(edPersonPhone.getText().toString());
                    dto.setBankAcc(edPersonCarNum.getText().toString());
                    dto.setName(edRealName.getText().toString());
                    dto.setBankNo(msBankDto.getBankNo());
                    dto.setOpenBranch(bigBankDto.getOpenBranch());

                    if(tvPersonSex.getText().toString().equals("男")){
                        dto.setSex(1+"");
                    }else {
                        dto.setSex(0+"");
                    }
                    presenter.bind(dto);
                }else if(tvResisterType.getText().toString().equals("机构")){

                    if(isEmptyStr(edTeanQuenName)){
                        context.showMessage("请输入车队长真实姓名");
                        return;
                    }
                    if(isEmptyStr(edTeamCarId)){
                        context.showMessage("请输入车队长身份证");
                        return;
                    }

                    if(context.isEmptyStr(edauthorName)){
                        context.showMessage("请输入机构名称");
                        return;
                    }
                    if(context.isEmptyStr(edAuthorCompanyNum)){
                        context.showMessage("请输入机构证件号码");
                        return;
                    }

                    if(context.isEmptyStr(edAuthorFrName)){
                        context.showMessage("请输入法人姓名");
                        return;
                    }
                    if(context.isEmptyStr(edAuthorFrIdCard)){
                        context.showMessage("请输入法人身份证");
                        return;
                    }
                    if(!idcardValidator.isIdcard(edAuthorFrIdCard.getText().toString())){
                        context.showMessage("法人身份证信息不合法");
                        return;
                    }
                    if(context.isEmptyStr(edAuthorJbrName)){
                        context.showMessage("请输入经办人姓名");
                        return;
                    }
                    if(context.isEmptyStr(edAuthorJbrIdCard)){
                        context.showMessage("请输入经办人身份证号");
                        return;
                    }
                    if(!idcardValidator.isIdcard(edAuthorJbrIdCard.getText().toString())){
                        context.showMessage("经办人身份证 信息不合法");
                        return;
                    }
                    if(context.isEmptyStr(edAuthorBankCardNum)){
                        context.showMessage("请输入银行卡号");
                        return;
                    }
                    if(context.isEmptyStr(tvAuthorOpenBank)){
                        context.showMessage("请选择开户行");
                        return;
                    }
                    if(context.isEmptyStr(edAuthorPhone)){
                        context.showMessage("请输入预留电话");
                        return;
                    }
                    BindBankDto dto=new BindBankDto();
                    dto.setClientType(0);
                    dto.setSfzzm(strigImgFront);
                    dto.setSfzfm(stringImgBack);
                    dto.setUserName(edTeamName.getText().toString());
                    dto.setYyzzbh(edAuthorCompanyNum.getText().toString());

                    dto.setClientName(edauthorName.getText().toString());

                    dto.setIdCode(edTeamCarId.getText().toString());
                    dto.setName(edTeanQuenName.getText().toString());
                    dto.setMobile(edAuthorPhone.getText().toString());
                    dto.setBankAcc(edAuthorBankCardNum.getText().toString());
                    dto.setBankNo(msBankDto.getBankNo());
                    dto.setOpenBranch(bigBankDto.getOpenBranch());
                    if(tvPersonSex.getText().toString().equals("男")){
                        dto.setSex(1+"");
                    }else {
                        dto.setSex(0+"");
                    }
                    dto.setReprName(edAuthorFrName.getText().toString());
                    dto.setReprIdCode(edAuthorFrIdCard.getText().toString());
                    dto.setActorName(edAuthorJbrName.getText().toString());
                    dto.setActorIdCode(edAuthorJbrIdCard.getText().toString());
                    presenter.bind(dto);
                }
                break;
        }
    }

    private MsBankDto msBankDto;//机构
    private MsBankDto bigBankDto;//大额行号
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_PIC && resultCode == RESULT_OK){
            String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
            String filePath = FileUtil.getSaveFile(mContext,tempImage).getAbsolutePath();
            if(!TextUtils.isEmpty(filePath)&&new File(filePath)!=null){
                File file=BaseActivity.compress(mContext,new File(filePath));
                if(CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)){
                    chooseType=1;

                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);

                    ImageLoadUtil.displayLocalImage(mContext, new File(filePath), imgPositive);
                    Uploadpic(file);
                }else if(CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)){
                    chooseType=2;
                    ImageLoadUtil.displayLocalImage(mContext, new File(filePath), imgBack);
                    Uploadpic(file);
                }else if(CameraActivity.CONTENT_TYPE_BANK_CARD.equals(contentType)){
                    RecognizeService.recBankCard(mContext.getApplicationContext(), filePath, new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            Log.e("msg",result);
                            try {
                                JSONObject jsonObject=new JSONObject(result);
                                JSONObject res=new JSONObject(jsonObject.getString("result"));
                                if(type.equals("person")){
                                    edPersonCarNum.setText(res.getString("bank_card_number"));
                                    presenter.cardBin(edPersonCarNum.getText().toString());
                                }else {
                                    edAuthorBankCardNum.setText(res.getString("bank_card_number"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }else if(requestCode==2000 && resultCode == RESULT_OK){
            bigBankDto= (MsBankDto) data.getSerializableExtra("data");
            if(data!=null){
                tvPersonOpenBank.setText(bigBankDto.getBankName());
            }
        } else if(requestCode==2001 && resultCode == RESULT_OK){
            bigBankDto= (MsBankDto) data.getSerializableExtra("data");
            msBankDto=(MsBankDto) data.getSerializableExtra("msbank");
            if(data!=null){
                tvAuthorOpenBank.setText(bigBankDto.getBankName());
            }
        }
    }
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
                    Log.e("msg","识别身份证"+result.toString());

                    if(result.getIdNumber()==null){
                        context.showMessage("未识别到身份证");
                        return;
                    }
                    if(result.getName()==null){
                        context.showMessage("未识别到姓名");
                        return;
                    }
                    Log.e("msg","识别"+result.getName().toString());
                    if(tvResisterType.getText().toString().equals("个人")){
                        if(!TextUtils.isEmpty(result.getIdNumber().toString())){
                            edPersonIdCard.setText(result.getIdNumber().toString());
                        }
                        if(!TextUtils.isEmpty(result.getName().toString())){
                            edRealName.setText(result.getName().toString());
                        }
                    }else {
                        if(!TextUtils.isEmpty(result.getIdNumber().toString())){
                            edTeamCarId.setText(result.getIdNumber().toString());
                        }
                        if(!TextUtils.isEmpty(result.getName().toString())){
                            edTeanQuenName.setText(result.getName().toString());
                        }
                    }


                }
            }
            @Override
            public void onError(OCRError error) {
                context.showMessage( error.getMessage());
            }
        });
    }

    private  void Uploadpic(File file){
        context.showLoadingDialog("图片上传中...");
        File temp=BaseActivity.compress(mContext,file);
        final RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), temp);
        //files 上传参数
        MultipartBody.Part part=
                MultipartBody.Part.createFormData("picture", temp.getName(), requestBody);
        context.authApi.loadImg(part).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {
                context.dismissLoadingDialog();
                if(chooseType==1){
                    showMessage("身份证正面上传成功");
                    strigImgFront=response.getUrl();
                }else if(chooseType==2){
                    showMessage("身份证反面上传成功");
                    stringImgBack=response.getUrl();
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
