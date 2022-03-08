package com.saimawzc.freight.ui.sendcar.driver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.my.driver.MyDriverAdapter;
import com.saimawzc.freight.adapter.sendcar.ScDriverAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.my.driver.MyDriverDto;
import com.saimawzc.freight.dto.sendcar.ScSearchDriverDto;
import com.saimawzc.freight.presenter.mine.driver.MyDriverPresenter;
import com.saimawzc.freight.presenter.sendcar.ScSearchDriverPresenter;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.view.mine.driver.MyDriverView;
import com.saimawzc.freight.view.sendcar.ScDriverView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.utils.LoadMoreListener;
import com.saimawzc.freight.weight.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/8/1.
 */

public class ScSearchDriverFragment extends BaseFragment implements ScDriverView {

    @BindView(R.id.rv)
    RecyclerView rv;
    private ScDriverAdapter adapter;
    private List<ScSearchDriverDto> mDatas = new ArrayList<>();
    private ScSearchDriverPresenter presenter;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.aaaa)
    LinearLayout aaaa;
    private String id;
    private String companyId = "";
    private  PopupWindow popupWindow;



    @Override
    public int initContentView() {
        return R.layout.fragment_scsearchdriver;
    }

    @Override
    public void initView() {
        mContext = getActivity();
        presenter = new ScSearchDriverPresenter(this, mContext);
        edSearch.addTextChangedListener(textWatcher);
        adapter = new ScDriverAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        id = getArguments().getString("id");
        companyId = ((OrderMainActivity) getActivity()).getCompanyId();
        if (!TextUtils.isEmpty(id)) {
            presenter.getcarList(id, companyId);
        }

    }

    @OnClick({R.id.tvCannel, R.id.llSearch})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tvCannel:
                context.finish();
                break;
            case R.id.llSearch:
                mDatas.clear();
                presenter.getcarList(id,companyId);
                break;
        }

    }

    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDatas.clear();
                presenter.getcarList(id,companyId);


            }
        });

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mDatas.size() <= position) {
                    return;
                }
                ScSearchDriverDto scSearchDriverDto = mDatas.get(position);
                if (scSearchDriverDto.getIsBlackList()!=null&&scSearchDriverDto.getIfDisable()!=null) {
                    if (scSearchDriverDto.getIsBlackList()==1) {
//                        ToastUtil.ShowMsg(getActivity(),"该司机已被"+scSearchDriverDto.getHzUserName()+"货主加入黑名单，如要继续派车，" +
//                                "请联系"+scSearchDriverDto.getHzUserName()+"货主",3);
                        View inflate = LayoutInflater.from(mContext).inflate(R.layout.message_send, null, false);
                        TextView messageText = inflate.findViewById(R.id.messageText);
                        messageText.setText("该司机已被"+scSearchDriverDto.getHzUserName()+"货主加入黑名单，如要继续派车，" +
                                "请联系"+scSearchDriverDto.getHzUserName()+"货主");
                        // PopUpWindow 传入 ContentView
                        popupWindow = new PopupWindow(inflate, 500, ViewGroup.LayoutParams.WRAP_CONTENT);
                        // 设置背景
                        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.scan_code_gray999));
                        // 外部点击事件
                        popupWindow.setOutsideTouchable(true);
                        popupWindow.showAtLocation(aaaa, Gravity.CENTER, 0, 0);
                        new Handler(new Handler.Callback() {
                            @Override
                            public boolean handleMessage(@NonNull Message msg) {
                                popupWindow.dismiss();
                                return false;
                            }
                        }).sendEmptyMessageDelayed(0,3000);
                        return;

                    }else {
                        if (scSearchDriverDto.getIfDisable()==2) {
//                            ToastUtil.ShowMsg(getActivity(),"该司机已被我找车平台加入黑名单，如要继续派车，请联系平台客服.",3);
                            View inflate = LayoutInflater.from(mContext).inflate(R.layout.message_send, null, false);
                            TextView messageText = inflate.findViewById(R.id.messageText);
                            messageText.setText("该司机已被我找车平台加入黑名单，如要继续派车，请联系平台客服.");
                            // PopUpWindow 传入 ContentView
                            popupWindow = new PopupWindow(inflate, 500, ViewGroup.LayoutParams.WRAP_CONTENT);
                            // 设置背景
                            popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.scan_code_gray999));
                            // 外部点击事件
                            popupWindow.setOutsideTouchable(true);
                            popupWindow.showAtLocation(aaaa, Gravity.CENTER, 0, 0);
                            new Handler(new Handler.Callback() {
                                @Override
                                public boolean handleMessage(@NonNull Message msg) {
                                    popupWindow.dismiss();
                                    return false;
                                }
                            }).sendEmptyMessageDelayed(0,3000);
                            return;
                        }else {
                            Intent intent = new Intent();
                            intent.putExtra("data", scSearchDriverDto);
                            context.setResult(Activity.RESULT_OK, intent);
                            context.finish();
                        }
                    }
                }else {
                        if (scSearchDriverDto.getIfDisable()==2) {
//                            ToastUtil.ShowMsg(getActivity(),"该司机已被我找车平台加入黑名单，如要继续派车，请联系平台客服.",3);
                            View inflate = LayoutInflater.from(mContext).inflate(R.layout.message_send, null, false);
                            TextView messageText = inflate.findViewById(R.id.messageText);
                            messageText.setText("该司机已被我找车平台加入黑名单，如要继续派车，请联系平台客服.");
                            // PopUpWindow 传入 ContentView
                            popupWindow = new PopupWindow(inflate, 500, ViewGroup.LayoutParams.WRAP_CONTENT);
                            // 设置背景
                            popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.scan_code_gray999));
                            // 外部点击事件
                            popupWindow.setOutsideTouchable(true);
                            popupWindow.showAtLocation(aaaa, Gravity.CENTER, 0, 0);
                            new Handler(new Handler.Callback() {
                                @Override
                                public boolean handleMessage(@NonNull Message msg) {
                                    popupWindow.dismiss();
                                    return false;
                                }
                            }).sendEmptyMessageDelayed(0,3000);
                            return;
                        }else {
                            Intent intent = new Intent();
                            intent.putExtra("data", scSearchDriverDto);
                            context.setResult(Activity.RESULT_OK, intent);
                            context.finish();
                        }

                }


            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
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
    public void stopRefresh() {
        stopSwipeRefreshLayout(refreshLayout);
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
    public void getDriverList(List<ScSearchDriverDto> driverDtos) {
        adapter.addMoreData(driverDtos);
        llSearch.setVisibility(View.GONE);
    }

    @Override
    public String getPhone() {
        return edSearch.getText().toString();
    }
}
