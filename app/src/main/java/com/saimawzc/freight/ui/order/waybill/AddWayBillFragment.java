package com.saimawzc.freight.ui.order.waybill;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.order.OrderAddWayBillAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.order.AddWayBillDto;
import com.saimawzc.freight.dto.order.bill.MyPlanOrderDto;
import com.saimawzc.freight.presenter.order.waybill.AddWayBillPresenter;
import com.saimawzc.freight.view.order.AddwaybillView;
import com.saimawzc.freight.weight.BottomDialogUtil;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/***
 * 新增预运单
 * **/
public class AddWayBillFragment extends BaseFragment implements AddwaybillView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.cy) RecyclerView rv;
    @BindView(R.id.tvYuliang)TextView tvYuliang;
    private OrderAddWayBillAdapter adapter;
    private List<AddWayBillDto>mDatas=new ArrayList<>();
    private AddWayBillPresenter presenter;
    MyPlanOrderDto.planOrderData data;

    @OnClick({R.id.tvAdd,R.id.tvOrder})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvAdd:
                if(data==null){
                    return;
                }
                final BottomDialogUtil bottomDialogUtil = new BottomDialogUtil.Builder()
                        .setContext(context) //设置 context
                        .setContentView(R.layout.dialog_addwaybill) //设置布局文件
                        .setOutSideCancel(true) //设置点击外部取消
                        .builder()
                        .show();

                bottomDialogUtil.setOnClickListener(R.id.btnOrder, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      EditText editText= (EditText) bottomDialogUtil.getItemView(R.id.edtrantnum);
                      if(TextUtils.isEmpty(editText.getText().toString())){
                          context.showMessage("请输入分配量");
                          return;
                      }
                        AddWayBillDto dto=new AddWayBillDto();
                        dto.setId("");
                        dto.setNum(editText.getText().toString());
                        mDatas.add(dto);
                        adapter.notifyDataSetChanged();
                        bottomDialogUtil.dismiss();
                    }
                });
                break;
            case R.id.tvOrder:
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                if(data==null){
                    return;
                }
                if(mDatas.size()<=0){
                    context.showMessage("请分配货物");
                    return;
                }
                if(Hawk.get(PreferenceKey.LOGIN_TYPE,0)==2){//承运商
                    presenter.addWayBill(data.getId()+"",mDatas);
                }else {
                    presenter.addsjWayBill(data.getId()+"",mDatas);
                }

                break;
        }
    }
    @Override
    public int initContentView() {
        return R.layout.fagment_addwaybill;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        data= (MyPlanOrderDto.planOrderData) getArguments().getSerializable("data");
        context.setToolbar(toolbar,"生成小单");
        if(data!=null){
            adapter=new OrderAddWayBillAdapter(mDatas,mContext,data.getId());
            layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            rv.setLayoutManager(layoutManager);
            rv.setAdapter(adapter);

            if(data.getWeightUnit()==1){
                tvYuliang.setText("余量："+data.getOverWeight()+"吨");
            }else {
                tvYuliang.setText("余量："+data.getOverWeight()+"方");
            }
        }
        presenter=new AddWayBillPresenter(this,mContext);

    }
    @Override
    public void initData() {

    }



    @Override
    public void showLoading() {
        context.dismissLoadingDialog();
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
        if(Hawk.get(PreferenceKey.LOGIN_TYPE,0)==2){
            EventBus.getDefault().post(Constants.reshYunDn);
            context.finish();
        }else {
            context.finish();
        }
    }
}
