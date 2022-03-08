package com.saimawzc.freight.ui.my.car;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.my.SearchCarAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.presenter.mine.car.SearchCarPresenter;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.view.mine.car.SearchCarView;
import com.saimawzc.freight.weight.ClearTextEditText;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/8/4.
 *
 * 车辆搜索
 */

public class SearchCarFragment extends BaseFragment implements SearchCarView{

    @BindView(R.id.edsearch)ClearTextEditText edSearch;
    @BindView(R.id.rv)RecyclerView rv;
    @BindView(R.id.llSearch)LinearLayout llSearch;
    @BindView(R.id.tvSearch)TextView tvSearch;
    private SearchCarAdapter adapter;
    private SearchCarPresenter presenter;
    private List<SearchCarDto> mDatas=new ArrayList<>();


   @OnClick({R.id.tvCannel,R.id.llSearch})
   public void click(View view){
       switch (view.getId()){
           case R.id. tvCannel:
               context.finish();
               break;
           case R.id.llSearch:
               if(tvSearch.getText().toString().length()<3){
                   context.showMessage("搜索车牌不能少于3位");
                   return;
               }
               llSearch.setVisibility(View.GONE);
               presenter.getcarList();
               break;
       }
   }

    @Override
    public int initContentView() {
        return R.layout.fragment_carsearch;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        presenter=new SearchCarPresenter(this,mContext);
        edSearch.addTextChangedListener(textWatcher);
        adapter = new SearchCarAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }
    @Override
    public void initData() {
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

    @Override
    public String getCarNum() {
        return tvSearch.getText().toString();
    }

    @Override
    public String getCompanyId() {
        return "";
    }

    @Override
    public void compelete(List<SearchCarDto> carDtoList) {
        tvSearch.setText("");
         llSearch.setVisibility(View.GONE);
         mDatas.clear();
        adapter.addMoreData(carDtoList);
    }


}
