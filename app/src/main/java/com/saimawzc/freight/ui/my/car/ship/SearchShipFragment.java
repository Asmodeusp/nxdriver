package com.saimawzc.freight.ui.my.car.ship;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.my.SearchShipAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.my.car.ship.SearchShipDto;
import com.saimawzc.freight.presenter.mine.car.SearchShipPresenter;
import com.saimawzc.freight.view.mine.car.ship.SearchShipView;
import com.saimawzc.freight.weight.ClearTextEditText;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
/**
 * Created by Administrator on 2020/8/4.
 * 船舶搜索
 */
public class SearchShipFragment extends BaseFragment implements SearchShipView {
    @BindView(R.id.edsearch)ClearTextEditText edSearch;
    @BindView(R.id.rv)RecyclerView rv;
    @BindView(R.id.llSearch)LinearLayout llSearch;
    @BindView(R.id.tvSearch)TextView tvSearch;
    private SearchShipAdapter adapter;
    private SearchShipPresenter presenter;
    private List<SearchShipDto> mDatas=new ArrayList<>();


   @OnClick({R.id.tvCannel,R.id.llSearch})
   public void click(View view){
       switch (view.getId()){
           case R.id. tvCannel:
               context.finish();
               break;
           case R.id.llSearch:
               if(context.isEmptyStr(edSearch)){
                   context.showMessage("船名不能为空");
                   return;
               }
               presenter.getcarList(edSearch.getText().toString());
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
        presenter=new SearchShipPresenter(this,mContext);
        edSearch.addTextChangedListener(textWatcher);
        edSearch.setHint("请输入船名进行搜索");
        adapter = new SearchShipAdapter(mDatas, mContext);
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
    public void compelete(List<SearchShipDto> carDtoList) {
        tvSearch.setText("");
        llSearch.setVisibility(View.GONE);
        mDatas.clear();
        adapter.addMoreData(carDtoList);
    }
}
