package com.saimawzc.freight.ui.sendcar.driver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.google.gson.Gson;
import com.hdgq.locationlib.LocationOpenApi;
import com.hdgq.locationlib.entity.ShippingNoteInfo;
import com.hdgq.locationlib.listener.OnResultListener;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.LcInfoDto;
import com.saimawzc.freight.dto.ScanCodeDto;
import com.saimawzc.freight.weight.NoData;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.sendcar.WaitExecuteAdpater;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.sendcar.WaitExecuteDto;
import com.saimawzc.freight.presenter.sendcar.WaitExcecutePresenter;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.view.sendcar.WaitExecuteView;
import com.saimawzc.freight.weight.ClearTextEditText;
import com.saimawzc.freight.weight.utils.LoadMoreListener;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.PopupWindowUtil;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.saimawzc.freight.adapter.BaseAdapter.IS_RESH;

/**
 * ?????????
 */
public class SendCarWaitExecuteFragment extends BaseFragment
        implements WaitExecuteView {
    private WaitExecuteAdpater adpater;
    private List<WaitExecuteDto.WaitExecuteData> mDatas = new ArrayList<>();
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private int page = 1;
    private LoadMoreListener loadMoreListener;
    private WaitExcecutePresenter presenter;
    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.tvpopuw)
    TextView tvPopuw;
    @BindView(R.id.llpopuw)
    LinearLayout llpopuw;
    private String queryType = "";
    private String isJump = "";
    private int position = -1;
    @BindView(R.id.nodata)
    NoData noData;
    @BindView(R.id.aaaa)
    LinearLayout aaaa;
    private String lcbh;
    private String dispatchCarNo;
    private String czbm;
    private String lcResult;
    private LinearLayout scanCodeOpenButton;
    private LinearLayout scanCodeCloseButton;
    private PopupWindow popupWindow;
    private String scanButtonType;
    public static final int OPEN_CODE = 110;
    public static final int CLOSE_CODE = 111;
    private String companyId;
    private String dispatchCarId;

    @Override
    public int initContentView() {
        return R.layout.fragment_driver_sendcar_wait;
    }

    @Override
    public void initView() {
        mContext = getActivity();
        adpater = new WaitExecuteAdpater(mDatas, mContext);
        layoutManager = new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adpater);
        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if (!IS_RESH) {
                    page++;
                    presenter.getData(page, queryType, edSearch.getText().toString());
                    IS_RESH = true;
                }

            }
        };
        rv.setOnScrollListener(loadMoreListener);

        presenter = new WaitExcecutePresenter(this, mContext);
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                presenter.getData(page, queryType, edSearch.getText().toString());
            }
        });

        edSearch.addTextChangedListener(textWatcher);
        edSearch.hiddenIco();
        initpermissionChecker();
        setNeedOnBus(true);

    }

    @OnClick({R.id.llSearch, R.id.llpopuw})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.llSearch:
                page = 1;
                presenter.getData(page, queryType, edSearch.getText().toString());
                break;
            case R.id.llpopuw:
                final PopupWindowUtil popupWindowUtil = new PopupWindowUtil.Builder()
                        .setContext(mContext.getApplicationContext()) //?????? context
                        .setContentView(R.layout.dialog_waitsendcar) //??????????????????
                        .setOutSideCancel(true) //????????????????????????
                        .setwidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setFouse(true)
                        .builder()
                        .showAsLaction(llpopuw, Gravity.CENTER, -10, 0);

                popupWindowUtil.setOnClickListener(new int[]{R.id.rlall, R.id.rlpaiche, R.id.rlCarNo,
                        R.id.rlcarrive, R.id.rlfahuodi, R.id.rlmudi, R.id.rlwuliao}, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.rlall://??????
                                tvPopuw.setText("??????");
                                queryType = "1";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlCarNo:
                                tvPopuw.setText("?????????");
                                queryType = "6";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlpaiche://
                                tvPopuw.setText("?????????");
                                queryType = "2";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlcarrive://
                                tvPopuw.setText("?????????");
                                queryType = "3";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlfahuodi://
                                tvPopuw.setText("?????????");
                                queryType = "4";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlmudi:
                                tvPopuw.setText("?????????");
                                queryType = "5";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlwuliao:
                                tvPopuw.setText("????????????");
                                queryType = "7";
                                popupWindowUtil.dismiss();
                                break;
                        }
                    }
                });
                break;
        }

    }

    private NormalDialog dialog;

    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                presenter.getData(page, queryType, edSearch.getText().toString());
            }
        });

        adpater.setOnTabClickListener(new BaseAdapter.OnTabClickListener() {
            @Override
            public void onItemClick(String type, final int position) {
                if (mDatas.size() <= position || mDatas.size() <= 0) {
                    return;
                }
                Bundle bundle;
                if (type.equals("tab1")) {//????????????
                    bundle = new Bundle();
                    bundle.putString("from", "changecar");
                    bundle.putString("companyId", mDatas.get(position).getCompanyId());
                    bundle.putString("dispatchCarId", mDatas.get(position).getId());
                    readyGo(OrderMainActivity.class, bundle);

                } else if (type.equals("tab2")) {//????????????
                    bundle = new Bundle();
                    bundle.putString("from", "changedriver");
                    bundle.putString("id", mDatas.get(position).getCysId());
                    bundle.putString("companyId", mDatas.get(position).getCompanyId());
                    Log.e("companyId", mDatas.get(position).getCompanyId());
                    bundle.putString("dispatchCarId", mDatas.get(position).getId());
                    readyGo(OrderMainActivity.class, bundle);

                } else if (type.equals("tab3")) {//????????????
                    bundle = new Bundle();
                    bundle.putString("from", "maptravel");
                    bundle.putString("id", mDatas.get(position).getId());
                    readyGo(OrderMainActivity.class, bundle);
                } else if (type.equals("tab4")) {//??????

                    if (!RepeatClickUtil.isFastClick()) {
                        context.showMessage("????????????????????????????????????");
                        return;
                    }
                    if (mDatas.get(position).getStatus() == 2) {//????????????
                        bundle = new Bundle();
                        bundle.putString("id", mDatas.get(position).getId());
                        bundle.putString("tranttype", mDatas.get(position).getTranType() + "");
                        bundle.putString("from", "trant");
                        readyGo(OrderMainActivity.class, bundle);
                    } else {//????????????
                        dialog = new NormalDialog(mContext).isTitleShow(false)
                                .content("??????????????????????")
                                .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                                .btnNum(2).btnText("??????", "??????");
                        dialog.setOnBtnClickL(
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        if (!context.isDestroy(context)) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("id", mDatas.get(position).getId());
                                            bundle.putString("tranttype", mDatas.get(position).getTranType() + "");
                                            bundle.putString("from", "trant");
                                            readyGo(OrderMainActivity.class, bundle);
                                            dialog.dismiss();
                                        }
                                    }
                                },
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        presenter.startTask(mDatas.get(position).getId(), position);
                                        if (!context.isDestroy(context)) {
                                            dialog.dismiss();
                                        }
                                    }
                                });
                        dialog.show();
                    }


                } else if (type.equals("scanCodeButton")) {//????????????
                    if (!RepeatClickUtil.isFastClick()) {
                        context.showMessage("????????????????????????????????????");
                        return;
                    }
                    lcbh = mDatas.get(position).getLcbh();
                    dispatchCarNo = mDatas.get(position).getDispatchCarNo();
                    czbm = mDatas.get(position).getCysId();
                    dispatchCarId = mDatas.get(position).getId();
                    lcResult = mDatas.get(position).getLcResult();
                    companyId = mDatas.get(position).getCompanyId();
                    presenter.getLcInfoDto(lcbh, dispatchCarNo, czbm,companyId);

                } else if (type.equals("callphone")) {//?????????
                    String[] PERMISSIONS = new String[]{
                            Manifest.permission.CALL_PHONE
                    };
                    if (permissionChecker.isLackPermissions(PERMISSIONS)) {
                        permissionChecker.requestPermissions();
                        context.showMessage("????????????????????????");
                    } else {
                        context.callPhone(mDatas.get(position).getPhone());
                    }
                }
            }
        });

        adpater.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mDatas.size() <= position) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("from", "sendcardelation");
                bundle.putString("id", mDatas.get(position).getId());
                bundle.putString("type", "trant");
                bundle.putInt("status", mDatas.get(position).getStatus());
                bundle.putString("danhao", mDatas.get(position).getDispatchCarNo());
                bundle.putString("startadress", mDatas.get(position).getFromUserAddress());
                bundle.putString("endadress", mDatas.get(position).getToUserAddress());
                bundle.putSerializable("data", mDatas.get(position));
                bundle.putInt("position", position);
                readyGo(OrderMainActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });


    }

    @Override
    public void isLastPage(boolean islastpage) {
        if (islastpage) {
            loadMoreListener.isLoading = true;
            adpater.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        } else {
            loadMoreListener.isLoading = false;
            adpater.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }
    }

    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void getSendCarList(List<WaitExecuteDto.WaitExecuteData> dtos) {
        if (dtos != null) {
            if (page == 1) {
                mDatas.clear();
                adpater.notifyDataSetChanged();
                if (dtos == null || dtos.size() <= 0) {
                    noData.setVisibility(View.VISIBLE);
                } else {
                    noData.setVisibility(View.GONE);
                }
            }

            llSearch.setVisibility(View.GONE);
            adpater.addMoreData(dtos);
            IS_RESH = false;
//            context.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    startyd(mDatas.get(0).getId(),"1234","140000","140000");
//                }
//            });
        }
        if (!TextUtils.isEmpty(isJump)) {
            if (position == -1) {
                return;
            }
            EventBus.getDefault().post(Constants.reshAuroAdto);

            Bundle bundle = new Bundle();
            bundle.putString("id", mDatas.get(0).getId());
            bundle.putString("tranttype", mDatas.get(0).getTranType() + "");
            bundle.putString("from", "trant");
            readyGo(OrderMainActivity.class, bundle);
        }
    }

    @Override
    public void getLcInfoDto(LcInfoDto lcInfoDto) {
        //??????popupWindow
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.message_send, null, false);
        final TextView messageText = inflate.findViewById(R.id.messageText);

        // PopUpWindow ?????? ContentView
        popupWindow = new PopupWindow(inflate, 200, ViewGroup.LayoutParams.WRAP_CONTENT);
        // ????????????
        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.scan_code_gray999));
        // ??????????????????
        popupWindow.setOutsideTouchable(true);
        //??????PopupWindow
        View scanCodeView = LayoutInflater.from(getContext()).inflate(R.layout.scan_code_diglog, null, false);
        scanCodeOpenButton = scanCodeView.findViewById(R.id.scanCodeOpenButton);
        scanCodeCloseButton = scanCodeView.findViewById(R.id.scanCodeCloseButton);
        popupWindow = new PopupWindow(scanCodeView, 900, 700);
        // ????????????
        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // ??????????????????
        popupWindow.setOutsideTouchable(true);
        if (lcInfoDto != null) {
            if (lcInfoDto.getLczt() != null) {
                if (Integer.parseInt(lcInfoDto.getLczt()) == 1) {
                    if (Integer.parseInt(lcResult) == 0) {
                        scanButtonType = "open";
                        scanCodeOpenButton.setBackground(getResources().getDrawable(R.drawable.scan_code_blue));
                        scanCodeCloseButton.setBackground(getResources().getDrawable(R.drawable.scan_code_gray999));
                        scanCodeCloseButton.setClickable(false);
                        scanCodeOpenButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                QrConfig qrConfig = new QrConfig.Builder()
                                        .setDesText("(?????????)")//??????????????????
                                        .setShowDes(false)//?????????????????????????????????
                                        .setShowLight(true)//?????????????????????
                                        .setShowTitle(true)//??????Title
                                        .setShowAlbum(true)//???????????????????????????
                                        .setCornerColor(Color.WHITE)//?????????????????????
                                        .setLineColor(Color.WHITE)//?????????????????????
                                        .setLineSpeed(QrConfig.LINE_MEDIUM)//?????????????????????
                                        .setScanType(QrConfig.TYPE_ALL)//???????????????????????????????????????????????????????????????????????????????????????
                                        .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE)//????????????????????????????????????????????????????????????????????????
                                        .setCustombarcodeformat(QrConfig.BARCODE_I25)//??????????????????????????????TYPE_CUSTOM????????????
                                        .setPlaySound(true)//?????????????????????bi~?????????
                                        .setDingPath(R.raw.qrcode)//???????????????(?????????????????????Ding~)
                                        .setIsOnlyCenter(true)//???????????????????????????(?????????????????????)
                                        .setTitleText("?????????")//??????Tilte??????
                                        .setTitleBackgroudColor(Color.BLACK)//?????????????????????
                                        .setTitleTextColor(Color.WHITE)//??????Title????????????
                                        .create();
                                QrManager.getInstance().init(qrConfig).startScan(context, new QrManager.OnScanResultCallback() {
                                    @Override
                                    public void onScanSuccess(String result1) {
                                        ScanCodeDto scanCodeDto = new Gson().fromJson(result1, ScanCodeDto.class);
                                        if (scanCodeDto.getStock_code()!=null) {
                                            if (scanCodeDto.getStock_code().equals(lcbh)) {

                                            }else {
                                                messageText.setText("???????????????");
                                                popupWindow.showAtLocation(aaaa, Gravity.CENTER, 0, 0);
                                                new Handler(new Handler.Callback() {
                                                    @Override
                                                    public boolean handleMessage(@NonNull Message msg) {
                                                        popupWindow.dismiss();
                                                        return false;
                                                    }
                                                }).sendEmptyMessageDelayed(0, 3000);
                                            }
                                        }


                                    }
                                });
                            }
                        });
                        popupWindow.showAtLocation(aaaa, Gravity.CENTER, 0, 0);
                    } else if (Integer.parseInt(lcResult) == 1) {
                        scanButtonType = "close";
                        scanCodeOpenButton.setBackground(getResources().getDrawable(R.drawable.scan_code_gray999));
                        scanCodeCloseButton.setBackground(getResources().getDrawable(R.drawable.scan_code_orange));
                        scanCodeOpenButton.setClickable(false);
                        scanCodeCloseButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                QrConfig qrConfig = new QrConfig.Builder()
                                        .setDesText("(?????????)")//??????????????????
                                        .setShowDes(false)//?????????????????????????????????
                                        .setShowLight(true)//?????????????????????
                                        .setShowTitle(true)//??????Title
                                        .setShowAlbum(true)//???????????????????????????
                                        .setCornerColor(Color.WHITE)//?????????????????????
                                        .setLineColor(Color.WHITE)//?????????????????????
                                        .setLineSpeed(QrConfig.LINE_MEDIUM)//?????????????????????
                                        .setScanType(QrConfig.TYPE_ALL)//???????????????????????????????????????????????????????????????????????????????????????
                                        .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE)//????????????????????????????????????????????????????????????????????????
                                        .setCustombarcodeformat(QrConfig.BARCODE_I25)//??????????????????????????????TYPE_CUSTOM????????????
                                        .setPlaySound(true)//?????????????????????bi~?????????
                                        .setDingPath(R.raw.qrcode)//???????????????(?????????????????????Ding~)
                                        .setIsOnlyCenter(true)//???????????????????????????(?????????????????????)
                                        .setTitleText("?????????")//??????Tilte??????
                                        .setTitleBackgroudColor(Color.BLACK)//?????????????????????
                                        .setTitleTextColor(Color.WHITE)//??????Title????????????
                                        .create();
                                QrManager.getInstance().init(qrConfig).startScan(context, new QrManager.OnScanResultCallback() {
                                    @Override
                                    public void onScanSuccess(String result1) {
                                        ScanCodeDto scanCodeDto = new Gson().fromJson(result1, ScanCodeDto.class);
                                        if (scanCodeDto.getStock_code()!=null) {
                                            if (scanCodeDto.getStock_code().equals(lcbh)) {

                                            }else {
                                                messageText.setText("???????????????");
                                                popupWindow.showAtLocation(aaaa, Gravity.CENTER, 0, 0);
                                                new Handler(new Handler.Callback() {
                                                    @Override
                                                    public boolean handleMessage(@NonNull Message msg) {
                                                        popupWindow.dismiss();
                                                        return false;
                                                    }
                                                }).sendEmptyMessageDelayed(0, 3000);
                                            }
                                        }

                                    }
                                });
                            }
                        });
                        popupWindow.showAtLocation(aaaa, Gravity.CENTER, 0, 0);
                    }
                } else if (Integer.parseInt(lcInfoDto.getLczt()) == 2) {
                    messageText.setText("???????????????");
                    popupWindow.showAtLocation(aaaa, Gravity.CENTER, 0, 0);
                    new Handler(new Handler.Callback() {
                        @Override
                        public boolean handleMessage(@NonNull Message msg) {
                            popupWindow.dismiss();
                            return false;
                        }
                    }).sendEmptyMessageDelayed(0, 3000);
                } else if (Integer.parseInt(lcInfoDto.getLczt()) == 3) {
                    messageText.setText("???????????????");
                    popupWindow.showAtLocation(aaaa, Gravity.CENTER, 0, 0);
                    new Handler(new Handler.Callback() {
                        @Override
                        public boolean handleMessage(@NonNull Message msg) {
                            popupWindow.dismiss();
                            return false;
                        }
                    }).sendEmptyMessageDelayed(0, 3000);

                } else if (Integer.parseInt(lcInfoDto.getLczt()) == 4) {
                    messageText.setText("???????????????");
                    popupWindow.showAtLocation(aaaa, Gravity.CENTER, 0, 0);
                    new Handler(new Handler.Callback() {
                        @Override
                        public boolean handleMessage(@NonNull Message msg) {
                            popupWindow.dismiss();
                            return false;
                        }
                    }).sendEmptyMessageDelayed(0, 3000);
                }


            }
        }


    }

    @Override
    public void siloScanLock(LcInfoDto lcInfoDto) {

    }

    @Override
    public void siloScanUnlock(LcInfoDto lcInfoDto) {

    }


    @Override
    public void oncomplete(int pos) {
        if (position >= mDatas.size() - 1) {
            return;
        }
        isJump = "true";
        position = pos;
        page = 1;
        presenter.getData(page, queryType, edSearch.getText().toString());

    }

    @Override
    public BaseActivity getcontect() {
        return context;
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
        if (TextUtils.isEmpty(PreferenceKey.DRIVER_IS_INDENFICATION) || !Hawk.get(PreferenceKey.DRIVER_IS_INDENFICATION, "").equals("1")) {
            if (!str.contains("??????")) {
                context.showMessage(str);
            }
        } else {
            if (mDatas == null || mDatas.size() <= 0) {
                noData.setVisibility(View.VISIBLE);
            } else {
                noData.setVisibility(View.GONE);
            }
            context.showMessage(str);
        }
    }

    @Override
    public void oncomplete() {

    }

    /**
     * ???????????????
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
            //???????????????????????????????????????
            if (!TextUtils.isEmpty(edSearch.getText().toString())) {
                llSearch.setVisibility(View.VISIBLE);
                tvSearch.setText(edSearch.getText().toString());
            } else {
                llSearch.setVisibility(View.GONE);
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshSendWaitExcute(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (str.equals(Constants.reshTrant) ||
                    str.equals(Constants.reshChange)) {
                Log.e("msg", "??????" + str);
                page = 1;
                presenter.getData(page, queryType, edSearch.getText().toString());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isJump = "";
        position = -1;
    }

    private void startyd(String shippingNoteNumber, String serialNumber, String startCountrySubdivisionCode, String endCountrySubdivisionCode) {
        ShippingNoteInfo d = new ShippingNoteInfo();
        d.setShippingNoteNumber(shippingNoteNumber);
        d.setSerialNumber(serialNumber);
        d.setStartCountrySubdivisionCode(startCountrySubdivisionCode);
        d.setEndCountrySubdivisionCode(endCountrySubdivisionCode);
        ShippingNoteInfo[] shippingNoteInfos = new ShippingNoteInfo[]{d};
        LocationOpenApi.start(context, shippingNoteInfos,
                new OnResultListener() {
                    @Override
                    public void onSuccess() {
                        Log.e("msg", "????????????");

                    }

                    @Override
                    public void onFailure(String s, String s1) {
                        Log.e("msg", "s=" + s + "s1=" + s1);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onPause() {
        super.onPause();
        isJump = "";
        position = -1;
    }
}
