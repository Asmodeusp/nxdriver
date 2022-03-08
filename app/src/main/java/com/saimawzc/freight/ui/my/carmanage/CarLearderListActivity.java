package com.saimawzc.freight.ui.my.carmanage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.freight.adapter.my.MyServiceAdapter;
import com.saimawzc.freight.adapter.my.carleader.CarLeaderListAdapter;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.my.carleader.CarLeaderListDto;
import com.saimawzc.freight.presenter.mine.carleader.CarLeaderListPresenter;
import com.saimawzc.freight.presenter.order.taxi.MyServiceListPresenter;
import com.saimawzc.freight.view.mine.carleader.CarLeaderListView;
import com.saimawzc.freight.weight.utils.LoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.saimawzc.freight.adapter.BaseAdapter.IS_RESH;

/**
 * 车队长管理
 * ***/
public class CarLearderListActivity extends BaseActivity implements CarLeaderListView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cy)
    RecyclerView rv;
    private CarLeaderListPresenter presenter;
    private CarLeaderListAdapter adapter;
    private List<CarLeaderListDto.leaderDto>mDatas=new ArrayList<>();
    private int page=1;
    private LoadMoreListener loadMoreListener;

    @Override
    protected int getViewId() {
        return R.layout.activity_carleader;
    }

    @Override
    protected void init() {
        setNeedOnBus(true);
        setToolbar(toolbar,"车队长管理");
        presenter= new CarLeaderListPresenter(this, CarLearderListActivity.this);
        adapter=new CarLeaderListAdapter(mDatas,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter.getCarLeaderList(page);

        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(IS_RESH==false){
                    page++;
                    isLoading = false;
                    presenter.getCarLeaderList(page);
                    IS_RESH=true;
                }

            }
        };
        rv.setOnScrollListener(loadMoreListener);
    }

    @Override
    protected void initListener() {

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",mDatas.get(position));
                readyGo(CarTeamInfoActivity.class,bundle);

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }
    @OnClick({R.id.tvadd})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvadd:
                readyGo(CreatCarLeaderActivity.class);
                break;
        }
    }


    @Override
    public void getList(CarLeaderListDto carLeaderListDto) {
        if(carLeaderListDto!=null){
            if(page==1){
                mDatas.clear();
            }
            if(carLeaderListDto.isLastPage()){
                loadMoreListener.isLoading = true;
                adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
            }else {
                loadMoreListener.isLoading = false;
                adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
            }
            adapter.addMoreData(carLeaderListDto.getList());
        }
        IS_RESH=false;
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reshCarLeader(String str) {
        Log.e("msg","刷新车队长列表");
        if(!TextUtils.isEmpty(str)){
            if(str.equals(Constants.reshCarLeaderList)){
                page=1;
                presenter.getCarLeaderList(page);
            }
        }
    }
}
