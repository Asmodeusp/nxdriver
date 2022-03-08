package com.saimawzc.freight.adapter.my;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.FooterHolder;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.dto.taxi.service.MyServiceListDto;
import com.saimawzc.freight.dto.taxi.service.ServiceData;
import com.saimawzc.freight.ui.login.LoginActivity;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import com.saimawzc.freight.weight.utils.api.mine.MineApi;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.http.Http;
import com.saimawzc.freight.weight.utils.http.JsonUtil;
import com.saimawzc.freight.weight.utils.http.RequestHeaderInterceptor;
import com.saimawzc.freight.weight.utils.http.RequestLogInterceptor;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import static com.saimawzc.freight.constants.Constants.baseSwUrl;

/**
 * Created by Administrator on 2020/8/4.
 * 我的服务方适配器
 */

public class MyServiceAdapter extends BaseAdapter {
    private List<MyServiceListDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private int type=0;
    private NormalDialog dialog;
    public MineApi mineApi= Http.http.createApi(MineApi.class);
    public MyServiceAdapter(List<MyServiceListDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }
    public MyServiceAdapter(List<MyServiceListDto> mDatas, Context mContext, int type) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
        this.type=type;
    }
    public void setData(List<MyServiceListDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<MyServiceListDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<MyServiceListDto> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        }
        else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_my_service, parent,
                    false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            return new FooterHolder(mInflater.inflate(R.layout.layout_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            final MyServiceListDto searchCarDto=mDatas.get(position);
            ImageLoadUtil.displayImage(mContext,searchCarDto.getCyczpfjBos(),((ViewHolder) holder).imageView);
            ((ViewHolder) holder).tvName.setText(searchCarDto.getFwfxm());
            ((ViewHolder) holder).carNo.setText(searchCarDto.getCycph());
            if(searchCarDto.getIsCanDelete()==1){
              ((ViewHolder) holder).rlUndind.setVisibility(View.VISIBLE);
            }else {
                ((ViewHolder) holder).rlUndind.setVisibility(View.GONE);
            }

            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemLongClick(holder.itemView, position);
                        return false;
                    }
                });

                ((ViewHolder) holder).rlUndind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog = new NormalDialog(mContext).isTitleShow(false)
                                .content("确定删除吗?")
                                .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                                .btnNum(2).btnText("取消", "确定");
                        dialog.setOnBtnClickL(
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        dialog.dismiss();
                                    }
                                },
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        dialog.dismiss();
                                        unbindCarRelation(searchCarDto.getId());
                                    }
                                });
                        dialog.show();

                    }
                });

            }
        }if (holder instanceof FooterHolder) {

            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    ((FooterHolder) holder).tvFooter.setVisibility(View.VISIBLE);
                    ((FooterHolder) holder).tvFooter.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    ((FooterHolder) holder).tvFooter.setVisibility(View.VISIBLE);
                    ((FooterHolder) holder).tvFooter.setText("正在加载数据...");
                    break;
                case LOADING_FINISH:
                    ((FooterHolder) holder).tvFooter.setVisibility(View.VISIBLE);
                    ((FooterHolder) holder).tvFooter.setText("没有更多了~");
                    break;
                default:
                    //  ((FooterHolder) holder).tvFooter.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() == 0 ? 0 : mDatas.size()+1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @BindView(R.id.imgView)ImageView imageView;
        @BindView(R.id.tvName)TextView tvName;
        @BindView(R.id.carNo)TextView carNo;
        @BindView(R.id.rlunBind)
        RelativeLayout rlUndind;

    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


    private void unbindCarRelation(String id){
        activity.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        String url=baseSwUrl+"api/deleteTaxationBaseCz";
        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new RequestLogInterceptor())
                .addInterceptor(new RequestHeaderInterceptor())
                .hostnameVerifier(new Http.TrustAllHostnameVerifier())//信任证书
                .retryOnConnectionFailure(true)//重连
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.dismissLoadingDialog();
                        Toast.makeText(activity,e.getMessage(),Toast.LENGTH_LONG);
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                activity.dismissLoadingDialog();
                if(response.isSuccessful()){
                    EventBus.getDefault().post(Constants.reshService);
                }else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.showMessage("请求数据失败");
                        }
                    });

                }
            }
        });
    }
}
