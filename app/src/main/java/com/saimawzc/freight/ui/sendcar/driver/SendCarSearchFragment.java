package com.saimawzc.freight.ui.sendcar.driver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.my.SearchCarAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.my.car.MyCarDto;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.presenter.sendcar.ScSearchCarPresenter;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.view.mine.car.SearchCarView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/8/4.
 * <p>
 * 车辆搜索
 */
public class SendCarSearchFragment extends BaseFragment implements SearchCarView {

    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.aaaa)
    LinearLayout aaaa;
    private SearchCarAdapter adapter;
    private ScSearchCarPresenter presenter;
    private List<SearchCarDto> mDatas = new ArrayList<>();


    @OnClick({R.id.tvCannel, R.id.llSearch})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tvCannel:
                context.finish();
                break;
            case R.id.llSearch:
                if (tvSearch.getText().toString().length() < 3) {
                    context.showMessage("搜索车牌不能少于3位");
                    return;
                }
                presenter.getData();
                break;
        }
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_carsearch;
    }

    @Override
    public void initView() {
        mContext = getActivity();
        presenter = new ScSearchCarPresenter(this, mContext);
        edSearch.addTextChangedListener(textWatcher);
        adapter = new SearchCarAdapter(mDatas, mContext, 1);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter.getData();
    }

    @Override
    public void initData() {
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {

            private PopupWindow popupWindow;

            @Override
            public void onItemClick(View view, int position) {
                if (mDatas.size() <= position) {
                    return;
                }
                SearchCarDto searchCarDto = mDatas.get(position);
                if (searchCarDto.getIsBlackList() != null && searchCarDto.getIfDisable() != null) {
                    if (searchCarDto.getIsBlackList() == 1) {
                        View inflate = LayoutInflater.from(mContext).inflate(R.layout.message_send, null, false);
                        TextView messageText = inflate.findViewById(R.id.messageText);
                        messageText.setText("该车辆已被" + searchCarDto.getHzUserName() + "货主加入黑名单，如要继续派车，" +
                                "请联系" + searchCarDto.getHzUserName() + "货主");
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
//                        ToastUtil.ShowMsg(getActivity(),"该车辆已被"+searchCarDto.getHzUserName()+"货主加入黑名单，如要继续派车，" +
//                                "请联系"+searchCarDto.getHzUserName()+"货主",3);
                        return;
                    } else {
                        if (searchCarDto.getIfDisable() == 2) {
//                            ToastUtil.ShowMsg(getActivity(),"该车辆已被我找车平台加入黑名单，如要继续派车，请联系平台客服.",3);
                            View inflate = LayoutInflater.from(mContext).inflate(R.layout.message_send, null, false);
                            TextView messageText = inflate.findViewById(R.id.messageText);
                            messageText.setText("该车辆已被我找车平台加入黑名单，如要继续派车，请联系平台客服.");
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
                        } else {
                            Intent intent = new Intent();
                            intent.putExtra("data", searchCarDto);
                            context.setResult(Activity.RESULT_OK, intent);
                            context.finish();
                        }
                    }
                } else {

                    if (searchCarDto.getIfDisable() == 2) {
//                        ToastUtil.ShowMsg(getActivity(),"该车辆已被我找车平台加入黑名单，如要继续派车，请联系平台客服.",3);
                        View inflate = LayoutInflater.from(mContext).inflate(R.layout.message_send, null, false);
                        TextView messageText = inflate.findViewById(R.id.messageText);
                        messageText.setText("该车辆已被我找车平台加入黑名单，如要继续派车，请联系平台客服.");
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
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("data", searchCarDto);
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
        return ((PersonCenterActivity) getActivity()).getCompanyId();
    }

    @Override
    public void compelete(List<SearchCarDto> carDtoList) {
        tvSearch.setText("");
        llSearch.setVisibility(View.GONE);
        mDatas.clear();
        adapter.addMoreData(carDtoList);
    }


}
