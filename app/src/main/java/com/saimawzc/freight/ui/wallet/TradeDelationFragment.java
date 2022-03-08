package com.saimawzc.freight.ui.wallet;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.wallet.TradeDelationAdapter;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.base.TimeChooseListener;
import com.saimawzc.freight.dto.wallet.TradeChooseDto;
import com.saimawzc.freight.dto.wallet.TradeDelationDto;
import com.saimawzc.freight.presenter.wallet.TradeDelationPresenter;
import com.saimawzc.freight.view.wallet.TradeDelationView;
import com.saimawzc.freight.weight.MyDrawerLayout;
import com.saimawzc.freight.weight.MyRadioGroup;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.LoadMoreListener;
import com.saimawzc.freight.weight.utils.TimeChooseDialogUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

import static com.saimawzc.freight.adapter.BaseAdapter.IS_RESH;
/***
 * 交易明细
 * **/
public class TradeDelationFragment extends BaseFragment
        implements TradeDelationView , RadioGroup.OnCheckedChangeListener, TextWatcher {
    @BindView(R.id.draw) MyDrawerLayout drawerLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rv) RecyclerView rv;
    private TradeDelationAdapter tradeadpater;
    private List<TradeDelationDto.data>tradeChooseDtos=new ArrayList<>();
    @BindView(R.id.tvtoday) TextView tvToday;
    @BindView(R.id.tvlinetoday)TextView tvLineToday;
    @BindView(R.id.tvsevenday) TextView tvSevenay;
    @BindView(R.id.tvlinesave)TextView tvLineSeven;
    @BindView(R.id.tvmonth) TextView tvMonth;
    @BindView(R.id.tvlinemonth)TextView tvLineMonth;
    @BindView(R.id.tvmore) TextView tvMore;
    @BindView(R.id.tvlinemore)TextView tvLineMore;
    private TradeDelationPresenter presenter;
    private int page=1;
    private LoadMoreListener loadMoreListener;
    private TradeChooseDto chooseDto;
    private String fundAcc="";
    //更多筛选
    @BindView(R.id.grouptime) RadioGroup groupTime;
    @BindView(R.id.tvquerytime)TextView tvQueryTime;
    @BindView(R.id.lltime) LinearLayout llTime;
    @BindView(R.id.tvStartTime)TextView tvStartTime;
    @BindView(R.id.tvEndTime)TextView tvEndTime;
    private TimeChooseDialogUtil timeChooseDialogUtil;
    @BindView(R.id.edminlimit) EditText edMinLimit;
    @BindView(R.id.edmaxlimit)EditText edMaxLimit;
    @BindView(R.id.groupLimit)RadioGroup groupLimit;
    //@BindView(R.id.groupszone)RadioGroup groupSzOne;
    @BindView(R.id.mygroup) MyRadioGroup myRadioGroup;
    @BindView(R.id.groupsort)RadioGroup groupSort;


    @Override
    public int initContentView() {
        return R.layout.fragment_walletdealtion;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"交易明细");
        fundAcc=getArguments().getString("fundAcc");
        layoutManager=new LinearLayoutManager(mContext);
        tradeadpater=new TradeDelationAdapter(tradeChooseDtos,mContext,fundAcc);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(tradeadpater);
        groupTime.setOnCheckedChangeListener(this);
        groupLimit.setOnCheckedChangeListener(this);
        groupSort.setOnCheckedChangeListener(this);
        edMinLimit.addTextChangedListener(this);
        edMaxLimit.addTextChangedListener(this);
        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(!IS_RESH){
                    IS_RESH=true;
                    page++;
                    presenter.getList(page,chooseDto);
                }
            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter=new TradeDelationPresenter(this,mContext);
        chooseDto=new TradeChooseDto();
        chooseDto.setStartTime(BaseActivity.getCurrentTime("yyyy-MM-dd")+" 00:00:00");
        chooseDto.setEndTime(BaseActivity.getCurrentTime("yyyy-MM-dd")+" 23:59:59");
        presenter.getList(page,chooseDto);
        //initLine(0);

    }
    @Override
    public void initData() {

        myRadioGroup.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                if(chooseDto==null){
                    chooseDto=new TradeChooseDto();
                }
                switch (checkedId){
                    case R.id.radio_sz_all:
                        chooseDto.setTransType("");
                        break;
                    case R.id.radio_sz_into:
                        chooseDto.setTransType("1");
                        break;
                    case R.id.radio_sz_zc:
                        chooseDto.setTransType("2");
                        break;
                    case R.id.radio_cz:
                        chooseDto.setTransType("3");
                        break;
                    case R.id.radio_sz_tx:
                        chooseDto.setTransType("4");
                        break;
                }
            }
        });
    }


    @Override
    public void getTradeList(TradeDelationDto delationDto) {
        if(delationDto!=null){

            if(page==1){
                tradeChooseDtos.clear();
                tradeadpater.notifyDataSetChanged();
            }
            tradeadpater.addMoreData(delationDto.getList());
            IS_RESH=false;
        }
    }

    @Override
    public void isLastPage(boolean isLastPage) {
        if(isLastPage==false){
            loadMoreListener.isLoading = false;
            tradeadpater.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }else {
            loadMoreListener.isLoading = true;
            tradeadpater.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }
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
    @OnClick({R.id.tvtoday,R.id.tvsevenday,R.id.tvmonth,R.id.tvmore,
            R.id.ingQuit,R.id.tvStartTime,R.id.tvEndTime,R.id.tvOrder,R.id.tvrest})
    public void click(View view){
        if(chooseDto==null){
            chooseDto=new TradeChooseDto();
        }
        switch (view.getId()){
            case R.id.tvrest:
                clear();
                break;
            case R.id.tvOrder:
                if(!context.isEmptyStr(tvStartTime)||!context.isEmptyStr(tvEndTime)){
                    if(context.isEmptyStr(tvStartTime)&&context.isEmptyStr(tvEndTime)){
                        context.showMessage("请选择完整的筛选日期");
                        return;
                    }
                }
                if(TextUtils.isEmpty(timeType)){
                    chooseDto.setStartTime("");
                    chooseDto.setEndTime("");
                }
                Log.e("msg",chooseDto.toString());
                page=1;
                presenter.getList(page,chooseDto);
                drawerLayout.closeDrawer(Gravity.END);
                break;

            case R.id.tvtoday:
                initLine(0);
                chooseDto.setStartTime(BaseActivity.getCurrentTime("yyyy-MM-dd")+" 00:00:00");
                chooseDto.setEndTime(BaseActivity.getCurrentTime("yyyy-MM-dd")+" 23:59:59");
                page=1;
                presenter.getList(page,chooseDto);
                break;
            case R.id.tvsevenday:
                initLine(1);
                chooseDto.setEndTime(BaseActivity.getCurrentTime("yyyy-MM-dd")+"  23:59:59");
                chooseDto.setStartTime(BaseActivity.delayWeek("yyyy-MM-dd")+" 00:00:00");
                page=1;
                presenter.getList(page,chooseDto);
                break;
            case R.id.tvmonth:
                initLine(2);
                chooseDto.setEndTime(BaseActivity.getCurrentTime("yyyy-MM-dd")+" 23:59:59");
                chooseDto.setStartTime(BaseActivity.delayMonth("yyyy-MM-dd",1)+" 00:00:00");
                page=1;
                presenter.getList(page,chooseDto);
                break;
            case R.id.tvmore:
                initLine(3);
                drawerLayout.openDrawer(Gravity.END);
                break;
            case R.id.ingQuit:
                drawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.tvStartTime:
                if(timeChooseDialogUtil==null){
                    timeChooseDialogUtil=new TimeChooseDialogUtil(mContext);
                }
                timeChooseDialogUtil.showDialog(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        tvStartTime.setText(result);
                        chooseDto.setStartTime(tvStartTime.getText().toString()+" 00:00:00");
                        if(!context.isEmptyStr(tvStartTime)&&!context.isEmptyStr(tvEndTime)){
                            tvQueryTime.setText(tvStartTime.getText().toString()+"~"+tvEndTime.getText().toString());
                        }
                    }
                    @Override
                    public void cancel() {
                        timeChooseDialogUtil.dissDialog();
                    }
                });
                break;
            case R.id.tvEndTime:
                if(timeChooseDialogUtil==null){
                    timeChooseDialogUtil=new TimeChooseDialogUtil(mContext);
                }
                timeChooseDialogUtil.showDialog(new TimeChooseListener() {
                    @Override
                    public void getTime(String result) {
                        tvEndTime.setText(result);
                        chooseDto.setEndTime(tvEndTime.getText().toString()+" 23:59:59");
                        if(!context.isEmptyStr(tvStartTime)&&!context.isEmptyStr(tvEndTime)){
                            tvQueryTime.setText(tvStartTime.getText().toString()+"~"+tvEndTime.getText().toString());

                        }
                    }
                    @Override
                    public void cancel() {
                        timeChooseDialogUtil.dissDialog();
                    }
                });
                break;
        }
    }

    private void initLine(int position){
        if(position==0){
            timeType="";
            chooseDto=new TradeChooseDto();
            clear();
            setTextLight(tvToday,tvLineToday,true);
            setTextLight(tvSevenay,tvLineSeven,false);
            setTextLight(tvMonth,tvLineMonth,false);
            setTextLight(tvMore,tvLineMore,false);
        }else if(position==1){
            timeType="";
            chooseDto=new TradeChooseDto();
            clear();
            setTextLight(tvToday,tvLineToday,false);
            setTextLight(tvSevenay,tvLineSeven,true);
            setTextLight(tvMonth,tvLineMonth,false);
            setTextLight(tvMore,tvLineMore,false);
        }else if(position==2){
            timeType="";
            chooseDto=new TradeChooseDto();
            clear();
            setTextLight(tvToday,tvLineToday,false);
            setTextLight(tvSevenay,tvLineSeven,false);
            setTextLight(tvMonth,tvLineMonth,true);
            setTextLight(tvMore,tvLineMore,false);
        }else if(position==3){
            setTextLight(tvToday,tvLineToday,false);
            setTextLight(tvSevenay,tvLineSeven,false);
            setTextLight(tvMonth,tvLineMonth,false);
            setTextLight(tvMore,tvLineMore,true);
        }
    }
    private void setTextLight(TextView tv,TextView tvLine,boolean isLight){
        if(isLight){
            tv.setTextColor(mContext.getResources().getColor(R.color.color_black));
            tvLine.setVisibility(View.VISIBLE);
        }else {
            tv.setTextColor(mContext.getResources().getColor(R.color.gray666));
            tvLine.setVisibility(View.GONE);
        }
    }
    private String timeType="";
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if(chooseDto==null){
            chooseDto=new TradeChooseDto();
        }
        if(radioGroup == groupTime){
            timeType="choosetime";
            switch (checkedId){
                case R.id.group_threemonth:
                    llTime.setVisibility(View.GONE);
                    tvStartTime.setText("");
                    tvEndTime.setText("");
                    tvQueryTime.setText(BaseActivity.delayMonth("yyyy-MM-dd",3)+"~"+BaseActivity.getCurrentTime("yyyy-MM-dd"));
                    chooseDto.setEndTime(BaseActivity.getCurrentTime("yyyy-MM-dd")+" 23:59:59");
                    chooseDto.setStartTime(BaseActivity.delayMonth("yyyy-MM-dd",3)+" 00:00:00");
                    break;
                case R.id.group_sixmonth:
                    tvStartTime.setText("");
                    tvEndTime.setText("");
                    llTime.setVisibility(View.GONE);
                    tvQueryTime.setText(BaseActivity.delayMonth("yyyy-MM-dd",6)+"~"+BaseActivity.getCurrentTime("yyyy-MM-dd"));
                    chooseDto.setEndTime(BaseActivity.getCurrentTime("yyyy-MM-dd")+" 23:59:59");
                    chooseDto.setStartTime(BaseActivity.delayMonth("yyyy-MM-dd",6)+" 00:00:00");
                    break;
                case R.id.group_costom:
                    llTime.setVisibility(View.VISIBLE);
                    break;

            }
        }else if(radioGroup == groupLimit){
            switch (checkedId){
                case R.id.rb_1000:
                    chooseDto.setMaxMoney(1000+"");
                    chooseDto.setMinMoney(0+"");
                    edMaxLimit.setText("");
                    edMinLimit.setText("");
                    break;
                case R.id.rb_5000:
                    chooseDto.setMinMoney("1000");
                    chooseDto.setMaxMoney(5000+"");
                    edMaxLimit.setText("");
                    edMinLimit.setText("");
                    break;
                case R.id.rb10000:
                    chooseDto.setMinMoney("5000");
                    chooseDto.setMaxMoney(10000+"");
                    edMaxLimit.setText("");
                    edMinLimit.setText("");
                    break;
                case R.id.rbmorethanwan:
                    chooseDto.setMinMoney("10000");
                    chooseDto.setMaxMoney("");
                    edMaxLimit.setText("");
                    edMinLimit.setText("");
                    break;


            }


        } else if(radioGroup==groupSort){
            switch (checkedId){
                case R.id.sort_desc:
                    chooseDto.setSortType("1");
                    break;
                case R.id.sort_asc:
                    chooseDto.setSortType("2");
                    break;
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
        if(chooseDto==null){
            chooseDto=new TradeChooseDto();
        }
        if(!TextUtils.isEmpty(edMinLimit.getText().toString())){
            if(!edMinLimit.getText().toString().equals(".")){
                chooseDto.setMinMoney(edMinLimit.getText().toString());
                groupLimit.check(0);
            }
        }
        if(!TextUtils.isEmpty(edMaxLimit.getText().toString())){
            if(!edMaxLimit.getText().toString().equals(".")){
                chooseDto.setMaxMoney(edMaxLimit.getText().toString());
                groupLimit.check(0);
            }
        }
    }
    int k=0;
    private void clear(){
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(chooseDto==null){
                        chooseDto=new TradeChooseDto();
                    }
                    chooseDto.setStartTime("");
                    chooseDto.setEndTime("");
                    chooseDto.setMinMoney("");
                    chooseDto.setMaxMoney("");
                    chooseDto.setTransType("");
                    chooseDto.setSortType("");
                    tvQueryTime.setText("查询时间");
                    edMinLimit.setText("");
                    edMaxLimit.setText("");
                    tvStartTime.setText("");
                    tvEndTime.setText("");
                    groupTime.check(-1);
                    groupLimit.check(-1);
                    groupSort.check(-1);
                    myRadioGroup.clear();
                    if(!chooseDto.isEmpty()){
                        k++;
                        if(k<=3){
                            clear();
                        }
                    }else {
                        k=0;
                    }
                }
            });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(tradeadpater!=null){
            tradeadpater=null;
        }

    }
}

