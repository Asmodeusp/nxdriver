package com.saimawzc.freight.ui.wallet;

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
import android.widget.TextView;

import com.baidu.ocr.ui.camera.CameraActivity;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.wallet.BindBankDto;
import com.saimawzc.freight.dto.wallet.MsBankDto;
import com.saimawzc.freight.presenter.wallet.BindBankPresenter;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.view.wallet.BindBankView;
import com.saimawzc.freight.weight.utils.FileUtil;
import com.saimawzc.freight.weight.utils.WheelDialog;
import com.saimawzc.freight.weight.utils.listen.WheelListener;
import com.saimawzc.freight.weight.utils.ocr.RecognizeService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.saimawzc.freight.ui.my.identification.DriverCarrierFragment.REQUEST_CODE_PIC;

public class WalletSignFragment extends BaseFragment implements BindBankView, TextWatcher {

    private BindBankPresenter presenter;

    @BindView(R.id.tvresister_type) TextView tvResisterType;
    @BindView(R.id.toolbar) Toolbar toolbar;
    /***
     * 个人账户
     * **/
    @BindView(R.id.llperson)LinearLayout llPerson;
    @BindView(R.id.ed_idcard) EditText edPersonIdCard;
    @BindView(R.id.tvsex)TextView tvPersonSex;
    @BindView(R.id.edardnum)EditText edPersonCarNum;
    @BindView(R.id.tvopenbank)TextView tvPersonOpenBank;
    @BindView(R.id.edphone)EditText edPersonPhone;
    @BindView(R.id.deleteperosncard) ImageView deletePersonCard;
    @BindView(R.id.deletepersonphone)ImageView deletePersonPhne;
    @BindView(R.id.deleteperosoname)ImageView deleteperosoname;
    @BindView(R.id.edrealName)EditText edRealName;
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

    private WheelDialog wheelDialog;
    private List<String>stringList=new ArrayList<>();
    private String type="";
    private String isCarBin="";


    @OnClick({R.id.rl_user_type,R.id.rl_user_sex,R.id.imgchoosePerson,R.id.rlpersonbank,R.id.deleteperosoname
    ,R.id.deleteperosncard,R.id.deletepersonphone,R.id.deleteauthorcard,R.id.deleteFrCard,R.id.deletejbrcard,R.id.deleteauthorname
    ,R.id.rlauthoropenbank,R.id.deleteauthorphone,R.id.tvSubmit,R.id.imgchooseAuthorBank})
    public void click(View view){
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
                        }else {
                            llPerson.setVisibility(View.GONE);
                            llAuthor.setVisibility(View.VISIBLE);
                        }
                    }
                },stringList);
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
                    dto.setIdCode(edPersonIdCard.getText().toString());
                    dto.setMobile(edPersonPhone.getText().toString());
                    dto.setBankNo(msBankDto.getBankNo());
                    dto.setBankAcc(edPersonCarNum.getText().toString());
                    dto.setOpenBranch(bigBankDto.getOpenBranch());
                    if(tvPersonSex.getText().toString().equals("男")){
                        dto.setSex(1+"");
                    }else {
                        dto.setSex(0+"");
                    }
                    presenter.bind(dto);
                }else if(tvResisterType.getText().toString().equals("机构")){
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
                    if(context.isEmptyStr(edAuthorJbrName)){
                        context.showMessage("请输入经办人姓名");
                        return;
                    }
                    if(context.isEmptyStr(edAuthorJbrIdCard)){
                        context.showMessage("请输入经办人身份证号");
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
                    if(context.isEmptyStr(tvAuthorOpenBank)){
                        context.showMessage("请输入预留电话");
                        return;
                    }
                    BindBankDto dto=new BindBankDto();
                    dto.setClientType(0);
                    dto.setClientName(edauthorName.getText().toString());
                    dto.setIdCode(edAuthorCompanyNum.getText().toString());
                    dto.setMobile(edAuthorPhone.getText().toString());
                    dto.setBankNo(msBankDto.getBankNo());
                    dto.setBankAcc(edAuthorBankCardNum.getText().toString());
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

    @Override
    public int initContentView() {
        return R.layout.fragment_walletsign;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"银行卡签约");
        wheelDialog=new WheelDialog(mContext);
        presenter=new BindBankPresenter(this,mContext);
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
    public void initData() {
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
        readyGo(WalletActivity.class);
        context.finish();
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
    private MsBankDto msBankDto;//机构
    private MsBankDto bigBankDto;//大额行号
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_PIC && resultCode == RESULT_OK){
            String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
            String filePath = FileUtil.getSaveFile(mContext,tempImage).getAbsolutePath();
            if(!TextUtils.isEmpty(filePath)&&new File(filePath)!=null){
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
}
