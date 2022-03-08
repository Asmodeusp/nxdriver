package com.saimawzc.freight.ui.my.setment.account.acountmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.base.TimeChooseListener;
import com.saimawzc.freight.dto.SearchValueDto;
import com.saimawzc.freight.dto.account.AuthorityDtoSerializ;
import com.saimawzc.freight.dto.account.ConsignmentCompanyDto;
import com.saimawzc.freight.ui.my.setment.account.ChooseAuthortityActivity;
import com.saimawzc.freight.ui.my.setment.account.ChooseConsignCompanyActivity;
import com.saimawzc.freight.weight.utils.TimeChooseDialogUtil;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;
import static android.app.Activity.RESULT_OK;

/***
 * 结算单查询
 * **/
public class AccountQueryFragment extends BaseFragment {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tvStartTime) TextView tvStartTime;
    @BindView(R.id.tvEndTime)TextView tvEndTime;
    @BindView(R.id.tvaccountcompany)TextView tvAccountcompany;//对账公司
    @BindView(R.id.tvtycompany)TextView tvTuoYunCompany;//托运公司
    @BindView(R.id.edtrantNo) EditText edtrantNo;
    @BindView(R.id.edWulIao)EditText edWuLiao;
    private TimeChooseDialogUtil timeChooseDialogUtil;
    ArrayList<SearchValueDto>list=new ArrayList<>();
    public  final int CHOOSE_COMPANY=101;
    public  final int CONSIGNcOMPANY=102;//托运公司
    @OnClick({R.id.btnsearch,R.id.tvStartTime,R.id.tvEndTime
    ,R.id.rlaccountcompany,R.id.rltycompany})
    public void  click(View view){
        switch (view.getId()){
            case R.id.btnsearch://搜索
                list.clear();
                Intent intent=new Intent();
                Bundle bundle=new Bundle();

                if(authorityDto!=null){
                    SearchValueDto dto=new SearchValueDto();
                    dto.setSearchName("companyId");
                    dto.setGetSearchValue(authorityDto.getId());
                    list.add(dto);
                }
                if(!TextUtils.isEmpty(tvStartTime.getText().toString())){
                    SearchValueDto dto=new SearchValueDto();
                    dto.setSearchName("startTime");
                    dto.setGetSearchValue(tvStartTime.getText().toString());
                    list.add(dto);

                }
                if(!TextUtils.isEmpty(tvEndTime.getText().toString())){
                    SearchValueDto dto=new SearchValueDto();
                    dto.setSearchName("endTime");
                    dto.setGetSearchValue(tvEndTime.getText().toString());
                    list.add(dto);

                }
                if(consignmentCompanyDto!=null){
                    SearchValueDto dto=new SearchValueDto();
                    dto.setSearchName("handCompanyId");
                    dto.setGetSearchValue(consignmentCompanyDto.getId());
                    list.add(dto);
                }
                if(!context.isEmptyStr(edtrantNo)){
                    SearchValueDto dto=new SearchValueDto();
                    dto.setSearchName("settleNo");
                    dto.setGetSearchValue(edtrantNo.getText().toString());
                    list.add(dto);
                }
                if(!context.isEmptyStr(edWuLiao)){
                    SearchValueDto dto=new SearchValueDto();
                    dto.setSearchName("materialsName");
                    dto.setGetSearchValue(edWuLiao.getText().toString());
                    list.add(dto);
                }

                bundle.putSerializable("list",list);
                intent.putExtras(bundle);
                context.setResult(RESULT_OK, intent);
                context.finish();
                break;
            case R.id.tvStartTime:
                if(timeChooseDialogUtil==null){
                timeChooseDialogUtil=new TimeChooseDialogUtil(mContext);
            }
                timeChooseDialogUtil.showDialogshowhour(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        tvStartTime.setText(result);
                    }
                    @Override
                    public void cancel() {
                        timeChooseDialogUtil.dissDialog();
                    }
                });
                break;
            case R.id.tvEndTime:
                if(timeChooseDialogUtil==null){
                    timeChooseDialogUtil=new TimeChooseDialogUtil(mContext);
                }
                timeChooseDialogUtil.showDialogshowhour(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        tvEndTime.setText(result);
                    }
                    @Override
                    public void cancel() {
                        timeChooseDialogUtil.dissDialog();
                    }
                });
                break;
            case R.id.rlaccountcompany:
                readyGoForResult(ChooseAuthortityActivity.class,CHOOSE_COMPANY);
                break;
            case R.id.rltycompany:
                readyGoForResult(ChooseConsignCompanyActivity.class,CONSIGNcOMPANY);
                break;

        }
    }




    @Override
    public int initContentView() {
        return R.layout.fragment_queryaccount;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"结算单查询");

    }

    @Override
    public void initData() {

    }

    AuthorityDtoSerializ authorityDto;//组织机构
    ConsignmentCompanyDto consignmentCompanyDto;//托运公司
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CHOOSE_COMPANY&& resultCode == RESULT_OK){
            authorityDto = (AuthorityDtoSerializ) data.getSerializableExtra("data");
            if(authorityDto!=null){
                tvAccountcompany.setText(authorityDto.getCompanyName());
            }
        }
        if(requestCode==CONSIGNcOMPANY&& resultCode == RESULT_OK){//托运公司
            consignmentCompanyDto = (ConsignmentCompanyDto) data.getSerializableExtra("data");
            if(consignmentCompanyDto!=null){
                tvTuoYunCompany.setText(consignmentCompanyDto.getCompanyName());
            }
        }

    }


}
