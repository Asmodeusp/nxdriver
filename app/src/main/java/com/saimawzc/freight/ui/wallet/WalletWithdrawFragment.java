package com.saimawzc.freight.ui.wallet;

import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.wallet.SonAccountDto;
import com.saimawzc.freight.presenter.wallet.WithDrawPresenter;
import com.saimawzc.freight.view.wallet.WithDrawView;
import com.saimawzc.freight.weight.BottomDialogUtil;
import com.saimawzc.freight.weight.LengthFilter;

import butterknife.BindView;
import butterknife.OnClick;

/***
 * 提现
 * ***/
public class WalletWithdrawFragment extends BaseFragment implements TextWatcher, WithDrawView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    private SonAccountDto accountDto;
    @BindView(R.id.tvBankName)TextView tvBankName;
    private SonAccountDto.bankList bankDto;
    @BindView(R.id.edwithdrawnum)EditText edWithDrawNum;
    private WithDrawPresenter presenter;
    @BindView(R.id.tvyue)TextView tvCanUseMoney;

    @Override
    public int initContentView() {
        return R.layout.fragment_withdraw;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"提现");
        presenter=new WithDrawPresenter(this,mContext);
        accountDto= (SonAccountDto) getArguments().getSerializable("data");
        edWithDrawNum.setFilters(new InputFilter[] {new LengthFilter(2)});
        if(accountDto!=null){
            tvCanUseMoney.setText("当前可提现余额"+accountDto.getUseBala()+"元");
            if(accountDto.getBankList()!=null&&accountDto.getBankList().size()>0){
                for(int i=0;i<accountDto.getBankList().size();i++){
                    if(accountDto.getBankList().get(i).getDefaultFlag()==1){
                        bankDto=accountDto.getBankList().get(i);
                        if(TextUtils.isEmpty(bankDto.getCardNo())){
                            tvBankName.setText(bankDto.getBankName());
                        }else {
                            tvBankName.setText(bankDto.getBankName()+"("+bankDto.getCardNo().substring(bankDto.getCardNo().length()-4)+")");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void initData() {
        edWithDrawNum.addTextChangedListener(this);
    }
    @OnClick({R.id.withdrawall,R.id.btnwithdraw})
    public void click(View view){
        switch (view.getId()){
            case R.id.withdrawall:
                if(accountDto!=null){
                    edWithDrawNum.setText(accountDto.getUseBala()+"");
                }
                break;
            case R.id.btnwithdraw:
                if(context.isEmptyStr(edWithDrawNum)){
                    context.showMessage("请输入提现金额");
                    return;
                }
                if(accountDto==null){
                    return;
                }
                if(accountDto!=null){
                    if(!edWithDrawNum.getText().toString().equals(".")){
                        if(Double.parseDouble(edWithDrawNum.getText().toString())>accountDto.getUseBala()){
                            context.showMessage("提现金额超出可用余额");
                            return;
                        }
                    }
                }
                withDraw();
                break;
        }
    }
    TextView textGetCode;
     BottomDialogUtil bottomDialogUtil;
    private void withDraw(){
       bottomDialogUtil = new BottomDialogUtil.Builder()
                .setContext(context) //设置 context
                .setContentView(R.layout.dialog_withdraw) //设置布局文件
                .setOutSideCancel(false) //设置点击外部取消
                .builder()
                .show();
         TextView tvMoney= (TextView) bottomDialogUtil.getItemView(R.id.tvwithDrawMonty);
         final EditText edCode= (EditText) bottomDialogUtil.getItemView(R.id.idnum);
         textGetCode = (TextView) bottomDialogUtil.getItemView(R.id.getcode);
         final TextView tvphone= (TextView) bottomDialogUtil.getItemView(R.id.tvphone);
         tvMoney.setText("￥"+edWithDrawNum.getText().toString());
         tvphone.setText(bankDto.getMobile());
         /**
          * 取消
          * **/
        bottomDialogUtil.setOnClickListener(R.id.dissmiss, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              bottomDialogUtil.dismiss();
            }
        });
        /***
         * 获取验证码
         * */
        bottomDialogUtil.setOnClickListener(R.id.getcode, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context.isEmptyStr(tvphone)){
                    context.showMessage("预留手机为空");
                    return;
                }
                presenter.getCode();
               // bottomDialogUtil.dismiss();
            }
        });
        /***
         * 确认
         * */
        bottomDialogUtil.setOnClickListener(R.id.tvOrder, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context.isEmptyStr(edCode)){
                    context.showMessage("请输入验证码");
                    return;
                }
                if(context.isEmptyStr(edWithDrawNum)){
                    context.showMessage("请输入提现金额");
                }
                presenter.withdraw(edWithDrawNum.getText().toString(),edCode.getText().toString());
            }
        });
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(accountDto!=null){
            if(!TextUtils.isEmpty(edWithDrawNum.getText().toString())){
                if(!edWithDrawNum.getText().toString().equals(".")){
                    if(Double.parseDouble(edWithDrawNum.getText().toString())>accountDto.getUseBala()){
                        context.showMessage("提现金额超出可用余额");
                    }
                }
            }
        }
    }

    @Override
    public void getCode(String code) {

    }

    @Override
    public void oncomplete(int status) {
        if(status==1){
            mCountDownTimer.start();
        }else if(status==2){
            edWithDrawNum.setText("");
            context.showMessage("提现成功");
            if(bottomDialogUtil!=null){
                bottomDialogUtil.dismiss();
                if(mCountDownTimer!=null){
                    mCountDownTimer.cancel();
                }
            }
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

    }
    private CountDownTimer mCountDownTimer = new CountDownTimer(60 * 1000, 1000) {//一分钟，间隔一秒
        @Override
        public void onTick(long millisUntilFinished) {
            if(textGetCode!=null){
                textGetCode.setEnabled(false);
                textGetCode.setText(millisUntilFinished / 1000 + "s");
            }
        }
        @Override
        public void onFinish() {
            if(textGetCode!=null){
                textGetCode.setText("获取验证码");
                textGetCode.setEnabled(true);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mCountDownTimer!=null){
            mCountDownTimer.cancel();
        }
    }
}
