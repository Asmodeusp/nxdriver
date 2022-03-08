package com.saimawzc.freight.ui.my.carrier;

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
import com.saimawzc.freight.adapter.my.carrier.MyCarrierAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.my.carrier.CarrierPageDto;
import com.saimawzc.freight.dto.my.carrier.MyCarrierDto;
import com.saimawzc.freight.presenter.mine.carrier.MyCarrierPresenter;
import com.saimawzc.freight.view.mine.carrier.MyCarrierView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.utils.LoadMoreListener;
import com.saimawzc.freight.weight.utils.api.mine.MineApi;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.http.Http;
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

public class UnPassCarrierFragment extends BaseFragment implements MyCarrierView, TextWatcher {
    public MineApi mineApi= Http.http.createApi(MineApi.class);
    @BindView(R.id.rv)RecyclerView rv;
    private MyCarrierAdapter adapter;
    private List<MyCarrierDto> mDatas=new ArrayList<>();
    private MyCarrierPresenter presenter;
    private int page=1;
    private LoadMoreListener loadMoreListener;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private int status=2;//1已经添加 2 待确定
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
        edSearch.setHint("请输入手机号或者承运商名称");
        adapter = new MyCarrierAdapter(mDatas, mContext,2);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        mContext=getActivity();
        presenter=new MyCarrierPresenter(this,mContext);

        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                isLoading = false;
                presenter.getcarList(status,page,edSearch.getText().toString());//0未确定 1已拒绝 2已同意

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
                if(mDatas.size()<=position){
                    return;
                }
                dialog = new NormalDialog(mContext).isTitleShow(false)
                        .content("确认该用户的添加申请?")
                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                        .btnNum(2).btnText("同意", "拒绝");
                dialog.setOnBtnClickL(
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if(!context.isDestroy(context)){
                                    dialog.dismiss();
                                }
                                if(mDatas.size()<=0||mDatas.size()<=position){
                                    context.showMessage("数据越界");
                                    return;
                                }
                                try {
                                    isAgreen(mDatas.get(position).getId(),1);
                                }catch (Exception e){

                                }
                            }
                        },
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if(!context.isDestroy(context)){
                                    dialog.dismiss();
                                }
                                if(mDatas.size()<=0||mDatas.size()<=position){
                                    context.showMessage("数据越界");
                                    return;
                                }
                                try {
                                    isAgreen(mDatas.get(position).getId(),3);
                                }catch (Exception E){

                                }

                            }
                        });
                if(dialog!=null){
                    dialog.show();
                }

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
        stopSwipeRefreshLayout(refreshLayout);
    }



    @Override
    public void getMyCarrierList(CarrierPageDto carrierDtos) {
        llSearch.setVisibility(View.GONE);
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
        }
        if(carrierDtos.isLastPage()==false){
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }else {
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }
        adapter.addMoreData(carrierDtos.getList());
    }

    @Override
    public void stopRefresh() {
        stopSwipeRefreshLayout(refreshLayout);

    }

    /**是否同意添加
     *
     * **/
    private void isAgreen(String id, int state){//state 1同意 3拒绝
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("state",state);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        mineApi.sjagrenncys(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                page=1;
                presenter.getcarList(status,page,edSearch.getText().toString());
            }

            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
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
        //控制登录按钮是否可点击状态
        if (!TextUtils.isEmpty(edSearch.getText().toString())) {
            llSearch.setVisibility(View.VISIBLE);
            tvSearch.setText(edSearch.getText().toString());
        } else {
            llSearch.setVisibility(View.GONE);
        }
    }
}
