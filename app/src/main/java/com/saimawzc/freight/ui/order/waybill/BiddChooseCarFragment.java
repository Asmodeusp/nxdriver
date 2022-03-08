package com.saimawzc.freight.ui.order.waybill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.order.CarListAdpater;
import com.saimawzc.freight.adapter.order.CarModelAdpater;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.order.CarInfolDto;
import com.saimawzc.freight.dto.order.CarModelDto;
import com.saimawzc.freight.presenter.order.SendCarPresenter;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.view.order.SendCarView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.LoadMoreListener;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/****
 * 派车  派船
 * */
public class BiddChooseCarFragment extends BaseFragment implements SendCarView {

    @BindView(R.id.edsearch) ClearTextEditText edSearch;
    @BindView(R.id.llSearch) LinearLayout llSearch;
    @BindView(R.id.tvSearch) TextView tvSearch;
    private List<CarInfolDto.carInfoData>carInfoData=new ArrayList<>();
    @BindView(R.id.rvCar)RecyclerView rvCar;
    public LinearLayoutManager carlistlayoutManager;
    private CarListAdpater carListadapter;
    private int trantType=1;
    private SendCarPresenter presenter;


    private int page=1;
    private LoadMoreListener loadMoreListener;
    private String currentCarTypeId;
    private int clickPosition=0;


    @OnClick({R.id.tvCannel,R.id.llSearch})
    public void click(View view){
        if(!RepeatClickUtil.isFastClick()){
            context.showMessage("您操作太频繁，请稍后再试");
            return;
        }
        switch (view.getId()){
            case R.id.tvCannel:
                context.finish();
                break;
            case R.id.llSearch:
                if(context.isEmptyStr(edSearch)){
                    context.showMessage("请输入货主名");
                    return;
                }
                page=1;
                carInfoData.clear();
                if(Hawk.get(PreferenceKey.LOGIN_TYPE,2)==2){
                    presenter.getCarInfo(page,currentCarTypeId,edSearch.getText().toString(),trantType,"");
                }else {
                    presenter.getsjCarInfo(page,currentCarTypeId,edSearch.getText().toString(),trantType,"");
                }
                break;

        }
    }
    @Override
    public int initContentView() {
        return R.layout.fragment_choosecar;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        currentCarTypeId=getArguments().getString("cartypeId");
        presenter=new SendCarPresenter(this,mContext);
        carlistlayoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        carListadapter=new CarListAdpater(carInfoData,mContext);
        rvCar.setLayoutManager(carlistlayoutManager);
        rvCar.setAdapter(carListadapter);
        loadMoreListener=new LoadMoreListener(carlistlayoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                if(Hawk.get(PreferenceKey.LOGIN_TYPE,2)==2){
                    presenter.getCarInfo(page,currentCarTypeId,edSearch.getText().toString(),trantType,"");
                }else {
                    presenter.getsjCarInfo(page,currentCarTypeId,edSearch.getText().toString(),trantType,"");
                }
            }
        };
        rvCar.setOnScrollListener(loadMoreListener);
        edSearch.addTextChangedListener(textWatcher);
        if(Hawk.get(PreferenceKey.LOGIN_TYPE,2)==2){
            presenter.getCarInfo(page,currentCarTypeId,edSearch.getText().toString(),trantType,"");
        }else {
            presenter.getsjCarInfo(page,currentCarTypeId,edSearch.getText().toString(),trantType,"");
        }
    }

    @Override
    public void initData() {


        carListadapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(carInfoData.size()<=position){
                    return;
                }
                clickPosition=position;
                carListadapter.setmPosition(position);
                Intent intent=new Intent();
                intent.putExtra("data",carInfoData.get(position));
                context.setResult(RESULT_OK, intent);
                context. finish();


            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }


    @Override
    public void getCarModelList(List<CarModelDto> dtos) {


    }

    @Override
    public void getCarInfolList(List<CarInfolDto.carInfoData> dtos) {
         if(page==1){
             carInfoData.clear();
             carListadapter.notifyDataSetChanged();
             if(!TextUtils.isEmpty(edSearch.getText().toString())){
                 carListadapter.setmPosition(10000);
                 llSearch.setVisibility(View.GONE);
             }
         }
        carListadapter.addMoreData(dtos);
    }

    @Override
    public void isLastPage(boolean islastpage) {
        if(islastpage){
            loadMoreListener.isLoading = true;
            carListadapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }else {
            loadMoreListener.isLoading = false;
            carListadapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }
    }

    @Override
    public void stopResh() {
        //context.stopSwipeRefreshLayout(refreshLayout);

    }

    @Override
    public void ishaveBeiDou(boolean stasue) {

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
    /**
     * 监听输入框
     */
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void afterTextChanged(Editable editable) {
            //控制登录按钮是否可点击状态
            if (!TextUtils.isEmpty(edSearch.getText().toString())) {
                llSearch.setVisibility(View.VISIBLE);
                tvSearch.setText(edSearch.getText().toString());
            } else {
                llSearch.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
