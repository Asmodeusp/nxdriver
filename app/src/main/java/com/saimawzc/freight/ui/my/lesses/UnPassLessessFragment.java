package com.saimawzc.freight.ui.my.lesses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.my.lessess.MyLessessAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.my.lessess.LessessPageDto;
import com.saimawzc.freight.dto.my.lessess.MyLessessDto;
import com.saimawzc.freight.presenter.mine.lessess.MyLessessPresenter;
import com.saimawzc.freight.view.mine.lessess.MyLessessView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.utils.LoadMoreListener;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;



/**
 * Created by Administrator on 2020/8/1.
 */

public class UnPassLessessFragment extends BaseFragment implements MyLessessView, TextWatcher {
    @BindView(R.id.rv)RecyclerView rv;
    private MyLessessAdapter adapter;
    private List<MyLessessDto> mDatas=new ArrayList<>();
    private int page=1;
    private LoadMoreListener loadMoreListener;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private int status=2;//	integer($int32)1?????? ?????? 2????????? 3??????
    private MyLessessPresenter presenter;
    private NormalDialog dialog;
    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @Override
    public int initContentView() {
        return R.layout.fragment_unpass_car;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        adapter = new MyLessessAdapter(mDatas, mContext,2);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        mContext=getActivity();

        presenter=new MyLessessPresenter(this,mContext);
        setNeedOnBus(true);
        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                isLoading = false;
                presenter.getcarList(status,page,edSearch.getText().toString());//0????????? 1????????? 2?????????
            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter.getcarList(status,page,edSearch.getText().toString());
    }

    @Override
    public void initData() {
        edSearch.addTextChangedListener(this);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getcarList(status,page,edSearch.getText().toString());
            }
        });
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

                if(mDatas==null||mDatas.size()<=0||mDatas.size()<=position){
                    return;
                }
                dialog = new NormalDialog(mContext).isTitleShow(false)
                        .content("???????????????????????????????")
                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                        .btnNum(2).btnText("??????", "??????");

                dialog.setOnBtnClickL(
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if(!context.isDestroy(context)){
                                    dialog.dismiss();
                                }
                                presenter.dealApply(mDatas.get(position).getId(),1+"",mDatas.get(position).getType());

                            }
                        },
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if(!context.isDestroy(context)){
                                    dialog.dismiss();
                                }
                                presenter.dealApply(mDatas.get(position).getId(),3+"",mDatas.get(position).getType());
                            }
                        });
                dialog.show();

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page=1;
                presenter.getcarList(status,page,edSearch.getText().toString());
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
        page=1;
        presenter.getcarList(status,page,edSearch.getText().toString());
        stopSwipeRefreshLayout(refreshLayout);

    }

    @Override
    public void stopRefresh() {
        stopSwipeRefreshLayout(refreshLayout);

    }

    @Override
    public void getLessessList(LessessPageDto lessessDtos) {
        llSearch.setVisibility(View.GONE);
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
        }
        if(lessessDtos.isLastPage()==false){
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }else {
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }
        adapter.addMoreData(lessessDtos.getList());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(dialog!=null){
            if(!context.isDestroy(context)){
                dialog.dismiss();
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        //???????????????????????????????????????
        if (!TextUtils.isEmpty(edSearch.getText().toString())) {
            llSearch.setVisibility(View.VISIBLE);
            tvSearch.setText(edSearch.getText().toString());
        } else {
            llSearch.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshCarLeader(String str) {
        if(!TextUtils.isEmpty(str)){
            if(str.equals(Constants.reshLessess)){
                Log.e("msg","??????"+str);
                page=1;
                presenter.getcarList(status,page,edSearch.getText().toString());
                return;
            }

        }
    }
}
