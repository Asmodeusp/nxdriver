package com.saimawzc.freight.ui.my.driver;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.my.driver.SearchDrivierAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.my.driver.SearchDrivierDto;
import com.saimawzc.freight.presenter.mine.driver.SearchDriverPresenter;
import com.saimawzc.freight.view.mine.driver.SearchDriverView;
import com.saimawzc.freight.weight.ClearTextEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/8/4.
 * 司机搜索
 */

public class SearchDriviFragment extends BaseFragment  implements SearchDriverView {

    @BindView(R.id.edsearch) ClearTextEditText edSearch;
    @BindView(R.id.rv)RecyclerView rv;
    @BindView(R.id.llSearch)LinearLayout llSearch;
    private SearchDrivierAdapter adapter;
    private List<SearchDrivierDto> mDatas=new ArrayList<>();
    @BindView(R.id.tvSearch)TextView tvSearch;
    private SearchDriverPresenter presenter;

   @OnClick({R.id.tvCannel,R.id.llSearch})
   public void click(View view){
       switch (view.getId()){
           case R.id. tvCannel:
               context.finish();
               break;
           case R.id.llSearch:
               if(tvSearch.getText().toString().length()!=11){
                   context.showMessage("请输入正确的手机号");
                   return;
               }
               presenter.getcarList(tvSearch.getText().toString());

               break;
       }
   }
    @Override
    public int initContentView() {
        return R.layout.fragment_adddriver;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        presenter=new SearchDriverPresenter(this,mContext);
        edSearch.addTextChangedListener(textWatcher);
        adapter = new SearchDrivierAdapter(mDatas, mContext);
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
    public void compelete(SearchDrivierDto driverDtos) {
        tvSearch.setText("");
        llSearch.setVisibility(View.GONE);
        mDatas.clear();
        if(driverDtos!=null){
            mDatas.add(driverDtos);
            adapter.notifyDataSetChanged();
        }else {
            context.showMessage("未搜索到该用户");
        }

    }
}
