package com.saimawzc.freight.ui.my.pubandservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.order.GoodsHzListAdapter;
import com.saimawzc.freight.adapter.order.TaxiAdressAdpater;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.login.TaxiAreaDto;
import com.saimawzc.freight.presenter.order.taxi.TaxiChooseAdressPresenter;
import com.saimawzc.freight.view.order.taxi.TaxiAdressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChooseTaxiAdressActivity extends BaseActivity implements TaxiAdressView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvProcice)
    TextView tvPrice;
    @BindView(R.id.tvCity)
    TextView tvCity;
    @BindView(R.id.cy)
    RecyclerView rv;
    private TaxiChooseAdressPresenter presenter;
    private String prociceId=0+"";
    private String cityId;
    private TaxiAdressAdpater adressAdpater;
    private List<TaxiAreaDto>mDatas=new ArrayList<>();

    private boolean currentPro=true;

    @Override
    protected int getViewId() {
        return R.layout.activity_choose_taxiadress;
    }

    @Override
    protected void init() {
        setToolbar(toolbar,"选择地址");
        presenter=new TaxiChooseAdressPresenter(this,this);
        adressAdpater=new TaxiAdressAdpater(mDatas,mContext,prociceId,cityId);
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adressAdpater);
        presenter.getAdress(prociceId);

    }

    @Override
    protected void initListener() {
        adressAdpater.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(currentPro){//选择省份
                    currentPro=false;
                    prociceId=mDatas.get(position).getId();
                    tvPrice.setText(mDatas.get(position).getName());
                    tvPrice.setTextColor(getResources().getColor(R.color.red));
                    tvCity.setTextColor(getResources().getColor(R.color.color_black));
                    presenter.getAdress(mDatas.get(position).getId());
                    tvCity.setText("");
                    cityId="";
                }else {//选择城市
                    tvCity.setText(mDatas.get(position).getName());
                    cityId=mDatas.get(position).getId();
                    Intent intent=new Intent();
                    intent.putExtra("data",mDatas.get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        tvPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPro=true;
                tvPrice.setTextColor(getResources().getColor(R.color.red));
                tvCity.setTextColor(getResources().getColor(R.color.color_black));
                presenter.getAdress(0+"");

            }
        });
        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPro=false;
                tvPrice.setTextColor(getResources().getColor(R.color.color_black));
                tvCity.setTextColor(getResources().getColor(R.color.red));
                presenter.getAdress(prociceId+"");
            }
        });

    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    @Override
    public void getadressList(List<TaxiAreaDto> dtos) {
        if(dtos!=null){
            if(dtos.size()>0){
                if(prociceId.equals("0")){
                    tvPrice.setText(dtos.get(0).getName());
                    tvPrice.setTextColor(getResources().getColor(R.color.red));
                    tvCity.setTextColor(getResources().getColor(R.color.color_black));
                    prociceId=dtos.get(0).getId();
                }
            }
            mDatas.clear();
            adressAdpater.addMoreData(dtos);
            adressAdpater.setDatas(prociceId,cityId);
//            if(currentPro){
//                tvPrice.setText(adressAdpater.getChooseName());
//            }else {
//                tvCity.setText(adressAdpater.getChooseName());
//            }
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

    }
}
